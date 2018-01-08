package com.github.redhatqe.alexandria.schema.db.testcase;

import com.github.redhatqe.alexandria.nodes.CreateType;
import com.github.redhatqe.alexandria.nodes.NodeType;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;

/**
 * This is the vertex type for a Project.  For TestCase nodes, it will have a relationship (an edge) of belongsTo
 * with a Project
 */
public class ProjectNode {
    public static final String name = ProjectNode.class.getSimpleName();
    public static CreateType node() {
        return (ODatabaseDocument odb) -> {
            OClass tc = NodeType.getClass(name, odb);
            tc.createProperty("id", OType.INTEGER);
            return tc;
        };
    }

    interface ToString {
        default String stringify() {
            return this.toString().toLowerCase();
        }
    }

    /**
     * As other teams use this, add other Project IDs here.  We use the enums here to validate the value of the ID
     */
    public static enum ProjectIDs implements ToString {
        Unknown(0),
        RHEL6(1),
        RedHatEnterpriseLinux7(2);

        private Integer id;

        ProjectIDs(Integer i) {
            this.id = i;
        }

        ProjectIDs fromString(Integer i) {
            ProjectIDs id = Unknown;
            switch(i){
                case 0:
                    id = RHEL6;
                    break;
                case 1:
                    id = RedHatEnterpriseLinux7;
                    break;
            }
            return id;
        }
    }
}
