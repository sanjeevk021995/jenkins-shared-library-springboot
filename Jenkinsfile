
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
        buildImage(IMAGE_NAME, TAG)
      }
    }
  }

  stage('Security Scan') {
    steps {
      container('trivy') {
        securityScan(IMAGE_NAME, TAG)
      }
    }
  }



  stage('Deploy') {

  steps {

    container('helm') {

      deployHelmChart("./helm/springboot-chart",
                 "springboot-demo",
                 IMAGE_NAME,
                 TAG)

    }

  }

}

 }

}