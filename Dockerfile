FROM ubuntu:latest

#Parameter to be provided by run  

ARG DB_NAME=mvntutorial
ARG DATASOURCE=mysqljpa
ARG APP=mvntutorial
ENV DB_NAME ${DB_NAME} 
ENV DATASOURCE ${DATASOURCE}
ENV APP ${APP}

# Install Maven
ARG USER_HOME_DIR="/root"

USER root
RUN \
    apt-get update && \
    apt-get install -y curl && \
    apt-get install -y expect && \
    apt-get install -y  sudo

# Install java

RUN apt-get install -y openjdk-11-jre openjdk-11-jdk \
    && echo "JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64/" >> /etc/environment

# Install mysql
RUN echo "=> APP= ${APP} , DB= ${DB_NAME}, DS= ${DATASOURCE} "
ENV DB_USER mvntutorial
ENV DB_PASS mvntutorial
ENV DB_URI localhost:3306
RUN \
    apt-get install -y mysql-server mysql-client

RUN chmod -R 755 /var/lib/mysql/
COPY ./my.cnf /etc/mysql/my.cnf
COPY ./init.sql /tmp

RUN usermod -d /var/lib/mysql/ mysql 

RUN sudo service mysql start \
    && sudo mysql -u root -e "CREATE USER '${DB_USER}'@'localhost' IDENTIFIED BY '${DB_PASS}';"\
    && sudo mysql -u root  -e "GRANT ALL PRIVILEGES ON * . * TO '${DB_USER}'@'localhost';"\
    && sudo mysql -u root  -e "FLUSH PRIVILEGES;" \
    && sudo mysql -u root  -e "CREATE DATABASE IF NOT EXISTS ${DB_NAME};" \
    && sudo mysql -u root  -e "USE ${DB_NAME};" \
    && sudo mysql -u root ${DB_NAME} < /tmp/init.sql;


# Update aptitude with new repo
RUN apt-get update

# Projekt bauen

#Install wildfly

# Set the WILDFLY_VERSION env variable
ENV WILDFLY_VERSION 21.0.1.Final
ENV WILDFLY_SHA1 949ab4e1b608f5dbdd2d63c7443cdaba0bc5c07b
ENV JBOSS_HOME /opt/jboss/wildfly
ENV WILDFLY_USER developper
ENV WILDFLY_PASS developper


ENV MYSQL_VERSION 8.0.12
ENV JBOSS_CLI /opt/jboss/wildfly/bin/jboss-cli.sh
ENV DEPLOYMENT_DIR /opt/jboss/wildfly/standalone/deployments/


RUN mkdir -p $JBOSS_HOME &&\
    groupadd -r jboss &&\
    useradd -r -g jboss -d $JBOSS_HOME -s /sbin/nologin jboss
# Add the WildFly distribution to /opt, and make wildfly the owner of the extracted tar content
# Make sure the distribution is available from a well-known place
RUN cd $HOME \
    && curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz \
    && sha1sum wildfly-$WILDFLY_VERSION.tar.gz | grep $WILDFLY_SHA1 \
    && tar xf wildfly-$WILDFLY_VERSION.tar.gz \
    && cp -rf $HOME/wildfly-$WILDFLY_VERSION/* $JBOSS_HOME \
    && rm wildfly-$WILDFLY_VERSION.tar.gz \
    && chown -R jboss:0 ${JBOSS_HOME} \
    && chmod -R g+rw ${JBOSS_HOME}

RUN mkdir -p ${JBOSS_HOME}/webapp


RUN  chown -R jboss:0 ${JBOSS_HOME} \
     && chmod -R g+rw ${JBOSS_HOME}
#ENV JAVA_OPTS

# Setting up WildFly Admin Console
RUN echo "=> Adding WildFly administrator"

RUN $JBOSS_HOME/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASS --silent

#Configure Wildfly server

RUN echo "=> Starting WildFly server" && sudo service mysql start &&\
      bash -c '$JBOSS_HOME/bin/standalone.sh &' && \
    echo "=> Waiting for the server to boot" && \
      bash -c 'until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do echo `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null`; sleep 1; done' && \
    echo "=> Downloading MySQL driver" && \
      curl --location --output /tmp/mysql-connector-java-${MYSQL_VERSION}.jar --url http://search.maven.org/remotecontent?filepath=mysql/mysql-connector-java/${MYSQL_VERSION}/mysql-connector-java-${MYSQL_VERSION}.jar && \
    echo "=> Adding MySQL module" && \
      $JBOSS_CLI --connect --command="module add --name=com.mysql --resources=/tmp/mysql-connector-java-${MYSQL_VERSION}.jar --dependencies=javax.api,javax.transaction.api" && \
    echo "=> Adding MySQL driver" && \
                                     #/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql.driver,driver-class-name=com.mysql.jdbc.Driver)
      $JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-class-name=com.mysql.cj.jdbc.Driver)" && \
    echo "=> Creating a new datasource" && \
      $JBOSS_CLI --connect --command="data-source add \
        --name=${DATASOURCE} \
        --jndi-name=java:/jboss/datasources/${DATASOURCE} \
        --user-name=${DB_USER} \
        --password=${DB_PASS} \
        --driver-name=mysql \
        --connection-url=jdbc:mysql://${DB_URI}/${DB_NAME}?allowPublicKeyRetrieval=true&useSSL=false \
        --use-ccm=false \
        --max-pool-size=25 \
        --blocking-timeout-wait-millis=5000 \
        --enabled=true" && \
    echo "=> add static webapp filter" && \
 #     $JBOSS_CLI --connect --command="/subsystem=undertow/configuration=handler/file=webapp/:add(cache-buffer-size=1024,cache-buffers=1024,directory-listing=true,follow-symlink=true,path=${JBOSS_HOME}/webapp/assiwo)" &&\ 
 #     $JBOSS_CLI --connect --command="/subsystem=undertow/server=default-server/host=default-host/location=\/${APP}/:add(handler=webapp)" &&\
    echo "=> Shutting down WildFly and Cleaning up" && \
      $JBOSS_CLI --connect --command=":shutdown" && \
      rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
      rm -f /tmp/*.jar

# Ensure signals are forwarded to the JVM process correctly for graceful shutdown
ENV LAUNCH_JBOSS_IN_BACKGROUND true

# Expose http and admin ports
EXPOSE 8080 9990 3306 8443

#echo "=> Restarting WildFly"
# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interfaces

# install ionic and get the app from git and build it and deploy into wildfly
CMD service mysql start &&\
    sudo mysql -u root -e "CREATE USER IF NOT EXISTS '${DB_USER}'@'%' IDENTIFIED BY '${DB_PASS}';" &&\
    sudo mysql -u root -e "GRANT ALL PRIVILEGES ON *.* TO '${DB_USER}'@'%' WITH GRANT OPTION;" &&\
    $JBOSS_HOME/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0
