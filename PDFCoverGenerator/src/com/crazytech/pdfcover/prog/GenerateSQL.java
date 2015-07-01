package com.crazytech.pdfcover.prog;

import java.io.File;
import java.io.IOException;

import com.crazytech.io.IOUtil;


public class GenerateSQL {
	private String filename;
	private String url, imgUrl;
	private Pubs pubs;
	public GenerateSQL(String filename, String url, String imgUrl) {
		super();
		this.filename = filename.split(".pdf")[0];
		this.url = url;
		this.imgUrl = imgUrl;
	}
	
	public String generate() throws IOException {
		pubs = new Pubs(filename);
		String line = "";
		if (pubs.isW()||pubs.isG()||pubs.isOkm())
			line = "insert into miniwl."+pubs.table()+" (filename,description,issue_date,locale,url,imgurl) "
				+"select * from (select "
				+"'"+filename+"',"
				+"'"+pubs.description()+"',"
				+"'"+pubs.issueDateLong()+"',"
				+"'"+pubs.pubLocale()+"',"
				+"'"+url+"','"+imgUrl+"')"
				+"as tmp where not exists(select filename from miniwl."+pubs.table()+" where filename = '"+filename+"')"
				+ ";";
		else 
			line = "insert into miniwl."+pubs.table()+" (filename,description,locale,url,imgurl) "
					+"select * from (select "
					+"'"+filename+"',"
					+"'"+pubs.description()+"',"
					+"'"+pubs.pubLocale()+"',"
					+"'"+url+"','"+imgUrl+"')"
					+"as tmp where not exists(select filename from miniwl."+pubs.table()+" where filename = '"+filename+"')"
					+ ";";
		System.out.println(line);
		return line+"\n";
	}
	
}
