FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./eyh-mall-gateway-0.0.1-SNAPSHOT.jar ./gateway.jar
EXPOSE 10010
ENTRYPOINT ["java","-jar"]
CMD ["gateway.jar"]
