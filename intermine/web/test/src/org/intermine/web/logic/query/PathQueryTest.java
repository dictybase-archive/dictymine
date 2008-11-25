package org.intermine.web.logic.query;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.intermine.TestUtil;
import org.intermine.metadata.Model;
import org.intermine.objectstore.query.PathQueryUtil;
import org.intermine.objectstore.query.ResultsInfo;
import org.intermine.path.Path;
import org.intermine.path.PathError;
import org.intermine.pathquery.Constraints;
import org.intermine.pathquery.PathQuery;
import org.intermine.pathquery.PathQueryBinding;



public class PathQueryTest extends TestCase
{
    Map expected, classKeys;
    PathQuery e, q;
    Model model;
    private static final String MSG = "Invalid path - path cannot be a null or empty string";

    public void setUp() throws Exception {
        super.setUp();
        model = TestUtil.getModel();
        classKeys = TestUtil.getClassKeys(model);
        InputStream is = getClass().getClassLoader().getResourceAsStream("PathQueryTest.xml");
        expected = PathQueryBinding.unmarshal(new InputStreamReader(is), classKeys);
    }

    public PathQueryTest(String arg) {
        super(arg);
    }

    public void testPathQueryModel() {
        new PathQuery(model);
    }

    public void testPathQueryPathQuery() {
        e = (PathQuery) expected.get("companyName");
        PathQuery actual = new PathQuery(e);
        assertEquals(e, actual);
    }

    public void testSetViewString() {

        // simple
        e = (PathQuery) expected.get("employeeName");
        q = new PathQuery(model);
        q.setView("Employee.name");
        assertEquals(e.getViewStrings(), q.getViewStrings());

        // multiple, long paths, multiple delims
        e = (PathQuery) expected.get("employeeDepartmentCompanyWildcard");
        q = new PathQuery(model);
        q.setView("Employee.name ,Employee.department.name, Employee.department.company.name");
        assertEquals(e.getViewStrings(), q.getViewStrings());

        // old view list replaced by new view list
        e = (PathQuery) expected.get("employeeDepartmentCompanyWildcard");
        q = new PathQuery(model);
        q.setView("Company.departments.name");
        q.setView("Employee.name ,Employee.department.name, Employee.department.company.name");
        assertEquals(e.getViewStrings(), q.getViewStrings());

        // bad path
        q = new PathQuery(model);
        q.setView("monkey");
        assertTrue(q.getViewStrings().isEmpty());
        assertFalse(q.isValid());

        // empty path
        q.setView("");
        assertEquals(MSG, q.getProblems()[1].getMessage().toString());
        assertFalse(q.isValid());

        // null path
        q.setView(new String());
        assertEquals(MSG, q.getProblems()[2].getMessage().toString());
        assertFalse(q.isValid());
    }

