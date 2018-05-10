FROM openjdk
VOLUME /tmp
ADD target/confidence-score-service-0.0.1-SNAPSHOT.jar usr/project/confidence-score-service.jar
WORKDIR usr/project/
RUN sh -c 'touch confidence-score-service.jar'
ENTRYPOINT ["java","-jar","confidence-score-service.jar"]