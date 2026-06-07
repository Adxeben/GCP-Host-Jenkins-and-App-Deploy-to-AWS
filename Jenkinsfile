library identifier: "jenkins-shared-library@main", retriever: modernSCM(
    [
        $class: "GitSCMSource",
        remote: "https://github.com/Adxeben/Jenkins-shared-library.git",
        credentialsID: "github-credentials"
    ]
)


pipeline {
    agent any

    tools {
        maven "Maven-3.9.16"
    }
    environment {
        // Define your full Docker image name here (registry/repo:tag)
        IMAGE_NAME = "sunesis003/app-jenkins:jsla-4.0"
    }
    stages {
        stage("build app") {
            steps {
                script {
                    echo "building application jar..."
                    buildJar()
                } 
            }
        }
        stage("create image") {
            steps {
                script {
                    echo "building the docker image..."
                    createImage(env.IMAGE_NAME)
                }
            }
        }
        stage("publish image") {
            steps {
                script {
                    echo "publish docker image to registry..."
                    publishImage(env.IMAGE_NAME)
                }
                
            }
        }
        stage('Wait for Image Availability') {
            steps {
                sh '''
                    for i in {1..12}; do
                        docker manifest inspect ${IMAGE_NAME} >/dev/null 2>&1 && exit 0

                        echo "Image not yet available. Waiting 10 seconds..."
                        sleep 10
                    done

                    echo "Image never became available."
                    exit 1
                '''
            }
        }
        stage("deploy app") {
            steps {
                script {
                    echo "deploying the application to AWS EC2 Instance..."
                    // def dockerCmd = "docker run -p 3080:3080 -d ${IMAGE_NAME}"
                    def dockerComposeCmd = "docker-compose -f docker-compose.yaml up -d"
                    sshagent(['aws-ubuntu-server-key']) {

                        // 1. copy file
                        sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ubuntu@16.16.79.157:/home/ubuntu"

                        // 2. run command on EC2
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@16.16.79.157 ${dockerComposeCmd}"
                    }   
                }
            }
        }
        
    }
    // post {
    //     always {
    //         echo "Pipeline finished"
    //         cleanWs()
    //     }

    //     success {
    //         echo "Build successful"
    //         slackSend channel: '#ci-cd', message: "Build passed: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
    //     }

    //     failure {
    //         echo "Build failed"
    //         slackSend channel: '#ci-cd', message: "Build failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
    //     }

    //     unstable {
    //         echo "Build unstable"
    //     }
    // } 
}