    public void testSetViewListOfString() {
        e = (PathQuery) expected.get("employeeDepartmentCompanyWildcard");
        q = new PathQuery(model);
        List<String> view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            add("Employee.department.company.name");
        }};
        q.setView(view);
        assertEquals(e.getViewStrings(), q.getViewStrings());

        // bad path
        q = new PathQuery(model);
        view = new ArrayList<String>() {{
            add("Monkey.paths");
        }};
        q.setView(view);
        assertTrue(q.getViewStrings().isEmpty());
        assertFalse(q.isValid());

        // bad path with good paths
        q = new PathQuery(model);
        view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            add("Monkey.paths");
            add("Employee.department.company.name");
        }};
        q.setView(view);
        assertEquals(e.getViewStrings(), q.getViewStrings());
        assertFalse(q.isValid());

        // bad path with empty path
        q = new PathQuery(model);
        view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            add("");
            add("Employee.department.company.name");
        }};
        q.setView(view);
        assertEquals(e.getViewStrings(), q.getViewStrings());
        assertFalse(q.isValid());

        // null path
        q = new PathQuery(model);
        q.setView(new ArrayList<String>());
        assertFalse(q.isValid());

        // bad paths with empty path
        q = new PathQuery(model);
        view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            String isnull = null;
            add(isnull);
            add("Employee.department.company.name");
        }};
        q.setView(view);
        assertEquals(e.getViewStrings(), q.getViewStrings());
        assertFalse(q.isValid());
    }

    public void testSetViewPaths() {
        e = (PathQuery) expected.get("employeeDepartmentCompanyWildcard");
        q = new PathQuery(model);
        List<Path> view = new ArrayList<Path>() {{
            add(new Path(model, "Employee.name"));
            add(new Path(model, "Employee.department.name"));
            add(new Path(model, "Employee.department.company.name"));
        }};
        q.setViewPaths(view);
        assertEquals(e.getView(), q.getView());

        // TODO need to test a bad/empty path
        q = new PathQuery(model);
        view = new ArrayList<Path>() {{
            add(new Path(model, "Employee.name"));
            try {
                add(new Path(model, "monkey"));
            } catch (PathError pathError) {
                // caught!
            }
            add(new Path(model, "Employee.department.name"));
            add(new Path(model, "Employee.department.company.name"));
        }};
        q.setViewPaths(view);
        assertEquals(e.getView(), q.getView());
        assertTrue(q.isValid());

        // null paths
        e = (PathQuery) expected.get("employeeDepartmentCompanyWildcard");
        q = new PathQuery(model);
        view = new ArrayList<Path>() {{
            add(new Path(model, "Employee.name"));
            Path nullpath = null;
            add(nullpath);
            add(new Path(model, "Employee.department.name"));
            add(new Path(model, "Employee.department.company.name"));
        }};
        q.setViewPaths(view);
        assertEquals(e.getView(), q.getView());
        assertTrue(q.isValid());
    }

    public void testAddViewString() {

        // simple
        e = (PathQuery) expected.get("companyName");
        q = new PathQuery(model);
        q.addView("Company.name");
        assertEquals(e.getViewStrings(), q.getViewStrings());

        // add one then another
        e = (PathQuery) expected.get("employeeDepartmentName");
        q = new PathQuery(model);
        q.addView("Employee.name");
        q.addView("Employee.department.name");
        assertEquals(e.getViewStrings(), q.getViewStrings());

        // add three at once
        e = (PathQuery) expected.get("employeeDepartmentCompany");
        q = new PathQuery(model);
        q.addView("Employee.name, Employee.department.name,Employee.department.company.name");
        assertEquals(e.getViewStrings(), q.getViewStrings());

        // bad path
        q = new PathQuery(model);
        q.addView("monkey");
        assertTrue(q.getViewStrings().isEmpty());
        assertFalse(q.isValid());

        // empty path
        q.addView("");
        assertEquals(MSG, q.getProblems()[1].getMessage().toString());
        assertFalse(q.isValid());

        // null path
        q.addView(new String());
        assertEquals(MSG, q.getProblems()[2].getMessage().toString());
        assertFalse(q.isValid());

    }

    public void testAddViewListOfString() {

        // add 3
        e = (PathQuery) expected.get("employeeDepartmentCompanyWildcard");
        q = new PathQuery(model);
        List<String> view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            add("Employee.department.company.name");
        }};
        q.addView(view);
        assertEquals(e.getViewStrings(), q.getViewStrings());

        // set 1, add 2
        e = (PathQuery) expected.get("employeeDepartmentCompanyWildcard");
        q = new PathQuery(model);
        q.setView("Employee.name");
        view = new ArrayList<String>() {{
            add("Employee.department.name");
            add("Employee.department.company.name");
        }};
        q.addView(view);
        assertEquals(e.getViewStrings(), q.getViewStrings());

        // bad path
        q = new PathQuery(model);
        view = new ArrayList<String>() {{
            add("Monkey.paths");
        }};
        q.addView(view);
        assertTrue(q.getViewStrings().isEmpty());
        assertFalse(q.isValid());

        // bad path with good paths
        q = new PathQuery(model);
        view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            add("Monkey.paths");
            add("Employee.department.company.name");
        }};
        q.addView(view);
        System.out.println("PROBLEMS: " + PathQueryUtil.getProblemsSummary(q.getProblems()));
        assertEquals(e.getViewStrings(), q.getViewStrings());
        assertFalse(q.isValid());

        // bad path with empty path
        q = new PathQuery(model);
        view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            add("");
            add("Employee.department.company.name");
        }};
        q.addView(view);
        assertEquals(e.getViewStrings(), q.getViewStrings());
        assertFalse(q.isValid());

        // null path
        q = new PathQuery(model);
        q.addView(new ArrayList<String>());
        assertFalse(q.isValid());

        // bad paths with empty path
        q = new PathQuery(model);
        view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            String isnull = null;
            add(isnull);
            add("Employee.department.company.name");
        }};
        q.addView(view);
        assertEquals(e.getViewStrings(), q.getViewStrings());
        assertFalse(q.isValid());
    }

    public void testAddPathToView() {

        e = (PathQuery) expected.get("employeeDepartmentCompanyWildcard");
        q = new PathQuery(model);
        List<Path> view = new ArrayList<Path>() {{
            add(new Path(model, "Employee.name"));
            add(new Path(model, "Employee.department.name"));
            add(new Path(model, "Employee.department.company.name"));
        }};
        q.addViewPaths(view);
        assertEquals(e.getView(), q.getView());

        // adding the same path again, should work
        view = new ArrayList<Path>() {{
            add(new Path(model, "Employee.name"));
        }};
        q.addViewPaths(view);
        assertEquals(4, q.getView().size());


        // null paths
        e = (PathQuery) expected.get("employeeDepartmentCompanyWildcard");
        q = new PathQuery(model);
        view = new ArrayList<Path>() {{
            add(new Path(model, "Employee.name"));
            Path nullpath = null;
            add(nullpath);
            add(new Path(model, "Employee.department.name"));
            add(new Path(model, "Employee.department.company.name"));
        }};
        q.addViewPaths(view);
        assertEquals(e.getView(), q.getView());
        assertTrue(q.isValid());

    }

    
    // Adding the same path with a different join style is invalud
    public void testAddViewPathsInvalidJoinStyle() {
        PathQuery pq = new PathQuery(model);
        pq.addView("Company.departments.name");
        try {
            pq.addView("Company:departments.manager.name");
            fail("Expected exception, can't have same paths with different join styles");
        } catch (IllegalArgumentException e) {
        }
    }
    
