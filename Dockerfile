FROM openjdk:17-jdk
WORKDIR /app
COPY target/ebanking-backend-0.0.1-SNAPSHOT.jar /app/ebanking-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "ebanking-backend-0.0.1-SNAPSHOT.jar"]