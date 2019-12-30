FROM maven:3.6.3-jdk-11-slim as builder

COPY . /src
WORKDIR /src/plotpoints

RUN mvn package
RUN mvn jacoco:prepare-agent jacoco:report

FROM openjdk:11.0.5-jre-stretch

COPY --from=builder /src .
WORKDIR /plotpoints/target
