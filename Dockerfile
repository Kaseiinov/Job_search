FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

COPY . .

RUN mvn clean package -DskipTests

# Copy the specific JAR file (more reliable)
COPY target/*.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]