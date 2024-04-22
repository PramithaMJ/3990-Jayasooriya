# Automated Jenkins Pipeline Setup for Java Maven Projects

---

## Introduction
This README provides guidance on setting up and configuring a Jenkins pipeline for automating the build and execution of Java Maven projects. By following these instructions, you can streamline your development process and ensure consistent builds and deployments.

## Prerequisites
- Jenkins installed and configured on your server.
- Java Development Kit (JDK) installed on the Jenkins server.
- Maven installed on the Jenkins server.

## Setup Steps
1. **Create a New Jenkins Job:**
   - Log in to your Jenkins dashboard.
   - Click on "New Item" to create a new job.
   - Enter a name for your job (e.g., "Java Maven Pipeline").
   - Select "Pipeline" as the job type and click "OK".

2. **Configure Pipeline Script:**
   - In the job configuration page, scroll down to the "Pipeline" section.
   - Choose "Pipeline script" from the Definition dropdown.
   - Copy and paste the following pipeline script:

```groovy
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
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/PramithaMJ/3990-Jayasooriya.git']])
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

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    pushDockerImage()
                }
            }
        }

        stage('Pull Docker Image') {
            steps {
                script {
                    pullDockerImage()
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

def pushDockerImage() {
    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub_credentials') {
        docker.image(env.DOCKER_IMAGE).push('latest')
    }
}

def pullDockerImage() {
    sh "docker pull ${env.DOCKER_IMAGE}:latest"
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

```
Replace `'[https://github.com/your_username/your_java_project.git](https://github.com/PramithaMJ/3990-Jayasooriya.git)'` with the URL of your Java Maven project repository.

### 3. Save and Run:
- Click on "Save" to save your pipeline configuration.
- Trigger a build by clicking on "Build Now".

### Additional Configuration (Optional)
- **Notifications:** Add email or Slack notifications to your pipeline for build status updates.
- **Artifacts:** Archive artifacts like JAR files after a successful build for future deployment.
- **Environment Variables:** Define environment variables for credentials or other sensitive data.

### Conclusion
You've successfully set up a Jenkins pipeline for automating your Java Maven project builds. With this pipeline in place, you can ensure consistent and reliable builds, streamline your development process, and improve overall productivity.

For further customization or troubleshooting, refer to the Jenkins documentation or seek assistance from your team.

