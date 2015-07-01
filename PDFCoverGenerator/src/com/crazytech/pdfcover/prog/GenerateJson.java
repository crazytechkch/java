package com.crazytech.pdfcover.prog;

import java.io.File;
import java.io.IOException;

import com.crazytech.io.IOUtil;

public class GenerateJson {
	private String _filename;
	private String url;
	private Pubs pubs;

	public GenerateJson(String filename, String url) {
		super();
		_filename = filename.split(".pdf")[0];
		this.url = url;
		
	}
	
	public void generate(String jsonName) throws IOException{
		pubs = new Pubs(_filename);
		String line = "";
		if(pubs.isW()||pubs.isG()) 
			line = "{"+
				"\"filename\":\""+_filename+"\","+
				"\"description\":\""+pubs.description()+"\","+
				"\"locale\":\""+pubs.pubLocale()+"\","+
				"\"issue_date\":\""+pubs.issueDateLong()+"\","+
				"\"url\":\""+url+"\"},";
		else
			line = "{"+
				"\"filename\":\""+_filename+"\","+
				"\"description\":\""+pubs.description()+"\","+
				"\"locale\":\""+pubs.pubLocale()+"\","+
				"\"url\":\""+url+"\"},";
		IOUtil.writeFile(line, jsonName+".json","UTF-8");
	}
	
	
	
}
