#Etapa 0 : db
FROM mariadb:lastes
COPY ScriptInit.sql /docker-entrypoint-initdb.d/auth

#Etapa 1: Build con Gradle y JDK 21 (compilacion)
FROM gradle:8.14.3-jdk-21-and-24-noble AS build
COPY --chown=gradle:gradle . /MicroservicioAutenticacion
WORKDIR /MicroservicioAutenticacion
RUN gradle bootJar -x validateStructure --no-daemon

#Etapa 2: Runtme con JDK 21 (ejecucion)
FROM eclipse-temurin:21-jdk-noble
#Validar este directorio de trabajo ojo
WORKDIR /MicroservicioAutenticacion
COPY --from=build  MicroservicioAutenticacion/applications/app-service/build/libs/*.jar CrediYa.Autenticacion.jar
ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","CrediYa.Autenticacion.jar"]

#validar lo de abajo
# Replace with a non-root user to avoid running the container with excessive privileges
# USER appuser
# ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar CrediYa.Autenticacion.jar" ]
