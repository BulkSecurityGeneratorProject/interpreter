#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    docker.image('frolvlad/alpine-oraclejdk8:full').inside('-u root -e MAVEN_OPTS="-Duser.home=./"') {
        stage('install libs') {
            sh "apk add --no-cache libstdc++"   
        }
        
        stage('check java') {
            sh "java -version"
        }

        stage('clean') {
            sh "chmod +x mvnw"
            sh "./mvnw clean"
        }

        stage('install tools') {
            sh "./mvnw com.github.eirslett:frontend-maven-plugin:install-node-and-yarn -DnodeVersion=v8.9.3 -DyarnVersion=v1.3.2"
        }

        stage('yarn install') {
            sh "./mvnw com.github.eirslett:frontend-maven-plugin:yarn"
        }

        stage('backend tests') {
            try {
                sh "./mvnw test"
            } catch(err) {
                throw err
            } finally {
                junit '**/target/surefire-reports/TEST-*.xml'
            }
        }

        stage('packaging') {
            sh "./mvnw verify -Pprod -DskipTests"
            archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
        }

    }
}
