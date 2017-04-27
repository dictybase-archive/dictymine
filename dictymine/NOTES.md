## Aim  
The idea was to have a intermine prototype for `dictybase`, __dictymine__
running with a basic `Genes -> GO -> GO annotation` with all the basic features
up and running. And then try out a deploy to [google
cloud](https://cloud.google.com/) using my stack of docker and
[kuberntes](https://kubernetes.io/)

## Progress
* On the first day saw the new intermine UI
  [redgenes](https://github.com/intermine/redgenes) which i set it up with a simple
  docker build.

  ```
  docker build --rm -t dictybase/redgenes github.com/intermine/redgenes
  ```

  and then run with 

  ```
  docker run --rm -p 3000:3000 dictybase/redgenes
  ```

  As a first impression, it was polished, responsive and nicely designed with a good dose of animations.
  So, to get `redgenes` on cloud i just have to set up a [helm](https://github.com/kubernetes/helm)
  chart later on.
  Anyway, moved on to backend and this is where the fun ended.

* To make your mine, the intermine repository have to be checked out and then
  you work on that copy. It means, a fork have to be done with periodic
  merge from upstream. It also means possible conflict resolution during
  merges. __Just wondering why not provide a bundled intermine core and release it
  as installable dependencies that every implementation could use it like a regular
  software.__ 

  ```
  So is it a design decision or a technical limitation
  ```
  Anyway, [backstore](https://github.com/1egoman/backstroke) is probably going to help here.

* For my first load and for no particular reason i wanted to load [gene
  ontology](http://obofoundry.org/ontology/go.html). The instructions are
  pretty
  [simple](http://intermine.readthedocs.io/en/latest/database/data-sources/library/go/go-obo/)
  and straight forward. So, as usual i took a docker centric approach and
  planned to build it on a postgresql inside my local `kubernetes` cluster. In
  this way, the entire setup becomes cloud deployable from day one.

* Here is the basic `dockerfile`.

    ```
    FROM openjdk:8-alpine
    MAINTAINER 'Siddhartha Basu<sidd.basu@gmail.com>'
    ARG user=cybersiddhu
    RUN apk update \
        && apk add apache-ant --update-cache --repository http://dl-4.alpinelinux.org/alpine/edge/testing/ --allow-untrusted
    RUN apk add git perl
    RUN addgroup -g 1000 -S $user && adduser -G $user -D -u 1000 -S $user
    ENV ANT_HOME /usr/share/java/apache-ant 
    ENV PATH $PATH:$ANT_HOME/bin
    ENV ANT_OPTS "-server -XX:MaxPermSize=512M -Xmx5g -XX:+UseParallelGC -Xms2g -XX:SoftRefLRUPolicyMSPerMB=1 -XX:MaxHeapFreeRatio=99"
    USER $user
    ```

    Then volume mounted the checked out folder and just ran the commands interactively inside the container’s shell.
    ```
    docker run --rm -it -v ${PWD}:/intermine   dictybase/intermine-ready /bin/sh
    ```

    And the software stack
    
    * OpenJDK 8
    * Ant 1.9.7
    * Postgresql 9.5

* The usual steps for starting intermine build, straight from the tutorial.
  * Create your mine workspace.
    ```
    bio/scripts/make_mine dictymine
    ```
  
  * Set up database configuration
  ```
  mkdir ${HOME}/.intermine
  cp /intermine/dictymine/dictymine.properties.example ${HOME}/.intermine/dictymine.properties
  Edit the file as necessary.
  ```

  __It would be great to make the location of this file(.intermine)
  configurable, something like using a environmental variable instead of a
  hardcoded location__.

  Or if it’s possible to pass the credentials to `ant` on the fly using command line parameter.

  * Add your source in `project.xml` file for gene ontology.
  ```
      <source name="go" type="go">
          <property name="src.data.file" location="/intermine/dictymine/data/ontology/go.obo"/>
      </source>
  ```

  * Load the data
  ```
   ant clean build-db
   ant -v -Dsource=go
  ```

  ### Not fun part
  And this is where things went really really slow. I spend part of wednesday,
  almost full of thursday and friday to figured it out. At least, four to five runs of load of 4-5 hours
  could not finish the data loading, so i have stop it in the middle of it. 
  The data finally got loaded in friday night(March 31st) after i changed to `ANT_OPTS` and gave it a high
  dose of memory. The line below 
  ```
  ANT_OPTS "-server -XX:MaxPermSize=512M -Xmx5g -XX:+UseParallelGC -Xms2g -XX:SoftRefLRUPolicyMSPerMB=1 -XX:MaxHeapFreeRatio=99"
  ```

  particularly `Xmx5g` and `Xms2g` made it work. However, it still took `2 and
  half hours` and almost all of the time went on to generate the transitive
  closures. It’s more or less the same issue reported in the mailing
  [list](http://gmod.827538.n3.nabble.com/adding-the-GO-source-tp3448770p3448809.html)
  a while back. 
  __Is this closure generation have to be that slow ? Can’t it be  made better?__
  I don’t know where it should be improved, but so far there’s no improvement in last 6 years.
  

  

  


  




