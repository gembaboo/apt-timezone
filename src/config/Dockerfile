FROM java:8

ENV http_proxy=${http_proxy}
ENV https_proxy=${https_proxy}
ENV no_proxy=localhost,127.0.0.0/8

# Create directories
RUN mkdir -p /var/log /var/run/sshd /var/data/postgresql /usr/src/apt-timezone /data/db
RUN mkdir -p /var/tmp/export /var/tmp/upload/.failed /var/tmp/upload/.inprogress /var/tmp/upload/.processed
RUN ln -s /var/tmp/export /usr/src/apt-timezone/export
RUN ln -s /var/tmp/upload /usr/src/apt-timezone/upload

# Set volumes for data, logging and tmp
VOLUME ["/var/data", "/var/log", "/var/tmp" ]

# Force apt-get to use IPV4 (IPV 6 mirrors may not be accessible by the host)
RUN echo "Acquire::ForceIPv4 true;" > /etc/apt/apt.conf.d/99force-ipv4

# Update apt-get sources and install MongoDB, Supervisor, open-ssh and postgresql
RUN apt-get update && apt-get install -y supervisor openssh-server postgresql

# Install mongodb
RUN curl -SL "https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-debian71-3.0.4.tgz" -o mongo.tgz \
	&& tar -xvf mongo.tgz -C /usr/local --strip-components=1 \
	&& rm mongo.tgz

# Set the password for the ssh access
RUN echo 'root:root' | chpasswd
RUN sed -i 's/PermitRootLogin without-password/PermitRootLogin yes/' /etc/ssh/sshd_config

USER postgres
# RUN /etc/init.d/postgresql start && psql --command "CREATE USER docker WITH SUPERUSER PASSWORD 'docker';" && createdb -O docker docker
USER root

# Copy artifacts
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf
COPY postgresql.conf /etc/postgresql/9.4/main/postgresql.conf
COPY apt-timezone-${project.version}.jar /usr/src/apt-timezone/
COPY airport.json /var/tmp/export/

# Configure mongodb, load initial data
RUN useradd -ms /bin/bash mongodb
RUN chown -R mongodb /data/db
#USER mongodb
#RUN /usr/local/bin/mongod --fork --logpath /var/log/mongodb.log && \
#    mongoimport --db apt-timezone --collection airport --file /var/tmp/export/airport.json --jsonArray && \
#    /usr/local/bin/mongod --shutdown

USER root
EXPOSE 22 8080 2000 2050 5432 27017

CMD ["/usr/bin/supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
