FROM openjdk
VOLUME /tmp
ADD target/content-crawler-0.0.1-SNAPSHOT.jar usr/project/schematicSearch-contentCrawler-service.jar
WORKDIR usr/project/
RUN sh -c 'touch schematicSearch-contentCrawler-service.jar'
ENTRYPOINT ["java","-jar","schematicSearch-contentCrawler-service.jar"]
