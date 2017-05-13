## Maven command to create local repo
```
mvn install:install-file -Dfile=/intermine/intermine/web/main/lib/jstl-1.1.jar -DgroupId=javax.servlet.jsp.jstl -DartifactId=jstl -Dversion=1.1 -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=/intermine/intermine/repo
```
