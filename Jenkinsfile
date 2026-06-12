#!/usr/bin/env groovy

library identifier: "jenkins-shared-library@main", retriever: modernSCM(
    [
        $class: "GitSCMSource",
        remote: "https://github.com/Adxeben/Jenkins-shared-library.git",
        credentialsID: "github-credentials"
    ]
)_

def gv

pipeline {
    agent any

    tools { 
        maven "Maven-3.9.16"
    }
    stages {
        stage("load script") {
            steps {
                script {
                    gv = load 'script.groovy'
                } 
            }
        }
         stage("incrementing app version") {
            steps {
                script {
                    gv.increaseVersion()
                } 
            }
        }
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
                    gv.deployApp()
                }
            }
        }
        stage ("commit version update"){
            steps {
                script {
                    gv.commitVersionGit()
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