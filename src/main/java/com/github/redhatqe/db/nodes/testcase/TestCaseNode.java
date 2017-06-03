package com.github.redhatqe.db.nodes.testcase;

import com.github.redhatqe.db.CreateType;
import com.github.redhatqe.db.MakeEdges;
import com.github.redhatqe.db.nodes.NodeType;
import com.github.redhatqe.db.utils.Tuple;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This vertex type represents a generic concept of a TestCase.  It contains enough information so that test methods
 * in a project's test framework can be linked to a node of this type.
 */
public class TestCaseNode {
    public static final String name = TestCaseNode.class.getSimpleName();
    public static Logger logger = LogManager.getLogger(name);
    public static final String projectEdge = "is in";   // A TestCaseNode "is in" a ProjectNode
    public static final String setupEdge = "setup by";  // A TestCaseNode is "setup by" a SetupNode

    // Creates the type for a TestCase.  This is the starting point, or root of our graph
    public static CreateType node() {
        return (ODatabaseDocument odb) -> {
            OClass tc = NodeType.getClass(TestCaseNode.name, odb);
            List<Tuple<String, OType>> props = new ArrayList<>();
            props.add(new Tuple<>("id", OType.INTEGER));
            props.add(new Tuple<>("title", OType.STRING));
            props.add(new Tuple<>("description", OType.STRING));
            props.add(new Tuple<>("importance", OType.STRING));
            props.add(new Tuple<>("posneg", OType.STRING));
            props.add(new Tuple<>("level", OType.STRING));
            props.add(new Tuple<>("automation", OType.STRING));
            props.add(new Tuple<>("method", OType.STRING));

            for(Tuple<String, OType> t: props) {
                if(!tc.existsProperty(t.first))
                    tc.createProperty(t.first, t.second);
                else
                    logger.info("{} property already exists", t.first);
            }

            return tc;
        };
    }

    public Tuple<OClass, Map<String, Tuple<OType, OClass>>> setup(ODatabaseDocument odb) {
        OClass vtx = odb.getClass(TestCaseNode.name);
        OClass project = odb.getClass(ProjectNode.name);
        OClass setup = odb.getClass(SetupNode.name);

        Map<String, Tuple<OType, OClass>> links = new HashMap<>();
        links.put(projectEdge, new Tuple<>(OType.LINKSET, project));  // can belong to more than one project
        links.put(setupEdge, new Tuple<>(OType.LINK, setup));

        return new Tuple<>(vtx, links);
    }

    /**
     * Creates the edges for
     * @return
     */
    public static MakeEdges edges() {
        return (OClass vtx, Map<String, Tuple<OType, OClass>> links) -> {
            for (Map.Entry<String, Tuple<OType, OClass>> es : links.entrySet()) {
                String name = es.getKey();
                OType ot = es.getValue().first;
                OClass oc = es.getValue().second;

                vtx.createProperty(name, ot, oc);
            }
        };
    }
}