//    public void testAddPathStringToView() {
//        //deprecated
//    }

    // -- old methods -- //

    public void testRemoveFromView() {
        e = (PathQuery) expected.get("employeeName");
        q = (PathQuery) expected.get("employeeDepartmentName");
        q.removeFromView("Employee.department.name");
        assertEquals(e.getView(), q.getView());
    }

    public void testViewContains() {
        q = (PathQuery) expected.get("employeeDepartmentCompanyWildcard");
        assertTrue(q.viewContains("Employee.name"));
        assertFalse(q.viewContains("Department.name"));
    }


    /***********************************************************************************/

    public void testAddConstraintStringConstraint() {
        e = (PathQuery) expected.get("employeeDepartmentCompany");
        q = new PathQuery(model);
        List<String> view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            add("Employee.department.company.name");
        }};
        q.addView(view);
        q.addConstraint("Employee.department.name", Constraints.eq("DepartmentA1"));
        assertEquals(e.getAllConstraints(), q.getAllConstraints());

        // TODO bad constraints, multiple constraints, different ops

    }

    public void testAddConstraintStringConstraintString() {
        e = (PathQuery) expected.get("employeeDepartmentCompany");
        q = new PathQuery(model);
        List<String> view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            add("Employee.department.company.name");
        }};
        q.addView(view);
        q.addConstraint("Employee.department.name", Constraints.eq("DepartmentA1"));
        assertEquals(e.getAllConstraints(), q.getAllConstraints());
    }

    public void testAddConstraintSubclass() {
        e = (PathQuery) expected.get("employeeDepartmentCompany");
        q = new PathQuery(model);
        List<String> view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            add("Employee.department.company.name");
        }};
        q.addView(view);
        q.addConstraint("Employee.department.name", Constraints.eq("DepartmentA1"));
        assertEquals(e.getAllConstraints(), q.getAllConstraints());
    }

    public void testGetAllConstraints() {

        // no constraints
        e = (PathQuery) expected.get("employeeName");
        assertEquals(e.getAllConstraints(), new ArrayList());

        // one constraint
        e = (PathQuery) expected.get("employeeDepartmentCompany");
        q = new PathQuery(model);
        List<String> view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            add("Employee.department.company.name");
        }};
        q.addView(view);
        q.addConstraint("Employee.department.name", Constraints.eq("DepartmentA1"));
        assertEquals(e.getAllConstraints(), q.getAllConstraints());

        // multiple constraints
        e = (PathQuery) expected.get("departmentBagConstraint");
        assertEquals(2, e.getAllConstraints().size());
    }

    public void testGetConstraintLogic() {

        // null logic
        e = (PathQuery) expected.get("employeeDepartmentCompany");
        q = new PathQuery(model);
        List<String> view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            add("Employee.department.company.name");
        }};
        q.addView(view);
        q.addConstraint("Employee.department.name", Constraints.eq("DepartmentA1"));
        assertEquals(e.getConstraintLogic(), q.getConstraintLogic());

        // A and B
        e = (PathQuery) expected.get("departmentBagConstraint");
        assertEquals(e.getConstraintLogic(), "A and B");

        // TODO test bad logic, OR logic, A and B and C, logic with user-created code

    }

    // same as above, except above method returns string
    public void testGetLogic() {

        // null logic
        q = new PathQuery(model);
        List<String> view = new ArrayList<String>() {{
            add("Employee.name");
            add("Employee.department.name");
            add("Employee.department.company.name");
        }};
        q.addView(view);
        q.addConstraint("Employee.department.name", Constraints.eq("DepartmentA1"));
        assertNull(q.getLogic());
    }

