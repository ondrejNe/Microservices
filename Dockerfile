FROM openjdk:17-jdk
VOLUME /tmp
COPY /services/uno.service/build/libs/uno.service-standalone.jar app.jar
CMD ["sh","-c","java -jar app.jar"]