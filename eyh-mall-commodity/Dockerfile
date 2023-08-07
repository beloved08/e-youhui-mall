FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./eyh-mall-commodity-0.0.1-SNAPSHOT.jar ./commodity.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar"]
CMD ["commodity.jar"]
