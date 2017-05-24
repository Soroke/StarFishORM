package starfish.orm.database;

import starfish.orm.util.DateTools;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * 获取数据库的信息
 * Created by song on 17/5/13.
 */
public class DataBaseInfo {

    /**
     * 数据库连接信息
     */
    private String driver;
    private String url;
    private String name;
    private String passwd;

    /**
     * 数据库连接对象
     */
    private Connection conn = null;
    private DatabaseMetaData meta = null;
    private ResultSet rs = null;

    /**
     * 变量用于存储数据库表结构信息
     */
    private Map<String,List<String>> dbData = new HashMap<String, List<String>>();





    public DataBaseInfo(){
        init();
        getTableColumn(getTableName());
    }

    /**
     * 初始化数据库信息并连接数据库
     */
    private void init() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("DB.properties"));
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            name = properties.getProperty("username");
            passwd = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,name,passwd);
        } catch (ClassNotFoundException e) {
            System.err.println(DateTools.getTime() + "JDBC/ODBC driver load filed.");
            e.printStackTrace();
        } catch (SQLException sqle) {
            System.err.println(DateTools.getTime() + "Database connect error." );
            sqle.printStackTrace();
        } finally {
            System.out.println(DateTools.getTime() + "Database connect success.");
        }
    }


    /**
     * 获取数据库表名
     */
    private List<String> getTableName() {
        /**
         * 数据库表名称
         */
        List<String> tableNames = new ArrayList<String>();

        try {
            meta = conn.getMetaData();
            rs = meta.getTables(null, null, null, new String[] { "TABLE" });

            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            System.err.println(DateTools.getTime() + "Select Table Name filed." );
            e.printStackTrace();
        } finally {
            if(meta != null) {
                meta = null;
            }
            try {
                if(rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch(SQLException e) {
                System.err.println(DateTools.getTime() + "Close object ResultSet filed.");
                e.printStackTrace();
            }
        }

        return tableNames;
    }


    /**
     * 根据表名称获取表列信息
     * @param tableNames
     */
    private void getTableColumn(List<String> tableNames) {


        for(int i =0;i<tableNames.size();i++) {
            List<String> columns = new ArrayList<String>();
            try {
                meta = conn.getMetaData();
                ResultSet rs = meta.getColumns(null,"%", tableNames.get(i),"%");
                while(rs.next()) {
                    columns.add(rs.getString("COLUMN_NAME"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if(meta != null) {
                    meta = null;
                }
                try {
                    if(rs != null) {
                        rs.close();
                        rs = null;
                    }
                } catch(SQLException e) {
                    System.err.println(DateTools.getTime()+"Close object ResultSet filed.");
                    e.printStackTrace();
                }
            }

            dbData.put(tableNames.get(i),columns);

        }


    }

    public Map<String,List<String>> getDbInfo() {
        disConnect();
        return this.dbData;
    }


    private void disConnect() {
        try {
            if(conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            System.out.println(DateTools.getTime() + "Database disconnect error.");
            e.printStackTrace();
        } finally {
            System.out.println(DateTools.getTime() + "Datebase disconnect success.");

        }
    }

}