//    public void testSyncLogicExpression() {
//        fail("Not yet implemented");
//    }

    public void testGetUnusedConstraintCode() {

        // A
        q = new PathQuery(model);
        assertEquals("A", q.getUnusedConstraintCode());

        // B
        q.addConstraint("Employee.department.name", Constraints.eq("DepartmentA1"));
        assertEquals("B", q.getUnusedConstraintCode());

        // B
        e = (PathQuery) expected.get("employeeDepartmentCompany");
        assertEquals("B", e.getUnusedConstraintCode());

        // C
        e = (PathQuery) expected.get("departmentBagConstraint");
        assertEquals("C", e.getUnusedConstraintCode());

        // add a constraint, but assign a different code
        e.addConstraint("Department.name", Constraints.eq("monkey"), "pants");
        assertEquals("C", e.getUnusedConstraintCode());

    }

    public void testGetConstraintByCode() {

        // monkey
        q = new PathQuery(model);
        q.addConstraint("Employee.department.name", Constraints.eq("*pants*"), "monkey");
        assertEquals("Constraint(=, *pants*)", q.getConstraintByCode("monkey").toString());

        // A
        e = (PathQuery) expected.get("employeeDepartmentCompany");
        assertEquals("Constraint(=, DepartmentA1)", e.getConstraintByCode("A").toString());

        // B
        e = (PathQuery) expected.get("departmentBagConstraint");
        assertEquals("Constraint(LOOKUP, *ABC*)", e.getConstraintByCode("B").toString());

        // null
        assertNull(e.getConstraintByCode("monkey"));

    }

