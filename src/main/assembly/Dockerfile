FROM java:8
COPY ${project.artifactId}-${project.version}.jar /usr/src/apt-timezone/
WORKDIR /usr/src/apt-timezone/
EXPOSE 8080 2000
CMD ["java", "-jar", "${project.artifactId}-${project.version}.jar"]