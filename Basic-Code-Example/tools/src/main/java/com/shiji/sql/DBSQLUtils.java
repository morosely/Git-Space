package com.shiji.sql;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;

public class DBSQLUtils {

    public static void main(String[] args) {
        String sql = "insert into goods select * from salegoods;";
        tableFromSql(sql);
    }

    //判断语句是否是select查询语句
    public static boolean isSelect(String sql) {
        return sql.matches("^(?i)(\\s*)(select)(\\s+)(((?!(\\binsert\\b|\\bdelete\\b|\\bupdate\\b)).)+)$");
    }

    //SQL中获取表名
    public static void tableFromSql(String sql) {
        DbType dbType = JdbcConstants.MYSQL;
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        if(stmtList!=null && !stmtList.isEmpty()){
            for (int i = 0; i < stmtList.size(); i++) {
                SQLStatement stmt = stmtList.get(i);
                MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
                stmt.accept(visitor);
                System.out.println("Tables : " + visitor.getTables());
            }
        }
    }
}