//    public void testAddCodesToLogic() {
//        fail("Not yet implemented");
//    }


    /***********************************************************************************/


    public void testSetOrderByString() {

        // no order by clauses
        e = (PathQuery) expected.get("employeeName");
        q = new PathQuery(model);
        q.setView("Employee.name");
        assertEquals(e.getSortOrderStrings(), q.getSortOrderStrings());

        e = (PathQuery) expected.get("noOrderBy");
        q = new PathQuery(model);
        q.setView("Employee.name,Employee.department.name");
        q.setOrderBy("Employee.name");
        assertEquals(e.getSortOrder(), q.getSortOrder());

        // ASC
        e = (PathQuery) expected.get("orderByAsc");
        q = new PathQuery(model);
        q.setView("Employee.name,Employee.department.name");
        q.setOrderBy("Employee.name");
        assertEquals(e.getSortOrder(), q.getSortOrder());

        // long path
        e = (PathQuery) expected.get("longPath");
        q = new PathQuery(model);
        q.setView("Employee.name,Employee.department.name");
        q.setOrderBy("Employee.department.name");
        assertEquals(e.getSortOrder(), q.getSortOrder());

        // multiple paths
        e = (PathQuery) expected.get("orderByVat");
        q = new PathQuery(model);
        q.setView("Company.vatNumber,Company.name,Company.address.address");
        q.setOrderBy("Company.vatNumber,Company.name,Company.departments.name");
        assertEquals(e.getSortOrderStrings(), q.getSortOrderStrings());

        // overwrite current orderby
        e = (PathQuery) expected.get("orderByVat");
        q = new PathQuery(model);
        q.setView("Company.vatNumber,Company.name,Company.address.address");
        q.setOrderBy("Company.address.address");
        q.setOrderBy("Company.vatNumber,Company.name,Company.departments.name");
        assertEquals(e.getSortOrderStrings(), q.getSortOrderStrings());

        q = (PathQuery) expected.get("orderByCompany");
        q.setOrderBy("Company.departments.name");
        assertEquals("Company.departments.name asc", q.getSortOrderStrings().get(0));

        // bad path
        q = (PathQuery) expected.get("companyName");
        q.setOrderBy("monkey.pants");
        assertTrue(q.getSortOrder().isEmpty());

        // empty value
        q = (PathQuery) expected.get("companyName");
        q.setOrderBy("");
        assertTrue(q.getSortOrder().isEmpty());

    }

    public void testSetOrderByStringBoolean() {

        // ASC
        e = (PathQuery) expected.get("orderByAsc");
        q = new PathQuery(model);
        q.setView("Employee.name,Employee.department.name");
        q.setOrderBy("Employee.name", PathQuery.ASCENDING);
        assertEquals(e.getSortOrder(), q.getSortOrder());

        // desc
        e = (PathQuery) expected.get("orderByDesc");
        q = new PathQuery(model);
        q.setView("Employee.name,Employee.department.name");
        q.setOrderBy("Employee.name", PathQuery.DESCENDING);
        assertEquals(e.getSortOrder(), q.getSortOrder());

        // long path
        e = (PathQuery) expected.get("longPath");
        q = new PathQuery(model);
        q.setView("Employee.name,Employee.department.name");
        q.setOrderBy("Employee.department.name", PathQuery.ASCENDING);
        assertEquals(e.getSortOrder(), q.getSortOrder());

        // multiple paths
        e = (PathQuery) expected.get("orderByVat");
        q = new PathQuery(model);
        q.setView("Company.vatNumber,Company.name,Company.address.address");
        q.setOrderBy("Company.vatNumber,Company.name,Company.departments.name", PathQuery.ASCENDING);
        assertEquals(e.getSortOrderStrings(), q.getSortOrderStrings());

        // overwrite current orderby
        e = (PathQuery) expected.get("orderByVat");
        q = new PathQuery(model);
        q.setView("Company.vatNumber,Company.name,Company.address.address");
        q.setOrderBy("Company.address.address", PathQuery.DESCENDING);
        q.setOrderBy("Company.vatNumber,Company.name,Company.departments.name", PathQuery.ASCENDING);
        assertEquals(e.getSortOrderStrings(), q.getSortOrderStrings());

        q = (PathQuery) expected.get("orderByCompany");
        q.setOrderBy("Company.departments.name", PathQuery.ASCENDING);
        assertEquals("Company.departments.name asc", q.getSortOrderStrings().get(0));

        // bad path
        q = (PathQuery) expected.get("companyName");
        q.setOrderBy("monkey.pants", PathQuery.DESCENDING);
        assertTrue(q.getSortOrder().isEmpty());

        // empty value
        q = (PathQuery) expected.get("companyName");
        q.setOrderBy("", PathQuery.ASCENDING);
        assertTrue(q.getSortOrder().isEmpty());

    }

    public void testSetOrderByListOfString() {

        // empty
        e = (PathQuery) expected.get("orderByAsc");
        q = new PathQuery(model);
        List orderBy = new ArrayList<String>() {{
            add("");
        }};
        q.setOrderBy(orderBy);
        assertEquals(new HashMap(), q.getSortOrder());

        // null
        e = (PathQuery) expected.get("orderByAsc");
        q = new PathQuery(model);
        orderBy = new ArrayList<String>() {{
            add(null);
        }};
        q.setOrderBy(orderBy);
        assertEquals(new HashMap(), q.getSortOrder());

    }

    public void testSetOrderByListOfStringBoolean() {

        // empty
        e = (PathQuery) expected.get("orderByAsc");
        q = new PathQuery(model);
        List orderBy = new ArrayList<String>() {{
            add("");
        }};
        q.setOrderBy(orderBy, PathQuery.ASCENDING);
        assertEquals(new HashMap(), q.getSortOrder());

        // null
        e = (PathQuery) expected.get("orderByAsc");
        q = new PathQuery(model);
        orderBy = new ArrayList<String>() {{
            add(null);
        }};
        q.setOrderBy(orderBy, PathQuery.ASCENDING);
        assertEquals(new HashMap(), q.getSortOrder());

        // one
        e = (PathQuery) expected.get("orderByAsc");
        q = new PathQuery(model);
        orderBy = new ArrayList<String>() {{
            add("Employee.name");
        }};
        q.setOrderBy(orderBy, PathQuery.ASCENDING);
        assertEquals(e.getSortOrder(), q.getSortOrder());

        // desc
        e = (PathQuery) expected.get("orderByDesc");
        q = new PathQuery(model);
        orderBy = new ArrayList<String>() {{
            add("Employee.name");
        }};
        q.setOrderBy(orderBy, PathQuery.DESCENDING);
        assertEquals(e.getSortOrder(), q.getSortOrder());

        //three
        e = (PathQuery) expected.get("orderByVat");
        q = new PathQuery(model);
        orderBy = new ArrayList<String>() {{
            add("Company.vatNumber");
            add("Company.name");
            add("Company.departments.name");
        }};
        q.setOrderBy(orderBy, PathQuery.ASCENDING);
        assertEquals(e.getSortOrder(), q.getSortOrder());

        // bad path
        q = new PathQuery(model);
        orderBy = new ArrayList<String>() {{
            add("Monkey.paths");
        }};
        q.setOrderBy(orderBy, PathQuery.ASCENDING);
        assertTrue(q.getSortOrderStrings().isEmpty());
        assertFalse(q.isValid());

        // bad path with good paths
        q = new PathQuery(model);
        orderBy = new ArrayList<String>() {{
            add("Company.vatNumber");
            add("Company.name");
            add("Monkey.paths");
            add("Company.departments.name");
        }};
        q.setOrderBy(orderBy, PathQuery.ASCENDING);
        assertEquals(e.getSortOrderStrings(), q.getSortOrderStrings());
        assertFalse(q.isValid());

        // bad path with empty path
        q = new PathQuery(model);
        orderBy = new ArrayList<String>() {{
            add("Company.vatNumber");
            add("");
            add("Company.name");
            add("Company.departments.name");
        }};
        q.setOrderBy(orderBy, PathQuery.ASCENDING);
        assertEquals(e.getSortOrderStrings(), q.getSortOrderStrings());
        assertFalse(q.isValid());

        // bad paths with empty path
        q = new PathQuery(model);
        orderBy = new ArrayList<String>() {{
            String isnull = null;
            add(isnull);
            add("Company.vatNumber");
            add("Company.name");
            add("Company.departments.name");
        }};
        q.setOrderBy(orderBy, PathQuery.ASCENDING);
        assertEquals(e.getSortOrderStrings(), q.getSortOrderStrings());
        assertFalse(q.isValid());
    }

    public void testSetOrderByList() {

        e = (PathQuery) expected.get("orderByVat");
        q = new PathQuery(model);
        Map<Path, String> orderBy = new LinkedHashMap<Path, String>() {{
            Path p =  PathQuery.makePath(model, q, "Company.vatNumber");
            put(p, PathQuery.ASCENDING);
            p = PathQuery.makePath(model, q, "Company.name");
            put(p, PathQuery.ASCENDING);
            p = PathQuery.makePath(model, q, "Company.departments.name");
            put(p, PathQuery.ASCENDING);
        }};
        q.setOrderByList(orderBy);
        assertEquals(e.getSortOrder(), q.getSortOrder());

        // adding the same path again, should reset
        orderBy = new LinkedHashMap<Path, String>() {{
            Path p =  PathQuery.makePath(model, q, "Company.name");
            put(p, PathQuery.ASCENDING);
        }};
        q.setOrderByList(orderBy);
        assertEquals(1, q.getSortOrder().size());

        // null paths
        e = (PathQuery) expected.get("orderByVat");
        q = new PathQuery(model);
        orderBy = new LinkedHashMap<Path, String>() {{
            Path p = null;
            put(p, "");
            p = PathQuery.makePath(model, q, "Company.vatNumber");
            put(p, "asc");
            p = PathQuery.makePath(model, q, "Company.name");
            put(p, "asc");
            p = PathQuery.makePath(model, q, "Company.departments.name");
            put(p, "asc");

        }};
        q.setOrderByList(orderBy);
        assertEquals(e.getSortOrder(), q.getSortOrder());
        assertFalse(q.isValid());
    }

    public void testAddOrderByString() {
        // TODO just one test to make sure method is there

    }

    public void testAddOrderByStringBoolean() {
        // simple
        e = (PathQuery) expected.get("companyName");
        q = new PathQuery(model);
        q.addView("Company.name");
        assertEquals(e.getViewStrings(), q.getViewStrings());

        // add one then another
        e = (PathQuery) expected.get("employeeDepartmentName");
        q = new PathQuery(model);
        q.addView("Employee.name");
        q.addView("Employee.department.name");
        assertEquals(e.getViewStrings(), q.getViewStrings());

        // add three at once
        e = (PathQuery) expected.get("employeeDepartmentCompany");
        q = new PathQuery(model);
        q.addView("Employee.name, Employee.department.name,Employee.department.company.name");
        assertEquals(e.getViewStrings(), q.getViewStrings());

        // bad path
        q = new PathQuery(model);
        q.addView("monkey");
        assertTrue(q.getViewStrings().isEmpty());
        assertFalse(q.isValid());

        // empty path
        q.addView("");
        assertEquals(MSG, q.getProblems()[1].getMessage().toString());
        assertFalse(q.isValid());

        // null path
        q.addView(new String());
        assertEquals(MSG, q.getProblems()[2].getMessage().toString());
        assertFalse(q.isValid());
    }

