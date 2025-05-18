Version Española
===============

WeekendPlanner – Proyecto de Desarrollo de Aplicaciones para Ciencia de Datos

1. Breve descripción del proyecto y propuesta de valor
------------------------------------------------------

WeekendPlanner es una aplicación modular desarrollada en Java 21 que permite a los usuarios planificar fines de semana combinando datos de vuelos y predicciones meteorológicas. El valor principal del sistema reside en integrar estas fuentes para ayudar al usuario a decidir destinos viables según la accesibilidad y el clima, utilizando tanto datos en tiempo real como históricos. La aplicación facilita este análisis mediante una interfaz sencilla, basada en consola.

2. Justificación de la elección de APIs y estructura del datamart
-----------------------------------------------------------------

Se han elegido dos APIs:
- **Amadeus API**: Permite obtener datos actualizados sobre vuelos y destinos accesibles desde un aeropuerto origen.
- **OpenWeatherMap API**: Proporciona predicciones meteorológicas útiles para evaluar las condiciones en los destinos potenciales.

Estas fuentes permiten realizar recomendaciones de escapadas que consideren no solo la disponibilidad de transporte, sino también el clima.

El **datamart** local combina eventos de ambas fuentes para permitir consultas combinadas. Se estructura de forma simplificada mediante SQLite, con dos tablas principales: `flights` y `weather`, y es alimentado tanto en tiempo real como desde archivos históricos `.events`.

3. Instrucciones de compilación y ejecución
-------------------------------------------

Requisitos previos:
- Java 21
- Apache ActiveMQ (por defecto en el puerto 61616)
- Maven
- IntelliJ IDEA (recomendado)

Pasos:
1. Clonar el repositorio y abrir el proyecto en IntelliJ.
2. Ejecutar ActiveMQ:
   - Descargar desde https://activemq.apache.org/components/classic/download/
   - Extraer y lanzar con `./bin/activemq start` (Linux/macOS) o `bin\activemq.bat start` (Windows)
3. Compilar todos los módulos: `mvn clean install`
4. Ejecutar los módulos:
   - `weather-feeder.Main`: Publica datos meteorológicos
   - `flight-feeder.Main`: Publica datos de vuelos
   - `event-store-builder.Main`: Almacena eventos recibidos en carpetas estructuradas
   - `business-unit.Main`: Lanza la interfaz para consultar y analizar los datos

4. Ejemplos de uso
------------------

**Consulta en la interfaz:**
- Introducir un aeropuerto origen (ej. "MAD") y ver posibles vuelos con su predicción meteorológica.
- Cargar eventos históricos de una fecha específica y consultarlos mediante filtros.

**Petición REST (si habilitado):**
```
GET http://localhost:7000/flights?origin=MAD
GET http://localhost:7000/weather?city=London
```

5. Arquitectura de sistema y arquitectura de aplicación
-------------------------------------------------------

**Arquitectura del sistema (Lambda-like):**
- weather-feeder → Topic `Weather` → ActiveMQ → event-store-builder + business-unit
- flight-feeder → Topic `AmadeusFlights` → ActiveMQ → event-store-builder + business-unit

**Arquitectura de la aplicación:**
- Cada módulo es independiente (modularidad Maven).
- Comunicación vía ActiveMQ (Publisher/Subscriber).
- Persistencia mediante SQLite y archivos `.events`.

(Diagramas deben añadirse como imagen si se entrega en formato visual)

6. Principios y patrones de diseño aplicados
--------------------------------------------

- **Separación de responsabilidades**: Cada módulo tiene una tarea bien definida.
- **Publisher/Subscriber**: Comunicación asíncrona entre módulos usando ActiveMQ.
- **DAO pattern**: Gestión de acceso a datos en SQLite mediante clases DAO.
- **Clean Code**: Nombres descriptivos, clases pequeñas, lógica separada y clara.
- **Arquitectura Hexagonal (Ports and Adapters)**: Interfaces definen los puertos, permitiendo cambiar la fuente de datos o la interfaz de usuario sin modificar la lógica de negocio.

¡Gracias por usar WeekendPlanner! Para más información consulta los diagramas del repositorio o contacta al equipo desarrollador.


English Version
===============

WeekendPlanner – Data Science Application Development Project

1. Brief Project Description and Value Proposition
--------------------------------------------------

WeekendPlanner is a modular application developed in Java 21 that allows users to plan weekend getaways by combining flight data and weather forecasts. Its core value lies in integrating these data sources to help users decide viable destinations based on accessibility and weather, using both real-time and historical data. The analysis is accessible through a simple console-based interface.

2. Justification for API Choice and Datamart Structure
------------------------------------------------------

Two APIs were selected:
- **Amadeus API**: Provides real-time data on flights and accessible destinations from a departure airport.
- **OpenWeatherMap API**: Delivers weather forecasts useful for evaluating the destination’s conditions.

These sources allow the application to recommend getaways that consider both transport availability and weather suitability.

The local **datamart** integrates both sources for joint querying. It is structured with SQLite and consists of two main tables: `flights` and `weather`, fed by both real-time and historical `.events` files.

3. Compilation and Execution Instructions
-----------------------------------------

Requirements:
- Java 21
- Apache ActiveMQ (default on port 61616)
- Maven
- IntelliJ IDEA (recommended)

Steps:
1. Clone the repository and open the project in IntelliJ.
2. Run ActiveMQ:
   - Download from https://activemq.apache.org/components/classic/download/
   - Extract and launch with `./bin/activemq start` (Linux/macOS) or `bin\activemq.bat start` (Windows)
3. Compile all modules: `mvn clean install`
4. Run the modules:
   - `weather-feeder.Main`: Publishes weather data
   - `flight-feeder.Main`: Publishes flight data
   - `event-store-builder.Main`: Stores received events in structured folders
   - `business-unit.Main`: Launches the interface to query and analyze data

4. Usage Examples
-----------------

**Console Interface:**
- Enter a departure airport (e.g., "MAD") to see available flights and their weather conditions.
- Load historical events for a specific date and filter the results.

**REST Requests (if enabled):**
```
GET http://localhost:7000/flights?origin=MAD
GET http://localhost:7000/weather?city=London
```

5. System and Application Architecture
--------------------------------------

**System Architecture (Lambda-like):**
- weather-feeder → Topic `Weather` → ActiveMQ → event-store-builder + business-unit
- flight-feeder → Topic `AmadeusFlights` → ActiveMQ → event-store-builder + business-unit

**Application Architecture:**
- Each module is independent (Maven modular structure).
- Communication via ActiveMQ (Publisher/Subscriber).
- Persistence via SQLite and `.events` files.

(Include architecture diagrams as images if submitting a visual document)

6. Applied Principles and Design Patterns
-----------------------------------------

- **Separation of Concerns**: Each module is responsible for a specific task.
- **Publisher/Subscriber**: Asynchronous communication between modules using ActiveMQ.
- **DAO Pattern**: Data access managed through DAO classes for SQLite.
- **Clean Code**: Descriptive names, small classes, and clear logic.
- **Hexagonal Architecture (Ports and Adapters)**: Interfaces define ports, enabling changes in data sources or user interfaces without modifying core logic.

Thanks for using WeekendPlanner! For more information, consult the diagrams in the repository or contact the development team.
