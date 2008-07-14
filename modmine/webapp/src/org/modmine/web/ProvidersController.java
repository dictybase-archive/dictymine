package org.modmine.web;

/*
 * Copyright (C) 2002-2008 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.actions.TilesAction;
import org.flymine.model.genomic.ExperimentSubmission;
import org.flymine.model.genomic.ModEncodeProject;
import org.flymine.model.genomic.ModEncodeProvider;
import org.intermine.objectstore.ObjectStore;
import org.intermine.objectstore.query.Query;
import org.intermine.objectstore.query.QueryClass;
import org.intermine.objectstore.query.QueryField;
import org.intermine.objectstore.query.Results;
import org.intermine.objectstore.query.ResultsRow;
import org.intermine.web.logic.Constants;

/**
 * Controller for providers.jsp
 * @author Tom Riley
 */
public class ProvidersController extends TilesAction
{
    /**
     * {@inheritDoc}
     */
    public ActionForward execute(@SuppressWarnings("unused")  ComponentContext context,
            @SuppressWarnings("unused") ActionMapping mapping,
            @SuppressWarnings("unused") ActionForm form,
            HttpServletRequest request,
            @SuppressWarnings("unused") HttpServletResponse response)
    throws Exception {
        try {
            HttpSession session = request.getSession();
            ObjectStore os =
                (ObjectStore) session.getServletContext().getAttribute(Constants.OBJECTSTORE);

            //get the list of providers 
            Query q = new Query();  
            QueryClass qc = new QueryClass(ModEncodeProvider.class);
            QueryField qfSurname = new QueryField(qc, "surname");

            q.addFrom(qc);
            q.addToSelect(qc);
            q.addToOrderBy(qfSurname);

            //            Results results = os.executeSingleton(q);
            Results results = os.execute(q);

            Map<ModEncodeProvider, Set<ExperimentSubmission>> ps =
                new LinkedHashMap<ModEncodeProvider, Set<ExperimentSubmission>>();

            Map<ModEncodeProvider, ModEncodeProject> pp =
                new LinkedHashMap<ModEncodeProvider, ModEncodeProject>();

            // for each provider, get its attributes and set the values for jsp

            for (Iterator iter = results.iterator(); iter.hasNext(); ) {
                ResultsRow row = (ResultsRow) iter.next();

                ModEncodeProvider provider = (ModEncodeProvider) row.get(0);
                Set<ExperimentSubmission> subs = provider.getExperimentSubmissions();
                ModEncodeProject project = provider.getProject();
                
                ps.put(provider, subs);
                pp.put(provider, project);
            
            }            
            
//            Iterator i = results.iterator();
//            while (i.hasNext()) {
//                
//                ModEncodeProvider provider = (ModEncodeProvider) i.next();
//                Set<ExperimentSubmission> subs = provider.getExperimentSubmissions();
//                ModEncodeProject project = provider.getProject();
//                
//                ps.put(provider, subs);
//                pp.put(provider, project);
//            }

            request.setAttribute("experiments", ps);
            request.setAttribute("project", pp);

        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }
}
