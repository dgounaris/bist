# Development environment

This is a short guide describing the project's development environment.

## Technologies

- Java, Kotlin

The project's main development languages are Java and Kotlin.\
The supported Java version is 11+.

- JUnit

The application's tests are written in JUnit. They are located in `test` modules that follow the same structure as the respective `main` modules.

- PostgreSQL

The project's database is PostgreSQL, due to performance and support benefits compared to other relational database alternatives.
  
- Flyway

The project's SQL database migration tool. In order to enable well-defined database schemas, more robust schema changes and the ability for rollback if needed,
a migration tool is necessary.\
Flyway is a tool with minimal required configuration, which reads and plays the files matching the pattern `V{X}__{Y}.sql` from `src/main/resources/db/migration` one by one ordered by version number.\
It uses the generated command hash to verify database integrity (in other words, do NOT alter existing sql scripts unless if you plan on pruning the whole database before running them).\
In order to preserve script sequence and no version clashes when multiple people are working on separate branches, a convenience gradle task autogenerates a file with the current timestamp as version.

To generate this file, simply run `./gradlew flywayCreate --name="script_description"`, where script description is a short description
of the script's purpose (will be appended to the script file name).\
For example, `./gradlew flywayCreate --name="CREATE_USER_TABLE"` generates a file with a name similar to `V1610457217391__CREATE_USERS_TABLE.sql`

- Gradle

The project's packaging and build tool is Gradle. For intact versioning across machines, Gradle Wrapper is used to request the actual gradle version of the project.

Some useful commands include:\
`./gradlew build` To build the project\
`./gradlew clean` To clean all build folders\
`./gradlew test` To run all the project tests

- Docker

For smoother development cycles it is important to keep a standardized development manner.
Thus, Docker and Docker-Compose are utilized to keep deployments the same across all machines.

Using `docker-compose.yml` and `Dockerfile` configurations, Docker containerizes multiple services under the same, isolated capsule,
superficially as if the services are running into a VM. This also has similar implications for network accessibility (host machine can't access Docker container's ports),
which is why `port` directives are needed.

To run the dockerized application, after having installed Docker and Docker-Compose, simply follow the commands below:
```
./gradlew clean build
sudo docker-compose build
sudo docker-compose up
```
You should now be able to access the server. Verify by hitting `http://localhost:8080/health` for a response.

To shut down the environment press `Ctrl+C` on the same terminal window, or run `sudo docker-compose down`.

For now, local docker database is cleared on every restart. This behaviour can be altered.