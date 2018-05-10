FROM openjdk
VOLUME /tmp
ADD target/RabbitMq-0.0.1-SNAPSHOT.jar usr/project/rabbitmq-service.jar
WORKDIR usr/project/
RUN sh -c 'touch rabbitmq-service.jar'
ENTRYPOINT ["java","-jar","rabbitmq-service.jar"]