FROM openjdk
VOLUME /tmp
ADD target/Neo4jManager-0.0.1-SNAPSHOT.jar usr/project/schematicSearch-Neo4jManager-service.jar
WORKDIR usr/project
RUN sh -c 'touch schematicSearch-Neo4jManager-service.jar'
ENTRYPOINT ["java","-jar","schematicSearch-Neo4jManager-service.jar"]