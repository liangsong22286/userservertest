#!/bin/bash
#
#    .   ____          _            __ _ _
#   /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
#  ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
#   \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
#    '  |____| .__|_| |_|_| |_\__, | / / / /
#   =========|_|==============|___/=/_/_/_/
#   :: Spring Boot Startup Script ::
#  	lanch.script from spring spring-boot-loader-tools-1.5.6.RELEASE-sources.jar


### BEGIN INIT INFO
# Provides:          {{initInfoProvides:spring-boot-application}}
# Required-Start:    {{initInfoRequiredStart:$remote_fs $syslog $network}}
# Required-Stop:     {{initInfoRequiredStop:$remote_fs $syslog $network}}
# Default-Start:     {{initInfoDefaultStart:2 3 4 5}}
# Default-Stop:      {{initInfoDefaultStop:0 1 6}}
# Short-Description: {{initInfoShortDescription:Spring Boot Application}}
# Description:       {{initInfoDescription:Spring Boot Application}}
# chkconfig:         {{initInfoChkconfig:2345 99 01}}
### END INIT INFO






javaexe="${JAVA_HOME}/bin/java"

SPRING_PROFILES_ACTIVE="prod"
SERVER_PORT="18600"
APPLICATION_NAME="userserver"
APPLICATION_JAR_NAME="UserServerApi-1.0-SNAPSHOT.jar"


GC_LOG="/data/logs/userserver/userserver-18600.log"
TMP_DIR="/tmp"


JAVA_OPTS="-Xms3072m -Xmx3072m -Xmn512m -Xss512k -Xdebug -Dcom.sun.management.jmxremote -Xloggc:$GC_LOG -XX:+PrintTenuringDistribution -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationConcurrentTime -XX:+PrintGCApplicationStoppedTime -Djava.awt.headless=true -Djava.io.tmpdir=$TMP_DIR -Dlogback.path.port=$SERVER_PORT"

#START_ARGS="-javaagent:$PINPOINT_AGENT_LOCATION -Dpinpoint.agentId=$PINPOINT_AGENT_ID -Dpinpoint.applicationName=$PINPOINT_APPLICATION_NAME"
#START_ARGS=" -Dlogstashurl=10.10.3.224:18930 -Denv=dev -Ddev_meta=http://10.10.3.225:18500"
START_ARGS=" -Denv=dev"

APPLICATION_ARGS="--spring.profiles.active=$SPRING_PROFILES_ACTIVE --server.port=$SERVER_PORT"


identity="$APPLICATION_NAME-$SERVER_PORT"
jarfile="$APPLICATION_JAR_NAME"



# Initialize log file name if not provided by the config file
[[ -z "$LOG_FILENAME" ]] && LOG_FILENAME="${identity}.log"


# Initialize stop wait time if not provided by the config file
[[ -z "$STOP_WAIT_TIME" ]] && STOP_WAIT_TIME="60"


# Determine the user to run as if we are root
# shellcheck disable=SC2012
[[ $(id -u) == "0" ]] && run_user=$(ls -ld "$jarfile" | awk '{print $3}')

# Build the pid and log filenames
PID_FOLDER="/var/run/${APPLICATION}"
pid_file="$PID_FOLDER/${identity}.pid"
log_file="./$LOG_FILENAME"



arguments=(-Dsun.misc.URLClassPath.disableJarChecking=true $START_ARGS $JAVA_OPTS -jar "$jarfile" $APPLICATION_ARGS "$@")




# Utility functions
checkPermissions() {
  touch "$pid_file" &> /dev/null || { echoRed "Operation not permitted (cannot access pid file)"; return 4; }
}


isRunning() {
  ps -p "$1" &> /dev/null
}

await_file() {
  end=$(date +%s)
  let "end+=10"
  while [[ ! -s "$1" ]]
  do
    now=$(date +%s)
    if [[ $now -ge $end ]]; then
      break
    fi
    sleep 1
  done
}


# ANSI Colors
echoRed() { echo $'\e[0;31m'"$1"$'\e[0m'; }
echoGreen() { echo $'\e[0;32m'"$1"$'\e[0m'; }
echoYellow() { echo $'\e[0;33m'"$1"$'\e[0m'; }


