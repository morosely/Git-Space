package com.shiji.sql;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;

import java.util.*;

public class DBSQLUtils {

    public static void main(String[] args) {
        String sql = "ALTER TABLE `order`.`orderdetail` ADD  COLUMN createDate date; delete from shop where id = 1; insert into `omdmain`.`goods` select * from salegoods; ";
        tableNameFromSql(sql).forEach(System.out::println);
    }

    //判断语句是否是select查询语句
    public static boolean isSelect(String sql) {
        return sql.matches("^(?i)(\\s*)(select)(\\s+)(((?!(\\binsert\\b|\\bdelete\\b|\\bupdate\\b)).)+)$");
    }

    /**
     * 数据库类型：getDbType();
     * 查询的字段：getColumns();
     * 表名：getTables().keySet();
     * 条件：visitor.getConditions();
     * group by：visitor.getGroupByColumns();
     * order by：getOrderByColumns();
     */
    //SQL中获取表名
    public static Set<String> tableNameFromSql(String sql) {
        Set<String> allTableName = new HashSet<String>();

//        SQLStatementParser parser = new MySqlStatementParser(sql);
//        // 使用Parser解析生成AST，这里SQLStatement就是AST
//        SQLStatement sqlStatement = parser.parseStatement();
//        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
//        sqlStatement.accept(visitor);
//        Map<TableStat.Name, TableStat> tables = visitor.getTables();
//        for (TableStat.Name key : tables.keySet()) {
//            System.out.println("key = " + key +" value = " + tables.get(key));
//            allTableName.add(key.getName());
//        }

        DbType dbType = JdbcConstants.MYSQL;
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        if(stmtList!=null && !stmtList.isEmpty()){
            for (int i = 0; i < stmtList.size(); i++) {
                SQLStatement stmt = stmtList.get(i);
                MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
                stmt.accept(visitor);
                Map<TableStat.Name, TableStat> tables = visitor.getTables();
                for (TableStat.Name t : tables.keySet()) {
                    allTableName.add(t.getName());
                }
            }
        }
        return allTableName;
    }
}
