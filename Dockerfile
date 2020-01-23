FROM adoptopenjdk/openjdk11:alpine-jre

COPY build/libs/product-0.0.1-SNAPSHOT.jar application.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://database:27017/test", "-jar", "/application.jar"]

