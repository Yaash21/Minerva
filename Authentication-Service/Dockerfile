FROM openjdk
VOLUME /tmp
ADD target/authentication-0.0.1-SNAPSHOT.jar usr/project/schematicSearch-authentication-service.jar
WORKDIR usr/project/
RUN sh -c 'touch schematicSearch-authentication-service.jar'
ENTRYPOINT ["java","-jar","schematicSearch-authentication-service.jar"]
