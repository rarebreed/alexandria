package com.github.redhatqe.alexandria.schema.db.testresult;

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

public class TestOutcomeNode {
    public static final String name = TestOutcomeNode.class.getSimpleName();
    public static Logger logger = LogManager.getLogger(name);

    // Creates the type for a TestCase.  This is the starting point, or root of our graph
    public static CreateType node() {
        return (ODatabaseDocument odb) -> {
            OClass result = NodeType.getClass(TestOutcomeNode.name, odb);
            List<Tuple<String, OType>> props = new ArrayList<>();
            props.add(new Tuple<>("id", OType.INTEGER));
            props.add(new Tuple<>("result", OType.BOOLEAN));  // "PASSED"|"FAILED"|"BLOCKED"|"SKIPPED"|"PENDING"
            props.add(new Tuple<>("duration", OType.INTEGER));
            props.add(new Tuple<>("start", OType.DATETIME));
            props.add(new Tuple<>("end", OType.DATETIME));

            for(Tuple<String, OType> t: props) {
                if(!result.existsProperty(t.first))
                    result.createProperty(t.first, t.second);
                else
                    logger.info("{} property already exists", t.first);
            }

            return result;
        };
    }
}
