FROM alpine:3.19.1
COPY .. /tmp
EXPOSE 8080
RUN apk add openjdk17
WORKDIR /tmp
RUN chmod +x ./gradlew
RUN ./gradlew build
ENTRYPOINT ["./gradlew", "bootRun"]
