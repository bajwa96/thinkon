## Running the Application

To run the application, use the following command:
mvn spring-boot:run

The application will start, and you can access it at `http://localhost:8080` (or your configured port).

## Configuration

### Database Configuration

The application uses MySQL by default. To change the database configuration, modify the following properties in `src/main/resources/application.properties`:

Replace these values with your database details:
- Change the IP address and port in the URL to match your database server.
- Update the username and password as necessary.

### Swagger Documentation

Swagger UI is configured for API documentation. After starting the application, you can access:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/api-docs`

### Server Port

By default, the server runs on port 8080. To change this, add the following property to `application.properties`:

Replace 8080 with your desired port number.

For testing out the api use http://localhost:8080/swagger-ui