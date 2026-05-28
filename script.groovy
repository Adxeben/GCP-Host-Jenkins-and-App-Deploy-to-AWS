def buildJar() {
    echo "building and testing the application..."
    echo "executing pipeline for $BRANCH_NAME branch"
    // sh "mvn clean package"
} 

def createImage() {
    echo "creating the docker image..."
    // sh "docker build -t sunesis003/app-jenkins:5.0 ."
} 

def publishImage() {
    echo "publishing to docker hub repo..."
    // withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
    //     sh "echo $PASS | docker login -u $USER --password-stdin"
    //     sh "docker push sunesis003/app-jenkins:5.0"
    // }
} 

def deployApp() {
    echo "deploying the application..."
} 

return this