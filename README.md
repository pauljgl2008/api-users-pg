# Java - api-users-smartjob

- Banco de datos en memoria (H2).
- Proceso de build vía Maven.
- Persistencia con JPA.
- Framework SpringBoot.
- Java 21
- Entrega en un repositorio público (github) con el código fuente y script de creación de BD.
- Readme explicando cómo probarlo.
- Diagrama de la solución.
- JWT como token, el token tiene una duración de 15 minutos.
- Pruebas unitarias (Junit y JaCoCo)
- Swagger
- Patrones de Diseño y buenas practicas

# BD

- El script de la base de datos con H2 se encuentra en la ruta src/main/resources/data.sql
- Se incluye el script de creación de la base de datos y la inserción de un usuario para que sirva en el inicio de sesión

# Cómo ejecutar el proyecto?

1. Limpia, compila, prueba y guarda el proyecto en el repositorio local de Maven

    `mvn clean install`

2. Ejecuta la aplicación Spring Boot directamente desde el código fuente

   `mvn spring-boot:run`

# Pruebas
## Swagger

1. Accedemos a la interfaz del Swagger

   http://localhost:8080/swagger-ui/index.html

2. Inicia sesión con el email y password del usuario cargado en memoria.

`curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "email": "paul@smartjob.com",
  "password": "hunter2"
}'`

3. Para listar usuarios, utiliza el endpoint GET "/api/v1/users"; el token es un ejemplo obtenido en el login

`curl -X 'GET' \
  'http://localhost:8080/api/v1/users' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MzQwOTU4ODAsImlzcyI6Ind3dy5zbWFydGpvYi5jb20iLCJzdWIiOiJwYXVsQHNtYXJ0am9iLmNvbSIsImV4cCI6MTczNDA5Njc4MH0.OMMrlAgmVwCBlIjvNUcZA00dNk_WXmpygFuZQWUFNC-LVGdvyyahP0Oh0OtDYSx1'`

4. Para crear un nuevo usuario, utiliza el endpoint POST "/api/v1/users"; el token es un ejemplo obtenido en el login

`curl -X 'POST' \
  'http://localhost:8080/api/v1/users' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MzQwOTU4ODAsImlzcyI6Ind3dy5zbWFydGpvYi5jb20iLCJzdWIiOiJwYXVsQHNtYXJ0am9iLmNvbSIsImV4cCI6MTczNDA5Njc4MH0.OMMrlAgmVwCBlIjvNUcZA00dNk_WXmpygFuZQWUFNC-LVGdvyyahP0Oh0OtDYSx1' \
  -H 'Content-Type: application/json' \
  -d '{
"name": "Juan Rodriguez",
"email": "juan@rodriguez.org",
"password": "hunter2",
"phones": [
{
"number": "1234567",
"cityCode": "1",
"countryCode": "57"
}
]
}'`

## Postman

Si deseas probarlo con Postman, puedes importar la colección "api-users.postman_collection.json" que está en la raíz del proyecto.

Para probar con CURL ejecutar:

Inicio de sesión (Login):

`curl --location 'localhost:8080/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
"email": "paul@test.com",
"password": "hunter2"
}'`

Obtener usuarios (Get Users):

`curl --location 'localhost:8080/api/v1/users' \
--header 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTEzMDU0NTAsImlzcyI6Ind3dy5uaXN1bS5jb20iLCJzdWIiOiJwYXVsQG5pc3VtLmNvbSIsImV4cCI6MTcxMTMwNjM1MH0.751WgCyol5REEwhhdVfoj2BbVvEksfgU4_ftsf85-Uz3-LAfYYq0JJd_8dPxWnJm'`

Crear usuario (Save User):

`curl --location 'localhost:8080/api/v1/users' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTEzMDU0NTAsImlzcyI6Ind3dy5uaXN1bS5jb20iLCJzdWIiOiJwYXVsQG5pc3VtLmNvbSIsImV4cCI6MTcxMTMwNjM1MH0.751WgCyol5REEwhhdVfoj2BbVvEksfgU4_ftsf85-Uz3-LAfYYq0JJd_8dPxWnJm' \
--data-raw '{
"name": "Juan Rodriguez",
"email": "juan@rodriguez.com",
"password": "hunter2",
"phones": [
{
"number": "1234567",
"cityCode": "1",
"countryCode": "57"
}
]
}'`
