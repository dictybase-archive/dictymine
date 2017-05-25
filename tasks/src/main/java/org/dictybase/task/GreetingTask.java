package org.dictybase.task;

import org.gradle.api.*;
import org.gradle.api.file.*;
import org.gradle.api.tasks.*;

public class GreetingTask extends DefaultTask {
    @Input
    public String greeting;

    //@Input
    //public String getGreeting() {
        //return this.greeting;
    //}

    @TaskAction
    public void run() {
        System.out.println(greeting);
    }
}
