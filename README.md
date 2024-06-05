# Petshop

Example project for applicants.

## Prelude

Complete the tasks in any order you prefer.

It's not necessary to complete all tasks.

You can use any dependencies and frameworks you like for each task. While the project skeleton is in Java, you're free to choose any programming language.

The goal is to understand your coding style and how you approach the tasks.

## Tasks

### Persist Pet Data:
- Design an endpoint capable of receiving data sent through a web request. Example data: `src/main/resources/pet.json`.
- Implement the functionality to persist the received data.
- Develop an integration test to validate the endpoint's functionality.

### Retrieve Persisted Data:
 - Create an endpoint to retrieve the data previously persisted.

### Data Modification Endpoint:
 - Develop an endpoint to alter the existing data.

### visualize (optional!)
 - Add a simple frontend to visualize the data.

### Dockerization of the Application:
 - Dockerize the entire application.

---------------------------------------------------------------------
### Assignment Status
 - run `./script/build_and_run_docker.sh` to run the dockerized app and hit base url `http://localhost:8080/v1/pets`.
 - access db @ `http://localhost:8080/h2-console/` with db-name `jdbc:h2:mem:petshop`, user `sa` and password `admin`
 - due to lack of time, I didn't work on UI or validation
