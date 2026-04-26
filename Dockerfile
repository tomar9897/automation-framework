FROM eclipse-temurin:17-jdk

RUN apt-get update && apt-get install -y \
maven \
wget \
unzip \
curl \
gnupg \
ca-certificates

RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" \
> /etc/apt/sources.list.d/google.list && \
apt-get update && \
apt-get install -y google-chrome-stable

WORKDIR /app

COPY . .

ENTRYPOINT ["mvn","test"]
