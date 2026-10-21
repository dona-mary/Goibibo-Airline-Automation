pipeline {
    // 1. Shift from static VMs to isolated, code-defined container agents
    agent {
        docker {
            image 'maven:3.9.9-eclipse-temurin-17'
            args '-v /var/run/docker.sock:/var/run/docker.sock --user root'
        }
    }

    options {
        timeout(time: 45, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '15', artifactNumToKeepStr: '5'))
        disableConcurrentBuilds()
    }

    environment {
        COMPOSE_PATH  = "${WORKSPACE}/docker"
        SELENIUM_HUB  = "http://localhost:4444/wd/hub"
    }

    stages {
        // Fix 1: Pull code first so the configuration and compose files actually exist
        stage('Source Checkout') {
            steps {
                cleanWs()
                checkout scm
            }
        }

        stage('Orchestrate Infrastructure') {
            steps {
                script {
                    echo "Spinning up Ephemeral Selenium Grid Cluster..."
                    sh "docker compose -f ${COMPOSE_PATH}/docker-compose.yml up -d"
                    
                    // Fix 2: Replaced brittle 'sleep 30' with an architectural curl health check
                    echo "Polling Selenium Grid Status API until ready..."
                    timeout(time: 5, unit: 'MINUTES') {
                        sh """
                            until curl --output /dev/null --silent --fail http://localhost:4444/status; do
  							  	echo 'Grid infrastructure initializing, retrying in 5 seconds...'
    							sleep 5
							done
                            echo 'Selenium Grid Hub is fully Operational!'
                        """
                    }
                }
            }
        }

        stage('Execute Unified Lifecycle') {
            steps {
                // Fix 3: Combined redundant compile/test cycles into a single multi-threaded 'verify'
                // Pass environment variables seamlessly via system definitions
                sh "mvn clean verify -Pregression -DseleniumGrid=true -DhubUrl=${SELENIUM_HUB}"
            }
        }
    }

    post {
        always {
            script {
                echo "Tearing down Ephemeral Infrastructure to preserve cluster resources..."
                sh "docker compose -f ${COMPOSE_PATH}/docker-compose.yml down"
            }
            
            echo "Archiving Extent Quality Test Results..."
            publishHTML(target: [
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/ExtentReport',  // Best Practice: Output directly to target/
                reportFiles: 'SparkReport.html',  
                reportName: 'Extent Report'
            ])
            
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml,target/failsafe-reports/*.xml'
        }

        success {
            emailext (
                to: 'donnammary@gmail.com',
                subject: "PASS: Quality Gate Cleared - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <html>
                <body>
                <h3>Automation Suite Passed Successfully</h3>
                <p><b>Job Execution:</b> ${env.JOB_NAME} [Build #${env.BUILD_NUMBER}]</p>
                <!-- Fix 4: Removed hardcoded localhost links to support global team distribution -->
                <p><b>Interactive Extent Report:</b> <a href="${env.BUILD_URL}Extent_20Report/">View Results Natively inside Jenkins</a></p>
                </body>
                </html>
                """,
                mimeType: 'text/html'
            )
        }

        failure {
            emailext (
                to: 'donnammary@gmail.com',
                subject: "FAIL: Quality Gate Breached ❌ - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <html>
                <body>
                <h3 style="color: red;">Build/Regression Defect Detected</h3>
                <p><b>Job Execution:</b> ${env.JOB_NAME} [Build #${env.BUILD_NUMBER}]</p>
                <p><b>Console Workspace Link:</b> <a href="${env.BUILD_URL}console">Analyze Jenkins Logs</a></p>
                </body>
                </html>
                """,
                mimeType: 'text/html',
                attachLog: true
            )
        }
    }
}
