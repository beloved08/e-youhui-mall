FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./eyh-mall-logistics-0.0.1-SNAPSHOT.jar ./logistics.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar"]
CMD ["logistics.jar"]
