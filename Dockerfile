FROM openjdk:17
EXPOSE 8080
ADD target/3990-jayasooriya.jar 3990-jayasooriya.jar
ENTRYPOINT ["java", "-jar", "/3990-jayasooriya.jar"]