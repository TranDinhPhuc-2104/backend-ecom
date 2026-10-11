pipeline {
    agent any
 
    environment {
        AWS_REGION  = 'us-east-1'
        ACCOUNT_ID  = '415642395658'
        ECR_REPO    = 'backend-ecom'
        ECS_CLUSTER = 'back-end-ecom'
        ECS_SERVICE = 'back-end-ecom-service-n0ylsjdr'
        TASK_FAMILY = 'back-end-ecom'          
        ECR_URL     = "${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
        IMAGE       = "${ECR_URL}/${ECR_REPO}:${BUILD_NUMBER}"
    }
 
    stages {
        stage('Build') {
            steps {
                sh 'docker build -t $IMAGE .'
            }
        }
 
        stage('Push ECR') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-ecs']]) {
                    sh '''
                        aws ecr get-login-password --region $AWS_REGION \
                          | docker login --username AWS --password-stdin $ECR_URL
                        docker push $IMAGE
                    '''
                }
            }
        }
 
        stage('Deploy ECS') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-ecs']]) {
                    sh '''
                        # 1. Lấy task definition hiện tại
                        aws ecs describe-task-definition --task-definition $TASK_FAMILY \
                          --region $AWS_REGION --query taskDefinition > td.json
 
                        # 2. Đổi image sang tag mới, bỏ các field chỉ-đọc
                        jq --arg IMG "$IMAGE" '
                          .containerDefinitions[0].image = $IMG
                          | del(.taskDefinitionArn, .revision, .status, .requiresAttributes,
                                .compatibilities, .registeredAt, .registeredBy)' td.json > new-td.json
 
                        # 3. Đăng ký revision mới
                        NEW_TD=$(aws ecs register-task-definition --cli-input-json file://new-td.json \
                          --region $AWS_REGION --query taskDefinition.taskDefinitionArn --output text)
                        echo "Registered: $NEW_TD"
 
                        # 4. Cập nhật service sang revision mới
                        aws ecs update-service --cluster $ECS_CLUSTER --service $ECS_SERVICE \
                          --task-definition $NEW_TD --region $AWS_REGION
                        aws ecs wait services-stable --cluster $ECS_CLUSTER --services $ECS_SERVICE --region $AWS_REGION
                    '''
                }
            }
        }
    }
 
    post {
        always {
            sh 'docker rmi $IMAGE || true'
        }
    }
}