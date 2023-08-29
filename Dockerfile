#
#FROM openjdk
#WORKDIR /app
#COPY . /app
## RUN javac /app/src/main/java/org/example/test.java -d /app/
#RUN javac /app/src/main/java/org/example/Main.java -d /app/
#CMD ["java", "test"]
## ENTRYPOINT java -jar /src/test.jar

FROM maven:3.9.3-eclipse-temurin-17 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM gcr.io/distroless/java17-debian11
COPY --from=build /usr/src/app/target/stg-discord-bot-0.1.jar /usr/app/stg-discord-bot-0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/stg-discord-bot-0.1.jar"]