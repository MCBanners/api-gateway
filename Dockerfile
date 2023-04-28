# Prepare environment
FROM alpine:3.17
RUN apk add openjdk16

# Download source code
RUN git clone https://github.com/MCBanners/api-gateway /app
WORKDIR /app

# Build the source into a binary
RUN ./gradlew clean build shadowJar

# Package the application
ENV GATEWAY_JWT_SECRET=""
CMD /bin/sh -c "GATEWAY_JWT_SECRET=$GATEWAY_JWT_SECRET java -Xms128M -Xmx128M -jar build/libs/gateway.jar"