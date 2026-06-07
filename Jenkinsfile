#!/usr/bin/env groovy

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
        stage("deploy app") {
            steps {
                script {
                    echo "deploying the application to AWS EC2 Instance..."

                    def ec2Instance = "ubuntu@16.16.79.157"

                    sshagent(['aws-ubuntu-server-key']) {

                        // copy files
                        sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ubuntu"
                        sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ubuntu"

                        // FIXED SSH EXECUTION (important part)
                        sh """
                            ssh -o StrictHostKeyChecking=no ${ec2Instance} \
                            'export IMAGE_NAME=${IMAGE_NAME} && bash ./server-cmds.sh'
                        """
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