FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /workspace
COPY . .
RUN ./mvnw -q -DskipTests package || mvn -q -DskipTests package

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /workspace/target/payment-graphql-poc-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
