#!/bin/bash

branch=$1
if [ x$1 == x ]; then
  branch='master'
fi

echo -e "******************Now is deplpoy branch: $branch *****************"

#stop tomcat servers
echo -e "******************     Stopping tomcat servers   *****************"
cd ~/tomcat-instances/
9090/stop-9090.sh
#8080/stop-8080.sh

#fetch latest code
echo -e "******************       Fetching latest code    *****************"
cd /data/whatever
mvn clean
git stash
git fetch
git checkout $branch
git reset --hard origin/$branch
git stash apply

echo -e "******************       Fetching last commit    *****************"
last_commit=`git log -1 --pretty=oneline | sed 's/ .*//'`
export LAST_COMMIT="$last_commit"

#build assets
echo -e "******************        Building assets        *****************"
rm -rf src/main/webapp/static/build/*
npm run build
mv src/webapp/static/manifest.json src/main/resources/props

#run maven to build project
echo -e "******************         Now packaging         *****************"
mvn package

#start tomcat servers
echo -e "******************     Starting tomcat servers   *****************"
#~/tomcat-instances/9090/start-8080.sh
~/tomcat-instances/9090/start-9090.sh