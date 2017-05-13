package starfish.orm.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by song on 17/5/13.
 */
public class PropertiesHelp {

    Properties properties = new Properties();

    public PropertiesHelp() {
        init();
    }

    private void init() {

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("DB.properties"));
        } catch (IOException e) {
            System.err.println("文件装载失败");
            e.printStackTrace();
        }
    }

    public String getPropertie(String key) {
        return properties.getProperty(key);
    }


}
