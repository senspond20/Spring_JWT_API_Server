#!/bin/bash

# Field
PROJECT_NAME=Spring_JWT_API_Server
BUILD_NAME=spring_api
SERVER_PORT=8083

PROJECT_PATH=/var/lib/jenkins/workspace/$PROJECT_NAME/target
JAR_FILE=$PROJECT_PATH/$BUILD_NAME.jar
TMP_PATH_NAME=/tmp/$PROJECT_NAME-pid


sudo echo "Stoping process on port: $SERVER_PORT"

sudo fuser -n tcp -k $SERVER_PORT 

# tcp $serverPort에 해당하는 port를 Kill함.

if [ -f $TMP_PATH_NAME ]; then
    sudo rm $TMP_PATH_NAME
fi