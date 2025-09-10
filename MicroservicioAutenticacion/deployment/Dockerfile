#Etapa 1: Build con Gradle y JDK 21 (compilacion)
FROM gradle:8.14.3-jdk-21 AS build
COPY --chown=gradle:gradle . /deployment
WORKDIR /deployment
RUN gradle bootJar -x validateStructure --no-daemon

#Etapa 2: Runtme con JDK 21 (ejecucion)
FROM eclipse-temurin:21-jdk-alpine
#Validar este directorio de trabajo ojo
WORKDIR /deployment
COPY --from=build  /deployment/build/libs/*.jar CrediYa.Autenticacion.jar
ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8080
USER appuser
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","CrediYa.Autenticacion.jar"]

#validar lo de abajo
# Replace with a non-root user to avoid running the container with excessive privileges
# USER appuser
# ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar CrediYa.Autenticacion.jar" ]
