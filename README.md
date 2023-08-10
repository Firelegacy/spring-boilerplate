# spring-boilerplate project

This project contains different example cases of how to use spring.

> Read comments carefully in the different classes - they'll give you a lot of contextual information._

## Success case treatment


## Error case treatment


## HTTP request body validation


## application.properties
Here's the meaning of the configs you can find in that file:

    spring.datasource.url=jdbc:h2:file:./src/main/resources/db/testdb;DB_CLOSE_ON_EXIT=FALSE

This configures the name of the database (in memory H2 in this case), using _file_ instead of _mem_ allows persistence.

    spring.datasource.driverClassName=org.h2.Driver
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

H2 driver and dialect to use.

    spring.datasource.username=sa
    spring.datasource.password=

Username and password used to connect to the database instance.

    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true

Add the first line to see in the Console the SQL commands that are executed by Hibernate.
The second is to display these commands in a pretty way.

    spring.jpa.hibernate.ddl-auto=create

Initialization mode used for the database, in-memory databases are usually set with _create_ or _create-drop_.
Be ure to disable this if you use a _schema.sql_ init file.

    spring.sql.init.data-locations=classpath:data.sql

File containing a couple of inserts to fill in the database.

    spring.sql.init.mode=always

Always initialize the database.

    spring.jpa.properties.hibernate.validator.apply_to_ddl=false

Apply validation of entity parameters when loading from _data.sql_.

    spring.jpa.properties.hibernate.globally_quoted_identifiers=true

When creating entities and parameters with names that are also SQL reserved words, this property will automatically
escape them.

    spring.jpa.defer-datasource-initialization=true

When no _schema.sql_ is defined and we're relying on Hibernate to create the table for the entity, we need to defer
the initialization of _data.sql_, otherwise it'll try to do it before the table is created and throw and exception when
launching the app.


