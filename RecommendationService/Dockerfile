FROM openjdk
VOLUME /tmp
ADD target/recommendation-service-0.0.1-SNAPSHOT.jar usr/project/schematicSearch-recommendation-service.jar
WORKDIR usr/project
RUN sh -c 'touch schematicSearch-recommendation-service.jar'
ENTRYPOINT ["java","-jar","schematicSearch-recommendation-service.jar"]