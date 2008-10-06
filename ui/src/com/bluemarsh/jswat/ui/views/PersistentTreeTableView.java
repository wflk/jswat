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
 * $Id: PersistentTreeTableView.java 15 2007-06-03 00:01:17Z nfiedler $
 */

package com.bluemarsh.jswat.ui.views;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.openide.ErrorManager;
import org.openide.explorer.view.NodeTreeModel;
import org.openide.explorer.view.TreeTableView;
import org.openide.explorer.view.Visualizer;
import org.openide.nodes.Node;

/**
 * A TreeTableView subclass that persists the various settings, and
 * restores them as needed.
 *
 * @author  Nathan Fiedler
 */
public class PersistentTreeTableView extends TreeTableView {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Restore the column width values from the input stream (performed
     * on the AWT event thread since this affects Swing components).
     *
     * @param  int  the stream to deserialize from.
     */
    public void restoreColumnWidths(ObjectInput in) {
        // Must read from the stream immediately and not on another
        // thread, lest it be closed by the time that thread is run.
        TableColumnModel tcm = treeTable.getColumnModel();
        int count = tcm.getColumnCount();
        final int[] widths = new int[count];
        try {
            for (int index = 0; index < count; index++) {
                widths[index] = in.readInt();
            }
        }  catch (IOException ioe) {
            // Could be reading an old instance which is missing data.
            // In any case, ignore this as there is no use in reporting it
            // (and return immediately so as not to invoke the runnable).
            return;
        }

        // Changing Swing widgets must be done on the AWT event thread.
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // TreeTableView prohibits moving the tree
                // column, so it is always offset zero.
                setTreePreferredWidth(widths[0]);
                for (int index = 1; index < widths.length; index++) {
                    setTableColumnPreferredWidth(index - 1, widths[index]);
                }
            }
            });
    }

    /**
     * Save the column width values to the output stream.
     *
     * @param  out  the stream to serialize to.
     */
    public void saveColumnWidths(ObjectOutput out) {
        try {
            TableColumnModel tcm = treeTable.getColumnModel();
            int count = tcm.getColumnCount();
            for (int index = 0; index < count; index++) {
                TableColumn tc = tcm.getColumn(index);
                int width = tc.getWidth();
                out.writeInt(width);
            }
        }  catch (IOException ioe) {
            ErrorManager.getDefault().notify(ErrorManager.WARNING, ioe);
        }
    }

    /**
     * Select the given node in the tree, scrolling as needed to make the
     * node visible, as well as expanding the path to the node.
     *
     * @param  node  node to be selected.
     */
    public void scrollAndSelectNode(Node node) {
        // It is basically guaranteed that the model is a NodeTreeModel.
        NodeTreeModel model = (NodeTreeModel) tree.getModel();
        TreeNode tn = Visualizer.findVisualizer(node);
        TreeNode[] tnp = model.getPathToRoot(tn);
        TreePath path = new TreePath(tnp);
        tree.setSelectionPath(path);
        tree.scrollPathToVisible(path);
    }
}
