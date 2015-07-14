package com.crazytech.pdfcover.prog;

import java.io.File;
import java.io.IOException;

import com.crazytech.io.IOUtil;


public class GenerateSQL {
	private Pubs pubs;
	public GenerateSQL() {
		super();
	}
	
	public String generate(String dbSchema, String tableName, String filename, String url, String imgUrl, String desc, String locale) throws IOException {
		pubs = new Pubs(filename);
		filename = filename.replace(".pdf", "");
		String line = "";
		if (pubs.isW()||pubs.isG()||pubs.isOkm())
			line = "insert into "+dbSchema+"."+tableName+" (filename,description,issue_date,locale,url,imgurl) "
				+"select * from (select "
				+"'"+filename+"',"
				+"'"+desc+"',"
				+"'"+pubs.issueDateLong()+"',"
				+"'"+locale+"',"
				+"'"+url+"','"+imgUrl+"')"
				+"as tmp where not exists(select filename from "+dbSchema+"."+tableName+" where filename = '"+filename+"')"
				+ ";";
		else 
			line = "insert into "+dbSchema+"."+tableName+" (filename,description,locale,url,imgurl) "
					+"select * from (select "
					+"'"+filename+"',"
					+"'"+desc+"',"
					+"'"+locale+"',"
					+"'"+url+"','"+imgUrl+"')"
					+"as tmp where not exists(select filename from "+dbSchema+"."+tableName+" where filename = '"+filename+"')"
					+ ";";
		System.out.println(line);
		return line+"\n";
	}
	
}
