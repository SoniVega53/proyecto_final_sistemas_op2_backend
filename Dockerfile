FROM eclipse-temurin:21-jdk

EXPOSE 9090

WORKDIR /root

RUN apt-get update && apt-get install -y dos2unix

COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn

RUN dos2unix mvnw && chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean install -DskipTests

ENTRYPOINT ["java", "-jar", "target/sistemas_op_umg2025-1.0.0.jar"]
