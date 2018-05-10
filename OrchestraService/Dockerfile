FROM openjdk
VOLUME /tmp
ADD target/orchestraService-0.0.1-SNAPSHOT.jar usr/project/schematicSearch-orchestra-service.jar
WORKDIR usr/project/
RUN sh -c 'touch schematicSearch-orchestra-service.jar'
ENTRYPOINT ["java","-jar","schematicSearch-orchestra-service.jar"]