FROM centos:centos7

ENV JAVA_HOME /jdk1.8.0_51
# ENV SPARK_HOME /usr/local/lib/spark-1.4.1-bin-hadoop2.6


# RUN yum install -y sudo wget which tar git vim
RUN yum install -y wget tar vim

# java
RUN wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u51-b16/jdk-8u51-linux-x64.tar.gz" && \
  tar xf jdk-8u51-linux-x64.tar.gz && \
  rm jdk-8u51-linux-x64.tar.gz

ENV PATH $PATH:$JAVA_HOME/bin

# flink
RUN cd /usr/local/lib && wget --no-check-certificate http://www.us.apache.org/dist/flink/flink-0.9.1/flink-0.9.1-bin-hadoop2.tgz
RUN cd /usr/local/lib && tar -xvf flink-0.9.1-bin-hadoop2.tgz
RUN cd /usr/local/lib && rm flink-0.9.1-bin-hadoop2.tgz
ENV FLINK_HOME /usr/local/lib/flink-0.9.1
ENV PATH $PATH:$FLINK_HOME/bin
RUN chmod og+w -R $FLINK_HOME



# # scala
# RUN wget --no-cookies --no-check-certificate http://www.scala-lang.org/files/archive/scala-2.11.7.rpm && \
	# yum install -y scala-2.11.7.rpm && \
	# rm scala-2.11.7.rpm

# # sbt
# RUN curl https://bintray.com/sbt/rpm/rpm | tee /etc/yum.repos.d/bintray-sbt-rpm.repo && \
	# # yum install -y sbt

RUN cd /tmp  && wget -O hamlet.txt http://www.gutenberg.org/cache/epub/1787/pg1787.txt

#Install Maven
RUN cd /usr/local/lib && wget http://apache.cs.utah.edu/maven/maven-3/3.0.5/binaries/apache-maven-3.0.5-bin.tar.gz
RUN cd /usr/local/lib && tar -xvzf apache-maven-3.0.5-bin.tar.gz
RUN cd /usr/local/lib && rm -rf apache-maven-3.0.5-bin.tar.gz

ENV M2_HOME /usr/local/lib/apache-maven-3.0.5
ENV PATH $PATH:$JAVA_HOME/bin:$M2_HOME/bin

# curl http://flink.apache.org/q/quickstart.sh | bash

CMD echo "start-local.sh : to start JobManger's web frontend http://localhost:8081" && \
    bash

