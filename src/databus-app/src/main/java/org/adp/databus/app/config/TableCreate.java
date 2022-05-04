package org.adp.databus.app.config;

/**
 * @author zzq
 */
public interface TableCreate {

    String TEST_TABLE = "create table if not exists TEST_TABLE\n" +
            "(\n" +
            "    id   integer primary key autoincrement,\n" +
            "    name varchar,\n" +
            "    type integer,\n" +
            "    created_at integer\n" +
            ")";

}
