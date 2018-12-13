#!/bin/sh

usage() {
	echo "Usage:"
	echo "    startup.sh [-c config_folder] [-l log_folder] [-d debug mode] [-h]"
	echo "Description:"
	echo "    config_folder - config folder path, not must,if empty,default classpath"
	echo "    log_folder - hsb server's logs base folder, /home/work  log path: /home/work/logs"
	echo "    debug_mode - 1|0  1 means debug port is open, 0 close ,default 0"
	echo "    -h - show this help"
	exit -1
}
LOG_BASE_DIR=""
CONFIG_DIR=""
DEBUG_MODE="0";

while getopts "h:l:c:d:" arg
do
	case $arg in
	    l) LOG_BASE_DIR=$OPTARG;;
		c) CONFIG_DIR=$OPTARG;;
		d) DEBUG_MODE=$OPTARG;;
		h) usage;;
		?) usage;;
	esac
done

#check JAVA_HOME & java
noJavaHome=false
#export JAVA_HOME=/app/was/jdk1.8.0_161
export JAVA_HOME=/usr/java/jdk1.8.0_181-amd64
if [ -z "$JAVA_HOME" ] ; then
    noJavaHome=true
fi
if [ ! -e "$JAVA_HOME/bin/java" ] ; then
    noJavaHome=true
fi
if $noJavaHome ; then
    echo
    echo "Error: JAVA_HOME environment variable is not set."
    echo
    exit 1
fi
#==============================================================================


saleLog_HOME=`pwd`
JAVA_OPTS="-server -Xmx512M -Xms512M -Xmn256M"
JAVA_OPTS="$JAVA_OPTS -XX:+DisableExplicitGC -XX:SurvivorRatio=1 -XX:+UseConcMarkSweepGC"
JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled"
JAVA_OPTS="$JAVA_OPTS -XX:+CMSClassUnloadingEnabled -XX:LargePageSizeInBytes=128M"
JAVA_OPTS="$JAVA_OPTS -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly"
JAVA_OPTS="$JAVA_OPTS -XX:CMSInitiatingOccupancyFraction=80 -XX:SoftRefLRUPolicyMSPerMB=0"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintClassHistogram -XX:+PrintGCDetails -XX:+PrintGCTimeStamps"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintHeapAtGC -Xloggc:${saleLog_HOME}/logs/gc.log"
#LOG4J2 ASYNC
JAVA_OPTS="$JAVA_OPTS -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector"

#==============================================================================

#set HOME
CURR_DIR=`pwd`
cd `dirname "$0"`/..
cd $CURR_DIR
if [ -z "$saleLog_HOME" ] ; then
    echo
    echo "Error: saleLog_HOME environment variable is not defined correctly."
    echo
    exit 1
fi
#==============================================================================

#set CLASSPATH
saleLog_CLASSPATH="$saleLog_HOME/conf:$saleLog_HOME/lib/*"
#==============================================================================

#startup Server
RUN_CMD="\"$JAVA_HOME/bin/java\""
RUN_CMD="$RUN_CMD -DappName=saleLog -DsaleLog_HOME=\"$saleLog_HOME\""
RUN_CMD="$RUN_CMD -classpath \"$saleLog_CLASSPATH\""
RUN_CMD="$RUN_CMD $JAVA_OPTS"
RUN_CMD="$RUN_CMD com.idea.sale.admin.Application $@"
RUN_CMD="$RUN_CMD >> \"$saleLog_HOME/logs/console.log\" 2>&1 &"
echo $RUN_CMD
eval $RUN_CMD
echo $! > $saleLog_HOME/bin/saleLog.pid
#==============================================================================