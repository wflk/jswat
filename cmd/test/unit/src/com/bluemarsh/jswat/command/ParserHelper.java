/*
 *                     Sun Public License Notice.
 *
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 *
 * The Original Code is the JSwat Command Module. The Initial Developer of the
 * Original Code is Nathan L. Fiedler. Portions created by Nathan L. Fiedler
 * are Copyright (C) 2005. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id: ParserHelper.java 15 2007-06-03 00:01:17Z nfiedler $
 */

package com.bluemarsh.jswat.command;

import java.io.PrintWriter;
import java.io.StringWriter;
import junit.framework.TestCase;

/**
 * Utility class for the CommandParser unit tests.
 *
 * @author Nathan Fiedler
 */
public class ParserHelper {

    /**
     * Runs the given command parser tests.
     *
     * @param  tcase   test case running this tester.
     * @param  parser  command parser to which input is sent.
     * @param  datum   array of test data.
     */
    public void performTest(TestCase tcase, CommandParser parser, TestData[] datum) {
        for (TestData data : datum) {
            StringWriter sw = new StringWriter(80);
            PrintWriter pw = new PrintWriter(sw);
            parser.setOutput(pw);
            String result = null;
            try {
                parser.parseInput(data.getInput());
                result = sw.toString();
                if (data.shouldFail()) {
                    // was expected to fail
                    StringBuilder buf = new StringBuilder();
                    buf.append(data.getInput());
                    buf.append(" <<should have failed -- result>> ");
                    buf.append(result);
                    tcase.fail(buf.toString());
                }
            } catch (CommandException ce) {
                // This handles missing arguments, too.
                if (!data.shouldFail()) {
                    // was not expected to fail
                    StringBuilder buf = new StringBuilder();
                    buf.append(data.getInput());
                    buf.append(" <<should not have failed>> ");
                    sw = new StringWriter(256);
                    pw = new PrintWriter(sw);
                    ce.printStackTrace(pw);
                    buf.append(sw.toString());
                    tcase.fail(buf.toString());
                }
            } catch (Exception e) {
                StringBuilder buf = new StringBuilder();
                buf.append(data.getInput());
                buf.append(" <<unexpected exception>> ");
                sw = new StringWriter(256);
                pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                buf.append(sw.toString());
                tcase.fail(buf.toString());
            }

            boolean equals;
            if (result == null && data.getResult() == null) {
                equals = true;
            } else if (result == null || data.getResult() == null) {
                equals = false;
            } else {
                equals = result.equals(data.getResult());
            }

            if (!equals) {
                if (data.getMessage() == null) {
                    StringBuilder buf = new StringBuilder();
                    buf.append(data.getInput());
                    buf.append(" <<should have been>> ");
                    buf.append(data.getResult());
                    buf.append(" <<but got>> ");
                    buf.append(result);
                    tcase.fail(buf.toString());

                } else {
                    StringBuilder buf = new StringBuilder();
                    buf.append(data.getMessage());
                    buf.append(" <<expected>> ");
                    buf.append(data.getResult());
                    buf.append(" <<but got>> ");
                    buf.append(result);
                    tcase.fail(buf.toString());
                }
            }
        }
    }
}
