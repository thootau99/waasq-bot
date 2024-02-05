FROM openjdk:21-slim
WORKDIR /src/app

COPY . .

RUN ./mvnw install

#ENTRYPOINT ["java","-jar","/src/app/target/that-chat.jar"]