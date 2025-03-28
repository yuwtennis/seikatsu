FROM debian:trixie-slim AS build

WORKDIR /work
COPY . ./

RUN apt update && apt install -y openjdk-21-jdk
RUN ./gradlew assemble

FROM debian:trixie-slim

# TODO Switch to distroless when it supports trixie for using java 21

WORKDIR /app
COPY --from=build /work/app/build/distributions/app-*.tar .
RUN apt update &&  \
    apt install -y openjdk-21-jdk &&  \
    apt clean \
RUN tar --strip-components=1  xvf app-*.tar

ENTRYPOINT ["/app/bin/app"]