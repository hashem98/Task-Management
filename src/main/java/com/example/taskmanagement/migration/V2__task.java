package com.example.taskmanagement.migration;

import java.util.Arrays;
import java.util.List;

public class V2__task extends TaskMigration {

    @Override
    public List<String> getMigrations() {
        return Arrays.asList("create table mst_tasks\n" +
                "(\n" +
                "    id                 bigserial not null,\n" +
                "    title          varchar(255) not null,\n" +
                "    description           varchar(255) not null,\n" +
                "    status           varchar(255) not null,\n" +
                "    primary key (id)\n" +
                ");\n");




    }
}
