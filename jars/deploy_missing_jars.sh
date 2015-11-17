#fichier de script pour deployer le jar dans le repository local\
#pour deployer le jar sur le serveur de dependances (artifactory) utiliser deploy:deploy-file\
mvn install:install-file -DgroupId=com.sybase -DartifactId=sybase-jdbc-genethon -Dversion=1.0 -Dpackaging=jar -Dfile=./sybase-jdbc-genethon.jar
#--oracle jdbc
mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=1.0 -Dpackaging=jar -Dfile=./ojdbc6.jar
#--ucanaccess (remplace jdbcodbc, plus disponible) + dependances
mvn install:install-file -DgroupId=net.ucanaccess -DartifactId=ucanaccess -Dversion=2.0.9.3 -Dfile=./ucanaccess-2.0.9.3.jar -Dpackaging=jar
mvn install:install-file -DgroupId=net.ucanaccess.deps -DartifactId=jackcess -Dversion=2.0.8 -Dfile=./jackcess-2.0.8.jar -Dpackaging=jar
mvn install:install-file -DgroupId=net.ucanaccess.deps -DartifactId=commons-logging -Dversion=1.1.1 -Dfile=./commons-logging-1.1.1.jar -Dpackaging=jar
mvn install:install-file -DgroupId=net.ucanaccess.deps -DartifactId=hsqldb -Dversion=1.0 -Dfile=.//hsqldb.jar -Dpackaging=jar
mvn install:install-file -DgroupId=net.ucanaccess.deps -DartifactId=commons-lang -Dversion=2.6 -Dfile=./commons-lang-2.6.jar -Dpackaging=jar

