#
# Build stage
#
FROM maven:3.9.4-amazoncorretto-20 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM amazoncorretto:20.0.2
COPY --from=build /target/myDCAnimationList-0.0.1-SNAPSHOT.jar myDCAnimationList.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "myDCAnimationList.jar"]
