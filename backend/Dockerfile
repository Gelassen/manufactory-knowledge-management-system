# Use Alpine as the base image
FROM alpine:3.19.1

# Install OpenJDK 17, curl, and bash
RUN apk add --no-cache openjdk17 curl bash

# Download wait-for-it.sh script and make it executable
RUN curl -o /usr/local/bin/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
    && chmod +x /usr/local/bin/wait-for-it.sh \
    && ls -l /usr/local/bin/wait-for-it.sh  # Optional, to verify download

# Copy application code into the container
COPY .. /tmp

# Expose the port your app will run on
EXPOSE 8080

# Set the working directory
WORKDIR /tmp

# Make gradle wrapper executable
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew build

# Use wait-for-it to wait for the PostgreSQL database to be available before starting the app
ENTRYPOINT ["/usr/local/bin/wait-for-it.sh", "postgres-db:5432", "--", "./gradlew", "bootRun"]
