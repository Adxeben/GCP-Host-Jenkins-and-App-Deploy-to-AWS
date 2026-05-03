#!/usr/bin/env groovy

@Library("Jenkins-shared-library")

def gv

pipeline {
    agent any

    tools {
        maven "Maven-3.9.14"
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("test jar") {
            steps {
                script {
                    gv.testJar()
                }
            }
        }
        stage("build jar") {
            when {
                expression {
                    BRANCH_NAME == "master"
                }
            }
            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        stage("create image") {
            when {
                expression {
                    BRANCH_NAME == "master"
                }
            }
            steps {
                script {
                    buildDockerImage(
                    imageName: "app-jenkins",
                    imageTag: "5.0"
                )
                    // gv.createImage()
                }
            }
        }
        stage("publish image") {
            when {
                expression {
                    BRANCH_NAME == "master"
                }
            }
            steps {
                script {
                    dockerpublish(
                        imageName: "sunesis003/app-jenkins",
                        imageTag: "5.0",
                        credentialsId: "docker-hub-repo"
                    )
                    // gv.publishImage()
                }
            }
        }
        stage("deploy") {
            when {
                expression {
                    BRANCH_NAME == "master"
                }
            }
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }   
}