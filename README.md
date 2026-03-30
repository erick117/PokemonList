# PokemonList App

Aplicación Android desarrollada en Kotlin + Jetpack Compose que consume la API de Pokémon para mostrar una lista, realizar búsquedas y visualizar el detalle de cada Pokémon.

---

Funcionalidades

-Listado inicial de Pokémon (consumo desde API)
-Búsqueda por:
-Nombre
-Número
-Tipo
-Filtro por tipo desde la pantalla de detalle
-Limpieza de filtros y retorno a la lista original
-Pantalla de detalle con:
-Nombre
-Número
-Sprites (normal y shiny)
-Tipos
-Estadísticas
-Manejo de estados:
-Loading
-Error
-Empty
-Reintento de petición en caso de error

---

Arquitectura

Se implementa una arquitectura basada en MVVM + Clean Architecture, separando responsabilidades en capas:

core/      → utilidades y configuración de red  
data/      → acceso a datos (API, repository, modelos)  
domain/    → casos de uso (lógica de negocio)  
ui/        → vistas, navegación y viewmodels

---

Flujo de datos

UI → ViewModel → UseCase → Repository → API

---

Tecnologías utilizadas

-Kotlin
-Jetpack Compose
-Retrofit
-Hilt (DI)
-Coil (carga de imágenes)
-Coroutines

---

API utilizada

https://pokeapi.co/

Endpoints principales:

-GET /pokemon → listado
-GET /pokemon/{name} → detalle / búsqueda por nombre o número
-GET /type/{type} → filtro por tipo

---

Manejo de errores

-Uso de Result<T> para encapsular éxito/error
-Mapeo de errores con NetworkError

Tipos manejados:

-Sin conexión a internet
-Timeout
-Error de servidor
-Recurso no encontrado (404)

---

UI y UX

-Jetpack Compose
-Componentes reutilizables
-Estados
-Indicadores de carga
-Selector de búsqueda (Nombre / Número / Tipo)

---

Decisiones técnicas

-Uso de Result<T> para simplificar manejo de errores
-DTOs reducidos solo a lo necesario
-Estado centralizado en ViewModel
-Navegación simple y clara
-UI desacoplada de lógica

---


Cómo ejecutar

git clone https://github.com/erick117/PokemonList.git

Abrir en Android Studio y ejecutar.

---

Autor

Erick Alberto Garcia Marquez
