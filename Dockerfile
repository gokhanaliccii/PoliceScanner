FROM gradle:8.0.2 AS BUILD
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:8-jre-alpine

#ENV APPLICATION_USER ktor
#RUN adduser -D -g '' $APPLICATION_USER
#RUN mkdir /app
#RUN chown -R $APPLICATION_USER /app

#USER $APPLICATION_USER
RUN mkdir /app
COPY --from=BUILD /home/gradle/src/build/libs/*.jar /app/apiv0.jar
COPY --from=BUILD /home/gradle/src/src/main/resources/ /app/resources/
CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:InitialRAMFraction=2", "-XX:MinRAMFraction=2", "-XX:MaxRAMFraction=2", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "/app/apiv0.jar"]
