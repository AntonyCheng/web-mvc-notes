package top.sharehome.Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DATEUtils {
    private static Date date = new Date();

    public static String getNowTimeOfMySQL() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String getReturnTime(Integer days) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime returnDay = localDateTime.plusDays(days);
        String resultDay = dateTimeFormatter.format(returnDay);
        return resultDay;
    }
}
