package com.github.redhatqe.db.nodes.testresult;

import com.github.redhatqe.db.CreateType;
import com.github.redhatqe.db.nodes.NodeType;
import com.github.redhatqe.db.nodes.testcase.TestCaseNode;
import com.github.redhatqe.db.utils.Tuple;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of a test
 */
public class ResultNode {
    public static Logger logger = LogManager.getLogger(ResultNode.class.getSimpleName());

    // Creates the type for a TestCase.  This is the starting point, or root of our graph
    public static CreateType node() {
        return (ODatabaseDocument odb) -> {
            OClass tc = NodeType.getClass(TestCaseNode.name, odb);
            List<Tuple<String, OType>> props = new ArrayList<>();
            props.add(new Tuple<>("id", OType.INTEGER));
            props.add(new Tuple<>("passes", OType.INTEGER));
            props.add(new Tuple<>("fails", OType.INTEGER));
            props.add(new Tuple<>("blocked", OType.INTEGER));
            props.add(new Tuple<>("skipped", OType.INTEGER));
            props.add(new Tuple<>("total", OType.INTEGER));

            for(Tuple<String, OType> t: props) {
                if(!tc.existsProperty(t.first))
                    tc.createProperty(t.first, t.second);
                else
                    logger.info("{} property already exists", t.first);
            }

            return tc;
        };
    }
}
