package com.example.taskmanagement.migration;

import java.util.Arrays;
import java.util.List;

public class V2__task extends TaskMigration {

    @Override
    public List<String> getMigrations() {
        return Arrays.asList("create table mst_tasks\n" +
                "(\n" +
                "    id                 bigserial not null,\n" +
                "    title          varchar(255) ,\n" +
                "    description           varchar(255),\n" +
                "    status           varchar(255) ,\n" +
                "    due_date       timestamp ,\n" +
                "    user_id int8 constraint fk_user_id references mst_users ,\n" +
                "    primary key (id)\n" +
                ");\n");




    }
}
