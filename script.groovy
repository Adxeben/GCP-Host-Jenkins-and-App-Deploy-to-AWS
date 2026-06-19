def increaseVersion(){
    echo "incrementing app version..."
    sh "mvn build-helper:parse-version versions:set \
        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
        versions:commit"
    def matcher = readFile("pom.xml") =~ "<version>(.+)</version>"
    def version = matcher[0][1]
    env.IMAGE_NAME = "sunesis003/mydocker-app:$version-$BUILD_NUMBER"
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
    
    def ec2Instance = "ubuntu@13.48.49.115"
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
    echo "Committing app version increment to remote git repo..."
    withCredentials([usernamePassword(credentialsId: 'github-credentials',
                                       usernameVariable: 'GITHUB_USER',
                                       passwordVariable: 'GITHUB_PASS')]) {
        sh """
            # Set up GIT_ASKPASS to supply the token when Git asks for a password
            export GIT_ASKPASS=\$(mktemp)
            echo 'echo \$GITHUB_PASS' > \$GIT_ASKPASS
            chmod +x \$GIT_ASKPASS

            # Set remote URL with only the username (password is supplied via ASKPASS)
            git remote set-url origin https://${GITHUB_USER}@github.com/Adxeben/Jenkins-deploy-AWS.git

            # Fetch the latest from remote
            git fetch origin

            # Switch to the main branch (or create it if it doesn't exist locally)
            git checkout main || git checkout -b main

            # Rebase local main onto remote main to incorporate upstream changes
            # (Use --rebase to avoid merge commits; if you prefer merge, use git pull --no-rebase)
            git pull --rebase origin main

            # Now add and commit changes (only if there are changes to commit)
            git add .
            if ! git diff --cached --quiet; then
                git commit -m 'version increment commit to git'
                # Push the updated main branch
                git push origin main
            else
                echo "No changes to commit, skipping push."
            fi

            # Clean up the temporary ASKPASS script
            rm -f \$GIT_ASKPASS
        """
    }
}
// URL Encode the Password 
// import java.net.URLEncoder

// def commitVersionGit() {
//     echo "committing app version increment to remote git repo..."
//     withCredentials([usernamePassword(credentialsId: 'github-credentials',
//                                        usernameVariable: 'GITHUB_USER',
//                                        passwordVariable: 'GITHUB_PASS')]) {
        
//         // Encode special characters in both user and pass
//         def encodedUser = URLEncoder.encode(GITHUB_USER, "UTF-8")
//         def encodedPass = URLEncoder.encode(GITHUB_PASS, "UTF-8")
//         def remoteUrl = "https://${encodedUser}:${encodedPass}@github.com/Adxeben/Jenkins-deploy-AWS.git"
        
//         // Wrap the URL in single quotes inside the shell to protect against shell expansion
//         sh """
//             git remote set-url origin '${remoteUrl}'
//             git add .
//             git commit -m 'version increment commit to git'
//             git push origin HEAD:main
//         """
//     }
// }

return this