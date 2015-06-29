FROM java:8

# Create directories
RUN mkdir -p /var/log /var/data/mongodb /var/run/sshd /var/data/postgresql

# Set volumes for data, logging and tmp
VOLUME ["/var/data", "/var/log", "/var/tmp" ]

# Update apt-get sources and install MongoDB, Supervisor, open-ssh and postgresql
RUN apt-get update && apt-get install -y mongodb supervisor openssh-server postgresql

# Set the password for the ssh access
RUN echo 'root:root' | chpasswd
RUN sed -i 's/PermitRootLogin without-password/PermitRootLogin yes/' /etc/ssh/sshd_config

USER postgres
# RUN /etc/init.d/postgresql start && psql --command "CREATE USER docker WITH SUPERUSER PASSWORD 'docker';" && createdb -O docker docker
USER root

# Log directory for Supervisor
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf
COPY postgresql.conf /etc/postgresql/9.4/main/postgresql.conf
COPY apt-timezone-${project.version}.jar /usr/src/apt-timezone/

EXPOSE 22 8080 2000 2050 5432 27017

CMD ["/usr/bin/supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
