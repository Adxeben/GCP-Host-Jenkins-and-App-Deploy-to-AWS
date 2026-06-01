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
                    buildJar()
                }
            }
        }
        stage("create image") {
            steps {
                script {
                    createImage("sunesis003/app-jenkins:jsl-1.0")
                }
            }
        }
        stage("publish image") {
            steps {
                script {
                    publishImage("sunesis003/app-jenkins:jsl-1.0")
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