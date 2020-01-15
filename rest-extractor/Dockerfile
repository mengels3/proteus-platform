FROM openjdk:13.0.1-jdk-buster
COPY ./target/rest-extractor-0.0.1-SNAPSHOT.jar /opt/sedi/platform.jar
WORKDIR /opt/sedi
CMD java -Dspring.profiles.active=prod -jar platform.jar
