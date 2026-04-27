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
        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                    sh "docker --version"
                }
            }
        }
        stage("create image") {
            steps {
                script {
                    gv.dockerImage()
                }
            }
        }
        stage("publish image") {
            steps {
                script {
                    gv.publishImage()
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