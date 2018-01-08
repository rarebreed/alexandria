package com.github.redhatqe.alexandria.schema.db.testcase;

import com.github.redhatqe.alexandria.nodes.CreateType;
import com.github.redhatqe.alexandria.nodes.NodeType;
import com.github.redhatqe.alexandria.utils.Tuple;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This node represents a method or function from some test framework which can be called and used by some other type.
 * For example, this is commonly linked to by a TestCaseNode type as the thing which actually implements the test case.
 * A SetupNode node can also link to a MethodNode as the function that implements whatever the setup needs.
 */
public class MethodNode {
    public static final String name = MethodNode.class.getSimpleName();
    public static Logger logger = LogManager.getLogger(name);

    public static CreateType node() {
        return (ODatabaseDocument odb) -> {
            OClass vtx = NodeType.getClass(name, odb);
            List<Tuple<String, OType>> props = new ArrayList<>();
            props.add(new Tuple<>("id", OType.INTEGER));
            props.add(new Tuple<>("package", OType.STRING));
            props.add(new Tuple<>("class", OType.STRING));
            props.add(new Tuple<>("methodName", OType.STRING));

            props.forEach(t -> {
                if(!vtx.existsProperty(t.first))
                    vtx.createProperty(t.first, t.second);
                else
                    logger.info("{} property already exists", t.first);
            });

            return vtx;
        };
    }

    public static CreateType node(List<Tuple<String, OType>> props) {
        return (ODatabaseDocument odb) -> {
            OClass vtx = NodeType.getClass(name, odb);
            props.forEach(t -> {
                if(!vtx.existsProperty(t.first))
                    vtx.createProperty(t.first, t.second);
                else
                    logger.info("{} property already exists", t.first);
            });

            return vtx;
        };
    }
}
