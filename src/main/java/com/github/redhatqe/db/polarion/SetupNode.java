package com.github.redhatqe.db.polarion;

import com.github.redhatqe.db.CreateType;
import com.github.redhatqe.db.MakeEdges;
import com.github.redhatqe.db.utils.Tuple;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This vertex type represents information related to the setting up of a test case.  It is declared as its own vertex
 * type rather than as a property on the TestCaseNode type, because it is possible that other TestCaseNode types will
 * have several SetupNode nodes in common.
 *
 * A SetupNode will often have several
 */
public class SetupNode {
    public static final String name = SetupNode.class.getSimpleName();
    public static Logger logger = LogManager.getLogger(name);

    public static CreateType node() {
        return (ODatabaseDocument odb) -> {
            OClass vtx = NodeType.getClass(name, odb);
            List<Tuple<String, OType>> props = new ArrayList<>();
            props.add(new Tuple<>("id", OType.INTEGER));
            props.add(new Tuple<>("description", OType.STRING));

            props.forEach(t -> {
                if(!vtx.existsProperty(t.first))
                    vtx.createProperty(t.first, t.second);
                else
                    logger.info("{} property already exists", t.first);
            });

            return vtx;
        };
    }

    /**
     * Creates the edges
     * @return
     */
    public static MakeEdges edges() {
        return (OClass n, Map<String, Tuple<OType, OClass>> links) -> {

        };
    }
}
