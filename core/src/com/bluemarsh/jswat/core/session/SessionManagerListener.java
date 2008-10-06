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
 * are Copyright (C) 2004. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id: SessionManagerListener.java 15 2007-06-03 00:01:17Z nfiedler $
 */

package com.bluemarsh.jswat.core.session;

import com.bluemarsh.jswat.core.session.Session;
import java.util.EventListener;

/**
 * The listener interface for receiving session manager events.
 *
 * @author  Nathan Fiedler
 */
public interface SessionManagerListener extends EventListener {

    /**
     * Called when a Session has been added to the SessionManager.
     *
     * @param  e  session manager event.
     */
    void sessionAdded(SessionManagerEvent e);

    /**
     * Called when a Session has been removed from the SessionManager.
     *
     * @param  e  session manager event.
     */
    void sessionRemoved(SessionManagerEvent e);

    /**
     * Called when a Session has been made the current session.
     *
     * @param  e  session manager event.
     */
    void sessionSetCurrent(SessionManagerEvent e);
}
