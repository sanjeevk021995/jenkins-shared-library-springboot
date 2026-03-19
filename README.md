

#  Jenkins Pipeline for Spring Boot Application

This repository contains a Jenkins pipeline definition (`Jenkinsfile`) that builds, scans, and deploys a **Java Spring Boot application** using a shared DevOps library.  
The pipeline leverages Kubernetes agents and containerized tools (Maven, Kaniko, Trivy, Helm) for a fully automated CI/CD workflow.

---

##  Pipeline Overview

The pipeline is defined with the following key components:

- **Shared Library**:  
  `@Library('devops-shared-lib') _`  
  Provides reusable functions (`buildApp`, `buildImage`, `securityScan`, `deployHelmChart`) for consistent CI/CD practices.        
  url - https://github.com/sanjeevk021995/jenkins-shared-library.git

- **Kubernetes Agent**:  
  Runs pipeline steps inside pods defined by `kubernetes-pod-template.yaml`.  
  Each stage uses a specific container (Maven, Kaniko, Trivy, Helm).

- **Environment Variables**:  
  - `IMAGE_NAME`: Docker image name (`docker.io/sanjeevk95/springboot-demo`)  
  - `TAG`: Build number used as the image tag

---

##  Pipeline Stages

### 1. Build
```groovy
stage('Build') {
  steps {
    container('maven') {
      buildApp()
    }
  }
}
```
- Compiles the Spring Boot application using Maven.
- Produces a JAR file.

---

### 2. Build Image
```groovy
stage('Build Image') {
  steps {
    container('kaniko') {
      buildImage(IMAGE_NAME, TAG)
    }
  }
}
```
- Uses **Kaniko** to build a Docker image inside Kubernetes.
- Tags the image with the build number.

---

### 3. Security Scan
```groovy
stage('Security Scan') {
  steps {
    container('trivy') {
      securityScan(IMAGE_NAME, TAG)
    }
  }
}
```
- Runs **Trivy** to scan the Docker image for vulnerabilities.
- Ensures compliance and security before deployment.

---

### 4. Deploy
```groovy
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
```
- Uses **Helm** to deploy the application to Kubernetes.
- References a Helm chart located at `./helm/springboot-chart`.

---

##  Benefits of This Pipeline

- **Reusable shared library** → consistent CI/CD functions across projects.
- **Containerized build tools** → isolated, reproducible builds.
- **Security scanning** → ensures images are safe before deployment.
- **Automated deployment** → Helm manages Kubernetes releases.

---
