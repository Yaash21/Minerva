FROM openjdk
VOLUME /tmp
ADD target/code-crawler-service-0.0.1-SNAPSHOT.jar usr/project/semanticsearch-code-crawler-service.jar
WORKDIR usr/project/
RUN sh -c 'touch semanticsearch-code-crawler-service.jar'
ENTRYPOINT ["java","-jar","semanticsearch-code-crawler-service.jar"]