package com.crazytech.pdfcover;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

import com.crazytech.pdfcover.prog.GenerateSQL;
import com.crazytech.pdfcover.prog.Pubs;
import com.crazytech.swing.texteditor.DragDropTextEditor;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JButton;

import org.apache.http.client.ClientProtocolException;

import javax.swing.JComboBox;

public class GeneratorUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPanel panel_north;
	private JLabel lbUrl;
	private JTextField tfUrl;
	private JPanel panel_west;
	private JLabel lbPub;
	private JLabel lbPubVal;
	private JLabel lbIssue;
	private JLabel lbIssueVal;
	private JPanel panel_center;
	private JPanel panel_south;
	private JButton btnExe;
	private DragDropTextEditor teOutput;
	private JLabel lbDbSchema;
	private JTextField tfDbSchema;
	private JLabel lbTableName;
	private JComboBox cbtfTableName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeneratorUI frame = new GeneratorUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GeneratorUI() {
		initUI();
	}
	
	private void initUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel_north = new JPanel();
		contentPane.add(panel_north, BorderLayout.NORTH);
		panel_north.setLayout(new BoxLayout(panel_north, BoxLayout.X_AXIS));
		
		lbUrl = new JLabel("URL");
		panel_north.add(lbUrl);
		
		tfUrl = new JTextField();
		tfUrl.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				new PopulateTextFields(tfUrl.getText()).execute();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				new PopulateTextFields(tfUrl.getText()).execute();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				
				new PopulateTextFields(tfUrl.getText()).execute();
			}
		});
		panel_north.add(tfUrl);
		tfUrl.setColumns(10);
		
		panel_west = new JPanel();
		contentPane.add(panel_west, BorderLayout.WEST);
		GridBagLayout gbl_panel_west = new GridBagLayout();
		gbl_panel_west.columnWidths = new int[]{0, 0, 0};
		gbl_panel_west.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_west.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_west.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_west.setLayout(gbl_panel_west);
		
		lbPub = new JLabel("Pub");
		GridBagConstraints gbc_lbPub = new GridBagConstraints();
		gbc_lbPub.anchor = GridBagConstraints.WEST;
		gbc_lbPub.insets = new Insets(0, 0, 5, 5);
		gbc_lbPub.gridx = 0;
		gbc_lbPub.gridy = 0;
		panel_west.add(lbPub, gbc_lbPub);
		
		lbPubVal = new JLabel("Pub Value");
		GridBagConstraints gbc_lbPubVal = new GridBagConstraints();
		gbc_lbPubVal.anchor = GridBagConstraints.WEST;
		gbc_lbPubVal.insets = new Insets(0, 0, 5, 0);
		gbc_lbPubVal.gridx = 1;
		gbc_lbPubVal.gridy = 0;
		panel_west.add(lbPubVal, gbc_lbPubVal);
		
		lbIssue = new JLabel("Issue");
		GridBagConstraints gbc_lbIssue = new GridBagConstraints();
		gbc_lbIssue.anchor = GridBagConstraints.WEST;
		gbc_lbIssue.insets = new Insets(0, 0, 5, 5);
		gbc_lbIssue.gridx = 0;
		gbc_lbIssue.gridy = 1;
		panel_west.add(lbIssue, gbc_lbIssue);
		
		lbIssueVal = new JLabel("Issue Value");
		GridBagConstraints gbc_lbIssueVal = new GridBagConstraints();
		gbc_lbIssueVal.anchor = GridBagConstraints.WEST;
		gbc_lbIssueVal.insets = new Insets(0, 0, 5, 0);
		gbc_lbIssueVal.gridx = 1;
		gbc_lbIssueVal.gridy = 1;
		panel_west.add(lbIssueVal, gbc_lbIssueVal);
		
		lbDbSchema = new JLabel("DB Schema");
		GridBagConstraints gbc_lbDbSchema = new GridBagConstraints();
		gbc_lbDbSchema.anchor = GridBagConstraints.WEST;
		gbc_lbDbSchema.insets = new Insets(0, 0, 5, 5);
		gbc_lbDbSchema.gridx = 0;
		gbc_lbDbSchema.gridy = 2;
		panel_west.add(lbDbSchema, gbc_lbDbSchema);
		
		tfDbSchema = new JTextField();
		GridBagConstraints gbc_tfDbSchema = new GridBagConstraints();
		gbc_tfDbSchema.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfDbSchema.insets = new Insets(0, 0, 5, 0);
		gbc_tfDbSchema.anchor = GridBagConstraints.NORTH;
		gbc_tfDbSchema.gridx = 1;
		gbc_tfDbSchema.gridy = 2;
		panel_west.add(tfDbSchema, gbc_tfDbSchema);
		tfDbSchema.setText("phpmysql");
		tfDbSchema.setColumns(10);
		
		lbTableName = new JLabel("Table Name");
		GridBagConstraints gbc_lbTableName = new GridBagConstraints();
		gbc_lbTableName.anchor = GridBagConstraints.EAST;
		gbc_lbTableName.insets = new Insets(0, 0, 0, 5);
		gbc_lbTableName.gridx = 0;
		gbc_lbTableName.gridy = 3;
		panel_west.add(lbTableName, gbc_lbTableName);
		
		cbtfTableName = new JComboBox();
		GridBagConstraints gbc_cbtfTableName = new GridBagConstraints();
		gbc_cbtfTableName.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbtfTableName.gridx = 1;
		gbc_cbtfTableName.gridy = 3;
		cbtfTableName.addItem("pubs_wt");
		cbtfTableName.addItem("pubs_g");
		cbtfTableName.addItem("pubs_books");
		cbtfTableName.addItem("pubs_brochures");
		cbtfTableName.addItem("pubs_booklets");
		cbtfTableName.addItem("pubs_okm");
		cbtfTableName.addItem("pubs_yearbooks");
		panel_west.add(cbtfTableName, gbc_cbtfTableName);
		
		panel_center = new JPanel();
		panel_center.setLayout(new BoxLayout(panel_center, BoxLayout.X_AXIS));
		teOutput = new DragDropTextEditor("output", new Locale("en"));
		panel_center.add(teOutput);
		contentPane.add(panel_center, BorderLayout.CENTER);
		
		panel_south = new JPanel();
		contentPane.add(panel_south, BorderLayout.SOUTH);
		
		btnExe = new JButton("EXECUTE");
		btnExe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				execute(tfUrl.getText());
			}
		});
		panel_south.add(btnExe);
		
	}
	
	
	private void execute(String url){
		teOutput.setText("");
		int startIndex = url.indexOf("pub=")+("pub=").length();
		String pub = url.substring(startIndex,url.indexOf("&", startIndex));
		startIndex = url.indexOf("langwritten=")+("langwritten=").length();
		String locale = url.substring(startIndex,url.indexOf("&", startIndex));
		String loopUrl = "";
		String pubFull = pub+"_"+locale;
		String issue = "";
		if(url.indexOf("issue=")>-1){
			startIndex = url.indexOf("issue=")+("issue=").length();
			issue = url.substring(startIndex,url.indexOf("&", startIndex));
			pubFull = pubFull+"_"+issue;
		}
		pubFull+=".pdf";
		Application app = new Application();
		for (String wtLocale : Pubs.LocaleMap().keySet()) {
			loopUrl = "http://www.jw.org/download/?"+(!issue.equals("")?("issue="+issue+"&"):"")+"output=html&pub="+pub+"&fileformat=EPUB%2CPDF%2CBRL%2CRTF%2CMOBI&alllangs=0&langwritten="+wtLocale+"&txtCMSLang="+wtLocale+"&isBible=0";
			pubFull = pub+"_"+wtLocale;
			issue = "";
			if(url.indexOf("issue=")>-1){
				startIndex = url.indexOf("issue=")+("issue=").length();
				issue = url.substring(startIndex,url.indexOf("&", startIndex));
				pubFull = pubFull+"_"+issue;
			}
			pubFull+=".pdf";
			try {
				String sb = app.decodeHttp(loopUrl);
				//String sb = app.decodeHttp("http://www.jw.org/download/?issue=20151015&output=html&pub=w&fileformat=EPUB%2CPDF%2CBRL%2CRTF%2CMOBI&alllangs=0&langwritten=x&txtCMSLang=x&isBible=0");
				//System.out.println(sb);
				String jwDomain = "http://www.jw.org/";
				startIndex = sb.indexOf("<img src=\"");
				String imagePath = sb.substring(startIndex+10, sb.indexOf("\" alt=\"\"", startIndex));
				imagePath = jwDomain+imagePath.replace("_xs.jpg", "_md.jpg");
				System.out.println(imagePath);
				startIndex = sb.indexOf("<iframe src=");
				String iframePath = sb.substring(startIndex+14,sb.indexOf("\"></iframe>", startIndex));
				iframePath = jwDomain+iframePath;
				//iframePath = iframePath.replace("output=html", "output=json");
				System.out.println(iframePath);
				String sbIframe;
				sbIframe = app.decodeHttp(iframePath);
				if (!sbIframe.endsWith("application/octet-stream")) {
					startIndex = sbIframe.indexOf("<title>");
					String desc = sbIframe.substring(startIndex+7,sbIframe.indexOf("</title>", startIndex));
					desc = desc.replace("&nbsp;", " ").replace("<br/>", " ");
					System.out.println(desc);
					startIndex = sbIframe.indexOf("<a href=\"", sbIframe.indexOf("PDF</caption>"))+9;
					String pdf = sbIframe.substring(startIndex, sbIframe.indexOf("\"", startIndex));
					System.out.println(pdf);
					new AddToOutput(new GenerateSQL().generate(tfDbSchema.getText(),cbtfTableName.getSelectedItem().toString(),pubFull, pdf, imagePath,desc,Pubs.LocaleMap().get(wtLocale))).execute();
				} 
			} catch (StringIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private class PopulateTextFields extends SwingWorker<String, String> {
		private String pub, issue;
		
		

		public PopulateTextFields(String url) {
			super();
			int startIndex = url.indexOf("pub=")+("pub=").length();
			String pub = url.substring(startIndex,url.indexOf("&", startIndex));
			startIndex = url.indexOf("langwritten=")+("langwritten=").length();
			String locale = url.substring(startIndex,url.indexOf("&", startIndex));
			String loopUrl = "";
			String pubFull = pub+"_"+locale;
			String issue = "";
			if(url.indexOf("issue=")>-1){
				startIndex = url.indexOf("issue=")+("issue=").length();
				issue = url.substring(startIndex,url.indexOf("&", startIndex));
				pubFull = pubFull+"_"+issue;
			}
			this.pub = pub;
			this.issue = issue;
		}



		@Override
		protected String doInBackground() throws Exception {
			lbPubVal.setText(pub);
			lbIssueVal.setText(issue);
			if(pub.equals("w")||pub.equals("ws"))cbtfTableName.setSelectedItem("pubs_wt");
			else if(pub.equals("g"))cbtfTableName.setSelectedItem("pubs_g");
			return null;
		}
		
		
	}
	
	private class AddToOutput extends SwingWorker<String,String> {
		private String output;
		
		
		public AddToOutput(String output) {
			super();
			this.output = output;
		}


		@Override
		protected String doInBackground() throws Exception {
			teOutput.setText(teOutput.getText()+output);
			return null;
		}
		
	}

}
