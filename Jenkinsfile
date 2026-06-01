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
}