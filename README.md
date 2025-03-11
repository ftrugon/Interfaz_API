
# 1. Crea un repositorio en GitHub, que sea público y que tenga al profesor como colaborador directo del repositorio. Crea un README.md donde plantees los siguientes puntos:
## Nombre del proyecto:
### TAREAPI

## Descripción detallada de los documentos que intervendrán en el proyecto, así como sus campos.


* ### Usuario --> El usuario que se logeara en la base de datos y se pueda asignar,borrar o actualizar una tarea si es dueño de la propia tarea o administrador
- _id : String?
- username: String
- password: String
- email: String
- direccion: Direccion
- roles: String = "USER"

* ### Direccion --> La direccion del usuario
- calle:String
- num:String
- municipio:String
- provincia:String
- cp:String

* ### Tarea --> La tarea que se vaya a asignar al usuario
- _id : String?
- titulo: String
- texto: String
- estado: Boolean
- fecha_inicio: Date
- usuario: String?

# 2. En el README anteriormente construido deberás incluir lo siguiente (aparte de lo ya descrito)
## a. Indicar los endpoints que se van a desarrollar para cada documento.
## b. Describir cada uno de los endpoints. Realiza una explicación sencilla de cada endpoint.

### /users
- POST → /login → Autenticar un usuario y generar un token JWT.
- POST → /register → Registra un nuevo usuario si no esta en la base de datos
- GET → /getInfo → Obtiene toda la informacion de un usuario, sin pasarlo por un dto

## /tareas
- POST → /crearTarea → Crea una tarea
- GET → /obtenerTareas → Lista tus tareas asignadas
- GET → /obtenerTareasSinAsignar → Lista las tareas que no se han asignado
- GET → /obtenerTodasTareas → Lista todas las tareas de la base de datos, solo puede ser ejecutado por un administrador
- PUT → /asignarTarea/{idTarea} → Asigna la tarea al usuario que ha entrado en el endpoint
- PUT → /asignarTareaAUsuario/{username}/{tareaId} → Asigna una tarea a un usuario, solo puede ser ejecutado por un administrador
- PUT → /modTarea/{tareaId} → Modifica la tarea, el texto o el titulo
- PUT → /completarTarea/{tareaId} → Completa una tarea asignada
- PUT → /desmarcarTarea/{tareaId} → Desmarca una tarea asignada
- DELETE → /eliminarTarea/{idTarea} → Elimina una tarea, solo puede hacerlo un admin o el propietario de la tarea

## c. Describe la lógica de negocio que va a contener tu aplicación.

- ## Usuarios
* La contraseña se hashea antes de entrar a la base de datos
* No se puede crear un usuario con un username existente
* La contraseña no puede ser una cadena vacía, el nombre tampoco
* El usuario puede ser eliminado por sí mismo o por un administrador

- ## Tareas
* Una tarea solo puede ser asignada a un usuario
* Una tarea no puede tener campos vacios
* Si una tarea ya ha sido asignada no se puede reasignar
* Una tarea solo puede ser borrada por el propio usuario o admin
* Una tarea solo puede ser acabada por el usuario que se la asigno

- ## Direccion
* La direccion debe de ser correcta (provincia y municipio) 



## d. Describe las excepciones que vas a generar y los códigos de estado que vas a poner en todos los casos.
- 400 → Bad request → La información de la request no son válidos
- 401 → Unauthorized → El acceso no está autorizado
- 403 → Forbidden → El acceso necesita admin
- 404 → Not found → No encuentra algo en la base de datos
- 409 → Conflict → Se intenta insertar algo que ya está en la base de datos

## e. Describe las restricciones de seguridad que vas a aplicar dentro de tu API REST
- Todos los endpoints a excepción de login y register requieren de JWT, cada usuario requerirá de un token para acceder a los endpoints que requieren autorización
- Roles, aparte del jwt si intentas acceder a un endpoint que requiere el admin debes de tener el role
- Bcrypt, la contraseña entra a la base de datos encriptada
- En cada endpoint que se ha creado se valida la información

# PRUEBAS GESTION DE USUARIOS
* Todas estas pruebas están consultando a render y no a localhost, asi que render funciona
* Las pruebas que hice en la interfaz grafica no estan borradas, estan comentadas en el readme


