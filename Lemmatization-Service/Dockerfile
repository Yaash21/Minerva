FROM openjdk
VOLUME /tmp
ADD target/lemmatization-service-0.0.1-SNAPSHOT.jar usr/project/semanticsearch-lemmatization-service.jar
WORKDIR usr/project/
RUN sh -c 'touch semanticsearch-lemmatization-service.jar'
ENTRYPOINT ["java","-jar","semanticsearch-lemmatization-service.jar"]