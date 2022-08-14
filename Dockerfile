FROM openjdk:17

RUN mkdir -p /staff-management-service
WORKDIR /staff-management-service

ENV JAR_FILE=staff-management-service-0.0.1-SNAPSHOT.jar
ENV TARGET_JAR_FILE=staff-management-service.jar

COPY target/$JAR_FILE ./$TARGET_JAR_FILE

EXPOSE 8080
ENV JAVA_ARGS=""
CMD java $JAVA_ARGS -jar $TARGET_JAR_FILE