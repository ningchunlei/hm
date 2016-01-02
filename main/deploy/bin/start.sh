#!/bin/sh
BASEDIR="$(dirname $(readlink -f $(dirname "$0")))"

profile=""
port=""
if [ $# -eq 2 ] ; then
   profile="-Dspring.profiles.active=$1"
   port="-Dserver.port=$2"
fi

str=`jps | grep -i "WebSiteApplication" | awk -F ' ' '{print $1}'`
if [ ! -n "$str" ]; then
        echo "huayuan-site process has been dead"
else
        echo "kill huayuan-site processId: $str"
        kill -9 $str
fi

nohup java $profile $port -server -Xmx2048m -cp $BASEDIR:$BASEDIR/config:$BASEDIR/lib/*:.  WebSiteApplication >> /data0/log/site/console.log 2>&1 &


str=`jps | grep -i "WebSiteApplication" | awk -F ' ' '{print $1}'`
if [ ! -n "$str" ]; then
        echo "huayuan-site process don't restart"
else
        echo "restart huayuan-site processId: $str"
fi