
Accessing the H2 Console
-----------------------------------------
Run your Spring Boot application.
Open a browser to http://localhost:8080/h2-console.
Provide the database connection information as below and connect:
JDBC URL: jdbc:h2:mem:testdb
User Name: kcb
Password: Empty or as configured in application.yml.
Commands to Run
Build and Run Containers:

docker-compose up --build

View Logs, to verify the H2 configuration:

docker logs app-container

Stop Containers:

docker-compose down