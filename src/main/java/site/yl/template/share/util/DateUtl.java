package site.yl.template.share.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 
 * @description 时间处理工具类
 * @author yliang
 */
public class DateUtl {

	public static Date now() {
		Instant now = Instant.now();
		return of(now);
	}

	public static Date of(Instant instant) {
		return Date.from(instant);
	}

	public static Date map(LocalDate localDate) {
		return map(localDate, ZoneId.systemDefault());
	}

	public static Date map(LocalDate localDate, ZoneId zone) {
		return of(toInstant(localDate, zone));
	}

	public static Date of(LocalDateTime localDateTime) {
		return of(localDateTime, ZoneId.systemDefault());
	}

	public static Date of(LocalDateTime localDateTime, ZoneId zone) {
		return of(toInstant(localDateTime, zone));
	}

	public static LocalDateTime of(Date date, ZoneId zone) {
		Instant instant = date.toInstant();
		return LocalDateTime.ofInstant(instant, zone);
	}

	public static LocalDateTime of(Date date) {
		ZoneId zone = ZoneId.systemDefault();
		return of(date, zone);
	}

	public static LocalDate map(Date date, ZoneId zone) {
		Instant instant = date.toInstant();
		return LocalDateTime.ofInstant(instant, zone).toLocalDate();
	}

	public static LocalDate map(Date date) {
		ZoneId zone = ZoneId.systemDefault();
		return map(date, zone);
	}

	// -------------------parse

	public static LocalDate str2LocalDate(String dateStr, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(dateStr, formatter);
	}

	public static Date str2Date(String str, String style) {
		return map(str2LocalDate(str, style));
	}

	public static LocalDateTime str2LocalDateTime(String dateTimeStr, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(dateTimeStr, formatter);
	}

	public static LocalTime timeStr2LocalTime(String timeStr, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalTime.parse(timeStr, formatter);
	}

	// -------------------format
	public static String format(LocalDateTime ldt, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return ldt.format(formatter);
	}

	public static String format(LocalDate ld, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return ld.format(formatter);
	}

	public static String format(LocalTime lt, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return lt.format(formatter);
	}

	// -------------------toNumbernic
	/***
	 * 
	 * @param ld
	 *            localDate
	 * @param pattern
	 *            number pattern (eg:"yyMMdd" or "yyMM")
	 * @return
	 */
	public static int toNumber(LocalDate ld, String pattern) {
		String str = format(ld, pattern);
		return Integer.parseInt(str);
	}

	public static int toNumber(LocalTime lt, String pattern) {
		String str = format(lt, pattern);
		return Integer.parseInt(str);
	}


	// -------------------toInstant
	public static Instant toInstant(LocalDate ld) {
		ZoneId zone = ZoneId.systemDefault();
		return toInstant(ld, zone);
	}

	public static Instant toInstant(LocalDateTime ldt) {
		ZoneId zone = ZoneId.systemDefault();
		return toInstant(ldt, zone);
	}

	public static Instant toInstant(LocalDate ld, ZoneId zone) {
		return ld.atStartOfDay(zone).toInstant();
	}

	public static Instant toInstant(LocalDateTime ldt, ZoneId zone) {
		return ldt.atZone(zone).toInstant();
	}

	public static void main(String[] args) {
		System.out.println(DateUtl.map(new Date()).getMonth());
	}

}
