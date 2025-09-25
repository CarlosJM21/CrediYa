#Etapa 1: Build con Gradle y JDK 21 (compilacion)
FROM gradle:8.14.3-jdk-21-and-24-noble  AS build
COPY --chown=gradle:gradle . /MicroservicioReportes
WORKDIR /MicroservicioReportes
RUN gradle bootJar -x validateStructure --no-daemon

#Etapa 2: Runtme con JDK 21 (ejecucion)
FROM eclipse-temurin:21-jdk-alpine
#Validar este directorio de trabajo ojo
WORKDIR /MicroservicioReportes
COPY --from=build  MicroservicioReportes/applications/app-service/build/libs/*.jar CrediYa.Reports.jar
ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8082
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","CrediYa.Reports.jar"]
