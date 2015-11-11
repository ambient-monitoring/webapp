#!/bin/bash

set -eu

mvn package

ssh root@ambient '/etc/init.d/jetty8 stop'

scp /home/eaglex/devel/self/ambient-monitoring/webapp/target/AmbientMonitoring-1.0-SNAPSHOT.war root@ambient:/root/

ssh root@ambient 'mv /root/AmbientMonitoring-1.0-SNAPSHOT.war /var/lib/jetty8/webapps/root.war'

ssh root@ambient '/etc/init.d/jetty8 start'
