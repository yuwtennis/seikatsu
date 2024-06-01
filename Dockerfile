FROM debian:bookworm-slim

RUN apt update && \
    apt install -y curl java-common && \
    apt-get clean
RUN curl https://corretto.aws/downloads/latest/amazon-corretto-21-x64-linux-jdk.deb -LO && \
    dpkg --install amazon-corretto-21-x64-linux-jdk.deb && \
    rm -f amazon-corretto-21-x64-linux-jdk.deb

