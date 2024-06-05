cd ..\

PROJECT_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
echo "project version: ${PROJECT_VERSION}"

mvn install:install-file -Dfile="docs-common-client/target/docs-common-client-${PROJECT_VERSION}.jar" -DgroupId="com.surikat.docs" -DartifactId="docs-common-client" -Dversion="${PROJECT_VERSION}" -Dpackaging=jar
mvn install:install-file -Dfile="docs-common/target/docs-common-${PROJECT_VERSION}.jar" -DgroupId="com.surikat.docs" -DartifactId="docs-common" -Dversion="${PROJECT_VERSION}" -Dpackaging=jar