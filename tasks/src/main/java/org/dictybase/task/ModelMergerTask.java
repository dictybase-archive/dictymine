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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.intermine.metadata.InterMineModelParser;
import org.intermine.metadata.MetaDataException;
import org.intermine.metadata.Model;
import org.intermine.modelproduction.ModelFileMerger;

import org.gradle.api.*;
import org.gradle.api.file.*;
import org.gradle.api.tasks.*;

/**
 * Task to merge a single additions file into an intermine XML model.
 *
 * @see org.intermine.modelproduction.ModelMerger
 * @author Thomas Riley
 * @author Siddhartha Basu
 */

public class ModelMergerTask
{
    protected List<File> additionsFiles = new ArrayList<File>();
    protected File inputModelFile;
    protected File outputModelFile;

    /**
     * Set the model to add additions to.
     * @param file path to model file
     */
    public void setInputModelFile(File file) {
        inputModelFile = file;
    }

    /**
     * The file containing model additions.
     * @param file the additions file
     */
    public void setAdditionsFile(File file) {
        additionsFiles.add(file);
    }

    /**
     * The files containing model additions.
     * @param files the additions files
     */
    public void setAdditionsFiles(List<File> files) {
        additionsFiles = files;
    }

    /**
     * Path of file to write resulting model to. May be the same as <code>inputModelFile</code>.
     * @param file path to write resulting model to
     */
    public void setOutputFile(File file) {
        outputModelFile = file;
    }

    public void run() throws GradleException {
        InterMineModelParser parser = new InterMineModelParser();

        try {
            Model merged =
                    ModelFileMerger.mergeModelFromFiles(inputModelFile, additionsFiles, parser);
            FileWriter writer = new FileWriter(outputModelFile);
            writer.write(merged.toString());
            writer.close();
        } catch (MetaDataException e) {
            throw new GradleException("Failed to parse model from input files", e);
        } catch (IOException e) {
            throw new GradleException("failed to write model file: " + outputModelFile, e);
        }
    }
}
