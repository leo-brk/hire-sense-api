# Hire Snse README #

This README contains all the steps are necessary to get this application up and running,
and also general information about some built tools and plugins that are used.

## License

This project is proprietary software owned by Borek Solutions Group. For more information, please see the [LICENSE](./LICENSE) file.

### What is this repository for? ###

* hire-sense-api provides the api for job management, provide historical data and statistics.
* Version 1.0

### Features

### Built With ###
[![Java][java.com]][java-url]
[![H2Database][maven.apache.org]][maven-url]
[![Spring][spring.io]][spring-url]
[![Postgres][postgresql.org]][postgresql-url]
[![Hibernate][hibernate.org]][hibernate-url]

## Deployment ##
#### Run app including linting and tests ####

```sh
    mvn clean install
```

#### Run app including linting but skipping tests ####
```sh
    mvn clean install -DskipTests
```

#### Run tests ####
```sh
    mvn test
```
#### Run linting check ####
```sh
    mvn checkstyle:check
```

### Who do I talk to? ###
* Product Owner: [Leotrim Vojvoda](mailto:leotrim.vojvoda@boreksolutions.de)

[spring.io]: https://img.shields.io/badge/Spring_BOOT-6CB42C?style=for-the-badge&logo=spring&logoColor=FFFFFF
[spring-url]: https://spring.io/
[java.com]: https://img.shields.io/badge/Java-ed2024?style=for-the-badge&logo=oracle&logoColor=white
[java-url]: https://www.java.com/en/
[postgresql.org]: https://img.shields.io/badge/Postgresql-31648c?style=for-the-badge&logo=postgresql&logoColor=white
[postgresql-url]: https://www.postgresql.org/
[h2database.com]: https://img.shields.io/badge/H2_Database-1020fe?style=for-the-badge
[h2database-url]: https://www.h2database.com/html/main.html
[maven.apache.org]: https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white
[maven-url]: https://maven.apache.org/
[flywaydb.org]: https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white
[flyway-url]: https://flywaydb.org/
[hibernate.org]: https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=black
[hibernate-url]: https://hibernate.org/
[checkstyle.sourceforge.io]: https://img.shields.io/badge/Checkstyle-fdc205?style=for-the-badge
[checkstyle-url]: https://checkstyle.sourceforge.io/


## Notes:
* Talk about the importance of optionals
* Writing testable code
* Testcontainers
