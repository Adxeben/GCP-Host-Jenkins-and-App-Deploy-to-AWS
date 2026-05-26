#!/usr/bin/env groovy


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
        stage("test jar") {
            steps {
                script {
                    gv.testJar()
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
        stage("deploy") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }   
}