# DRTI Modernized Application

DRTI is a Java EE web application. The legacy RichFaces and JSP-based views have been converted to PrimeFaces with Facelets templates, providing a modern component set and cleaner page structure.

## Prerequisites

- **Java Development Kit 11** or newer
- **Apache Maven 3** or newer

## Building

Use Maven to compile the sources and create the WAR package:

```bash
mvn clean package
```

The build generates `target/DRTI.war`.

## Running

Deploy the generated WAR to a Servlet container such as **Apache Tomcat 9+**:

1. Copy `target/DRTI.war` to your container's `webapps` directory.
2. Start the container and access the application at `http://localhost:8080/DRTI`.

## Deploying to Production

1. Ensure JDK 11 or later is installed on the server.
2. Build the project with `mvn clean package`.
3. Deploy the resulting WAR to your enterprise application server (Tomcat, WildFly, etc.).
4. Configure any environment-specific settings (database, email, etc.) in the server.

---

This README outlines the new workflow after migrating from RichFaces/JSP to PrimeFaces/Facelets.
