FROM openjdk:17
EXPOSE 8080
RUN mkdir ./app
COPY ./exratesanalyser.jar ./app
CMD java -jar ./app/exratesanalyser.jar