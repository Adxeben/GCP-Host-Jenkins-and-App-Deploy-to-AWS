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
        stage("version increment") {
            steps {
                script {
                    gv.increaseVersion()
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        stage("create image") {
            steps {
                script {
                    gv.createImage()
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
        stage("deploy application") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
        stage("git version commit") {
            steps {
                script {
                    gv.commitVersionGit()
                }
            }
        }
    }
    post {
        always {
            echo "Pipeline finished"
            cleanWs()
        }

        success {
            echo "Build successful"
            slackSend channel: '#ci-cd', message: "Build passed: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        }

        failure {
            echo "Build failed"
            slackSend channel: '#ci-cd', message: "Build failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        }

        unstable {
            echo "Build unstable"
        }
    } 
}