FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./eyh-mall-aliyun-0.0.1-SNAPSHOT.jar ./aliyun.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar"]
CMD ["aliyun.jar"]
