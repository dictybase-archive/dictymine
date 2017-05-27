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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.intermine.task.project.Project;
import org.intermine.task.project.ProjectXmlBinding;
import org.intermine.task.project.Source;
import org.dictybase.task.ModelMergerTask;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.gradle.api.*;
import org.gradle.api.file.*;
import org.gradle.api.tasks.*;

/**
 * Task to merge additions files from all sources in the project.xml into an intermine XML model.
 *
 * @see org.intermine.modelproduction.ModelMerger
 * @author Kim Rutherford
 * @author Siddhartha Basu
 */

public class MergeSourceModelsTask extends DefaultTask
{
    private File modelFile, projectXml;
    private FileCollection extraModelPathsStart;
    private FileCollection extraModelPathsEnd;
    /** Base directory that all projects are relative to. */
    //private File workspaceBaseDir;

    //private static final String MODEL_MERGER_TASK = "org.intermine.task.ModelMergerTask";

    /**
     * Set the classpath to use for post processing.
     * @param ref the classpath reference
     */
    //public void setClassPathRef(Reference ref) {
        //this.classPathRef = ref;
    //}

    /**
     * Base directory that all projects are assumed relative to.
     *
     * @param basedir base directory that all projects are assumed relative to
     */
    //@InputDirectory
    //public void setBasedir(File basedir) {
        //workspaceBaseDir = basedir;
    //}

    /**
     * Set the project.xml file to use when post-processing.
     * @param projectXml the project xml file
     */
    @InputFile
    public File getProjectXml() {
        return this.projectXml;
    }
    public void setProjectXml(File file) {
        projectXml = file;
    }

    /**
     * Set the model to add additions to.
     * @param file path to model file
     */
    @InputFile
    public File getModelFile() {
        return this.modelFile;
    }
    public void setModelFile(File file) {
        modelFile = file;
    }

    /**
     * The paths containing extra model additions that should be merged first.
     * @param extraModelPathsStart is a list of model addition paths
     */
    @InputFiles
    public FileCollection getExtraModelPathsStart() {
        return this.extraModelPathsStart;
    }
    public void setExtraModelPathsStart(FileCollection files) {
        extraModelPathsStart = files;
    }

    /**
     * The paths containing extra model additions that should be merged last.
     * @param extraModelPathsEnd is a list of model addition paths
     */
    @InputFiles
    @Optional
    public FileCollection getExtraModelPathsEnd() {
        return this.extraModelPathsEnd;
    }
    public void setExtraModelPathsEnd(FileCollection files) {
        extraModelPathsEnd = files;
    }

    @TaskAction
    public void run() throws GradleException {
        Project imProject = ProjectXmlBinding.unmarshall(projectXml);
        List<File> pathsToMerge = new ArrayList<File>();
        // Add the first set of extra models
        pathsToMerge.addAll(extraModelPathsStart.getFiles());

        Collection<Source> sources = imProject.getSources().values();
        for (Source source: sources) {
            String additionsFileName = source.getType() + "_additions.xml";
            File additionsFile;
            try {
                additionsFile = new File(source.getLocation(),
                                                additionsFileName).getCanonicalFile();
            } catch (IOException e) {
                throw new GradleException("unable to get filepath ", e);
            }
            if (additionsFile.exists()) {
                if (!pathsToMerge.contains(additionsFile)) {
                    pathsToMerge.add(additionsFile);
                }
            } else {
                System.err.println("warning: " + additionsFile + " not found");
            }
        }

        // Add the last set of extra models
        if (extraModelPathsEnd != null) {
            if (!extraModelPathsEnd.isEmpty()) {
                pathsToMerge.addAll(extraModelPathsEnd.getFiles());
            }
        }

        ModelMergerTask mergeTask = new ModelMergerTask();
        mergeTask.setInputModelFile(modelFile);
        mergeTask.setOutputFile(modelFile);
        try {
            mergeTask.setAdditionsFiles(pathsToMerge);
        } catch (Exception e) {
            throw new GradleException("exception while adding files to model merger task ", e);
        }
        try {
            mergeTask.run();
        } catch (Exception e) {
            throw new GradleException("exception while invoking execute on model merger task ", e);
        }
    }
}
