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
 * are Copyright (C) 1999-2005. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id: ResolveException.java 15 2007-06-03 00:01:17Z nfiedler $
 */

package com.bluemarsh.jswat.core.breakpoint;

import com.bluemarsh.jswat.core.util.AmbiguousMethodException;
import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.InvalidTypeException;
import org.openide.util.NbBundle;

/**
 * ResolveException is thrown whenever a breakpoint fails to resolve.
 *
 * @author Nathan Fiedler
 */
public class ResolveException extends Exception {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a ResolveException with no message.
     */
    public ResolveException() {
        super();
    }

    /**
     * Constructs a ResolveException with the given message.
     *
     * @param  message  the detail message.
     */
    public ResolveException(String message) {
        super(message);
    }

    /**
     * Constructs a ResolveException with the given message and cause.
     *
     * @param  message  the detail message.
     * @param  cause    the cause.
     */
    public ResolveException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a ResolveException with the given cause.
     *
     * @param  cause  the cause.
     */
    public ResolveException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a localized description of this throwable.
     *
     * @return  The localized description of this throwable.
     */
    public String getLocalizedMessage() {
        Throwable cause = getCause();
        if (cause instanceof AbsentInformationException) {
            String msg = super.getMessage();
            return NbBundle.getMessage(ResolveException.class,
                    "ERR_Resolve_AbsentInfo", msg);
        } else if (cause instanceof AmbiguousClassSpecException) {
            return NbBundle.getMessage(ResolveException.class,
                    "ERR_Resolve_AmbiguousClass");
        } else if (cause instanceof AmbiguousMethodException) {
            return NbBundle.getMessage(ResolveException.class,
                    "ERR_Resolve_AmbiguousMethod");
        } else if (cause instanceof IllegalArgumentException) {
            return NbBundle.getMessage(ResolveException.class,
                    "ERR_Resolve_InvalidSyntax");
        } else if (cause instanceof InvalidParameterTypeException) {
            return NbBundle.getMessage(ResolveException.class,
                    "ERR_Resolve_InvalidArgumentType", cause.getMessage());
        } else if (cause instanceof InvalidTypeException) {
            String msg = super.getMessage();
            return NbBundle.getMessage(ResolveException.class,
                    "ERR_Resolve_InvalidType", msg);
        } else if (cause instanceof NoSuchMethodException) {
            return NbBundle.getMessage(ResolveException.class,
                    "ERR_Resolve_NoSuchMethod");
        } else {
            return NbBundle.getMessage(ResolveException.class,
                    "ERR_Resolve_Unknown", cause.toString());
        }
    }

    /**
     * Returns a String representation of this.
     *
     * @return  string of this.
     */
    public String toString() {
        return getLocalizedMessage();
    }
}
