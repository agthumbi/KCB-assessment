FROM registry.access.redhat.com/ubi9/openjdk-21:1.18-1

LABEL maintainer="KCB Group - BSS bssolutions@kcbgroup.com" version="1.0.0"

ENV PORT 8080

ENV TZ=Africa/Nairobi

USER root

RUN microdnf update -y

RUN microdnf clean all -y

USER jboss

COPY target/*.jar /opt/application.jar

WORKDIR /opt

ENTRYPOINT exec java  -jar application.jar
