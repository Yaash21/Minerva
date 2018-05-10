FROM openjdk
VOLUME /tmp
ADD target/UserSearchService-0.0.1-SNAPSHOT.jar usr/project/schematicSearch-UserSearch-service.jar
WORKDIR usr/project
RUN sh -c 'touch schematicSearch-UserSearch-service.jar'
ENTRYPOINT ["java","-jar","schematicSearch-UserSearch-service.jar"]