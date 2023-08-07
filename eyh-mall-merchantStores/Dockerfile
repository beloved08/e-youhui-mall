FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./eyh-mall-merchantStores-0.0.1-SNAPSHOT.jar ./merchantStores.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar"]
CMD ["merchantStores.jar"]
