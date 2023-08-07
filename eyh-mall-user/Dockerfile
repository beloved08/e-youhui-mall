FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./eyh-mall-user-0.0.1-SNAPSHOT.jar ./user.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar"]
CMD ["user.jar"]
