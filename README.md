![expressCart](https://raw.githubusercontent.com/mrvautin/expressCart/master/public/images/logo.png)

Check out the documentation [here](https://github.com/mrvautin/expressCart/wiki).

View the demo shop [here](https://expresscart-demo.markmoffat.com/).

# Testing y Automatización CI/CD para la Tienda CLOTH

## Estructura del Proyecto

Este repositorio contiene la automatización de pruebas y configuraciones de CI/CD para la tienda **CLOTH**. A continuación, se describe la organización del código y los archivos clave:

### 📂 Estructura de Carpetas y Archivos

- **`app.js`**: Archivo principal que configura y arranca la aplicación Express.
- **`/bin`**: Contiene scripts de arranque del servidor.
- **`/config`**: Archivos de configuración para la aplicación.
- **`/demo`**: Datos o configuraciones de demostración.
- **`/lib`**: Módulos y bibliotecas reutilizables.
- **`/locales`**: Archivos de localización e internacionalización.
- **`/public`**: Archivos estáticos como CSS, JavaScript e imágenes.
- **`/routes`**: Define las rutas y la lógica de negocio.
- **`/test`**: ✨ Contiene las pruebas automatizadas para validar funcionalidades clave.
- **`/views`**: Plantillas de vistas para la interfaz de usuario.
- **`.github/workflows`**: Configuraciones de CI/CD con GitHub Actions.
- **`Dockerfile` y `docker-compose.yml`**: Configuración para despliegue en contenedores Docker.
- **`package.json`**: Dependencias del proyecto y scripts para ejecución.

## 🚀 Pruebas y Automatización

Las pruebas automatizadas se encuentran en la carpeta **`/test`** y cubren las siguientes áreas:

- **Pruebas unitarias**: Validan la funcionalidad de los módulos individuales.
- **Pruebas de integración**: Aseguran que los componentes trabajan juntos correctamente.
- **Pruebas end-to-end (E2E)**: Simulan el comportamiento del usuario final en la tienda.

## 🛠️ Configuración y Ejecución

### 📌 Dependencias y Configuración

- Si el proyecto utiliza Maven, las dependencias deben asignarse en el archivo **`pom.xml`**.

- Al estar basado en **Node.js**, se deben instalar las dependencias necesarias con: **`npm install`**.

- Para el almacenamiento de datos, se recomienda MongoDB (opcional). Si se desea utilizar, asegurarse de que el servicio esté en ejecución y configurar la conexión en el archivo de entorno.

### 🔧 Pasos para Ejecutar las Pruebas Localmente

1. Clonar el repositorio **`git clone https://github.com/Camilo-Daza-Ramon/ExpressCartTest.git`** seguido de **`cd ExpressCartTest`**.

2.  Instalar las dependencias: **`npm install`**.

3. Ejecutar la base de datos (si se usa MongoDB, asegurarse de que esté corriendo): **`mongod --dbpath ./data/db`**

4. Ejecutar las pruebas: **`npm test`**

5. Para ejecutar la aplicación en local: **`npm start`**

6. Luego, abrir en el navegador **`http://localhost:1111/ `**

##📄 Registro de Resultados de Pruebas

Los resultados de las pruebas se almacenan en el archivo **`test-results.txt `**, el cual registra los detalles de cada prueba ejecutada, incluyendo:

- Casos de prueba exitosos.
- Casos de prueba fallidos y sus errores asociados.
- Resúmenes de ejecución para facilitar el análisis de funcionamiento.

Este archivo permite un monitoreo detallado del estado de las pruebas sin necesidad de ejecutar nuevamente el pipeline completo, facilitando el diagnóstico y mejora del sistema.

##⚙️ Manejo y Registro de Errores

El sistema de pruebas cuenta con una estrategia para el manejo de errores, incluyendo:

- Excepciones controladas: Se implementan try-catch para manejar errores esperados y proporcionar mensajes detallados.
- Errores HTTP 500: Se captura y registra cualquier error interno del servidor, almacenándolo en logs para su posterior análisis.
- Registro de errores: Se usa un sistema de logging para documentar fallos y permitir su depuración en un entorno de pruebas profesional.
- Reporte y gestión de errores: Los errores críticos se reportan automáticamente a la herramienta de monitoreo utilizada en el entorno CI/CD.

##📌 Conclusión
Este proyecto proporciona una solución integral para la automatización de pruebas en la tienda CLOTH, asegurando estabilidad y calidad en cada despliegue. La integración con CI/CD permite una ejecución eficiente de los tests, y el archivo **`test-results.txt `** facilita el análisis de resultados. Con esta implementación, garantizamos un proceso de desarrollo más confiable y optimizado.

####🚀 ¡Gracias por contribuir y mejorar este proyecto! 🎯