package org.intermine.web.struts;

/*
 * Copyright (C) 2002-2010 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.intermine.api.profile.Profile;
import org.intermine.api.profile.SavedQuery;
import org.intermine.api.template.TemplateQuery;
import org.intermine.api.util.NameUtil;
import org.intermine.web.logic.session.SessionMethods;

/**
 * Implementation of <strong>Action</strong> that modifies a saved query or bag.
 *
 * @author Mark Woodbridge
 */
public class ModifyQueryChangeAction extends InterMineDispatchAction
{
    private static final Logger LOG = Logger.getLogger(ModifyQueryChangeAction.class);

    /**
     * Load a query.
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return an ActionForward object defining where control goes next
     * @exception Exception if the application business logic throws
     *  an exception
     */
    public ActionForward load(ActionMapping mapping,
                              @SuppressWarnings("unused") ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
        throws Exception {
        HttpSession session = request.getSession();
        Profile profile = SessionMethods.getProfile(session);
        String queryName = request.getParameter("name");

        SavedQuery sq;

        if (request.getParameter("type").equals("history")) {
            sq = profile.getHistory().get(queryName);
        } else {
            sq = profile.getSavedQueries().get(queryName);
        }

        SessionMethods.loadQuery(sq.getPathQuery(), session, response);

        if (sq.getPathQuery() instanceof TemplateQuery) {
            return new ForwardParameters(mapping.findForward("template"))
                        .addParameter("loadModifiedTemplate", "true")
                        .addParameter("name", sq.getName()).forward();
        }
        return mapping.findForward("query");
    }

    /**
     * Excecute a query.
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return an ActionForward object defining where control goes next
     * @exception Exception if the application business logic throws
     *  an exception
     */
    public ActionForward run(ActionMapping mapping,
                             @SuppressWarnings("unused") ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response)
        throws Exception {
        HttpSession session = request.getSession();
        Profile profile = SessionMethods.getProfile(session);
        String queryName = request.getParameter("name");
        String trail = request.getParameter("trail");
        SavedQuery sq;

        if (request.getParameter("type").equals("history")) {
            sq = profile.getHistory().get(queryName);
        } else {
            sq = profile.getSavedQueries().get(queryName);
        }

        if (sq == null) {
            LOG.error("No such query " + queryName + " type=" + request.getParameter("type"));
            return null;
        }

        SessionMethods.loadQuery(sq.getPathQuery(), session, response);
        String qid = SessionMethods.startQueryWithTimeout(request, false, sq.getPathQuery());
        Thread.sleep(200); // slight pause in the hope of avoiding holding page
        return new ForwardParameters(mapping.findForward("waiting"))
                    .addParameter("trail", trail)
                    .addParameter("qid", qid).forward();
    }

    /**
     * Save a query from the history.
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return an ActionForward object defining where control goes next
     * @exception Exception if the application business logic throws
     *  an exception
     */
    public ActionForward save(ActionMapping mapping,
                              @SuppressWarnings("unused") ActionForm form,
                              HttpServletRequest request,
                              @SuppressWarnings("unused") HttpServletResponse response)
        throws Exception {
        HttpSession session = request.getSession();
        Profile profile = SessionMethods.getProfile(session);
        String queryName = request.getParameter("name");
        SavedQuery sq = profile.getHistory().get(queryName);
        sq = SessionMethods.saveQuery(session,
                NameUtil.findNewQueryName(profile.getSavedQueries().keySet(), queryName),
                sq.getPathQuery(), sq.getDateCreated());
        recordMessage(new ActionMessage("savedInSavedQueries.message", sq.getName()), request);
        return new ForwardParameters(mapping.findForward("mymine"))
            .addParameter("action", "rename")
            .addParameter("subtab", "saved")
            .addParameter("name", sq.getName()).forward();
    }
}
