package db;

import org.testng.annotations.Test;
import starfish.orm.database.DataBaseInfo;
import starfish.orm.database.ProducesClass;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 17/5/13.
 */
public class test {

    @Test
    public void testGetDBInfo() {
        DataBaseInfo db = new DataBaseInfo();
        Map<String,List<String>> dbData = db.getDbInfo();
        for (String key : dbData.keySet()) {
            List<String> columns = dbData.get(key);
            String column = "";
            for(String col:columns) {
                column=column + "\t" + col;
            }
            System.out.println(key + ":" + column);
        }
    }

    @Test
    public void testPath() {

        File file=new File("");
        String abspath=file.getAbsolutePath();
        System.out.println(abspath);
    }

    @Test
    public void testProducesDBFile() {

        new ProducesClass();
    }



}
