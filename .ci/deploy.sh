#!/bin/bash

set -eu

mvn package

ssh root@ambient '/etc/init.d/jetty9 stop'

scp target/AmbientMonitoring-1.0-SNAPSHOT.war root@ambient:/var/lib/jetty9/webapps/root.war

ssh root@ambient '/etc/init.d/jetty9 start'

