FROM openjdk
VOLUME /tmp
ADD target/nlp-service-0.0.1-SNAPSHOT.jar usr/project/semanticsearch-nlp-service.jar
WORKDIR usr/project/
RUN sh -c 'touch semanticsearch-nlp-service.jar'
ENTRYPOINT ["java","-jar","semanticsearch-nlp-service.jar"]