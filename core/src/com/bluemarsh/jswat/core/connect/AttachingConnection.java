/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is JSwat. The Initial Developer of the Original
 * Software is Nathan L. Fiedler. Portions created by Nathan L. Fiedler
 * are Copyright (C) 2005-2006. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id: AttachingConnection.java 6 2007-05-16 07:14:24Z nfiedler $
 */

package com.bluemarsh.jswat.core.connect;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import java.io.IOException;
import java.util.Map;

/**
 * Implements a connection that attaches to a debuggee, either on the same
 * host as the debugger or on a different host elsewhere on the network.
 *
 * @author Nathan Fiedler
 */
public class AttachingConnection extends AbstractConnection {

    /**
     * Creates a new instance of AttachingConnection.
     *
     * @param  connector  connector.
     * @param  args       connector arguments.
     */
    public AttachingConnection(Connector connector,
            Map<String, ? extends Connector.Argument> args) {
        super(connector, args);
    }

    public void connect() throws IllegalConnectorArgumentsException, IOException {
        AttachingConnector conn = (AttachingConnector) getConnector();
        VirtualMachine vm = conn.attach(getConnectorArgs());
        setVM(vm);
        fireEvent(new ConnectionEvent(this, ConnectionEvent.Type.CONNECTED));
    }
}