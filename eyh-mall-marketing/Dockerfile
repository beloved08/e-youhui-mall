FROM openjdk:8
ENV APP_HOME=/apps
WORKDIR $APP_HOME
COPY ./eyh-mall-marketing-0.0.1-SNAPSHOT.jar ./marketing.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar"]
CMD ["marketing.jar"]
