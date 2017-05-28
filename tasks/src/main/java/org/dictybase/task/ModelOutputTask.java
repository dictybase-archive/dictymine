package org.dictybase.task;

/*
 * Copyright (C) 2002-2016 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.io.File;

import org.intermine.codegen.JavaModelOutput;
import org.intermine.metadata.Model;

import org.gradle.api.*;
import org.gradle.api.file.*;
import org.gradle.api.tasks.*;

/**
 * Creates and runs a ModelOutput process to generate java or config files.
 *
 * @author Richard Smith
 * @author Siddhartha Basu
 */

public class ModelOutputTask extends DefaultTask
{
    private File destDir;
    private Model dataModel;
    private String type;
    private String model;

    /**
     * Sets the directory that output should be written to.
     * @param destDir the directory location
     */
    @OutputDirectory
    public File getDestDir() {
        return this.destDir;
    }
    public void setDestDir(File file) {
        this.destDir = file;
    }

    /**
     * Set the type of model output required.
     * @param type the type of output
     */
    @Input
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type.toLowerCase();
    }

    /**
     * Set the model to be used.
     * @param modelName the model to be used
     */
    @Input
    public String getModel() {
        return this.model;
    }
    public void setModel(String name) {
        this.model = name;
    }

    @TaskAction
    public void run() {
        try {
            dataModel = Model.getInstanceByName(getModel());
        } catch (Exception e) {
            throw new GradleException("could not get model instance ", e);
        }
        try {
            if ("java".equals(getType())) {
                JavaModelOutput mo = new JavaModelOutput(dataModel, getDestDir());
                mo.process();
            } else {
                throw new GradleException("Unrecognised value for output type: " + getType());
            }
        } catch (Exception e) {
            throw new GradleException("unable to generate model ", e);
        }
    }
}
