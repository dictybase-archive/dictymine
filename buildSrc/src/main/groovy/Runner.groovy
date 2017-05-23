class TaskRunner {
    def makeXmlModel(project) { 
        project.ant.taskdef(
            name: 'buildtorque',
            classname: 'org.intermine.objectstore.intermine.TorqueModelOutputTask'
        ) {
            classpath {
                pathelement(path: project.configurations.builder.asPath)
                pathelement(path: "${project.buildDir}/main")
                pathelement(path: project.sourceSets.main.output.classesDir)
            }
        }
        project.ant.buildtorque(
            osName: osName,
            destFile: "${project.buildDir}/main/${project.osName}-schema.xml"
        )
    }

    def prepareDatabase(project) {  
        project.ant.taskdef(
            name: 'preparedb',
            classname: 'org.intermine.task.BuildDbTask'
        ) {
            classpath {
                pathelement(path: project.configurations.builder.asPath)
                pathelement(path: "${project.buildDir}/main")
            }
        }
        project.ant.preparedb(
            osName: osName,
            tempdir: "${project.buildDir}/tmp",
            schemafile: "${project.osName}-schema.xml",
            model: project.model
        )
    }

    def insertDbModel(project) { 
        project.ant.taskdef(
            name: 'insertmodel',
            classname: 'org.intermine.task.StoreMetadataTask'
        ) {
            classpath {
                pathelement(path: project.configurations.builder.asPath)
                pathelement(path: "${project.buildDir}/main")
                pathelement(path: "${project.buildDir}/model")
            }
        }
        project.ant.insertmodel(
            osName: project.osName,
            modelName: project.model
        )
    }

    def createDbIndexes(project) {
        project.ant.taskdef(
            name: 'createindexes',
            classname: 'org.intermine.task.CreateIndexesTask'
        ) {
            classpath {
                pathelement(path: project.configurations.integration.asPath)
                pathelement(path: project.sourceSets.main.output.classesDir)
                pathelement(path: "${project.buildDir}/main")
                pathelement(path: "${project.buildDir}/model")
            }
        }
        project.ant.createindexes(
            alias: project.osName
        )
    }

    def analyseDatabase(project) { 
        project.ant.taskdef(
            name: 'analysedb',
            classname: 'org.intermine.task.AnalyseDbTask'
        ) {
            classpath {
                pathelement(path: project.configurations.integration.asPath)
                pathelement(path: project.sourceSets.main.output.classesDir)
                pathelement(path: "${project.buildDir}/main")
                pathelement(path: "${project.buildDir}/model")
            }
        }
        ant.analysedb(
            osName: project.osName
        )
    }


    def parseSourceProp(filePath) {
        def props = new Properties()
        new File(filePath).withInputStream{ props.load(it) }
        props
    }


    def testThat = { project -> 
        println project.findProject(':intermine:integrate').sourceSets.main.output.classesDir
    }

    def sourceXmlToConfig(source) {
        def props = new Properties()
        source.property.each { prop ->
            def values = prop.attributes().values() 
            props.setProperty(values[0],values[1])
        }
        props
    }

    def mergeProps(props1, props2) { 
        def merged = new Properties()
        merged.putAll(props1)
        merged.putAll(props2)
        merged
    }

    def projXmltoSourceTree(project, name) {
        project.sources.source.find {
           it.@name == name 
        }
    }

    def projXmltoType(project, name) { 
        source = this.projXmltoSourceTree(project, name)
        source.@type
    }

    def commonOsPrefix(project) {
        def prop = project.property.find {
            it.@name == 'common.os.prefix'
        }
        prop.@value
    }

    def tgtModel(project) {
        def prop = project.property.find {
            it.@name == 'target.model'
        }
        prop.@value
    }

    def srcLocation(source) {
        def prop = source.property.find {
            it.@name == 'src.data.dir'
        }
        prop.@location
    }

    def loadTgtSource(project, srcName) { 
        def projectXml = project.projectXml
        def srcType = this.projXmltoType(projectXml, srcName)
        def srcTree = this.projXmltoSourceTree(projectXml, srcName)
        def mergedProps = this.mergeProps(
            this.parseSourceProp("${project.sourceDir}/${srcType}/project.properties"),
            this.sourceXmlToConfig(srcTree) 
        ) 
        // check if its a gff3 source
        if (mergedProps.containsKey('have.file.gff3')) {
            project.ant.taskdef(
                name: 'convertgff3',
                classname: 'org.intermine.bio.task.GFF3ConverterTask'
            ){
                classpath {
                    pathelement(path: project.findProject(':bio:core').sourceSets.main.output.classesDir)
                    pathelement(path: project.findProject(':intermine:objectstore').sourceSets.main.output.classesDir)
                    pathelement(path: project.findProject(':intermine:integrate').sourceSets.main.output.classesDir)
                    pathelement(path: project.findProject(':intermine:model').sourceSets.main.output.classesDir)
                    pathelement(path: project.findProject(':intermine:integrate:model:fulldata').sourceSets.main.output.classesDir)
                    pathelement(path: project.findProject(':intermine:integrate:model:fulldata').sourceSets.main.output.resourcesDir)
                    pathelement(path: project.findProject(':dictymine:dbmodel').sourceSets.main.output.resourcesDir)
                    pathelement(path: project.findProject(":bio:sources:${srcType}").sourceSets.main.output.classesDir)
                    pathelement(path: project.findProject(':bio:core').sourceSets.main.output.resourcesDir)
                    pathelement(path: project.configurations.log4j.asPath)
                    pathelement(path: project.configurations.lang.asPath)
                    pathelement(path: project.configurations.hikari.asPath)
                    pathelement(path: project.configurations.pg.asPath)
                    pathelement(path: "${project.buildDir}/main")
                }
            }
            project.ant.convertgff3(
                converter: 'org.intermine.bio.dataconversion.GFF3Converter',
                target: "osw.${this.commonOsPrefix(projectXml)}-tgt-items",
                seqClsName: mergedProps['gff3.seqClsName'],
                orgTaxonId:  mergedProps['gff3.taxonId'],
                dataSourceName: mergedProps['gff3.dataSourceName'],
                dataSetTitle: mergedProps['gff3.dataSetTitle'],
                handlerClassName: mergedProps['gff3.handlerClassName'],
                model: tgtModel(projectXml),
                seqDataSourceName: mergedProps.containsKey('gff3.seqDataSourceName') ? mergedProps['gff3.seqDataSourceName']: null
                //seqHandlerClassName: mergedProps.containsKey('gff3.seqHandlerClassName') ? mergedProps['gff3.seqHandlerClassName']: '',
                //dontCreateLocations: mergedProps.containsKey('gff3.dontCreateLocations') ? mergedProps['gff3.dontCreateLocations']: false
            ) {
                fileset(dir: srcLocation(srcTree)) {
                    include(name: '*.gff')
                    include(name: '*.gff3')
                }
            }
        } else {
            println 'other source is not defined yet'
        }
    }

    def loadTgtToProd(project, srcName) {  
        def projectXml = project.projectXml
        def srcType = this.projXmltoType(projectXml, srcName)
        def srcTree = this.projXmltoSourceTree(projectXml, srcName)

        project.ant.taskdef(
           name: 'loadtgttoprod',
          classname: 'org.intermine.dataloader.ObjectStoreDataLoaderTask'
        ) {
            classpath {
                pathelement(path: project.findProject(':bio:core').sourceSets.main.output.classesDir)
                pathelement(path: project.findProject(':intermine:objectstore').sourceSets.main.output.classesDir)
                pathelement(path: project.findProject(':intermine:integrate').sourceSets.main.output.classesDir)
                pathelement(path: project.findProject(':intermine:model').sourceSets.main.output.classesDir)
                pathelement(path: project.findProject(':intermine:integrate:model:fulldata').sourceSets.main.output.classesDir)
                pathelement(path: project.findProject(':intermine:integrate:model:fulldata').sourceSets.main.output.resourcesDir)
                pathelement(path: project.findProject(':dictymine:dbmodel').sourceSets.main.output.resourcesDir)
                pathelement(path: project.findProject(':dictymine:dbmodel').sourceSets.main.output.classesDir)
                pathelement(path: project.findProject(":bio:sources:${srcType}").sourceSets.main.output.classesDir)
                pathelement(path: project.findProject(":bio:sources:${srcType}").sourceSets.main.output.resourcesDir)
                pathelement(path: project.findProject(':bio:core').sourceSets.main.output.resourcesDir)
                pathelement(path: project.configurations.log4j.asPath)
                pathelement(path: project.configurations.lang.asPath)
                pathelement(path: project.configurations.hikari.asPath)
                pathelement(path: project.configurations.pg.asPath)
                pathelement(path: project.configurations.cglib.asPath)
                pathelement(path: project.configurations.torque.asPath)
                pathelement(path: project.configurations.torquegen.asPath)
                pathelement(path: "${project.buildDir}/main")
            }
        }
        project.ant.loadtgttoprod(
            integrationWriter: 'integration.production',
            source: "os.${this.commonOsPrefix(projectXml)}-translated",
            sourceName: srcName,
            sourceType: srcType,
            allSources: projectXml.sources.source.collect { it.@name }.join(' ')
        )
    }
}
