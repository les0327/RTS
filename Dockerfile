FROM openjdk:8-jre-alpine

COPY ./build/libs/rts-0.0.1-SNAPSHOT.jar /rts-0.0.1-SNAPSHOT.jar

CMD ["/usr/bin/java", "-jar", "/rts-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=production"]