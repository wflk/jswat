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
 * are Copyright (C) 2005-2006. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id: BreakpointEventMulticaster.java 15 2007-06-03 00:01:17Z nfiedler $
 */

package com.bluemarsh.jswat.core.breakpoint;

import java.beans.PropertyChangeEvent;

/**
 * Class BreakpointEventMulticaster implements a thread-safe list of
 * breakpoint listeners. It is technically a tree but it grows only
 * in one direction, which makes it more like a linked list. This
 * class behaves like a listener but it simply forwards the events
 * to the contained listeners.
 *
 * <p>This marvelous design was originally put to code by Amy Fowler and
 * John Rose in the form of the <code>AWTEventMulticaster</code> class
 * in the <code>java.awt</code> package. This implementation is based on
 * the description given in <u>Taming Java Threads</u> by Allen Holub.</p>
 */
public class BreakpointEventMulticaster implements BreakpointListener {
    /** A session listener. */
    protected final BreakpointListener listener1;
    /** A session listener. */
    protected final BreakpointListener listener2;

    /**
     * Adds the second listener to the first listener and returns the
     * resulting multicast listener.
     *
     * @param  l1  a session listener.
     * @param  l2  the session listener being added.
     * @return  session multicast listener.
     */
    public static BreakpointListener add(BreakpointListener l1,
                                         BreakpointListener l2) {
        return (l1 == null) ? l2 :
               (l2 == null) ? l1 : new BreakpointEventMulticaster(l1, l2);
    }

    /**
     * Removes the second listener from the first listener and returns
     * the resulting multicast listener.
     *
     * @param  l1  a session listener.
     * @param  l2  the listener being removed.
     * @return  session multicast listener.
     */
    public static BreakpointListener remove(BreakpointListener l1,
                                            BreakpointListener l2) {
        if (l1 == l2 || l1 == null) {
            return null;
        } else if (l1 instanceof BreakpointEventMulticaster) {
            return ((BreakpointEventMulticaster) l1).remove(l2);
        } else {
            return l1;
        }
    }

    /**
     * Creates a session event multicaster instance which chains
     * listener l1 with listener l2.
     *
     * @param  l1  a session listener.
     * @param  l2  a session listener.
     */
    protected BreakpointEventMulticaster(BreakpointListener l1, BreakpointListener l2) {
        listener1 = l1;
        listener2 = l2;
    }

    /**
     * Removes a session listener from this multicaster and returns the
     * resulting multicast listener.
     *
     * @param  l  the listener to be removed.
     * @return  the other listener.
     */
    protected BreakpointListener remove(BreakpointListener l) {
        if (l == listener1) {
            return listener2;
        }
        if (l == listener2) {
            return listener1;
        }
        // Recursively seek out the target listener.
        BreakpointListener l1 = remove(listener1, l);
        BreakpointListener l2 = remove(listener2, l);
        return (l1 == listener1 && l2 == listener2) ? this : add(l1, l2);
    }

    public void breakpointAdded(BreakpointEvent event) {
        listener1.breakpointAdded(event);
        listener2.breakpointAdded(event);
    }

    public void breakpointRemoved(BreakpointEvent event) {
        listener1.breakpointRemoved(event);
        listener2.breakpointRemoved(event);
    }

    public void breakpointStopped(BreakpointEvent event) {
        listener1.breakpointStopped(event);
        listener2.breakpointStopped(event);
    }

    public void errorOccurred(BreakpointEvent event) {
        listener1.errorOccurred(event);
        listener2.errorOccurred(event);
    }

    public void propertyChange(PropertyChangeEvent event) {
        listener1.propertyChange(event);
        listener2.propertyChange(event);
    }
}
