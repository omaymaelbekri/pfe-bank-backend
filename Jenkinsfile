pipeline{
  agent any
tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "Maven"
    }

  stages{
        stage('Build'){
            steps{
                bat 'mvn -Dmaven.test.skip=true clean install'
            }
        }
        stage('Test Service'){
              steps{
                  bat 'mvn test -Dtest=BankAccountServiceImplTest'
                  bat 'mvn test -Dtest=BankServiceTest'
              }
        }

        }
  }
}