package com.example.taskmanagement.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.util.List;

public abstract class TaskMigration extends BaseJavaMigration {

    /**
     * Return all migration files that will be executed
     * version
     *
     * @return
     */
    public abstract List<String> getMigrations();

    @Override
    public void migrate(Context context){
        List<String> scriptList = getMigrations();
        for (String script : scriptList) {
            // Execute the script
            new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true)).execute(script);
        }
    }


}
