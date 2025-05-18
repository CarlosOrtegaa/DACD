
WeekendPlanner – Proyecto de Desarrollo de Aplicaciones para Ciencia de Datos

Bienvenida
----------

¡Bienvenido al proyecto WeekendPlanner! Esta aplicación está diseñada para ayudarte a planificar actividades de fin de semana mediante la combinación de datos en tiempo real de vuelos y eventos. El proyecto se estructura en tres sprints que integran captura de datos, procesamiento en tiempo real y explotación de la información para generar recomendaciones útiles a través de una interfaz de usuario.

Breve descripción del sistema
-----------------------------

Este proyecto consta de varios módulos desarrollados en Java 21 y organizados mediante arquitectura modular:

- event-feeder: Consume datos de eventos desde la API de Ticketmaster.
- flight-feeder: Consume datos de vuelos desde la API de Amadeus.
- event-store-builder: Almacena eventos recibidos en ficheros `.events` organizados por fecha y tipo.
- business-unit: Ofrece una interfaz donde el usuario puede consultar eventos y vuelos, tanto en tiempo real como históricos.

El sistema utiliza Apache ActiveMQ como bróker de mensajería para implementar el patrón Publisher/Subscriber.

Uso del sistema
---------------

El sistema no se ejecuta directamente desde una única clase con interfaz visible. En su lugar, debes ejecutar el módulo `business-unit`, el cual lanza una pestaña (interfaz GUI o consola) desde la que el usuario podrá introducir los datos deseados y realizar consultas.

Asegúrate de seguir estos pasos:

1. Instalar y ejecutar Apache ActiveMQ (por defecto, en el puerto 61616).
2. Ejecutar los módulos `event-feeder` y `flight-feeder` para comenzar a enviar datos al bróker.
3. Ejecutar `event-store-builder` para almacenar los datos recibidos.
4. Finalmente, lanzar `business-unit`, desde donde podrás interactuar con el sistema.

Compatibilidad y advertencias
-----------------------------

Este sistema ha sido probado en:

- Windows 10 y 11
- macOS (últimas versiones con Java 21)
- Linux (Ubuntu 20.04+)

Problemas comunes:

- En macOS, puede requerirse dar permisos adicionales para abrir la GUI.
- Verifica que el puerto 61616 no esté bloqueado por el firewall.
- El sistema debe ejecutarse desde IntelliJ o desde terminales compatibles con Java 21 y Maven.

Recomendación: Ejecutar cada módulo de forma independiente desde su clase `Main.java`.

¡Gracias por usar WeekendPlanner!
