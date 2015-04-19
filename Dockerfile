FROM java:8
COPY target/apt-timezone-0.0.1-SNAPSHOT.jar /usr/src/apt-timezone/
WORKDIR /usr/src/apt-timezone/
EXPOSE 8080 2000
CMD ["java", "-jar", "-Djava.security.egd=file:/dev/random", "apt-timezone-0.0.1-SNAPSHOT.jar"]