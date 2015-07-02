package com.crazytech.pdfcover;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.crazytech.test.bies.BiesUI;

public class Application{

	private JFrame _frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final Application window = new Application();
					window._frame.setVisible(true);
				}  catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		
		try {
			Application app = new Application();
			String sb = app.decodeHttp("http://www.jw.org/download/?issue=20151015&output=html&pub=w&fileformat=EPUB%2CPDF%2CBRL%2CRTF%2CMOBI&alllangs=0&langwritten=x&txtCMSLang=x&isBible=0");
			//System.out.println(sb);
			String jwDomain = "http://www.jw.org/";
			int startIndex = sb.indexOf("<img src=\"");
			String imagePath = sb.substring(startIndex+10, sb.indexOf("\" alt=\"\"", startIndex));
			imagePath = jwDomain+imagePath.replace("_xs.jpg", "_md.jpg");
			System.out.println(imagePath);
			startIndex = sb.indexOf("<iframe src=");
			String iframePath = sb.substring(startIndex+14,sb.indexOf("\"></iframe>", startIndex));
			iframePath = jwDomain+iframePath;
			//iframePath = iframePath.replace("output=html", "output=json");
			System.out.println(iframePath);
			String sbIframe = app.decodeHttp(iframePath);
			if (!sbIframe.endsWith("application/octet-stream")) {
				startIndex = sbIframe.indexOf("<title>");
				String desc = sbIframe.substring(startIndex+7,sbIframe.indexOf("</title>", startIndex));
				desc = desc.replace("&nbsp;", " ").replace("<br/>", " ");
				System.out.println(desc);
				startIndex = sbIframe.indexOf("<a href=\"", sbIframe.indexOf("PDF</caption>"))+9;
				String pdf = sbIframe.substring(startIndex, sbIframe.indexOf("\"", startIndex));
				System.out.println(pdf);
			} 
		} /*catch (SocketTimeoutException e) {
			e.printStackTrace();
		} */catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String decodeHttp(String url) throws ClientProtocolException,IOException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		int timeoutConnection = 3000;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		httpClient.setParams(httpParameters);
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream is = httpEntity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				is, /*"iso-8859-1"*/"utf-8"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		is.close();
		return sb.toString()+httpEntity.getContentType().toString();
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		_frame = new JFrame();
		_frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Application.class.getResource("/com/crazytech/pdfcover/res/icon.png")));
		_frame.setForeground(Color.BLACK);
		_frame.setTitle("PDF Cover Generator");
		_frame.setBounds(100, 100, 800, 600);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		try {
			tabbedPane.addTab("Cover Generator", new CoverGeneratorUI().getContentPane());
			tabbedPane.addTab("BiEs Generator", new BiesUI().getContentPane());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
	}
	
	
}
