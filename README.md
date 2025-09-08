## CrediYa 📄
"CrediYa" es una plataforma que busca digitalizar y optimizar la gestión de solicitudes de préstamos personales, eliminando la necesidad de procesos manuales y presenciales
todo esto basado en un proyecto de microservicios y arquitectura hexagonal tomando como tecnologia principal Java con Webflux para la reactividad usando el plugin de 
Scaffolding se  bancolombia.

## Arquitectuta Hexagonal 🏗️

El proyecto esta estructurado en modulos siguiendo esta arquitectura limpia:

- **Domain**: Contiene los modelos y las reglas de negocio.
- **Usecases**: Implementa la logica de la aplicacion para cada una de las funcionalidades..
- **Infrastructure**: Contiene tanto los adaptadores como los puntos de entrada.
- **Application**: Contiene los ensamblado,es el punto de arranque y la configuracion general.


## Tecnologias ☕

- ☕ Java 21+
- 🌐 Spring WebFlux
- 🗄️ R2DBC (Mariadb_MySQL)
- 🏗️ Gradle (multi-modulo)
- 📋 SLF4J for logging
- 📄 OpenAPI/Swagger para documentación de API's

## Referencias 🔗
- [Scaffold Clean Architecture Documentation](https://bancolombia.github.io/scaffold-clean-architecture/docs/intro)
- [ntroducción a la arquitectura basada en eventos 👨‍🏫: Construye sistemas escalables 🚀](https://medium.com/@diego.coder/introducci%C3%B3n-a-la-arquitectura-orientada-a-eventos-a532c71c9945)
- [Clean Architecture – Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)
