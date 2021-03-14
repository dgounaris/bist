pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './gradlew build -x test'
      }
    }

    stage('Unit tests') {
      agent any
      steps {
        sh './gradlew test'
      }
    }

  }
}