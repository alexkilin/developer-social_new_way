#!groovy

properties([disableConcurrentBuilds()])

pipeline {
    agent {
        label 'master'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '1', artifactNumToKeepStr: '1'))
        timestamps()
    }
    stages {
        stage("Package") {
            steps {
                echo "============= started packaging ============="
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage('Docker') {
            steps {
                echo "============= started dockerizing ============="

                sh 'docker build -t platform .'
                sh 'docker-compose up -d'
            }
        }
    }
}



//pipeline {
//    agent any
//    tools {
//        maven 'maven_3_6_3'
//        jdk 'jdk_8_221'
//        git 'git'
//    }
//    options {
//        disableConcurrentBuilds()
//        buildDiscarder(logRotator(numToKeepStr: '1', artifactNumToKeepStr: '1'))
//        timestamps()
//    }
//    stages {
//        stage("Package") {
//            steps {
//                echo "============= started packaging ============="
//                sh 'mvn package -DskipTests=true'
//            }
//        }
//        stage('Docker') {
////            agent { dockerfile true }
//            environment {
//                TAG = sh(returnStdout: true, script: "date +%s")
//                IMAGE_NAME = 'tacirus/devsocial:'
//                LATEST = 'latest'
//                AWS_IMAGE_NAME = 'public.ecr.aws/j4x8o0a2/devsocial:latest'
//                AWS_PASS = sh(returnStdout: true, script: '/usr/local/bin/aws ecr-public get-login-password --region us-east-1').trim()
//                AWS_VERSION = sh(returnStdout: true, script: '/usr/local/bin/aws --version')
//            }
//            steps {
//                echo "============= started dockerizing ============="
//
//                sh 'echo $AWS_VERSION'
//                sh 'docker build --rm -t $AWS_IMAGE_NAME .'
//
//                sh '/usr/local/bin/aws ecr-public batch-delete-image --repository-name devsocial --image-ids imageDigest=$(cat $HOME/image_digest.txt) --region us-east-1'
//                sh 'echo  $AWS_PASS | cat > /var/lib/jenkins/workspace/dockerizing/dockerPass.txt'
//                sh 'docker login --username AWS --password-stdin < /var/lib/jenkins/workspace/dockerizing/dockerPass.txt public.ecr.aws'
//
//                sh 'docker push $AWS_IMAGE_NAME'
//                sh 'docker images $AWS_IMAGE_NAME --digests|grep -Eo "\b[a-z0-9]+:[a-z0-9]{64,72}\b"|cat>"$HOME/image_digest.txt"'
//                sh 'docker rmi $AWS_IMAGE_NAME'
//            }
//        }
//    }
//}