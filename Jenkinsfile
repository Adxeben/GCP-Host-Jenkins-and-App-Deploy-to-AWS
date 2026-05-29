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
                    gv.createImage()
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
                    gv.publishImage()
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