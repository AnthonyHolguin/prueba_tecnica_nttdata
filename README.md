# prueba_tecnica_nttdata
Prueba técnica destinada a evaluar las habilidades y conocimientos de los  candidatos en el desarrollo de Java, con un enfoque específico en el uso de Spring Boot.

# Para levantar el proyecto se necesita docker

se compila cada proyecto desde su carpeta con ./mvnw.cmd clean package -DskipTests
Tambien puede ser mvn clean package -DskipTests

El programa de docker debe estar iniciado.

Luego en la carpeta de nttdata levanta y compila el docker con el comando docker-compose up --build 
Utilicé springdoc-openapi-starter-webflux-ui con el fin de generar el archivo YAML de OpenAPI para seguir en el enfoque Contract First API First

nttdata.postman_collection es una colección de postman de las pruebas que hice a los services

Usé Java 17.0.18
En clientes-app usé spring 4.0.6
En banca-web usé spring 3.4.1



