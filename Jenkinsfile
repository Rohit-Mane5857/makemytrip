pipeline {

    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '3', artifactNumToKeepStr: '3'))
    }

    tools {
        maven 'mvn_3.9.9'  // Make sure this Maven tool is defined in Jenkins global tools
    }

    stages {
        stage('Code Compilation') {
            steps {
                echo 'Starting Code Compilation...'
                sh 'mvn clean compile'
                echo 'Code Compilation Completed Successfully!'
            }
        }

        stage('Code QA Execution') {
            steps {
                echo 'Running JUnit Test Cases...'
                sh 'mvn clean test'
                echo 'JUnit Test Cases Completed Successfully!'
            }
        }
        stage('SonarQube Code Quality') {
            environment {
                scannerHome = tool 'qube'
            }
            steps {
                echo 'Starting SonarQube Code Quality Scan...'
                withSonarQubeEnv('sonar-server') {
                    sh 'mvn sonar:sonar'
                }
                echo 'SonarQube Scan Completed. Checking Quality Gate...'
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
                echo 'Quality Gate Check Completed!'
            }
        }

        stage('Code Package') {
            steps {
                echo 'Creating JAR Artifact...'
                sh 'mvn clean package'
                echo 'JAR Artifact Created Successfully!'
            }
        }

        stage('Build & Tag Docker Image') {
            steps {
                echo 'Building Docker Image with Tags...'
                sh "docker build -t rohitmanep9054/makemytrip:latest -t makemytrip:latest ."
                echo 'Docker Image Build Completed!'
            }
        }

        stage('Docker Image Scanning') {
            steps {
                echo 'Scanning Docker Image with Trivy...'
                echo 'Docker Image Scanning Completed!'
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhubCred', variable: 'dockerhubCred')]) {
                        sh 'docker login docker.io -u rohitmanep9054 -p ${dockerhubCred}'
                        echo 'Pushing Docker Image to Docker Hub...'
                        sh 'docker push rohitmanep9054/makemytrip:latest'
                        echo 'Docker Image Pushed to Docker Hub Successfully!'
                    }
                }
            }
        }

        stage('Push Docker Image to Amazon ECR') {
            steps {
                script {
                    withDockerRegistry([credentialsId: 'ecr:ap-south-1:ecr-credentials', url: "https://296062549097.dkr.ecr.ap-south-1.amazonaws.com"]) {
                        echo 'Tagging and Pushing Docker Image to ECR...'
                        sh '''
                            docker images
                            docker tag makemytrip:latest 296062549097.dkr.ecr.ap-south-1.amazonaws.com/makemytrip:latest
                            docker push 296062549097.dkr.ecr.ap-south-1.amazonaws.com/makemytrip:latest
                        '''
                        echo 'Docker Image Pushed to Amazon ECR Successfully!'
                    }
                }
            }
        }

        stage('Upload Docker Image to Nexus') {
             steps {
                 script {
                    withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh 'docker login http://13.201.87.25:8085/repository/makemytrip/ -u admin -p ${PASSWORD}'
                        echo "Push Docker Image to Nexus : In Progress"
                        sh 'docker tag makemytrip 13.201.87.25:8085/makemytrip:latest'
                        sh 'docker push 13.201.87.25:8085/makemytrip'
                        echo "Push Docker Image to Nexus : Completed"
                    }
                 }
             }
        }


        stage('Clean Up Local Docker Images') {
            steps {
                echo 'Cleaning Up Local Docker Images....'
                sh '''
                    docker rmi Rohit-Mane5857/makemytrip:latest || echo "Image not found or already deleted"
                    docker rmi makemytrip:latest || echo "Image not found or already deleted"
                    docker rmi 296062549097.dkr.ecr.ap-south-1.amazonaws.com/makemytrip:latest || echo "Image not found or already deleted"
                    docker image prune -f
                '''
                echo 'Local Docker Images Cleaned Up Successfully!'
            }
        }
    }

    post {
        success {
            echo '✅ Build completed successfully.'
        }
        failure {
            echo '❌ Build failed.'
        }
    }
}