package starfish.orm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by song on 17/5/19.
 */
public class DateTools {

    public static String getTime() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式

        return "[" + dateFormat.format(now) + "]";
    }
}
