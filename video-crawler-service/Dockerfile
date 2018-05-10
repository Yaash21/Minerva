FROM openjdk
VOLUME /tmp
ADD target/video-crawler-service-0.0.1-SNAPSHOT.jar usr/project/video-crawler-service.jar
WORKDIR usr/project/
RUN sh -c 'touch video-crawler-service.jar'
ENTRYPOINT ["java","-jar","video-crawler-service.jar"]