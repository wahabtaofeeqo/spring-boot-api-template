FROM openjdk:8-jdk-alpine
WORKDIR /api
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run