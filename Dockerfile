FROM adoptopenjdk/maven-openjdk11:latest
COPY ./pom.xml /usr/src/myapp/
COPY ./abstractions /usr/src/myapp/abstractions
COPY ./business /usr/src/myapp/business
COPY ./loader /usr/src/myapp/loader
COPY ./repository /usr/src/myapp/repository
COPY ./webapp /usr/src/myapp/webapp
WORKDIR /usr/src/myapp
RUN mvn install -DskipTests
WORKDIR /usr/src/myapp/webapp/target
CMD ["java", "-jar", "moviecardgame-webapp-1.0-SNAPSHOT.jar"]