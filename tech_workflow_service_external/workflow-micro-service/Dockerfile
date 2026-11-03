FROM java:8
VOLUME /tmp/workflow-micro-service
ADD ./target/workflow-micro-service.jar workflow-micro-service.jar
RUN bash -c 'touch /workflow-micro-service.jar'
EXPOSE 18080 8000
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Xms218m -Xmx512m -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 -Djava.security.egd=file:/dev/./urandom -jar /workflow-micro-service.jar" ]