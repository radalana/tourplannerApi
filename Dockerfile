# Use official OpenJDK 21 runtime as a parent image
FROM eclipse-temurin:21-jre

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file into the container (build with mvn package)
COPY target/tourplannerApi-*.jar app.jar

# Expose the port Spring Boot will run on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
