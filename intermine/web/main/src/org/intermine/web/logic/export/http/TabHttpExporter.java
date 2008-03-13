package org.intermine.web.logic.export.http;

/*
 * Copyright (C) 2002-2008 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.intermine.web.logic.export.Exporter;
import org.intermine.web.logic.export.ExporterImpl;
import org.intermine.web.logic.export.ResponseUtil;
import org.intermine.web.logic.export.rowformatters.TabRowFormatter;

/**
 * Exporter that exports table with results in excel format.
 * @author Jakub Kulaviak
 **/
public class TabHttpExporter extends HttpExporterBase
{
    /**
     * Constructor.
     */
    public TabHttpExporter() { }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setResponseHeader(HttpServletResponse response) {
        ResponseUtil.setTabHeader(response, "results-table.tsv");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Exporter getExporter(OutputStream out) {
        return new ExporterImpl(out, new TabRowFormatter());
    }
}
