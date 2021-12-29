FROM openjdk:17
LABEL maintainer="https://github.com/markedo"
EXPOSE 8080
RUN mkdir ./app
COPY ./exchange-rate-analyser-0.0.1.jar ./app
CMD java -jar ./app/exchange-rate-analyser-0.0.1.jar
