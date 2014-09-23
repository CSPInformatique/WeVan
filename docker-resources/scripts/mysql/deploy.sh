NAME="wevan-mysql"
RUNTIME_OPTS="-d"
PORT=3306
IMAGE="tutum/mysql"

CMD="docker run --name $NAME -p $PORT:PORT $RUNTIME_OPTS $IMAGE"

echo Deploting MySql with docker command :
echo "\t$CMD"

bash $CMD

echo "MySql container has been created. Proceed with reset of admin account."