FROM clojure:openjdk-11-lein-slim-buster AS build
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN lein uberjar

FROM openjdk:11-jre-slim
WORKDIR /usr/src/app
COPY --from=build /usr/src/app/target/uberjar/*-standalone.jar ./app.jar

CMD ["java", "-jar", "app.jar"]

RUN apt-get update -q && apt-get install -yq wget && \
    wget -O /dd-java-agent.jar 'https://dtdg.co/latest-java-tracer' && \
    apt-get purge -y --auto-remove gnupg wget

