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
                    gv.buildJar()
                }
            }
        }
        stage("create image") {
            // when {
            //     expression {
            //         BRANCH_NAME == "main"
            //     }
            // }
            steps {
                script {
<<<<<<< Updated upstream
                    gv.createImage()
=======
                    createImage("sunesis003/app-jenkins:param-8.0")
>>>>>>> Stashed changes
                }
            }
        }
        stage("publish image") {
            // when {
            //     expression {
            //         BRANCH_NAME == "main"
            //     }
            // }
            steps {
                script {
<<<<<<< Updated upstream
                    gv.publishImage()
=======
                    publishImage("sunesis003/app-jenkins:param-8.0")
>>>>>>> Stashed changes
                }
            }
        }
        stage("deploy") {
            // when {
            //     expression {
            //         BRANCH_NAME == "main"
            //     }
            // }
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }   
}