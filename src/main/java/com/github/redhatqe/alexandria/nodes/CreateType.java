package com.github.redhatqe.alexandria.nodes;

import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import com.orientechnologies.orient.core.metadata.schema.OClass;


/**
 * Created by stoner on 5/11/17.
 */
@FunctionalInterface
public interface CreateType {
    OClass create(ODatabaseDocument db);
}
