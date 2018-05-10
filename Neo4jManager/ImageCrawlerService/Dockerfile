FROM openjdk
VOLUME /tmp
ADD target/image-crawler-service-0.0.1-SNAPSHOT.jar usr/project/schematicSearch-imageCrawler-service.jar
WORKDIR usr/project/
RUN sh -c 'touch schematicSearch-imageCrawler-service.jar'
ENTRYPOINT ["java","-jar","schematicSearch-imageCrawler-service.jar"]