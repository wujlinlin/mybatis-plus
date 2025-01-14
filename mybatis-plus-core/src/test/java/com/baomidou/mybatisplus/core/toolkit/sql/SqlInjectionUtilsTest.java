package com.baomidou.mybatisplus.core.toolkit.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * SQL 注入验证工具类测试
 *
 * @author hubin
 * @since 2021-08-15
 */
 class SqlInjectionUtilsTest {

    @Test
     void sqlTest() {
        assertSql(false, "insert abc");
        assertSql(true, "SELECT * FROM user");
        assertSql(true, "delete from user");
        assertSql(true, "drop TABLE user");
        assertSql(true, ";TRUNCATE from user");
        assertSql(false, "update");
        assertSql(false, "trigger");
        assertSql(false, "convert(name using GBK)");

        // 无空格
        assertSql(false, "insert_into");
        assertSql(true, "SELECT aa FROM user");
        // 无空格
        assertSql(true, "SELECT*FROM user");
        // 左空格
        assertSql(true, "SELECT *FROM user");
        // 右空格
        assertSql(true, "SELECT* FROM user");
        // 左tab
        assertSql(true, "SELECT                 *FROM user");
        // 右tab
        assertSql(true, "SELECT*        FROM user");
        assertSql(false, "SELECT*FROMuser");
        // 该字符串里包含 setT or
        assertSql(false, "databaseType desc,orderNum desc)");
        // 双引号情况
        assertSql(true, "\\\" or 1=1 and \\\"123\\\"=\\\"123\\\"");
        //Wrapper的apply情况
        assertSql(true, "1 = 1) OR 1 = 1 --");
    }

    private void assertSql(boolean injection, String sql) {
        Assertions.assertEquals(injection, SqlInjectionUtils.check(sql));
    }
}
