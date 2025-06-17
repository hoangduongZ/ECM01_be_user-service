package com.ecomerce.user_service.auto_config_database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import static com.ecomerce.user_service.auto_config_database.SqlQuery.*;
@Component
public class SqlLoader implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    @Value("${sql.scan.location}")
    private String rootLocation;
    @Value("${auto.run.enabled}")
    private String enable;

    public SqlLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!"true".equalsIgnoreCase(enable)){
            System.out.println("Disable auto init");
            return;
        }
        createHistorySchemaIfNotExist();
        List<Resource> sqlFiles = getSqlScriptsFromPackage(rootLocation);
        List<SqlScript> scripts = scanVersion(sqlFiles);
        executionSqlVersion(scripts);
    }

    private List<SqlScript> scanVersion(List<Resource> resources) throws IOException {
        List<SqlScript> result = new ArrayList<>();
        for (Resource res : resources) {
            String filename = res.getFilename();
            if (filename == null || !filename.matches("V\\d+_.+\\.sql")) continue;

            String version = filename.substring(1, filename.indexOf("_")); // V1_init.sql -> 1
            String description = filename.substring(filename.indexOf("_") + 1, filename.lastIndexOf("."));
            String sql = new String(res.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            result.add(new SqlScript(version, description, filename, sql));
        }

        result.sort(Comparator.comparing(SqlScript::versionAsInt));
        return result;
    }

    private void createHistorySchemaIfNotExist() {
        jdbcTemplate.execute(HISTORY_SCHEMA_TABLE_CREATE_SQL);
    }

    public List<Resource> getSqlScriptsFromPackage(String pathInResources) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:" + pathInResources + "/*.sql");
        return Arrays.asList(resources);
    }

    private void executionSqlVersion(List<SqlScript> sqlScripts) {
        for (SqlScript script : sqlScripts) {
            Boolean alreadyExecuted = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) > 0 FROM schema_history WHERE version = ?",
                    Boolean.class,
                    script.version
            );

            if (Boolean.TRUE.equals(alreadyExecuted)) {
                continue;
            }
            try {
                jdbcTemplate.execute(script.sql);
                jdbcTemplate.update("""
                INSERT INTO schema_history (version, description, script_name, success)
                VALUES (?, ?, ?, ?)
            """, script.version, script.description, script.fileName, true);
            } catch (Exception e) {
                jdbcTemplate.update("""
                INSERT INTO schema_history (version, description, script_name, success)
                VALUES (?, ?, ?, ?)
            """, script.version, script.description, script.fileName, false);
                System.err.println("Failed at version: " + script.version +e.getMessage());
            }
        }
    }

    public record SqlScript(String version, String description, String fileName, String sql) {
        public int versionAsInt() {
            return Integer.parseInt(version);
        }
    }
}
