FROM radut/openjdk-17
WORKDIR /app
EXPOSE 8080
COPY /build/libs/JuMarket-0.0.1-SNAPSHOT.jar /app/jumarket-app.jar
ENTRYPOINT ["java", "-jar", "jumarket-app.jar"]