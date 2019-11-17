#!/bin/bash

FILE_NAME="src/main/resources/database.properties"
PREFIX="spring.data.mongodb."

touch $FILE_NAME

echo "$PREFIX.host=$MLAB_HOST" >> $FILE_NAME
echo "$PREFIX.port=$MLAB_PORT" >> $FILE_NAME
echo "$PREFIX.database=plotrouter" >> $FILE_NAME
echo "$PREFIX.username=$MLAB_USERNAME" >> $FILE_NAME
echo "$PREFIX.password=$MLAB_PASSWORD" >> $FILE_NAME
