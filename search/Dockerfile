FROM openjdk
VOLUME /tmp
ADD target/search-service-0.0.1-SNAPSHOT.jar usr/project/search-service.jar
WORKDIR usr/project/
RUN sh -c 'touch search-service.jar'
ENTRYPOINT ["java","-jar","search-service.jar"]