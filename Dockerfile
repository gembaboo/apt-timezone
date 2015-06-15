FROM java:8
COPY target/apt-timezone-0.0.1-SNAPSHOT.jar /usr/src/apt-timezone/
WORKDIR /usr/src/apt-timezone/
EXPOSE 8080 2000 2050
CMD ["java", "-jar", "-agentlib:jdwp=transport=dt_socket,address=2050,suspend=n,server=y", "apt-timezone-0.0.1-SNAPSHOT.jar"]