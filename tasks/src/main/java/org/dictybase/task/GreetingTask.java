package org.dictybase.task;

import org.gradle.api.*;
import org.gradle.api.file.*;
import org.gradle.api.tasks.*;

public class GreetingTask extends DefaultTask {
    private String greeting;

    @Input
    public void setGreeting(String text) {
        greeting = text;
    }

    @TaskAction
    public void run() {
        System.out.println(greeting);
    }
}
