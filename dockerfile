FROM java:8

MAINTAINER JIADE

COPY tiktok2.jar app.jar
 
CMD ["--server.port=8090"]
 
EXPOSE 8090
 
ENTRYPOINT ["java", "-jar", "app.jar"]