# Determine the script mode

action="$1"

# Action functions
start() {
  if [[ -f "$pid_file" ]]; then
    pid=$(cat "$pid_file")
    isRunning "$pid" && { echoYellow "Already running [$pid]"; return 0; }
  fi
  do_start "$@"
}



do_start() {
  mkdir -p "$PID_FOLDER" &> /dev/null
  checkPermissions || return $?
  "$javaexe" "${arguments[@]}" >> "$log_file" 2>&1 &
  pid=$!
  disown $pid
  echo "$pid" > "$pid_file"
  [[ -z $pid ]] && { echoRed "Failed to start"; return 1; }
  echoGreen "Started [$pid]"
}

stop() {
  working_dir=$(dirname "$jarfile")
  pushd "$working_dir" > /dev/null
  [[ -f $pid_file ]] || { echoYellow "Not running (pidfile not found)"; return 0; }
  pid=$(cat "$pid_file")
  isRunning "$pid" || { echoYellow "Not running (process ${pid}). Removing stale pid file."; rm -f "$pid_file"; return 0; }
  do_stop "$pid" "$pid_file"
}

do_stop() {
  kill "$1" &> /dev/null || { echoRed "Unable to kill process $1"; return 1; }
  for i in $(seq 1 $STOP_WAIT_TIME); do
    isRunning "$1" || { echoGreen "Stopped [$1]"; rm -f "$2"; return 0; }
    [[ $i -eq STOP_WAIT_TIME/2 ]] && kill "$1" &> /dev/null
    sleep 1
  done
  echoRed "Unable to kill process $1";
  return 1;
}

force_stop() {
  [[ -f $pid_file ]] || { echoYellow "Not running (pidfile not found)"; return 0; }
  pid=$(cat "$pid_file")
  isRunning "$pid" || { echoYellow "Not running (process ${pid}). Removing stale pid file."; rm -f "$pid_file"; return 0; }
  do_force_stop "$pid" "$pid_file"
}

do_force_stop() {
  kill -9 "$1" &> /dev/null || { echoRed "Unable to kill process $1"; return 1; }
  for i in $(seq 1 $STOP_WAIT_TIME); do
    isRunning "$1" || { echoGreen "Stopped [$1]"; rm -f "$2"; return 0; }
    [[ $i -eq STOP_WAIT_TIME/2 ]] && kill -9 "$1" &> /dev/null
    sleep 1
  done
  echoRed "Unable to kill process $1";
  return 1;
}

restart() {
  stop && start
}

force_reload() {
  working_dir=$(dirname "$jarfile")
  pushd "$working_dir" > /dev/null
  [[ -f $pid_file ]] || { echoRed "Not running (pidfile not found)"; return 7; }
  pid=$(cat "$pid_file")
  rm -f "$pid_file"
  isRunning "$pid" || { echoRed "Not running (process ${pid} not found)"; return 7; }
  do_stop "$pid" "$pid_file"
  do_start
}

status() {
  working_dir=$(dirname "$jarfile")
  pushd "$working_dir" > /dev/null
  [[ -f "$pid_file" ]] || { echoRed "Not running"; return 3; }
  pid=$(cat "$pid_file")
  isRunning "$pid" || { echoRed "Not running (process ${pid} not found)"; return 1; }
  echoGreen "Running [$pid]"
  return 0
}

run() {
  pushd "$(dirname "$jarfile")" > /dev/null
  "$javaexe" "${arguments[@]}"
  result=$?
  popd > /dev/null
  return "$result"
}

# Call the appropriate action function
case "$action" in
start)
  start "$@"; exit $?;;
stop)
  stop "$@"; exit $?;;
force-stop)
  force_stop "$@"; exit $?;;
restart)
  restart "$@"; exit $?;;
force-reload)
  force_reload "$@"; exit $?;;
status)
  status "$@"; exit $?;;
run)
  run "$@"; exit $?;;
*)
  echo "Usage: $0 {start|stop|force-stop|restart|force-reload|status|run}"; exit 1;
esac

exit 0
