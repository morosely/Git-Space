package com.shiji.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.parser.Token;
import com.alibaba.druid.stat.TableStat;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * add by yihaitao
 */
public class SQLTools {

    //判断是否select语句
    public static boolean isSelect(String sql) {
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, "mysql");
        return Token.SELECT.equals(parser.getExprParser().getLexer().token());
      //return sql.matches("^(?i)(\\s*)(select)(\\s+)(((?!(\\binsert\\b|\\bdelete\\b|\\bupdate\\b)).)+)$");
    }

    /**
     * 数据库类型：getDbType();
     * 查询的字段：getColumns();
     * 表名：getTables().keySet();
     * 条件：visitor.getConditions();
     * group by：visitor.getGroupByColumns();
     * order by：getOrderByColumns();
     */
    //获取SQL中的表名
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
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, "mysql");
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
