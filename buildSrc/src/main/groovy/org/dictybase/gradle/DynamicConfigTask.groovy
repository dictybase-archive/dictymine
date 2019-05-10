package org.dictybase.gradle

import org.gradle.api.*
import org.gradle.api.tasks.*

class DynamicConfigTask extends DefaultTask {
    //@Input
    //@Optional
    //String getName() { return name }
    //void setName(String name) { name = name }
    String osName

    @TaskAction
    void runme() {
        print osName
    }

}
