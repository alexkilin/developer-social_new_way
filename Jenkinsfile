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

                sh 'docker build --rm -t platform .'
                sh 'docker-compose up -d'
                sh 'docker rmi $(docker images -f "dangling=true" -q)'

                echo "============= end dockerizing ============="
            }
        }
    }
}
