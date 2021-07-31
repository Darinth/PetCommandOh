## Commands to load various dependencies into your local maven repository

Must be ran from within a maven repository, such as the one created by this project. You'll need to update these commands to point to the location of these jars on your local machine, they should be in your wurm unlimited dedicated server directory.

mvn install:install-file -Dfile="E:\SteamLibrary\steamapps\common\Wurm Unlimited Dedicated Server\common.jar" -DgroupId=com.wurmonline -DartifactId=common -Dversion=1.9.1.6 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile="E:\SteamLibrary\steamapps\common\Wurm Unlimited Dedicated Server\server.jar" -DgroupId=com.wurmonline -DartifactId=server -Dversion=1.9.0.0 -Dpackaging=jar

mvn install:install-file -Dfile="E:\SteamLibrary\steamapps\common\Wurm Unlimited Dedicated Server\modlauncher.jar" -DgroupId=org.gotti.wurmunlimited -DartifactId=modlauncher -Dversion=0.45-e51224b -Dpackaging=jar
