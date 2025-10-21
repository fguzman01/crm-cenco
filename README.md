# CRM-Cenco (Proyecto de Demostración)

Este repositorio contiene un proyecto de ejemplo para automatización de pruebas y manejo de datos en un entorno de CRM, orientado a prácticas de integración y pruebas automatizadas. **No es funcional en producción** y no contiene accesos ni credenciales válidas.

## Estructura del Proyecto

- **src/test/java/cencosud/**: Código fuente de pruebas automatizadas (TestNG, Selenium, RestAssured).
- **src/test/resources/**: Recursos de pruebas (drivers, datos, configuraciones).
- **inputs/**: Datos de entrada y colecciones de pruebas (JSON, Postman).
- **output/**: Resultados y archivos generados por pruebas.
- **doc/**: Documentación y notas de referencia.
- **suites/**: Archivos de configuración de suites de pruebas (TestNG XML).
- **pom.xml**: Archivo de configuración Maven (dependencias y plugins).

## Dependencias principales

- Java 8+
- Maven
- Selenium 3.x
- TestNG 7.x
- RestAssured 3.x
- Allure (reportes)

## Uso (solo demostrativo)

Este proyecto no puede ejecutarse sin credenciales y configuraciones privadas. Si deseas adaptarlo:

1. Clona el repositorio.
2. Instala Java y Maven.
3. Ajusta los archivos de configuración y datos según tu entorno.
4. Ejecuta pruebas con Maven:
   ```sh
   mvn clean test
   ```

## Recomendaciones de limpieza y organización

- Mantén solo los datos de ejemplo mínimos necesarios.
- Borra resultados de pruebas y carpetas de build antes de subir cambios (`target/`, `allure-results/`, `allure-report/`).
- No subas archivos binarios grandes ni drivers innecesarios.
- Usa `.gitignore` para excluir archivos temporales y de entorno.
- Elimina código antiguo o duplicado que no aporte valor.

## Notas

- Este repositorio es solo de referencia y muestra.
- No contiene información sensible ni accesos reales.
- Si tienes dudas sobre qué archivos puedes limpiar, revisa la estructura y elimina carpetas vacías, de respaldo o resultados de pruebas.

---

> Proyecto de ejemplo para prácticas de automatización y organización de repositorios.
