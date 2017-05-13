package starfish.orm.database;

import starfish.orm.util.PropertiesHelp;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 17/5/13.
 */
public class ProducesClass {

    /**
     * 获取配置文件帮助类对象
     */
    PropertiesHelp propertiesHelp = new PropertiesHelp();


    public ProducesClass(){producesFile();}

    /**
     * 文件生成
     */
    public void producesFile() {

        //获取项目绝对路径
        String rootPath = getProjectRootPath() + "/src/";
        //获取数据库表结构
        DataBaseInfo db = new DataBaseInfo();
        Map<String,List<String>> dbData = db.getDbInfo();

        //获取包名配置文件信息并拼装包名路径
        String[] entrypaths = propertiesHelp.getPropertie("entrypath").split("\\.");
        String packagePath = "";
        for(String pat:entrypaths) {
            packagePath = packagePath + pat + "/";
        }

        //maven项目添加路径
        if(propertiesHelp.getPropertie("isMavenProject").equals("ture")) rootPath  = rootPath + "main/java/";

        //根据数据库表结构循环添类文件
        String classNames = "";
        for (String key : dbData.keySet()) {
            String path=rootPath + packagePath + turnFirstUp(key) +".java";
            String entityString = producesClassContent(key,dbData.get(key));
            try{
                File file=new File(path);
                if(!file.exists()) {
                    file.createNewFile();
                } else {
                    System.err.println(turnFirstUp(key) +".java类文件已生成，退出执行！");
                    return;
                }
                FileOutputStream out=new FileOutputStream(file,true);
                out.write(entityString.getBytes("utf-8"));
                out.close();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            classNames = classNames + turnFirstUp(key) + " ";
        }

        System.err.println("包" + propertiesHelp.getPropertie("entrypath") + "下" + dbData.size() + "个类文件：" + classNames + "生成完毕！");


    }

    /**
     * 根据表名和表列名生成类文件对象
     * @param tableName 表名
     * @param columns 列名
     * @return
     */
    public String producesClassContent(String tableName,List<String> columns) {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("package "+propertiesHelp.getPropertie("entrypath")+";\r\n");
        stringBuilder.append("/**\r\n");
        stringBuilder.append("* " + new Date() + "\r\n");
        stringBuilder.append("* StarFish ORM atuo generate: " + tableName + " \r\n");
        stringBuilder.append("*/ \r\n");
        stringBuilder.append("public class " + turnFirstUp(tableName) + "{\r\n");

        for(String colName:columns){
            stringBuilder.append("\tprivate String "+colName+";\r\n\n");
        }

        for(String colName:columns){
            stringBuilder.append("\tpublic String get"+turnFirstUp(colName)
                    +"(){\r\n\n");
            stringBuilder.append("\t\treturn "+colName+";\r\n\n");
            stringBuilder.append("\t}\r\n\n");
            stringBuilder.append("\tpublic void set"+turnFirstUp(colName)
                    + "(String "+colName+"){\r\n\n");
            stringBuilder.append("\t\tthis."+colName+"="+colName+";\r\n\n");
            stringBuilder.append("\t}\r\n\n");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /**
     * 将Stirng首字母大写
     * @param str
     * @return
     */
    private String turnFirstUp(String str) {
        char[] ch = str.toCharArray();
        if(ch[0]>='a'&&ch[0]<='z'){
            ch[0]=(char)(ch[0]-32);
        }
        return new String(ch);
    }


    /**
     * 获取项目根目录
     * @return
     */
    private String getProjectRootPath() {
        File file=new File("");
        String rootpath=file.getAbsolutePath();
        return rootpath;
    }

}
