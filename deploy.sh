#!/usr/bin/env bash

export work_dir="/opt/project/erp"

echo "start erp rebuild ......"

killErp(){
    # get the old erp process if necessary
    pid=`ps -ef | grep erp | grep java | awk '{print $2}'`

    echo "erp pid is : $pid"

    # if exist kill it
    kill -9 ${pid}

    echo "kill the old erp process"
}

# stop old erp process
killErp

# input worker dir
cd ${work_dir}

echo "cd the work_dir"

# remove the old erp sources
rm -rf erp
rm -rf nohup.out

echo "remove the old erp sources"

# clone the new resources from git
git clone git@github.com:anguoyoula/erp.git

echo "clone the resources from git"

# cd new erp dir
cd erp

# build the new erp by maven
mvn clean package -Dmaven.test.skip=true

echo "build new erp by maven"

# cd work_dir
cd ../

# start new erp
nohup java -jar ${work_dir}/erp/target/erp-2.0-SNAPSHOT.jar --spring.profiles.active=prod &

echo "rebuild erp success"