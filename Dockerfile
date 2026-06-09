FROM eclipse-temurin:21-jdk
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080

#docker build -t prontuario-app-1.4.4 .
#docker run -d -p 8080:8080 --name prontuario-app prontuario-app-1.4.4
#docker login
#docker tag prontuario-app-1.4.4:latest uverdocker/prontuario-app:1.4.4
#docker push uverdocker/prontuario-app:latest

#docker run -d -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock --name ci-cd-jenkins jenkins/jenkins:lts