FROM java:8

# Update apt-get sources AND install MongoDB and Supervisor
RUN apt-get update && apt-get install -y mongodb supervisor openssh-server

# Create the MongoDB data directory
RUN mkdir -p /data/db /var/run/sshd 

# Log directory for Supervisor
RUN mkdir -p /var/log/supervisor
COPY src/config/supervisord.conf /etc/supervisor/conf.d/supervisord.conf
COPY target/apt-timezone-0.0.1-SNAPSHOT.jar /usr/src/apt-timezone/

EXPOSE 22 8080 2000 2050 27017

# Running with supervisor, so it is commented out for now
# WORKDIR /usr/src/apt-timezone/
# CMD ["java", "-jar", "-Djava.security.egd=file:/dev/random", "-agentlib:jdwp=transport=dt_socket,address=2050,suspend=n,server=y", "apt-timezone-0.0.1-SNAPSHOT.jar"]

CMD ["/usr/bin/supervisord"]
