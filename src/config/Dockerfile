FROM java:8

# Create directories
RUN mkdir -p /var/log /var/run/sshd /usr/src/apt-timezone /data/db /var/log/mongodb
RUN mkdir -p /var/tmp/export /var/tmp/upload/.failed /var/tmp/upload/.inprogress /var/tmp/upload/.processed
RUN ln -s /var/tmp/export /usr/src/apt-timezone/export
RUN ln -s /var/tmp/upload /usr/src/apt-timezone/upload

# Force apt-get to use IPV4 (IPV 6 mirrors may not be accessible by the host)
RUN echo "Acquire::ForceIPv4 true;" > /etc/apt/apt.conf.d/99force-ipv4

# Update apt-get sources and install MongoDB, Supervisor, open-ssh
RUN apt-get update && apt-get install -y supervisor openssh-server

# Install mongodb
RUN curl -SL "https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-debian71-3.0.4.tgz" -o mongo.tgz \
	&& tar -xvf mongo.tgz -C /usr/local --strip-components=1 \
	&& rm mongo.tgz

# Set the password for the ssh access
RUN echo 'root:root' | chpasswd
RUN sed -i 's/PermitRootLogin without-password/PermitRootLogin yes/' /etc/ssh/sshd_config

# Copy files
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf
COPY airport.json /var/tmp/export/
COPY airport.csv /var/tmp/upload/

# Configure mongodb
RUN useradd -ms /bin/bash mongodb
RUN chown -R mongodb:mongodb /data/db
RUN chown mongodb:mongodb /var/log/mongodb/
USER mongodb
# Load initial data
RUN /usr/local/bin/mongod --nojournal --smallfiles --fork --logpath /var/log/mongodb/mongodb.log && \
    mongoimport --db apt-timezone --collection airport --file /var/tmp/export/airport.json --jsonArray && \
    /usr/local/bin/mongod --shutdown

USER root
# Copy java app
COPY apt-timezone-${project.version}.jar /usr/src/apt-timezone/

# Expose http, jmx, mongo and ssh ports
EXPOSE 8080 2000 27017 22

CMD ["/usr/bin/supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
