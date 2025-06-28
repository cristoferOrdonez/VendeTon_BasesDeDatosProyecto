# VendeTon - Sistema Integral de Gestión de Ventas Mayoristas

## 🚀 Descripción del Proyecto

**VendeTon** es una robusta aplicación Android diseñada en Java, enfocada en optimizar la gestión de ventas mayoristas. Desarrollada para negocios con alta rotación de inventario y una amplia cartera de clientes, esta solución integral simplifica el registro, seguimiento y administración de documentos de venta, productos y clientes. Es ideal tanto para pequeñas empresas que buscan eficiencia operativa como para profesionales que desean presentar una solución sólida y bien estructurada en su portafolio.

## ✨ Características Destacadas

* **Gestión Documental Completa:** Creación, edición, visualización y seguimiento detallado de documentos de venta.
* **Administración de Productos Avanzada:**
    * Registro exhaustivo de productos, incluyendo control de cantidades.
    * Definición de dimensiones y origen del producto.
    * Soporte para precios diferenciados (mayorista y minorista) para una flexibilidad comercial óptima.
* **Control de Clientes Mayoristas:** Gestión centralizada de información de contacto y un historial de compras detallado para cada cliente.
* **Interfaz de Usuario Intuitiva (UI/UX):** Desarrollada con un diseño moderno y amigable, utilizando componentes Material Design y RecyclerView para una experiencia de usuario fluida.
* **Persistencia de Datos Robusta:** Integración eficiente con bases de datos MySQL, utilizando procedimientos almacenados para garantizar la integridad y el rendimiento de los datos.
* **Seguridad y Control de Acceso:** Implementación de un sistema básico de roles (usuario público, cliente mayorista, administrador) para una gestión de permisos efectiva.
* **Arquitectura Modular y Escalable:** Separación clara entre la capa de presentación (UI), lógica de negocio (dominio) y persistencia de datos, facilitando el mantenimiento y la futura expansión.
* **Código de Calidad Profesional:** Adherencia a buenas prácticas de programación, claridad en el código y comentarios exhaustivos, lo que lo convierte en un excelente activo para cualquier portafolio.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje Principal:** Java
* **Framework:** Android SDK
* **Gestor de Dependencias:** Gradle (Kotlin DSL: `build.gradle.kts`, `settings.gradle.kts`)
* **Base de Datos:** MySQL (Conectividad vía JDBC y optimización mediante procedimientos almacenados)
* **IDE Recomendado:** Android Studio
* **Componentes UI/UX:** Material Design, RecyclerView
* **Patrón de Arquitectura:** Modelo-Vista-Controlador (MVC)

## 📸 Capturas de Pantalla

<img src="https://github.com/user-attachments/assets/34d7c3ee-6a95-4ee6-a0b1-1eab2f338b4a" width="20.8%">
<img src="https://github.com/user-attachments/assets/c64a03a9-e4df-4aa4-bb2b-3673aaf30d82" width="30%">
<img src="https://github.com/user-attachments/assets/14eab122-2364-4944-9e9f-8ad0f540bc21" width="30%">

## ⚙️ Instalación y Ejecución

### Prerrequisitos

Asegúrate de tener instalados los siguientes componentes:

* **Java Development Kit (JDK):** Versión 8 o superior.
* **Android Studio:** Entorno de desarrollo integrado para Android.
* **Servidor MySQL:** Con una instancia de base de datos configurada y los procedimientos almacenados necesarios importados.
* **Conectividad de Base de Datos:** Parámetros de conexión configurados en `gradle.properties` o en las clases de conexión de la aplicación.

### Pasos para Compilar y Ejecutar

1.  **Clonar el Repositorio:**
    ```sh
    git clone https://github.com/cristoferOrdonez/VendeTon_BasesDeDatosProyecto.git
    ```

2.  **Abrir en Android Studio:**
    Importa el proyecto `VendeTon_BasesDeDatosProyecto` en Android Studio.

3.  **Configurar Conexión a la Base de Datos:**
    Edita los parámetros de conexión (usuario, contraseña, host, puerto) en el archivo `gradle.properties` o directamente en las clases de conexión de la aplicación (`app/src/main/java/com/example/vendeton/`).

4.  **Sincronizar y Compilar:**
    Utiliza la opción "Sync Project with Gradle Files" en Android Studio para resolver las dependencias y compilar el proyecto.

5.  **Ejecutar la Aplicación:**
    Despliega la aplicación en un emulador de Android o en un dispositivo físico conectado.

### Notas Importantes

* La configuración y disponibilidad de la base de datos MySQL, junto con sus procedimientos almacenados, son **esenciales** para el correcto funcionamiento de la aplicación.
* Este proyecto ha sido diseñado pensando en la claridad y la facilidad de extensión, lo que lo hace ideal para adaptaciones académicas o como base para nuevas funcionalidades.
* Para incorporar nuevas características, se recomienda seguir la estructura modular existente, creando nuevas actividades y entidades según sea necesario.

## 📂 Estructura del Proyecto
 ```
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/vendeton/    # Lógica de la aplicación: Actividades, Entidades, Clases de Conexión
│   │       └── res/                         # Recursos de la aplicación: Layouts XML, Drawables, Valores
├── build.gradle.kts                         # Configuración de dependencias del módulo de la aplicación
├── settings.gradle.kts                      # Configuración de módulos del proyecto Gradle
├── gradle.properties                        # Propiedades globales de Gradle (ej. parámetros de conexión a DB)
├── gradlew                                  # Script de shell para Gradle Wrapper (Linux/macOS)
├── gradlew.bat                              # Script de batch para Gradle Wrapper (Windows)
└── .gitignore                               # Archivos y directorios a ignorar por Git
 ```
## 👥 Créditos y Autores

Desarrollado por:
| Nombre           | GitHub   | Correo                  |
| ---------------- | -------- |-------------------------|
| Deivid Farid Ardila Herrera | [@faridardila](https://github.com/faridardila) | deardilah@unal.edu.co |
| Cristofer Damián Camilo Ordoñez Osa |  [@cristoferOrdonez](https://github.com/cristoferOrdonez) | crordonezo@unal.edu.co |
| Kevin Alexis Bermudez Caicedo |  | kbermudezc@unal.edu.co |

## ⚖️ Licencia

Este proyecto se distribuye bajo una licencia de **uso académico y demostrativo**. Para cualquier fin comercial, por favor contacta directamente al autor para discutir las condiciones de uso.


