#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
    target/platform-0.0.1-SNAPSHOT.jar \
    user@91.241.64.181:/home/user/

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa user@91.241.64.181 << EOF

pgrep java | xargs kill -9
nohup java -jar -Dspring.profiles.active=dev platform-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'
