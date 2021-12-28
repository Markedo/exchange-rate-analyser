FROM openjdk:17
RUN mkdir ./app
COPY ./exratesanalyser.jar ./app
CMD java -jar ./app/exratesanalyser.jar