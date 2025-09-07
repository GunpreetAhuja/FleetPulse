
pipeline {
  agent any

  tools {
    jdk 'jdk17'
    gradle 'gradle8'
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build') {
      steps { sh './gradlew clean build -x test' }
    }
    stage('Test') {
      steps { sh './gradlew test' }
      post {
        always { junit 'build/test-results/test/*.xml' }
      }
    }
    stage('Docker Build') {
      steps { sh 'docker build -t fleetpulse-activemq:latest .' }
    }
    stage('Deploy (Compose)') {
      when { expression { return fileExists('docker-compose.yml') } }
      steps { sh 'docker compose up -d --force-recreate' }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
    }
  }
}