//    public void testAddOrderByListOfString() {
//        fail("Not yet implemented");
//    }
//
//    public void testAddOrderByListOfStringBoolean() {
//        fail("Not yet implemented");
//    }

    // --- old methods -- //
//
//    public void testChangeDirection() {
//
//        // simple
//        e = (PathQuery) expected.get("noOrderBy");
//        q = new PathQuery(model);
//        q.addView("Employee.name, Employee.department.name");
//        q.changeDirection("asc");
//        assertEquals(e.getViewStrings(), q.getViewStrings());
//
//    }

//    public void testAddPathStringToSortOrder() {
//        fail("Not yet implemented");
//    }
//
//    public void testResetSortOrder() {
//        // this is for the querybuilder only
//        fail("Not yet implemented");
//    }
//
//    public void testRemoveOrderBy() {
//        // this is for the querybuilder only
//        fail("Not yet implemented");
//    }


    /***********************************************************************************/


    public void testGetModel() {
        q = new PathQuery(model);
        q.getModel().equals(model);
    }

    public void testGetNodes() {
        // no nodes
        q = new PathQuery(model);
        assertEquals(new HashMap(), q.getNodes());

        // 3
        e = (PathQuery) expected.get("departmentBagConstraint");
        assertEquals(3, e.getNodes().size());
    }

