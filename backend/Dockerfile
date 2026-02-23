# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# 🔥 IMPORTANT — Use dynamic PORT from Render
ENV PORT=8080
EXPOSE ${PORT}

# 🔥 Bind to 0.0.0.0 and dynamic port
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -Dserver.address=0.0.0.0 -jar app.jar"]