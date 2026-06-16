def increaseVersion(){
    echo "incrementing app version..."
    sh "mvn build-helper:parse-version versions:set \
        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
        versions:commit"
    def matcher = readFile("pom.xml") =~ "<version>(.+)</version>"
    def version = matcher[0][1]
    env.IMAGE_NAME = "sunesis003/app-jenkins:$version-$BUILD_NUMBER"
}

// def buildJar() {
//     echo "building and testing the java application..."
//     echo "executing pipeline for $BRANCH_NAME branch"
//     sh "mvn clean package"
// } 


// def createImage() {
//     echo "creating the docker image..."
//     sh "docker build -t sunesis003/app-jenkins:${IMAGE_TAG} ."
// } 


// def publishImage() {
//     echo "publishing to docker image to docker hub repo..."
//     withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
//         sh "echo $PASS | docker login -u $USER --password-stdin"
//         sh "docker push sunesis003/app-jenkins:${IMAGE_TAG}"
//     }
// } 


def deployApp() {
    echo "deploying the application to AWS EC2 Instance..."
    
    def ec2Instance = "ubuntu@16.16.139.21"
    def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME}"

    sshagent(['aws-ubuntu-server-key']) {
        // copy files to server (shell script and docker-compose file)
        sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ubuntu"
        sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ubuntu"

        // SSH EXECUTION (ssh into server and execute script)
        sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
    }   
} 


def commitVersionGit() {
    echo "commiting app version increment to remote git repo..."
    withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'GITHUB_USER', passwordVariable: 'GITHUB_PASS')]){
        sh "git remote set-url origin https://${GITHUB_USER}:${GITHUB_PASS}@github.com/Adxeben/Jenkins-deploy-AWS.git"
        sh "git add ."
        sh "git commit -m 'version increment commit to git'"
        sh "git push origin HEAD:main"

    }   
} 

return this