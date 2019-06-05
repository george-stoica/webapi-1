pipeline {
  agent any

  stages {
    stage('Build Docker Image') {
      steps {
        sh 'chmod +x gradlew'
        sh './gradlew clean build docker'
      }
    }
  }
}