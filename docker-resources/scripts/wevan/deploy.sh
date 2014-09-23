#!/bin/bash
NAME="wevan-test"
PORT=8050
IMAGE="cspinformatique/wevan"
RUNTIME_OPTS="-d"

docker run --name $NAME -p $8050:$8050 $RUNTIME_OPTS $IMAGE
