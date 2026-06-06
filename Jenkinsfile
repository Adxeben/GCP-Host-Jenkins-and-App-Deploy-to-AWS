library identifier: "jenkins-shared-library@main", retriever: modernSCM(
    [
        $class: "GitSCMSource",
        remote: "https://github.com/Adxeben/Jenkins-deploy-AWS.git",
        credentialsID: "github-credentials"
    ]
)


pipeline {
    agent any

    tools {
        maven "Maven-3.9.16"
    }
    environment {
        IMAGE_NAME = "sunesis003/app-jenkins:jsl-1.0"
    }
    stages {
        stage("build app") {
            steps {
                echo "building application jar..."
                buildJar()
            }
        }
        stage("create image") {
            steps {
                echo "building the docker image..."
                createImage(env.IMAGE_NAME)
            }
        }
        stage("publish image") {
            steps {
                echo "publish docker image to registry..."
                publishImage(env.IMAGE_NAME)
            }
        }
        stage("deploy app") {
            steps {
                echo "deploying the application to AWS EC2 Instance..."
                def dockerCmd = "docker run -p 3080:3080 -d {IMAGE_NAME}"
                sshagent(['aws-ubuntu-server-key']) {
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@34.35.94.241 ${dockerCmd}"
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