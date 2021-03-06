package com.github.redhatqe.alexandria.nodes;

import com.github.redhatqe.alexandria.utils.Tuple;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;

import java.util.Map;


@FunctionalInterface
public interface MakeEdges {
    void link(OClass node, Map<String, Tuple<OType, OClass>> links);
}
