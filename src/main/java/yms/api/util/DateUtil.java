package yms.api.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	public static final String DATE_FORMAT_DDMMYY_SLASH = "dd/MM/yyyy";
	public static final String DATE_FORMAT_DB = "yyyy-MM-dd";
	
	public static String convertDateToString(Date date, String format) throws Exception {
		return new SimpleDateFormat(format, Locale.ENGLISH).format(date);
		
	} 
	
	public static String convertDateRequestFormatToDateDBFormat(String dateRequestFormat) throws Exception{
		SimpleDateFormat formatRequest = new SimpleDateFormat(DATE_FORMAT_DDMMYY_SLASH, Locale.ENGLISH);
		SimpleDateFormat formatDB = new SimpleDateFormat(DATE_FORMAT_DB, Locale.ENGLISH);
		
		Date date = formatRequest.parse(dateRequestFormat);
		
		return formatDB.format(date);
		
	}
	
}
