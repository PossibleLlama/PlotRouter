FROM maven:3.6.0-jdk-8-alpine as builder

COPY . /src
WORKDIR /src/plotpoints

RUN mvn package

FROM openjdk:8u191-jdk-alpine3.8

COPY --from=builder /src .
WORKDIR /plotpoints/target
