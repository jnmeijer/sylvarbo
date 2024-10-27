#!/bin/bash

cat create.sql | sed -e 's/) ENGINE=InnoDB/)/' -e 's/BOOL /BOOLEAN /g' \
    -e 's/TEXT/CLOB/g' \
    -e 's/MEDIUMTEXT/CLOB/g' \
    -e 's/LONGTEXT/CLOB/g' \
    -e 's/^DELETE FROM AUTO_PK_SUPPORT/commit;\nDELETE FROM AUTO_PK_SUPPORT/' > create_hsql.sql

echo "commit;" >> create_hsql.sql
