FROM openjdk
VOLUME /tmp
ADD target/DialogManager-0.0.1-SNAPSHOT.jar usr/project/schematicSearch-DialogManager.jar
WORKDIR usr/project/
RUN sh -c 'touch schematicSearch-DialogManager.jar'
ENTRYPOINT ["java","-jar","schematicSearch-DialogManager.jar"]
