/*
 *                     Sun Public License Notice.
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is the JSwat Core module. The Initial Developer of the
 * Original Code is Nathan L. Fiedler. Portions created by Nathan L. Fiedler
 * are Copyright (C) 2006. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id: DefaultOutputWriter.java 15 2007-06-03 00:01:17Z nfiedler $
 */

package com.bluemarsh.jswat.core.output;

/**
 * Default implementation of the OutputWriter interface that does nothing.
 * This is a placeholder for the unit tests which do not presently provide
 * access to the NetBeans interface. The real implementation is in the
 * ui module.
 *
 * @author Nathan Fiedler
 */
public class DefaultOutputWriter implements OutputWriter {

    public void ensureVisible() {
        // ignored
    }

    public void printError(final String msg) {
        // ignored
    }

    public void printOutput(final String msg) {
        // ignored
    }
}
