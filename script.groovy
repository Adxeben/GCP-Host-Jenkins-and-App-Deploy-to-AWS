def increaseVersion(){
    echo "incrementing app version..."
    sh "mvn build-helper:parse-version versions:set \
        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
        versions:commit"
    def matcher = readFile("pom.xml") =~ "<version>(.+)</version>"
    def version = matcher[0][1]
    env.IMAGE_TAG = "$version-$BUILD_NUMBER"
}

def buildJar() {
    echo "building and testing the application..."
    echo "executing pipeline for $BRANCH_NAME branch"
    sh "mvn clean package"
} 


def createImage() {
    echo "creating the docker image..."
    sh "docker build -t sunesis003/app-jenkins:${IMAGE_TAG} ."
} 


def publishImage() {
    echo "publishing to docker image to docker hub repo..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push sunesis003/app-jenkins:${IMAGE_TAG}"
    }
} 


def deployApp() {
    echo "deploying the application to AWS EC2 Instance..."
} 


def commitVersionGit() {
    echo "commiting app version increment to remote gitgit repo..."
    withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'GITHUB_USER', passwordVariable: 'GITHUB_PASS')]){

        sh "git config --global user.email 'jasonsunesis@gmail.com'"
        sh "git config --global user.name 'omotola' "

        sh "git status"
        sh "git branch"
        sh "git config --list"

        sh "git remote set-url origin https://${GITHUB_USER}:${GITHUB_PASS}@github.com/Adxeben/Jenkins-deploy-AWS.git"
        sh "git add ."
        sh "git commit -m 'version increment commit to git'"
        sh "git push origin HEAD:main"
    }   
} 

return this