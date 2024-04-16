pipeline {
    agent any
    tools{
        maven 'maven_3_9_6'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/PramithaMJ/3990-Jayasooriya.git']])
                sh 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t pramithamj/3990-jayasooriya .'
                }
            }
        }
        stage('Run Docker container') {
            steps {
                script {
                    sh 'docker run -d --name java_container pramithamj/3990-jayasooriya'
                }
            }
        }
        stage('Check Docker containers') {
            steps {
                script {
                    sh 'docker ps'
                }
            }
        }
    }
}