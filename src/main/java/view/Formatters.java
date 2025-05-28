package view;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Ciso
 */
public class Formatters {

    public static Timestamp dateTimestampFormatter(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = (Date) dateFormat.parse(date);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}