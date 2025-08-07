pipeline {
    agent any

    tools {
        jdk 'jdk-17-temurin'       
        maven 'Maven 3.9'          
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=false"
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/mrk1117/ATF_CONTACTLISTAPP.git', branch: 'main'
            }
        }

        stage('Build & Run Tests') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Publish Test Results') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/surefire-reports/*.xml', fingerprint: true
        }
        failure {
            echo 'Tests failed! Investigate the build logs and surefire reports.'
        }
    }
}
