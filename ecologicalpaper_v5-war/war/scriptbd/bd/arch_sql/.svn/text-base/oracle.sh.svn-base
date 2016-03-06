#!/bin/bash
#
# Run-level Startup script for the Oracle Instance and Listener
#
# chkconfig: 345 91 19
# description: Startup/Shutdown Oracle listener and instance
#!/bin/sh
# ---------------------------------------------------------------------------
# Start script for the OpenOffice transform service
# ---------------------------------------------------------------------------
export JDK_HOME=/opt/jdk1.6.0_02
export JAVA_HOME=/opt/jdk1.6.0_02
export CATALINA_HOME=/opt/jakarta-tomcat-5.0.28
export TOMCAT_HOME=/opt/jakarta-tomcat-5.0.28
export ANT_HOME=/opt/apache-ant-1.7.0
export PATH=$PATH:/opt/jdk1.6.0_02/bin:$ANT_HOME/bin
export JAVA_OPTS='-Xms128m -Xmx512m -XX:MaxPermSize=256M -server'


# ---------------------------------------------------------------------------
# Start script for the OpenOffice transform service
# ---------------------------------------------------------------------------

echo "Starting OpenOffice service..."

# Comment or uncomment the appropriate location using #
# Assumes OpenOffice is installed in /opt
/usr/lib/openoffice/program/soffice "-accept=socket,host=localhost,port=8100;urp;StarOffice.ServiceManager" -nologo -headless -nofirststartwizard &



ORA_HOME="/u01/app/oracle/product/10.1.0/db_1"
ORA_OWNR="oracle"

# if the executables do not exist -- display error

if [ ! -f $ORA_HOME/bin/dbstart -o ! -d $ORA_HOME ]
then
        echo "Oracle startup: cannot start"
        exit 1
fi

# depending on parameter -- startup, shutdown, restart 
# of the instance and listener or usage display 

case "$1" in
    start)
        # Oracle listener and instance startup
        echo -n "Starting Oracle: "
        # start TNS listener
        su - $ORA_OWNR -c "$ORA_HOME/bin/lsnrctl start"
       
        # start database
        su - $ORA_OWNR -c $ORA_HOME/bin/dbstart

        echo -n "Starting Enterprice Manager: "
        su - $ORA_OWNR -c "$ORA_HOME/bin/emctl start dbconsole"
  
        echo  "*** Starting Oracle iSQL Plus *** "
        su - $ORA_OWNR -c "$ORA_HOME/bin/isqlplusctl start"

        #touch /var/lock/subsys/oracle
        echo "OK"
        ;;
    stop)
        # Oracle listener and instance shutdown
        echo -n "Shutdown Oracle: "
        # stop TNS listener
        su - $ORA_OWNR -c "$ORA_HOME/bin/lsnrctl stop"
        # stop enterprice manager
        su - $ORA_OWNR -c "$ORA_HOME/bin/emctl stop dbconsole"

        echo  "*** stop Oracle iSQL Plus *** "
        su - $ORA_OWNR -c "$ORA_HOME/bin/isqlplusctl stop"

        # stop database
        su - $ORA_OWNR -c $ORA_HOME/bin/dbshut
        rm -f /var/lock/subsys/oracle
        echo "OK"
        ;;
    reload|restart)
        $0 stop
        $0 start
        ;;
    *)
        echo "Usage: $0 start|stop|restart|reload"
        exit 1
esac
exit 0
