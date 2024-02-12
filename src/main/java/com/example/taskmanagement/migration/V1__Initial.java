package com.example.taskmanagement.migration;

import java.util.ArrayList;
import java.util.List;

public class V1__Initial extends TaskMigration {

    @Override
    public List<String> getMigrations() {
        List<String> migrationList = new ArrayList<>();

        migrationList.add("create table mst_users\n" +
                "(\n" +
                "    id                 bigserial not null,\n" +
                "    user_name          varchar(255) not null,\n" +
                "    password           varchar(255) not null,\n" +
                "    primary key (id)\n" +
                ");\n");

        migrationList.add("Insert Into mst_users(id,user_name, password)\n" +
                        "    Values (-1, 'user@user.com.jo', '$2a$10$O9jWFnnr8i/YfMQ/N8uH9ufCPlIk37MdOF0YIOj.vVoZJlgTxXRam')");


        return migrationList;
    }
}
