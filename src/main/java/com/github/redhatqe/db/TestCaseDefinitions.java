package com.github.redhatqe.db;

import com.github.redhatqe.db.polarion.ProjectNode;
import com.github.redhatqe.db.polarion.TestCaseNode;
import com.github.redhatqe.db.polarion.MethodNode;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseType;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.*;


public class TestCaseDefinitions {
    private OrientDB odb;
    public static final String name = "TestCaseType";
    public static Logger logger = LogManager.getLogger(name);
    public String dbName = "testcaseDefinitions";
    public String dburl; // path to db like "remote:192.168.1.1"
    public String user;
    public String password;

    public static String makeServerUrl(TestCaseDefinitions db) {
        return String.format("%s/%s", db.dburl, db.dbName);
    }

    public TestCaseDefinitions(String url,  String user, String pw) {
        this.dburl = url;
        this.user = user;
        this.password = pw;

        OrientDBConfig cfg = OrientDBConfig.defaultConfig();
        this.odb = new OrientDB(this.dburl, this.user, this.password, cfg);
        System.out.println("Database is open");
    }

    public void createDB(String dbname) {
        this.odb.createIfNotExists(dbname, ODatabaseType.PLOCAL);
    }

    /**
     * Generates a list of functions that will be called that will create all the Vertex types
     * @return
     */
    private List<CreateType> makeAllVertices() {
        List<CreateType> fns = new ArrayList<>();
        fns.add(MethodNode.node());
        fns.add(TestCaseNode.node());
        fns.add(ProjectNode.node());

        return fns;
    }

    /**
     * Generates all the types for the testcaseDefinitions graph.  This includes all the nodes and their edges
     *
     * The first step is to just generate all the vertices, then we will hook them up with edges
     *
     * @return
     */
    public void createGraphTypes(List<CreateType> cts){
        ODatabasePool pool = new ODatabasePool(this.odb, this.dbName,"root","redHAT2017");
        try (ODatabaseDocument db = pool.acquire()) {
            cts.forEach(ct -> ct.create(db));
        }
        finally {
            pool.close();
            this.odb.close();
        }
    }

    public static void main(String[] args) {
        TestCaseDefinitions tcdb = new TestCaseDefinitions("remote:192.168.1.107", "root", "redHAT2017");
        tcdb.createDB(tcdb.dbName);

        List<CreateType> vtxFns = tcdb.makeAllVertices();
        tcdb.createGraphTypes(vtxFns);
    }
}
