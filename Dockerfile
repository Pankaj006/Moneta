FROM openjdk:17-jdk-alpine
WORKDIR /tmp
COPY  target/*.jar  /tmp/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
