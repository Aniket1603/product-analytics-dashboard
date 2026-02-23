# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render provides PORT env variable automatically
ENV PORT=10000

EXPOSE 10000

CMD java -Dserver.port=$PORT -Dserver.address=0.0.0.0 -jar app.jar