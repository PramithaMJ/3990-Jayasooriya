pipeline {
    agent any

    options {
        timestamps()
        timeout(time: 1, unit: 'HOURS')
    }

    environment {
        DOCKER_IMAGE = 'pramithamj/3990-jayasooriya'
        CONTAINER_NAME = 'java_container'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    buildMavenProject()
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    buildDockerImage()
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    runDockerContainer()
                }
            }
        }

        stage('Check Docker Containers') {
            steps {
                script {
                    checkDockerContainers()
                }
            }
        }
    }

    post {
        always {
            cleanUp()
        }

        success {
            echo 'Pipeline completed successfully'
        }

        failure {
            echo 'Pipeline failed'
        }

        unstable {
            echo 'Pipeline unstable'
        }
    }
}

def buildMavenProject() {
    sh 'mvn clean install'
}

def buildDockerImage() {
    docker.build(env.DOCKER_IMAGE)
}

def runDockerContainer() {
    docker.image(env.DOCKER_IMAGE).run("-d --name ${env.CONTAINER_NAME}")
}

def checkDockerContainers() {
    sh 'docker ps'
}

def cleanUp() {
    cleanWs()
}
