FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./eyh-mall-pay-0.0.1-SNAPSHOT.jar ./pay.jar
EXPOSE 8088
ENTRYPOINT ["java","-jar"]
CMD ["pay.jar"]
