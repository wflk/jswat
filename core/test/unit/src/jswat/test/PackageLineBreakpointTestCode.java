/*
 *                     Sun Public License Notice.
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is JSwat. The Initial Developer of the Original
 * Code is Nathan L. Fiedler. Portions created by Nathan L. Fiedler
 * are Copyright (C) 2004-2006. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id: PackageLineBreakpointTestCode.java 15 2007-06-03 00:01:17Z nfiedler $
 */

// This test calls for a class with a package name that does not match
// the directory structure in which the source file is stored.
package org.acme.test;

/**
 * Test code for the PackageLineBreakpointTest.
 *
 * @author  Nathan Fiedler
 */
public class PackageLineBreakpointTestCode {

    public static void main(String[] args) {
        String s = "ABC".substring(0, 1); // breakpoint, line 30
        System.out.println("s = " + s);
    }
}
