package com.ecomerce.user_service.auto_config_database;

public abstract class SqlQuery {
    public static final String HISTORY_SCHEMA_TABLE_CREATE_SQL = """
             CREATE TABLE IF NOT EXISTS schema_history (
                version VARCHAR(50) PRIMARY KEY,
                description VARCHAR(255),
                script_name VARCHAR(255),
                success BOOLEAN,
                executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
}
