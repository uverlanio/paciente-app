FROM eclipse-temurin:21-jdk
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080

#docker build -t prontuario-app-1.4.4 .
#docker run -d -p 8080:8080 --name prontuario-app prontuario-app-1.4.4
#docker login
#docker tag prontuario-app-1.4.4:latest uverdocker/prontuario-app:1.4.4
#docker push uverdocker/prontuario-app:latest

