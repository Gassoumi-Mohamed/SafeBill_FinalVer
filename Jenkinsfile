pipeline {
    agent any
    tools {
      maven 'M2_HOME'
    }
    stages{
        stage('Hello'){
            steps{
                echo 'Hello World'
            }
        }
      stage('MAVEN'){
        steps{
          sh "mvn --version"
        }
      }
    
    }
}
