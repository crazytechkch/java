package com.crazytech.pdfcover.prog;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Pubs {
	private String filename;
	private String url ="";
	private Map<String,String> wtPubsLocaleMap;
	
	public static void main(String[] args) {
		Pubs pubs = new Pubs("ws_E_20140701");
		System.out.println(pubs.filename+"|"+pubs.description()+"|"+(pubs.isG()||pubs.isW()?pubs.issueDateLong():""));
	}
	
	public Pubs(String filename) {
		super();
		this.filename = filename;
		wtPubsLocaleMap = new HashMap<String,String>();
		wtPubsLocaleMap.put("E", "en");
		wtPubsLocaleMap.put("X", "de");
		wtPubsLocaleMap.put("S", "es");
		wtPubsLocaleMap.put("F", "fr");
		wtPubsLocaleMap.put("CHS", "zh");
		wtPubsLocaleMap.put("J", "ja");
		wtPubsLocaleMap.put("KO", "ko");
		wtPubsLocaleMap.put("ML", "ms");
	}

	public String description(){
		String pubName = "";
		ResourceBundle res = ResourceBundle.getBundle("message",new Locale(pubLocale()));
		if(isW()) pubName = res.getString("watchtower");
		else if(isG())pubName = res.getString("awake");
		else if(isOkm())pubName = res.getString("okm");
		return pubName+(isG()||isW()?" "+issueDateShort():"");
	}
	
	public String pubType() {
		return filename.split("_")[0];
	}
	
	public String pubLocale() {
		return wtPubsLocaleMap.get(filename.split("_")[1]);
	}
	/**
	 * @return "dd MM yyyy"
	 */
	public String issueDateShort() {
		String dateStr = filename.split("_")[2];
		Integer day, month, year;
		day = (pubType().equals("g")||pubType().equals("km"))?1:Integer.valueOf(dateStr.substring(6, 8)); month= Integer.valueOf(dateStr.substring(4,6));year = Integer.valueOf(dateStr.substring(0, 4));
		Date date = new Date(year-1900, month-1, day);
		if (pubLocale().equals("zh")) return new SimpleDateFormat("yyyy年 M月 d日",new Locale(pubLocale())).format(date);
		if (pubLocale().equals("ja")) return new SimpleDateFormat("yyyy年 M月 d日",new Locale(pubLocale())).format(date);
		if (pubLocale().equals("ko")) return new SimpleDateFormat("yyyy M월 d일",new Locale(pubLocale())).format(date);
		return new SimpleDateFormat("dd MMM yyyy",new Locale(pubLocale())).format(date);
	}
	
	/**
	 * @return "yyyy_MM_MMM yyyy"
	 */
	public String issueDateLong() {
		String dateStr = filename.split("_")[2];
		Integer day, month, year;
		day = (pubType().equals("g")||pubType().equals("km"))?1:Integer.valueOf(dateStr.substring(6, 8)); month= Integer.valueOf(dateStr.substring(4,6));year = Integer.valueOf(dateStr.substring(0, 4));
		Date date = new Date(year-1900, month-1, day);
		if (pubLocale().equals("zh")) return new SimpleDateFormat("yyyy_MM_yyyy年 M月",new Locale(pubLocale())).format(date);
		if (pubLocale().equals("ja")) return new SimpleDateFormat("yyyy_MM_yyyy年 M月",new Locale(pubLocale())).format(date);
		if (pubLocale().equals("ko")) return new SimpleDateFormat("yyyy_MM_yyyy M월",new Locale(pubLocale())).format(date);
		return new SimpleDateFormat("yyyy_MM_MMM yyyy",new Locale(pubLocale())).format(date);
	}
	
	public String table() {
		if (isW()) return "pubs_wt";
		else if(isG()) return "pubs_g";
		else if(isOkm()) return "pubs_okm";
		return "[table]";
	}
	
	public Boolean isW(){
		if(pubType().equals("w")||pubType().equals("wp")||pubType().equals("ws")) return true;
		return false;
	}
	
	public Boolean isG(){
		if(pubType().equals("g")) return true;
		return false;
	}
	
	public Boolean isOkm() {
		if(pubType().equals("km")) return true;
		return false;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
