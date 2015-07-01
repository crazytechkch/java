package com.crazytech.test.bies;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.crazytech.io.IOUtil;
import com.crazytech.theocratic.bible.BibleMapping;

public class Bies {
	
	private String log = "";
	
	public void generateBiFromWeb(String outFile,String locale, String biCode, String wLocale, 
			String rVer, String pubYear, JTextPane label, int startBook, int endBook) {
		BibleMapping biMap = new BibleMapping();
		Integer book = 1;
		StyledDocument doc = label.getStyledDocument();
		Style error = label.addStyle("error", null);
		Style success = label.addStyle("success", null);
		StyleConstants.setForeground(error, Color.RED);
		StyleConstants.setForeground(success, Color.decode("0X00914C"));
		if (!new File(outFile).exists()) new File(outFile).mkdirs();
		for (int h = startBook; h <= endBook; h++) {
			book = h;
			for (int i = 1; i <= biMap.getBibleMap().get("bk_"+book).size(); i++) {
				try {
					String response = getHttpResponse("http://wol.jw.org/"+locale+"/wol/b/r"+rVer+"/lp-"+wLocale.toLowerCase()+"/"+biCode+"/"+wLocale.toUpperCase()+"/"+pubYear+"/"+book+"/"+i, "UTF-8");
					response = response.substring(response.indexOf("<div class='document bible' dir='ltr'>"),response.indexOf("</div>", response.indexOf("<div id=\"navScrollPositionFloating\" class=\"scrollPositionDisplay\"")));
					System.out.println(response);
					String line = "<text chap=\""+i+"\">"+response+"</text>";
					IOUtil.writeFile(line, outFile+"\\bi_"+book+".xml","UTF-8");
					doc.insertString(0, "b"+String.format("%02d", h)+"c"+String.format("%03d", i)+" success\n", success);
					//setLog("Chapter "+i+" success\n");
					//label.setText(getLog());
				} catch (Exception e) {
					// TODO: handle exception
					try {
						doc.insertString(0, "b"+String.format("%02d", h)+"c"+String.format("%03d", i)+" failed\n", error);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//setLog("Chapter "+i+" failed\n");
					//setLog(e.getMessage()+"\n");
					//label.setText(getLog());
					e.printStackTrace();
					
				}
			}
		}
		restyleBiXml(outFile, label);
		try {
			doc.insertString(0, "Finished Generating Bible\n", success);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void restyleBiXml(String folder,  JTextPane label) {
		File dir = new File(folder);
		int h=0;
		StyledDocument doc = label.getStyledDocument();
		Style error = label.addStyle("error", null);
		Style success = label.addStyle("success", null);
		StyleConstants.setForeground(error, Color.RED);
		StyleConstants.setForeground(success, Color.decode("0X00914C"));
		for (File xmlFile : dir.listFiles()) {
			try {
				String text = IOUtil.readFile(xmlFile.toString(), "UTF-8");
				String line = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><bible>"+text+"</bible>";
				IOUtil.overwriteFile(line, xmlFile.toString(), "UTF-8");
				System.out.println(xmlFile.toString());
				doc.insertString(0, "Formatting b"+String.format("%02d", h)+"\n", success);
				h++;
			} catch (IOException | BadLocationException e) {
				// TODO Auto-generated catch block
				try {
					doc.insertString(0, "Formatting b"+String.format("%02d", h)+"\n", error);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void generateEsFromWeb(Integer id, Calendar date,
			Integer nDays, Integer step, Boolean isMemorial, String outFile,
			String locale, String wLocale, String rVer, JTextPane label){
		int i = id; /*28 60*/
		//Date date = cal.getTime(); /*10/04/20147 - 17/04/2014*/
		Calendar cal = date;
		Long endTime = cal.getTimeInMillis()+nDays*Long.valueOf("86400000");
		
		StyledDocument doc = label.getStyledDocument();
		Style error = label.addStyle("error", null);
		Style success = label.addStyle("success", null);
		StyleConstants.setForeground(error, Color.RED);
		StyleConstants.setForeground(success, Color.decode("0X00914C"));
		
		String locale2 = locale.equalsIgnoreCase("zh-hans")?"zh":locale;
		
		while(cal.getTimeInMillis()<endTime){
			if (cal.get(Calendar.DATE)==1) i=1;
		try {
			System.out.println(cal.getTime().toString());
			String response = getHttpResponse("http://wol.jw.org/"+locale+"/wol/dt/r"+rVer+"/lp-"+wLocale.toLowerCase()+"/"+cal.get(Calendar.YEAR)+"/"+new SimpleDateFormat("M/d").format(cal.getTime()),"UTF-8");
			response = response.substring(response.indexOf("id='p"+Integer.valueOf(i+1)+"'"),response.indexOf("</div>", response.indexOf("id='p"+Integer.valueOf(i+step-1)+"'")));
			String text = response.substring(response.indexOf("<p class='sa'>"), response.indexOf("</p>",response.indexOf("<p class='sa'>")));
			String desc = response.substring(response.indexOf("<p class='sb'>"), response.indexOf("</p>",response.indexOf("<p class='sb'>")));
			String memorial = "";
			if(isMemorial)memorial = response.substring(response.indexOf("<p class='sd'>"), response.indexOf("</p>",response.indexOf("<p class='sd'>")));
			String line = "<text date='"+new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime())+"'>"+"<date>"+new SimpleDateFormat("MMMM d, EEEE",new Locale(locale2)).format(cal.getTime())+"</date>"+"<script>"+text+"</script>"+"<desc>"+desc+memorial+"</desc></text>";
			IOUtil.writeFile(line, outFile,"UTF-8");
			doc.insertString(0, new SimpleDateFormat("yyyy-MM-dd EEE").format(cal.getTime())+" success\n", success);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				doc.insertString(0, new SimpleDateFormat("yyyy-MM-dd EEE").format(cal.getTime())+" error "+e.getMessage()+"\n", error);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		cal.setTimeInMillis(cal.getTimeInMillis()+86400000);
		i+=step;
		}
	}
	
	private String getLog(){
		return this.log;
	}
	
	private void setLog(String log){
		this.log += log;
	}
	
	private String getHttpResponse(String url, String charSet) throws Exception {
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		//con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream(),charSet));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		return response.toString();
	}
}
