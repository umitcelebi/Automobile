FROM openjdk:17-oracle
ARG JAR_FILE=target/Automobile-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} automobile.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n","-Djava.security.egd=file:/dev/./urandom","-jar","/automobile.jar"]