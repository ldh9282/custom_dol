package com.custom.dlp.cmmn.config;

import java.util.Locale;

import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import jakarta.annotation.PostConstruct;

@Configuration
public class P6SpySqlFormatConfig implements MessageFormattingStrategy {

	@PostConstruct
    public void init() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(this.getClass().getName());
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category,
                                String prepared, String sql, String url) {

        StringBuilder sb = new StringBuilder();

        if (sql != null && !sql.trim().isEmpty()) {
        	if (!(sql.contains("/*") && sql.contains("*/"))) { // JPA
        		sb.append("바인딩 후 쿼리\n").append(formatSql(category, sql));
        	} else { // 마이바티스
        		sb.append("바인딩 후 쿼리\n").append(sql.replaceAll("\n\t\t", "\n"));
        	}
        }

        return sb.toString();
    }

    private String formatSql(String category, String sql) {
        if (sql != null && !sql.trim().isEmpty() && Category.STATEMENT.getName().equals(category)) {
            String trimmed = sql.trim().toLowerCase(Locale.ROOT);
            if (trimmed.startsWith("create") || trimmed.startsWith("alter") || trimmed.startsWith("comment")) {
                return FormatStyle.DDL.getFormatter().format(sql);
            } else {
                return FormatStyle.BASIC.getFormatter().format(sql);
            }
        }
        return sql;
    }
}
