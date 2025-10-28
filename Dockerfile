FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8081

RUN echo "Hello world"

CMD ["java", "-jar", "app.jar"]
