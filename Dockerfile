FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

COPY . .

# Build AND copy in one RUN command
RUN mvn clean package -DskipTests && \
    find target/ -name "*.jar" -exec cp {} app.jar \;

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]