- Iniciar sesion con un usuario existente
![image](https://github.com/user-attachments/assets/cadc629b-fcdf-436c-8035-d99ba99001af)
<!--
![img_1.png](src/main/resources/capturasDeFuncionamiento/img_1.png)
![img.png](src/main/resources/capturasDeFuncionamiento/img.png)
![img_2.png](src/main/resources/capturasDeFuncionamiento/img_2.png)
-->
- Iniciar sesion con credenciales incorrectas
![image](https://github.com/user-attachments/assets/3646b3c3-06ba-4afe-aa21-c466c8ced8c6)
<!--
![img_4.png](src/main/resources/capturasDeFuncionamiento/img_4.png)
![img_3.png](src/main/resources/capturasDeFuncionamiento/img_3.png)
-->
- Registrarse correctamente
![image](https://github.com/user-attachments/assets/b4cf1e4c-4906-4bac-b9e5-e9cac82afcab)
![image](https://github.com/user-attachments/assets/37b46ca3-0468-4502-b53c-c367f0de4e9e)
<!--
![img_6.png](src/main/resources/capturasDeFuncionamiento/img_6.png)
![img_5.png](src/main/resources/capturasDeFuncionamiento/img_5.png)
![img_7.png](src/main/resources/capturasDeFuncionamiento/img_7.png)
![img_8.png](src/main/resources/capturasDeFuncionamiento/img_8.png)
-->
- Registro incorrecto

![image](https://github.com/user-attachments/assets/596f929b-b87b-4550-9672-7dfc25644abd)
![image](https://github.com/user-attachments/assets/253502d9-1c93-4701-99ea-c7fa0ccbbda9)
![image](https://github.com/user-attachments/assets/461aea95-cedd-49ec-a965-85bcd73ce5b9)

<!--
![img_10.png](src/main/resources/capturasDeFuncionamiento/img_10.png)
![img_11.png](src/main/resources/capturasDeFuncionamiento/img_11.png)
![img_9.png](src/main/resources/capturasDeFuncionamiento/img_9.png)
-->

<!--
- videos demostrando el funcionamiento de la interfaz grafica, login y registro
- https://drive.google.com/file/d/1u-fG6bRFW7P0V1mL9h0HFCUSNWxk0N31/view?usp=sharing
- https://drive.google.com/file/d/1Znc8UbSSNjrjdEDooLWrA_4seVOkr9OA/view?usp=sharing
-->

# PRUEBAS GESTION DE TAREAS

* ### TOKEN DE ADMINISTRADOR: 
* eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZnJhbiIsImV4cCI6MTc0MDU4OTMwMSwiaWF0IjoxNzQwNTg1NzAxLCJyb2xlcyI6IlJPTEVfQURNSU4ifQ.eHrGxsBl4iHD57D5alsug5E324_wwIBF8k2X4pDcUNWYooBm7FQMY1lvcJcbIm5ihhyCurZ9cM78qtWKu1NORRe_PcFTsETqbYJm4ksSudoUYAWxk3KRRS9AKyhKYxZO63AyMlg0MbudAdiKsd8EOaHG3_oZI9oV_feOto7xJrQlTzNDG-vhP24fGO5BaRfCtzJkskO_0pqe1ctLDs2jSXm60kzXanKJciDPjGJHqcPlCvN8IAzq7iGFb8iQ4X64tEanvKwkJVFcUyjl9zZlDlFad4Jqg6aBudmWlXVpRHpDss_BT4mrHMJKPlepEq-HZApMpakl0b7FYy_FQwrizw
### TOKEN DE USUARIO:
* eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZnJhbmNlc2MiLCJleHAiOjE3NDA1ODkzMjgsImlhdCI6MTc0MDU4NTcyOCwicm9sZXMiOiJST0xFX1VTRVIifQ.STC0RGynn35XfDVAB_OL0AIiodQw-VbBWQfpTYDYvm1n3dLg0GjUYaMB2v6Hs7sYUu8gS9uztUj7tb13eS47f-w1NstS1aQxUuw86H8IOlbirLPh0NB3SnJnpAxy-SgHeqwINcgqUlO2unYIaVupMqXKGaeKrbZDzy3kAPLYegKQBuYHcuyHbf6kWsDifkKK6o_0dC2mMdoOUTMDuM_ANt58tQTP88HyYSj71i8-oYLVuK80wsmWVchm5ZcYL6ycAp4T12qzRfCvYMqcZOBqqgsicEmp6FjZZOL5V-dndJZkT-gbc57Od-o5iSPL5jDuj4N6ps1h0VgQFnuSjD8XLQ

- ## Insertar una tarea
- Necesitas un jwt para que funcione, y los campos de la tarea no pueden estar vacios

* Caso correcto 
![image](https://github.com/user-attachments/assets/e8cfd0e8-0ed5-49c7-a4f9-5af51c1af50e)

* No hay autorizacion ( En todos funciona y es igual asi que no pondre mas esta captura)
![image](https://github.com/user-attachments/assets/525f54b7-e1dc-48b1-aa99-16538ee0ec75)

* El json esta mal
![image](https://github.com/user-attachments/assets/acfffa01-2aa6-4013-928a-45482487d387)


- ## Obtener tus tareas asignadas
- Necesitas un jwt, si no tienes tareas asignadas te devuelve una lista vacia, para asignarte a una tarea es otro endpoint

* Caso correcto
![image](https://github.com/user-attachments/assets/3b737e9a-4030-400b-9031-bc80395a7b00)

- ## Obtener las tareas que no se han asignado
- Necesitas un jwt, si no hay tareas devuelve una lista vacia

* Caso correcto
![image](https://github.com/user-attachments/assets/d16223e4-9de2-4e93-9834-5503b18f90ca)

- ## Obtener todas las tareas
- Necesitas un jwt y ser administrador

* Caso correcto
![image](https://github.com/user-attachments/assets/9ab0bb3c-be5b-48dd-8131-f624230adf12)

* No eres admin
![image](https://github.com/user-attachments/assets/07b85ada-ae3a-44a1-a833-cb92069475dd)

- ## Asignarse una tarea
- Necesitas un jwt, la tarea no puede se puede asignar si ya esta asignada y que la tarea exista

* Caso correcto
![image](https://github.com/user-attachments/assets/ef22cfc7-0c5e-4390-8c86-cea699a77f10)

* La tarea ya estaba asignada
![image](https://github.com/user-attachments/assets/d6138df2-9ea8-45fb-8bc9-6cf8dffbb983)

* La tarea no existe
![image](https://github.com/user-attachments/assets/14b2acb1-145d-4ca4-8de0-8ab35880f9be)

- ## Asignar tarea a un usuario
- Necesitas un jwt, ser admin, que la tarea no tenga a nadie asignado y que la tarea y el usuario existan

* Caso correcto
![image](https://github.com/user-attachments/assets/499378dc-2e04-4eb1-b9db-2de1e541c217)

* No eres admin
![image](https://github.com/user-attachments/assets/bf482366-edbb-4101-b01f-0ec8e9f7b244)

* La tarea ya estaba asignada
![image](https://github.com/user-attachments/assets/1fe9007c-c104-4563-a1f5-cbb21df49397)

* La tarea no existe
![image](https://github.com/user-attachments/assets/16bba343-582a-4287-be3f-a2c193f7a0a0)

* El usuario no existe
![image](https://github.com/user-attachments/assets/130a6487-f165-4121-8a5a-0d673ce50cd0)

- ## Modificar el titulo o texto de una tarea
- Necesitas un jwt, ser admin o el que tiene la tarea asignada, el json de la tarea tiene que ser correcto(titulo y texto no pueden estar vacios), la tarea tiene que estar asignada y la tarea tiene que existir

* Caso correcto
![image](https://github.com/user-attachments/assets/328493a7-0027-4cae-9535-db162ca91427)

* No eres el que tiene la tarea asignada
![image](https://github.com/user-attachments/assets/9492f879-5c1e-45b2-92e7-638ae16e7cec)

* Json mal
![image](https://github.com/user-attachments/assets/b95c6e42-fff8-4260-aa6c-c67d45073519)

* La tarea no existe
![image](https://github.com/user-attachments/assets/d35e66bb-e628-4299-8922-531cc13beacb)

- ## Completar una tarea y desmarcar una tarea, es practicamente el mismo endpoint
- Necesitas un jwt, la tarea tiene que estar asignada al que realiza la peticion, la tarea tiene que estar por acabar y que la tarea exista

* Caso correcto
![image](https://github.com/user-attachments/assets/152b28eb-4f2f-4cec-9513-28170f649936)

* No tienes la tarea asignada
![image](https://github.com/user-attachments/assets/791fcb92-5651-455b-a9b9-41e5d7b77a21)

* No existe la tarea
![image](https://github.com/user-attachments/assets/8e3e3bf1-d5fb-4dbb-b800-907b1ab9d07e)

* La tarea esta asignada a otro usuario
![image](https://github.com/user-attachments/assets/af57ac71-ad99-4222-9e37-1b28c504a4de)

* La tarea ya esta acabada 
![image](https://github.com/user-attachments/assets/15216479-70a5-4067-b4e9-c8132b65536f)

![image](https://github.com/user-attachments/assets/1c50e058-bc12-4afa-8125-690b0e04d184)


- ## Eliminar una tarea
- Necesitas un jwt y ser admin o el que tiene la tarea asignada

* Caso correcto
![image](https://github.com/user-attachments/assets/480404b7-5ad9-4588-893a-666cee858442)

* No existe la tarea
![image](https://github.com/user-attachments/assets/d98b9a3d-9d8f-48b9-b6e9-8518e5670ddb)

* No eres el que tiene la tarea asignada o no eres admin
![image](https://github.com/user-attachments/assets/4e5e8a33-e4cd-4222-81c7-0774110e70ce)

# FUNCIONAMIENTO DE LA INTERFAZ GRAFICA
* https://drive.google.com/file/d/1TTjsL4PGNh62h9qsk5H8ae1lAs3CoDaZ/view?usp=sharing
