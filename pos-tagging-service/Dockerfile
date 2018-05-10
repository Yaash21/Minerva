FROM openjdk
VOLUME /tmp
ADD target/pos-tagging-service-0.0.1-SNAPSHOT.jar usr/project/pos-tagging-service.jar
WORKDIR usr/project/
RUN sh -c 'touch pos-tagging-service.jar'
ENTRYPOINT ["java","-jar","pos-tagging-service.jar"]