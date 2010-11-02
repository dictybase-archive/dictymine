package org.intermine.api.query.codegen;

/*
 * Copyright (C) 2002-2010 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import org.intermine.api.template.TemplateQuery;
import org.intermine.pathquery.PathQuery;

/**
 *
 * @author Fengyuan Hu
 *
 */
public interface WebserviceCodeGenerator
{

    /**
     * This method will generate web service source code from a path query.
     *
     * @param query a PathQuery
     * @return web service source code in a string
     */
    String generate(PathQuery query);

    /**
    * This method will generate web service source code from a template query.
    *
    * @param query a TemplateQuery
    * @return web service source code in a string
    */
    String generate(TemplateQuery query);

}