# Back-end for Library System

This is a simple App which I developed as a Back-end assignment for Integrify Full Stack program.

Tech Stack: Java SpringBoot, PostgreSQL.

The App is deployed at:
https://library-system.herokuapp.com/

The Front-end repo:
https://github.com/ochertkova/library-system-react/tree/fullstack



## Setup & Installation

Clone the repo.

```bash
git clone <repo-url>
```
## Running The App

To run the App locally:

1. Run the Docker compose file: docker-compose.yml
or from project directory:
```bash
docker compose up -d
```
2. Run Fs14BackendApplication.java 
or from project directory:
```bash
./mvnw package
```
```bash
java -jar target/fs14-backend-0.0.1-SNAPSHOT.jar
```
## Using the API

The Dev server starts locally at `http://localhost:8080`


### Implemented features

The backend application exposes a set of REST APIs for several endpoints.
Swagger file for the APIs can be found in [api-docs.json](api-docs.json).

If run locally, at http://localhost:8080/swagger-ui/index.html

### Planned further development

- Add Unit tests and integration tests
- Add more functionality for Admin role