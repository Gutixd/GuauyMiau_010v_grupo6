
# GuauMiau — App de Gestión de Mascotas

Aplicación móvil desarrollada en Kotlin + Jetpack Compose, integrada a microservicios en Spring Boot, con consumo de API externa, CRUD completo, validaciones, pruebas unitarias y APK firmado.

Repositorio oficial del proyecto:
https://github.com/Gutixd/GuauyMiau_010v_grupo6.git

## 1. Integrantes
Equipo 6 — Sección 010V

- Diego Gutiérrez — Desarrollo Android
- Felipe Sandoval — Desarrollo Backend

## 2. Descripción del Proyecto
GuauMiau es una aplicación que permite administrar mascotas de forma simple y ordenada.  
El usuario puede registrar, editar, eliminar y visualizar sus mascotas, además de obtener imágenes reales desde una API externa.

Incluye:
- CRUD completo de mascotas
- Persistencia y conexión remota
- API externa (dog.ceo)
- Arquitectura MVVM
- Validaciones
- Pruebas unitarias
- APK firmado para distribución

## 3. Arquitectura del Proyecto

### Frontend (Android)
- Kotlin
- Jetpack Compose
- Navigation Compose
- Room
- Hilt
- Coroutines + StateFlow
- Retrofit2

### Backend (Spring Boot)
- Spring Web
- Servicios + Controladores
- CRUD Repository
- Base de datos H2/MySQL

### Patrón general
- MVVM
- Repository Pattern

## 4. Funcionalidades Implementadas

### Frontend
- Crear mascota
- Editar mascota
- Eliminar mascota
- Listado dinámico
- API externa integrada
- Manejo de estados con Flow
- Interfaz Material 3
- Lógica validada
- Pruebas unitarias

### Backend
- CRUD de mascotas
- Endpoints REST
- Persistencia en base de datos
- Integración con la app

## 5. API Externa Utilizada
Dog CEO API  
Endpoint:
https://dog.ceo/api/breeds/image/random

## 6. Endpoints del Microservicio

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /pets/{userId} | Retorna mascotas por usuario |
| POST | /pets | Crea mascota |
| PUT | /pets/{id} | Actualiza mascota |
| DELETE | /pets/{id} | Elimina mascota |

## 7. Pruebas Unitarias

Ejemplo:

```
class ValidatorsTest {
    @Test
    fun emailValido() {
        assertTrue(Validators.isValidEmail("test@example.com"))
    }

    @Test
    fun emailInvalido() {
        assertFalse(Validators.isValidEmail("test"))
    }
}
```

## 8. Cómo Ejecutar el Proyecto

### Frontend (Android)
1. Abrir Android Studio  
2. File → Open → GuauMiau  
3. Conectar emulador o dispositivo  
4. Ejecutar Run

### Backend (Spring Boot)
1. Abrir carpeta backend en IntelliJ  
2. Ejecutar la clase principal  
3. Acceder a:
   http://localhost:8080/pets

## 9. Estructura del Proyecto

```
/app
   /data
   /domain
   /ui
   AndroidManifest.xml

/backend
   /src/main/java/com/...
   pom.xml
```
