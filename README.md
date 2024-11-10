# sylvarbo
Genealogy tool using Apache Tapestry and Cayenne

## Various dev tasks

### Regenerate the DB schema
 - In Cayenne Modeler, in the menu, select Tools -> Generate Database Schema;
 - Under SQL Options tab, only select the Create checkboxes;
 - Under Adapter, select org.apache.cayenne.dba.h2.H2Adapter;
 - Click Save SQL, and under sylvarbo/resources/ save as create.sql

### Recreate the H2 database
In a terminal, change directory to path/to/h2/bin/ (extracted from H2 zip), and run:
```
bash path/to/sylvarbo/resources/recreatedb_h2.sh
```

## Building sylvarbo

### Configuring modules
**Note:** To run from a WAR, the execution-mode modules have to be specified in AppConfiguration.java, because web.xml won't be picked up.

e.g. in order to specify -Dtapestry.execution-mode=dev to use DevelopmentModule, *first* use the following line in AppConfiguration.java:
```
servletContext.setInitParameter("tapestry.dev-modules", "net.xytra.sylvarbo.services.DevelopmentModule");
```

### Creating a WAR
To create a WAR:
```
mvn clean compile package
```

## Running sylvarbo
### With Maven and Jetty
To run via Maven and Jetty:
```
# default execution mode in pom.xml is dev
mvn -Dtapestry.execution-mode=<specify> jetty:run
```

### As WAR
```
java -Dtapestry.execution-mode=<specify> -jar sylvarbo-1.0-SNAPSHOT.war
```
