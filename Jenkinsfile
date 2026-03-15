
@Library('devops-shared-lib') _
pipeline {

agent {
    kubernetes {
        yamlFile 'kubernetes-pod-template.yaml'

    }
}




 environment {

   IMAGE_NAME = "docker.io/sanjeevk95/springboot-demo"
   TAG = "${BUILD_NUMBER}"

 }

 stages {

  stage('Build') {
    steps {
      container('maven') {
        buildApp()
      }
    }
  }

  stage('Build Image') {
    steps {
      container('kaniko') {
        buildImageKaniko(IMAGE_NAME, TAG)
      }
    }
  }

  stage('Security Scan') {
    steps {
      container('trivy') {
        securityScanTrivy(IMAGE_NAME, TAG)
      }
    }
  }

  stage('Push Image') {
    steps {
      container('kaniko') {
        pushImage(IMAGE_NAME, TAG)
      }
    }
  }

  stage('Deploy') {

  steps {

    container('helm') {

      helmDeploy("./helm/springboot-chart",
                 "springboot-demo",
                 IMAGE_NAME,
                 TAG)

    }

  }

}

 }

}