cities-migrations-pg
---

This is a project to generate a PostgreSQL docker image that comes with scripts to populate a initial database.

# Features:
* Based on the official [PostgreSQL docker image](https://hub.docker.com/_/postgres)
* A Maven project that has [no Java code](https://github.com/raonigabriel/cities-migrations-pg/tree/master/src) (except the [tests](https://github.com/raonigabriel/cities-migrations-pg/tree/master/src/test/java/com/github/raonigabriel))
* Integrating Maven and Docker using [maven-exec-plugin](https://www.mojohaus.org/exec-maven-plugin/)
* Maven [resource filtering](https://maven.apache.org/plugins/maven-resources-plugin/examples/filter.html)
* Docker image testing using [container-structured-tests](https://github.com/GoogleContainerTools/container-structure-test#container-structure-tests) 
* Integrated testing using [TestContainers](https://www.testcontainers.org/modules/databases/postgres/)
* [CI](https://github.com/raonigabriel/cities-migrations-pg/blob/master/.github/workflows/ci.yml), using GitHub Actions
* Flyway [Migrations](https://flywaydb.org/documentation/concepts/migrations.html)
* [Semantic versioning](https://semver.org/)
 
# License
Released under the [Apache 2.0 License](https://github.com/raonigabriel/cities-migrations-pg/blob/master/LICENSE)

# Disclaimer
This code comes with no warranty. Use it at your own risk.

I don't like Apple. Fuck off, fan-boys.

I don't like left-winged snowflakes. Fuck off, code-covenant.

I will call my branches the old way. Long live master, fuck-off renaming.
