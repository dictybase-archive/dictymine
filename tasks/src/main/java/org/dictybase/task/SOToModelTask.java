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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.intermine.bio.ontology.SequenceOntology;
import org.intermine.bio.ontology.SequenceOntologyFactory;
import org.intermine.metadata.Model;

import org.gradle.api.*;
import org.gradle.api.file.*;
import org.gradle.api.tasks.*;

/**
 * A Task that reads a SO OBO files and writes so_additions.xml
 *
 * @author Kim Rutherford
 * @author Siddhartha Basu
 */

public class SOToModelTask extends DefaultTask
{
    //private File soFile, soTermListFile, outputFile;

    /**
     * Sets the File containing the SO OBO data.
     *
     * @param soFile the SO OBO file
     */
    @InputFile
    public File soFile;

    /**
     * Set the file containing a list of SO terms to be added to the data model.
     * @param soTermListFile file containing list of SO terms
     */
    @InputFile
    public File soTermListFile;

    /**
     * Set the output file to write generated additions XML to.
     * @param outputFile the additions file that will be generated
     */
    @OutputFile
    public File outputFile;

    @TaskAction
    public void run() {
        try {
            SequenceOntology so = SequenceOntologyFactory.getSequenceOntology(soFile,
                    soTermListFile);
            Model model = null;
            PrintWriter out = null;
            try {
                model = so.getModel();
                out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
            } catch (IOException e) {
                throw new GradleException("Couldn't create new model file", e);
            }
            out.println(model.toAdditionsXML());
            out.flush();
            out.close();
            System.out.println("Wrote " + outputFile.getPath());
        } catch (Exception e) {
            throw new GradleException("issue with ontology file", e);
        }
    }
}
