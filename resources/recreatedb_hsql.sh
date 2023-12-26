# Use the following commands (from the hsqldb directory) to recreate the db

DB_NAME=dev
DB_DIR=~/Development/sylvarbodb
PROJECT_DIR=~/Development/GitHub/sylvarbo

rm -rf ${DB_DIR}/${DB_NAME}.*  # drop database all at once
java -jar lib/sqltool.jar --inlineRc=url=jdbc:hsqldb:file:${DB_DIR}/${DB_NAME},user=SA,password= ${PROJECT_DIR}/resources/create_hsql.sql
java -jar lib/sqltool.jar --inlineRc=url=jdbc:hsqldb:file:${DB_DIR}/${DB_NAME},user=SA,password= ${PROJECT_DIR}/resources/data.sql

# To flush, ultimately deleting tmp dir and log file:
java -jar lib/sqltool.jar --inlineRc=url=jdbc:hsqldb:file:${DB_DIR}/${DB_NAME},user=SA,password= --sql="shutdown;"
