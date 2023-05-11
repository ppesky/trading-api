#! /bin/sh

echo -e "Starting......\n"

nohup java -jar -Dspring.profiles.active=prod ./trading-api0.jar 1>/dev/null 2>&1 &

export pname=`ps -ef | grep trading-api0.jar | grep -v "grep" | awk '{print $8,$9,$10,$11}'`
echo $pname
echo -e "\n"

# timeout 30s tail -f logs/trading-api0.log
