#!/user/bin/env groovy

@Library("jenkins-shared-library")

def gv

pipeline {
    agent any

    tools {
        maven "Maven-3.9.16"
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    buildJar("sunesis003/app-jenkins:jsl-7.0")
                }
            }
        }
        stage("create image") {
            steps {
                script {
                    createImage("sunesis003/app-jenkins:jsl-7.0")
                }
            }
        }
        stage("publish image") {
            steps {
                script {
                    publishImage()
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }   
}