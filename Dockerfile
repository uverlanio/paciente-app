FROM eclipse-temurin:21-jdk
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080

#docker build -t prontuario-app-1.4.3 .
#docker run -d -p 8080:8080 --name prontuario-app prontuario-app-1.4.3
#docker login
#docker tag prontuario-app:1.4.3 user/prontuario-app:latest
#docker push user/prontuario-app:latest

