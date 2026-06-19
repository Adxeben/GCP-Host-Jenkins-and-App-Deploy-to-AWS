# Cross-Cloud CI/CD Pipeline: GCP → AWS 
(See jenkins-jobs branch)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white)
![GCP](https://img.shields.io/badge/Google_Cloud-4285F4?style=for-the-badge&logo=google-cloud&logoColor=white)

## 📌 Table of Contents
- [About The Project](#-about-the-project)
- [Architecture Overview](#-architecture-overview)
- [Built With](#-built-with)
- [Pipeline Breakdown](#-pipeline-breakdown)
- [Screenshots](#-screenshots)
- [Prerequisites](#-prerequisites)
- [Future Improvements](#-future-improvements)
- [Author](#-author)

---

## 📖 About The Project

This project demonstrates a **complete DevOps lifecycle** by bridging two major cloud providers. 

I provisioned a **Jenkins CI server on Google Cloud Platform (GCP)** to automate the build process. The pipeline takes a Java Maven application, applies **dynamic semantic versioning**, containerizes it with **Docker**, and securely deploys the final image to an **AWS EC2 instance** using **Docker Compose**.

**Key Challenge Solved:** Instead of hardcoding deployment commands in the Jenkinsfile (which becomes messy), I abstracted all remote Linux deployment logic into a reusable shell script, making the pipeline cleaner and enterprise-ready.

---

## 🏛️ Architecture Overview

*Below is the end-to-end flow of the pipeline:*

    A[Developer Push to GitHub] --> B[Jenkins Master (GCP VM)]
    B --> C[Dynamic Version Increment]
    C --> D[Build & Test with Maven]
    D --> E[Build Docker Image]
    E --> F[Push Image to Docker Hub]
    F --> G[SSH into AWS EC2]
    G --> H[Execute remote shell script]
    H --> I[Docker Compose pulls & deploys new version]
    I --> J[Commit version update back to GitHub]

---

## 🛠️ Built With

| Category | Tools & Technologies |
| :--- | :--- |
| **Cloud (CI)** | Google Cloud Platform (Compute Engine VM) |
| **Cloud (CD)** | AWS (EC2 Instance) |
| **CI/CD Engine** | Jenkins, Jenkins Shared Library |
| **Containerization** | Docker, Docker Compose, Docker Hub |
| **Application** | Java, Maven, Git |
| **Scripting** | Linux Bash (Ubuntu), SSH |

---

## ⚙️ Pipeline Breakdown

### 🟢 Continuous Integration (CI) - *On GCP*
1. **Code Checkout**: Pulls the latest code from the GitHub repository.
2. **Maven Build**: Compiles the Java application and runs unit tests.
3. **Dynamic Versioning**: Automatically increments the application version (e.g., `1.0.0` → `1.0.1`) and tags the build.
4. **Dockerization**: Builds a lightweight Docker image from the compiled JAR/WAR file.
5. **Registry Push**: Authenticates with Docker Hub and pushes the image using a **Jenkins Shared Library** for reusable pipeline code.
6. **Commit Back**: Commits the new version number back to the GitHub repository.

### 🔵 Continuous Deployment (CD) - *On AWS*
1. **Secure SSH**: Uses Jenkins-managed SSH credentials to connect to the AWS EC2 instance.
2. **Remote Execution**: Transfers and executes a predefined `server.cmd.sh` shell script on the EC2 server.
3. **Docker Compose Up**: The script pulls the newly tagged image and runs `docker-compose up -d` to refresh the running container with zero manual intervention.
4. **Security**: AWS Security Groups are configured to allow traffic only from necessary sources (e.g., Jenkins master IP, user HTTP/HTTPS ports).

---

## 📸 Screenshots

*Add your screenshots here to visually prove the working pipeline!*

### 1. Jenkins Pipeline (Stage View)
![alt text](<Screenshot 2026-06-19 093957.png>)
<br/>
<br/> 
![alt text](<Screenshot 2026-06-19 094102.png>)
> *Show the successful execution of all stages (Increment version, Build, Test, Push, Deploy, Commit version increment to git).*

### 2. GCP VM Instance (Jenkins Master)
![alt text](<Screenshot 2026-06-19 105116.png>)
> *Show the Compute Engine instance running the Jenkins server.*

### 3. AWS EC2 Instance (Deployment Target)
![alt text](<Screenshot 2026-06-19 105635.png>)
> *Show the EC2 instance details and public IP where the app is hosted.*

### 4. Docker Hub Repository
![alt text](<Screenshot 2026-06-19 113542.png>)
> *Show the multiple tagged images being pushed automatically with version numbers.*

### 5. Deployed Application
![alt text](<Screenshot 2026-06-19 110214.png>)
> *Show container running successfully on the AWS EC2 public IP/port.*

---

## 📋 Prerequisites

If you want to replicate this setup, ensure you have:

- A **GCP Project** with Compute Engine API enabled.
- An **AWS Account** with a running EC2 instance (Amazon Linux 2 / Ubuntu).
- A **Docker Hub** account.
- Jenkins installed on the GCP VM with the following plugins:
  - Docker Pipeline
  - SSH Pipeline Steps
  - Credentials Binding
  - Git
- SSH Key-Pair configured for passwordless access from Jenkins to EC2.

---

## 🚀 Future Improvements

- [ ] **Infrastructure as Code**: Replace manual VM creation with Terraform scripts.
- [ ] **Orchestration**: Move from Docker Compose to Kubernetes (EKS or GKE) for auto-scaling.
- [ ] **Monitoring**: Add Prometheus + Grafana stack to monitor the live application.
- [ ] **Notifications**: Integrate Slack/Email alerts for pipeline failures.

---
⭐ *If you found this project helpful, please give it a star! It helps me build my DevOps portfolio.*
