#!/bin/bash
NAME="wevan-mysql"
RUNTIME_OPTS="-d"
PORT=3306
IMAGE="tutum/mysql"
PASSWORD="admin"

CMD="docker run --name $NAME -p $PORT:$PORT -e MYSQL_PASS="$PASSWORD" $RUNTIME_OPTS $IMAGE"

echo Deploting MySql with docker command :
echo "	$CMD"

$CMD

echo "MySql container has been created. Proceed with reset of admin account."
