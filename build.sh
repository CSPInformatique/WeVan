mvn install:install-file -Dfile=lib/amaranth-1.0.0.jar -DgroupId=com.cspinformatique -DartifactId=amaranth -Dversion=1.0.0 -Dpackaging=jar

mvn install:install-file -Dfile=lib/jasperreports-fonts-5.5.1.jar -DgroupId=net.sf.jasperreports -DartifactId=jasperreports-fonts -Dversion=5.5.1 -Dpackaging=jar

mvn install:install-file -Dfile=lib/jasperreports-functions-5.5.1.jar -DgroupId=net.sf.jasperreports -DartifactId=jasperreports-functions -Dversion=5.5.1 -Dpackaging=jar

mvn clean install