//    public void testGetNode() {
//        fail("Not yet implemented");
//    }
//
//    public void testAddNode() {
//        fail("Not yet implemented");
//    }

    public void testClone() {
        e = (PathQuery) expected.get("departmentBagConstraint");
        q = e.clone();
        assertEquals(e, q);

        // TODO no constraints, many constraints, no view, bad view, no sort order
        // subclasses, problems
    }

//    public void testCloneNode() {
//        // TODO test me
//    }

    public void testInfo() {
        int start = 123;
        int complete = 456;
        int rows = 789;
        int min = 1;
        int max = 123456789;

        ResultsInfo r = new ResultsInfo(start, complete, rows, min, max);
        q = new PathQuery(model);
        q.setInfo(r);
        assertEquals(123, q.getInfo().getStart());
        assertEquals(456, q.getInfo().getComplete());
        assertEquals(789, q.getInfo().getRows());
        assertEquals(1, q.getInfo().getMin());
        assertEquals(123456789, q.getInfo().getMax());
    }

    public void testGetBagNames() {
        e = (PathQuery) expected.get("departmentBagConstraint");
        List<Object> bags = e.getBagNames();
        assertEquals(1, bags.size());
        assertEquals("departmentBag", bags.get(0).toString());
    }

    public void testProblems() {
        q = new PathQuery(model);

        List<Throwable> errors = new ArrayList<Throwable>() {{
            add(new Throwable("boo"));
            add(new PathError("monkeypants is not a valid path", "monkey.pants"));
        }};

        q.setProblems(null);
        assertTrue(q.getProblems().length == 0);

        q.setProblems(errors);
        assertEquals(2, q.getProblems().length);

        q.addProblem(new ClassNotFoundException("monkeypants is not a valid class"));
        assertEquals(3, q.getProblems().length);

    }

    public void testIsValid() {
        q = new PathQuery(model);
        q.setProblems(null);
        assertTrue(q.isValid());
        q.setProblems(Arrays.asList(new Throwable("problem!")));
        assertFalse(q.isValid());
    }

    // this method should edit the join style (normal or outer join) of a path, deferring to any 
    // path style already added to the query
    public void testCorrectJoinStyle() {
        PathQuery pq = new PathQuery(model);
        pq.addNode("Company.departments:manager");        
       
        assertEquals("Company.departments", pq.getCorrectJoinStyle("Company.departments"));
        assertEquals("Company.departments", pq.getCorrectJoinStyle("Company:departments"));
        
        assertEquals("Company.departments.name", pq.getCorrectJoinStyle("Company:departments.name"));
        assertEquals("Company.departments.name", pq.getCorrectJoinStyle("Company.departments.name"));
        
        assertEquals("Company.departments:manager.name", pq.getCorrectJoinStyle("Company:departments.manager.name"));
        assertEquals("Company.departments:manager.name", pq.getCorrectJoinStyle("Company:departments:manager.name"));    
    }
    
    
    // flip node changes the join style of the last join in the path
    public void testFlipJoinStyle() {
        PathQuery pq = new PathQuery(model);
        pq.addNode("Company.departments.manager");
        pq.addNode("Company.departments.manager.name");
        pq.addView("Company.departments.manager.name");
        
        assertEquals("Company.departments:manager", pq.flipJoinStyle("Company.departments.manager"));
        // child node should have been updated
        assertNotNull(pq.getNode("Company.departments:manager.name"));
        assertNull(pq.getNode("Company.departments.manager.name"));
    
        // the view list should be updated as well
        assertEquals(new ArrayList<String>(Collections.singleton("Company.departments:manager.name")),
                pq.getViewStrings());

        // path doesn't exist this join style
        try {
            pq.flipJoinStyle("Company:departments");
            fail("Expected as error, this path doesn't exist.");
        } catch (IllegalArgumentException e) {
        }
        
        
        // test opposite flip for completenes
        assertEquals("Company.departments.manager", pq.flipJoinStyle("Company.departments:manager"));
    }
    

    public void testSetJoinStyleForPath() {
        PathQuery pq = new PathQuery(model);
        pq.addNode("Company.departments");
        pq.addNode("Company.departments.manager");
        pq.setView("Company.name, Company.departments, Company.departments.manager.name");
        
        pq.setJoinStyleForPath("Company.departments.manager", true);
        
        List<String> expectedView = Arrays.asList(new String[] {"Company.name", "Company:departments", "Company:departments:manager.name"});
        assertEquals(expectedView, pq.getViewStrings());
        System.out.println(pq.getNodes().keySet());
        assertEquals(3, pq.getNodes().size());
        assertNotNull(pq.getNode("Company:departments"));
        assertNotNull(pq.getNode("Company:departments:manager"));
        assertNotNull(pq.getNode("Company"));
    }
    
//    public void testEqualsObject() {
//        fail("Not yet implemented");
//    }

//    public void testGetPathDescriptions() {
//        fail("Not yet implemented");
//    }
//
//    public void testGetPathDescription() {
//        fail("Not yet implemented");
//    }
//
//    public void testGetPathStringDescriptions() {
//        fail("Not yet implemented");
//    }
//
//    public void testAddPathStringDescription() {
//        fail("Not yet implemented");
//    }
//
//    public void testMakePath() {
//        fail("Not yet implemented");
//    }
}
