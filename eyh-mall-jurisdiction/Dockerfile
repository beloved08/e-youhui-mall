FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./eyh-mall-jurisdiction-0.0.1-SNAPSHOT.jar ./jurisdiction.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar"]
CMD ["jurisdiction.jar"]
