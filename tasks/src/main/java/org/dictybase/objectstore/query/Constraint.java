package org.dictybase.objectstore.query;

/*
 * Copyright (C) 2002-2016 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import org.dictybase.metadata.ConstraintOp;

/**
 * Abstract reperesentation of a query constraint.
 *
 * @author Richard Smith
 * @author Mark Woodbridge
 */

public abstract class Constraint
{
    protected ConstraintOp op;

    /**
     * Negate this constraint. i.e equals becomes not equals.
     */
    public void negate() {
        op = op.negate();
    }

    /**
     * Get op of constraint
     *
     * @return operation type
     */
    public ConstraintOp getOp() {
        return op;
    }

}
