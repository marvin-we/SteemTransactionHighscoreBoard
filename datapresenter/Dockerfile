FROM java:openjdk-8u111-jdk-alpine

ADD target/app /app

EXPOSE 8080

RUN chmod +x ./app/bin/datapresenter

ENTRYPOINT ./app/bin/datapresenter