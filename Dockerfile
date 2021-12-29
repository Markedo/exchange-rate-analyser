FROM openjdk:17
LABEL maintainer="https://github.com/markedo"
EXPOSE 8080
RUN mkdir ./app
COPY ./exratesanalyser.jar ./app
CMD java -jar ./app/exchange-rate-analyser.jar