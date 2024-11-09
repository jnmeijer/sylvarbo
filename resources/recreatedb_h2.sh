# Use the following commands (from the hsqldb directory) to recreate the db

DB_VERSION=2.3.232
DB_NAME=dev
DB_DIR=~/Development/h2db/sylvarbodb
PROJECT_DIR=~/Development/GitHub/sylvarbo

rm -rf ${DB_DIR}/${DB_NAME}.*  # drop database all at once
java -cp h2-${DB_VERSION}.jar org.h2.tools.RunScript -url jdbc:h2:file:${DB_DIR}/${DB_NAME} -user sa -script ${PROJECT_DIR}/resources/create.sql
java -cp h2-${DB_VERSION}.jar org.h2.tools.RunScript -url jdbc:h2:file:${DB_DIR}/${DB_NAME} -user sa -script ${PROJECT_DIR}/resources/data.sql
java -cp h2-${DB_VERSION}.jar org.h2.tools.RunScript -url jdbc:h2:file:${DB_DIR}/${DB_NAME} -user sa -script ${PROJECT_DIR}/resources/mydata.sql

# To flush, ultimately deleting tmp dir and log file:
java -cp h2-${DB_VERSION}.jar org.h2.tools.RunScript -url jdbc:h2:file:${DB_DIR}/${DB_NAME} -user sa -script ${PROJECT_DIR}/resources/shutdown.sql
