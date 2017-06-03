package com.github.redhatqe.db.nodes;

import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.metadata.schema.OClass;


/**
 * Created by stoner on 5/17/17.
 */
public class NodeType {
    public static Boolean hasClass(String name, ODatabaseDocument odd) {
        return odd.getClass(name) == null;
    }
    public static OClass getClass(String name, ODatabaseDocument odd) {
        OClass vtx;
        if (NodeType.hasClass(name, odd))
            vtx = odd.createVertexClass(name);
        else
            vtx = odd.getClass(name);
        return vtx;
    }
}
