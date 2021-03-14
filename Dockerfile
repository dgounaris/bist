FROM openjdk:11.0.9.1-jdk-slim

ENV ARTIFACT_NAME=bist.jar
ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME
COPY ./build/libs/$ARTIFACT_NAME .
COPY ./dogfood/build/libs/dogfood.jar .

ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
EXPOSE 8080

ENTRYPOINT exec java -jar ${ARTIFACT_NAME}