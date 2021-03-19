#!/bin/bash

# Field
PROJECT_NAME=Spring_JWT_API_Server
BUILD_NAME=spring_api
SERVER_PORT=8083

PROJECT_PATH=/var/lib/jenkins/workspace/$PROJECT_NAME/target
JAR_FILE=$PROJECT_PATH/$BUILD_NAME.jar

sudo nohup java -jar $JAR_FILE  --spring.profiles.active=dev > /dev/null 2>&1 &


sudo echo "java -jar $JAR_FILE start on $SERVER_PORT"
sudo echo "RUN!!"
sudo echo "http://senspond.iptime.org:8083/"
