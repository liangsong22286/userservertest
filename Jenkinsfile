node {
//test6
    jdk = tool name: 'jdk1.8'
    env.JAVA_HOME = "${jdk}"
    echo "jdk installation path is: ${jdk}"
    sh "${jdk}/bin/java -version"
   def mvnHome
   def workspace = pwd()
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'git@10.10.3.50:njc-usp/UserServer.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // ** in the global configuration.           
      mvnHome = tool 'maven3.5.3'
   }
   stage('Build') {
      // Run the maven build
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
 
      } else {
         bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
      }
   }
   stage('Test') {
      // Run the maven build
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' test -DfailIfNoTests=false"
 
      } else {
         bat(/"${mvnHome}\bin\mvn" test -DfailIfNoTests=false/)
      }
   }
   stage('Deploy') {
      //sh "'/scripts/deploy.sh' ${workspace} deploy"
      sh "echo ${BUILD_NUMBER}"
      sh "echo ${JENKINS_HOME}"
      sh "echo ${JOB_NAME}"
      sh "echo ${workspace}"
      dir('/newjincin/apps/userserver/') {
            sh "cp -rf ${workspace}/Restful/target/UserServerApi-1.0-SNAPSHOT.jar /newjincin/apps/userserver/UserServerApi-1.0-SNAPSHOT.jar.bck"
            sh "cp -rf ${workspace}/Restful/target/*.sh /newjincin/apps/userserver/"
            sh "chmod 700 /newjincin/apps/userserver/*.sh"
             if (fileExists('UserServerApi-1.0-SNAPSHOT.jar')) {
                //已经有jar包了,那么备份一下
                //创建bck目录
                dir("bck") {
                    echo pwd()
                    //备份就文件
                	sh "mv '../UserServerApi-1.0-SNAPSHOT.jar' 'UserServerApi-1.0-SNAPSHOT.jar.${BUILD_NUMBER}'"
                }
             }
             echo pwd()
	         sh "mv UserServerApi-1.0-SNAPSHOT.jar.bck UserServerApi-1.0-SNAPSHOT.jar"
             //sh "more userserver-18600.sh"
             //sh "ls -l"
	         sh "JENKINS_NODE_COOKIE=dontKillMe ./userserver-18600.sh restart"
	         sh "echo 'restart complate'"
      }
   }
}
