FROM eclipse-temurin:17-jdk

RUN apt-get update && apt-get install -y \
maven \
wget \
unzip \
chromium \
chromium-driver

WORKDIR /app

COPY . .

ENTRYPOINT ["mvn","test"]
