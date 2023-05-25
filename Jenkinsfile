pipeline {
    triggers {
    //    Needs to be configured in the Jenkins UI to trigger pipelines when they are "tagged" (Scan Multibranch Pipeline Triggers )
        pollSCM('H/4 * * * *')
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        skipDefaultCheckout()
    }
    agent any
    environment {
        DOCKERHUB_USER='claudiooak'
        DOCKERHUB_REPO='recommendationservice'
        DOCKERHUB_CREDS= credentials('fd7e66ac-cc28-4dde-97ea-ee73e3bbd880')
        DEPLOY_DIR='pd-ansible'
        DEPLOY_USER='ubuntu'
        DEPLOY_HOST='localhost'
    }
    stages {
        stage('Build and publish - PROD ') {
            when {
                buildingTag()
            }
            steps {
                cleanWs()
                checkout scm
                sh '''
                docker build -t ${DOCKERHUB_USER}/${DOCKERHUB_REPO}:$TAG_NAME .
                docker login -u $DOCKERHUB_CREDS_USR -p $DOCKERHUB_CREDS_PSW
                docker push ${DOCKERHUB_USER}/${DOCKERHUB_REPO}:$TAG_NAME
                '''
            }
        }
        stage('Build and Publish - DEV ') {
            when {
                branch "master"
            }
            steps {
                cleanWs() 
                checkout scm
                sh '''
                docker build -t ${DOCKERHUB_USER}/${DOCKERHUB_REPO}:dev-$BUILD_NUMBER .
                docker login -u $DOCKERHUB_CREDS_USR -p $DOCKERHUB_CREDS_PSW
                docker push ${DOCKERHUB_USER}/${DOCKERHUB_REPO}:dev-$BUILD_NUMBER
                '''
            }
        }
        stage('Deploy - PROD'){
            when {
                    buildingTag()
            }
            steps {
                sshagent(['ubuntulocalhost']) {
                    sh "ssh -l $DEPLOY_USER $DEPLOY_HOST 'ansible-playbook -i pd-ansible/inventory/prod/hosts.yml --diff pd-ansible/playbooks/recommendationservice-swarm.yml --extra-vars \"recommendationservice_tag=$TAG_NAME\"'"
                } 
            }
        }
        stage('Deploy - DEV'){
            when {
                branch "master"
            }
            steps {
                sshagent(['ubuntulocalhost']) {
                    sh "ssh -l $DEPLOY_USER $DEPLOY_HOST 'ansible-playbook -i pd-ansible/inventory/dev/hosts.yml --diff pd-ansible/playbooks/recommendationservice-swarm.yml --extra-vars \"recommendationservice_tag=dev-$BUILD_NUMBER\"'"
                } 
            }
        }
    }
    post {
        always {  
	        sh 'docker logout'     
        }      
        cleanup {
            cleanWs()
        }
    }
}