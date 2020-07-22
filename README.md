# User Finder API

## Instructions
### Running the application
Run the application using the `bootRun` Gradle task.

### Endpoint
The REST API endpoint to find users who live in or within 50 miles of London is given as follows:

`GET /users/vicinity/London`

When running the application locally, this can be consumed at `http://localhost:8080/users/vicinity/London`.

### Running the tests
The unit and integration tests can be run using the `test` and `integrationTest` Gradle tasks respectively.

## Additional Details
The application is designed to be generic, with the capability to support more cities (although as per the brief, 
only London is currently supported). The supported cities and corresponding GPS coordinates are stored in a 
local H2 database, which is loaded on application startup from `src/main/resources/data.sql`. Additionally, the maximum
distance from the city can be specified as a query parameter (e.g. `GET /users/vicinity/London?distance=60`) although
the default value is 50 as per the brief.