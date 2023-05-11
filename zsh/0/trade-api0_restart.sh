#! /bin/sh


export pid=`ps -ef | grep trading-api0.jar | grep -v "grep"| wc -l`

if [ "$pid" == 1 ]; then
	echo -e "Stopping...\n"
	curl -X POST http://127.0.0.1:9080/actuator/shutdown
	echo -e "\n\n"
	sleep 3s
fi

export pid1=`ps -ef | grep trading-api0.jar | grep -v "grep"| wc -l`

if [ "$pid1" == 1 ]; then
        kill -9 `ps -ef | grep trading-api0.jar | grep -v "grep" | awk '{print $2}'`
	echo -e "Kill Stop.\n"
fi

echo -e "Starting......\n"

nohup java -jar -Dspring.profiles.active=prod ./trading-api0.jar 1>/dev/null 2>&1 &

export pname=`ps -ef | grep trading-api0.jar | grep -v "grep" | awk '{print $8,$9,$10,$11}'`
echo $pname
echo -e "\n"

# timeout 30s tail -f logs/trading-api0.log
