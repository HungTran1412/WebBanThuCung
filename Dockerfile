
# Importing JDK and copying required files
FROM openjdk:23-jdk AS build
WORKDIR /pet
COPY pom.xml .
COPY src src

# Copy Maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Set execution permission for the Maven wrapper
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final Docker image using OpenJDK 19
FROM openjdk:23-jdk
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /pet/target/*.jar pet.jar
ENTRYPOINT ["java","-jar","/pet.jar"]
EXPOSE 8080