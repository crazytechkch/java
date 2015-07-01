package com.crazytech.test.bies;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.crazytech.io.IOUtil;
import com.toedter.calendar.JCalendar;

import net.miginfocom.swing.MigLayout;

public class BiesUI extends JFrame {
	private JTextField tfEsOut;
	private JTextField tfBiOut;
	private JTextField tfEsUrlLoc;
	private JTextField tfEsUrlR;
	private JTextField tfEsUrlWLoc;
	private JTextField tfEsUrlYear;
	private JTextField tfBiUrlLoc;
	private JTextField tfBiUrlR;
	private JTextField tfBiUrlWLoc;
	private JTextField tfBiUrlYear;
	private JTextField tfBiUrlBiCode;
	private JTextPane tpLog;
	private JCheckBox checkEs,checkBi,checkNT;
	private Bies wtt;
	private JCalendar calMemorialStart,calMemorialEnd;
	private JTextField tfPid1;
	private JTextField tfPid2;
	private GuiWorker worker;
	private JTextField _textField;
	private JTextField _tfStartBook;
	private JTextField _tfEndBook;
	
	public BiesUI() {
		wtt = new Bies();
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		setBounds(100, 100, 800, 600);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[grow][][grow][grow][]", "[][][][][grow][][][][][grow][][][grow][grow]"));
		
		checkEs = new JCheckBox("");
		panel.add(checkEs, "cell 1 1");
		
		JLabel lblEsOutput = new JLabel("ES Output");
		panel.add(lblEsOutput, "flowx,cell 2 1");
		
		tfEsOut = new JTextField();
		panel.add(tfEsOut, "cell 2 2 2 1,growx");
		tfEsOut.setColumns(10);
		
		JButton btnEsBrowse = new JButton("Browse");
		btnEsBrowse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setPath(tfEsOut);
			}
		});
		panel.add(btnEsBrowse, "cell 4 2");
		
		JPanel panelEsUrl = new JPanel();
		panel.add(panelEsUrl, "cell 2 4 3 1,grow");
		panelEsUrl.setLayout(new MigLayout("", "[]", "[]"));
		
		JLabel lblHttpwoljworg = new JLabel("http://wol.jw.org/");
		panelEsUrl.add(lblHttpwoljworg, "flowx,cell 0 0");
		
		tfEsUrlLoc = new JTextField();
		tfEsUrlLoc.setToolTipText("locale");
		panelEsUrl.add(tfEsUrlLoc, "cell 0 0");
		tfEsUrlLoc.setColumns(10);
		
		JLabel lblwoldtr = new JLabel("/wol/dt/r");
		panelEsUrl.add(lblwoldtr, "cell 0 0");
		
		tfEsUrlR = new JTextField();
		tfEsUrlR.setToolTipText("rValue");
		panelEsUrl.add(tfEsUrlR, "cell 0 0");
		tfEsUrlR.setColumns(10);
		
		JLabel lbllp = new JLabel("/lp-");
		panelEsUrl.add(lbllp, "cell 0 0");
		
		tfEsUrlWLoc = new JTextField();
		tfEsUrlWLoc.setToolTipText("wLocale");
		panelEsUrl.add(tfEsUrlWLoc, "cell 0 0");
		tfEsUrlWLoc.setColumns(10);
		
		JLabel label_1 = new JLabel("/");
		panelEsUrl.add(label_1, "cell 0 0");
		
		tfEsUrlYear = new JTextField();
		tfEsUrlYear.setToolTipText("year");
		tfEsUrlYear.setText(Calendar.getInstance().get(Calendar.YEAR)+"");
		panelEsUrl.add(tfEsUrlYear, "cell 0 0");
		tfEsUrlYear.setColumns(10);
		
		JLabel lblPid = new JLabel("pid1");
		panel.add(lblPid, "flowx,cell 2 5");
		
		JLabel lblMemorialStart = new JLabel("Memorial Start");
		panel.add(lblMemorialStart, "flowx,cell 2 6");
		
		checkBi = new JCheckBox("");
		panel.add(checkBi, "cell 1 7");
		
		JLabel label = new JLabel("Bi Output");
		panel.add(label, "flowx,cell 2 7");
		
		tfBiOut = new JTextField();
		panel.add(tfBiOut, "cell 2 8 2 1,growx");
		tfBiOut.setColumns(10);
		
		JButton btnBiBrowse = new JButton("Browse");
		btnBiBrowse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setPath(tfBiOut);
			}
		});
		panel.add(btnBiBrowse, "cell 4 8");
		
		JPanel panelBiUrl = new JPanel();
		panel.add(panelBiUrl, "cell 2 9 3 1,grow");
		panelBiUrl.setLayout(new MigLayout("", "[]", "[]"));
		
		JLabel label_2 = new JLabel("http://wol.jw.org/");
		panelBiUrl.add(label_2, "flowx,cell 0 0,alignx trailing");
		
		tfBiUrlLoc = new JTextField();
		tfBiUrlLoc.setToolTipText("locale");
		tfBiUrlLoc.setColumns(10);
		panelBiUrl.add(tfBiUrlLoc, "cell 0 0,growx");
		
		JLabel lblwolbr = new JLabel("/wol/b/r");
		panelBiUrl.add(lblwolbr, "cell 0 0");
		
		tfBiUrlR = new JTextField();
		tfBiUrlR.setToolTipText("rValue");
		tfBiUrlR.setColumns(10);
		panelBiUrl.add(tfBiUrlR, "cell 0 0");
		
		JLabel label_3 = new JLabel("/lp-");
		panelBiUrl.add(label_3, "cell 0 0");
		
		tfBiUrlWLoc = new JTextField();
		tfBiUrlWLoc.setToolTipText("wLocale");
		tfBiUrlWLoc.setColumns(10);
		panelBiUrl.add(tfBiUrlWLoc, "cell 0 0");
		
		JLabel label_4 = new JLabel("/");
		panelBiUrl.add(label_4, "cell 0 0 14 1");
		
		tfBiUrlBiCode = new JTextField();
		tfBiUrlBiCode.setToolTipText("Bi Code");
		tfBiUrlBiCode.setColumns(10);
		panelBiUrl.add(tfBiUrlBiCode, "cell 0 0");
		
		JLabel label_5 = new JLabel("/");
		panelBiUrl.add(label_5, "cell 0 0");
		
		tfBiUrlYear = new JTextField();
		tfBiUrlYear.setToolTipText("year");
		tfBiUrlYear.setText(Calendar.getInstance().get(Calendar.YEAR)+"");
		tfBiUrlYear.setColumns(10);
		panelBiUrl.add(tfBiUrlYear, "cell 0 0");
		
		_tfStartBook = new JTextField();
		_tfStartBook.setText("1");
		panel.add(_tfStartBook, "flowx,cell 2 10");
		_tfStartBook.setColumns(10);
		
		checkNT = new JCheckBox("New Testament Only");
		checkNT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkNT.isSelected())_tfStartBook.setText("40");
				else _tfStartBook.setText("1");
			}
		});
		panel.add(checkNT, "flowx,cell 1 11 2 1");
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "cell 0 12 5 1,grow");
		
		tpLog = new JTextPane();
		scrollPane.setViewportView(tpLog);
		
		calMemorialStart = new JCalendar();
		panel.add(calMemorialStart, "cell 2 6");
		
		JLabel lblMemorialEnd = new JLabel("Memorial End");
		panel.add(lblMemorialEnd, "cell 2 6");
		
		calMemorialEnd = new JCalendar();
		panel.add(calMemorialEnd, "cell 2 6");
		
		tfPid1 = new JTextField();
		panel.add(tfPid1, "cell 2 5");
		tfPid1.setColumns(10);
		
		JLabel lblPid_1 = new JLabel("pid2");
		panel.add(lblPid_1, "cell 2 5");
		
		tfPid2 = new JTextField();
		panel.add(tfPid2, "cell 2 5");
		tfPid2.setColumns(10);
		
		JButton btnSaveEs = new JButton("Save");
		btnSaveEs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					saveEsSettings();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		panel.add(btnSaveEs, "cell 2 1");
		
		JButton btnLoadEs = new JButton("Load");
		btnLoadEs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					loadEsSettings();
				} catch (SAXException | IOException
						| ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnLoadEs, "cell 2 1");
		
		JButton btnSaveBi = new JButton("Save");
		btnSaveBi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					saveBiSettings();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnSaveBi, "cell 2 7");
		
		JButton btnLoadBi = new JButton("Load");
		btnLoadBi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					loadBiSettings();
				} catch (SAXException | IOException
						| ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnLoadBi, "cell 2 7");
		
		_tfEndBook = new JTextField();
		_tfEndBook.setText("66");
		panel.add(_tfEndBook, "cell 2 10");
		_tfEndBook.setColumns(10);
		
		JPanel southPanel = new JPanel();
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiWorker work = new GuiWorker();
				setWorker(work);
				work.execute();
			}
		});
		southPanel.add(btnGenerate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getWorker().cancel(true);
			}
		});
		southPanel.add(btnCancel);
		
		
	}
	
	private void saveEsSettings() throws IOException {
		String line = "<setting>"
				+ "<output>"+tfEsOut.getText()+"</output>"
				+ "<locale>"+tfEsUrlLoc.getText()+"</locale>"
				+ "<r>"+tfEsUrlR.getText()+"</r>"
				+ "<lp>"+tfEsUrlWLoc.getText()+"</lp>"
				+ "<year>"+tfEsUrlYear.getText()+"</year>"
				+ "<pid1>"+tfPid1.getText()+"</pid1>"
				+ "<pid2>"+tfPid2.getText()+"</pid2>"
				+ "<memstart>"+calMemorialStart.getCalendar().getTimeInMillis()+"</memstart>"
				+ "<memend>"+calMemorialEnd.getCalendar().getTimeInMillis()+"</memend>"
				+ "</setting>";
		JFileChooser chooser = new JFileChooser("D:/download/temp");
		int response = chooser.showDialog(null, "Save");
		if(response == chooser.APPROVE_OPTION)IOUtil.overwriteFile(line, chooser.getSelectedFile().getAbsolutePath());
	}
	
	private void saveBiSettings() throws IOException {
		String line = "<setting>"
				+ "<output>"+tfBiOut.getText()+"</output>"
				+ "<locale>"+tfBiUrlLoc.getText()+"</locale>"
				+ "<r>"+tfBiUrlR.getText()+"</r>"
				+ "<lp>"+tfBiUrlWLoc.getText()+"</lp>"
				+ "<year>"+tfBiUrlYear.getText()+"</year>"
				+ "<bi>"+tfBiUrlBiCode.getText()+"</bi>"
				+ "</setting>";
		JFileChooser chooser = new JFileChooser("D:/download/temp");
		int response = chooser.showDialog(null, "Save");
		if(response == chooser.APPROVE_OPTION)IOUtil.overwriteFile(line, chooser.getSelectedFile().getAbsolutePath());
	}
	
	private void loadEsSettings() throws SAXException, IOException, ParserConfigurationException {
		String path = "";
		JFileChooser chooser = new JFileChooser("D:/download/temp");
		int response = chooser.showDialog(null, "Select");
		if(response == chooser.APPROVE_OPTION)path = chooser.getSelectedFile().getAbsolutePath();
		File file = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		NodeList nodes = doc.getElementsByTagName("setting");
		Node node = nodes.item(0);
		Element element = (Element)node;
		tfEsOut.setText(element.getElementsByTagName("output").item(0).getTextContent());
		tfEsUrlLoc.setText(element.getElementsByTagName("locale").item(0).getTextContent());
		tfEsUrlR.setText(element.getElementsByTagName("r").item(0).getTextContent());
		tfEsUrlWLoc.setText(element.getElementsByTagName("lp").item(0).getTextContent());
		tfEsUrlYear.setText(element.getElementsByTagName("year").item(0).getTextContent());
		tfPid1.setText(element.getElementsByTagName("pid1").item(0).getTextContent());
		tfPid2.setText(element.getElementsByTagName("pid2").item(0).getTextContent());
		Long startTime = Long.parseLong(element.getElementsByTagName("memstart").item(0).getTextContent());
		Long endTime = Long.parseLong(element.getElementsByTagName("memend").item(0).getTextContent());
		Calendar calStart = Calendar.getInstance();
		calStart.setTimeInMillis(startTime);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTimeInMillis(endTime);
		calMemorialStart.setCalendar(calStart);
		calMemorialEnd.setCalendar(calEnd);
	}
	
	private void loadBiSettings() throws SAXException, IOException, ParserConfigurationException {
		String path = "";
		JFileChooser chooser = new JFileChooser("D:/download/temp");
		int response = chooser.showDialog(null, "Select");
		if(response == chooser.APPROVE_OPTION)path = chooser.getSelectedFile().getAbsolutePath();
		File file = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		NodeList nodes = doc.getElementsByTagName("setting");
		Node node = nodes.item(0);
		Element element = (Element)node;
		tfBiOut.setText(element.getElementsByTagName("output").item(0).getTextContent());
		tfBiUrlLoc.setText(element.getElementsByTagName("locale").item(0).getTextContent());
		tfBiUrlR.setText(element.getElementsByTagName("r").item(0).getTextContent());
		tfBiUrlWLoc.setText(element.getElementsByTagName("lp").item(0).getTextContent());
		tfBiUrlBiCode.setText(element.getElementsByTagName("bi").item(0).getTextContent());
		tfBiUrlYear.setText(element.getElementsByTagName("year").item(0).getTextContent());
	}
	
	private void setPath(JTextField tf){
		JFileChooser chooser = new JFileChooser();
		int response = chooser.showDialog(null, "OK");
		if(response == chooser.APPROVE_OPTION)tf.setText(chooser.getSelectedFile().getAbsolutePath());
	}
	
	private void generateEs(){
		Integer year = new Integer(tfEsUrlYear.getText());
		String outFile = tfEsOut.getText();
		String locale = tfEsUrlLoc.getText();
		String wLocale = tfEsUrlWLoc.getText();
		String rVer = tfEsUrlR.getText();
		Calendar start = Calendar.getInstance();
		start.set(year, 0, 1);
		Calendar memStart = calMemorialStart.getCalendar();
		Calendar memEnd = calMemorialEnd.getCalendar();
		Long endMilis = memEnd.getTimeInMillis()+Long.valueOf("86400000");
		memEnd.setTimeInMillis(endMilis);
		Integer pid1 = Integer.valueOf(tfPid1.getText());
		Integer pid2 = Integer.valueOf(tfPid2.getText());
		wtt.generateEsFromWeb(1, start, 365, 3, false, outFile, locale, wLocale, rVer, tpLog);
		Long nDays = (memEnd.getTimeInMillis() - memStart.getTimeInMillis())/86400000;
		wtt.generateEsFromWeb(pid1, memStart, Integer.valueOf(nDays+""), 4, true, outFile, locale, wLocale, rVer, tpLog);
		Integer nDays2 = memEnd.getMaximum(Calendar.DAY_OF_MONTH) - memEnd.get(Calendar.DAY_OF_MONTH) + 1;
		wtt.generateEsFromWeb(pid2, memEnd, nDays2, 3, false, outFile, locale, wLocale, rVer, tpLog);
	}
	
	private void generateBi(){
		String year = tfBiUrlYear.getText();
		String outFile = tfBiOut.getText();
		String locale = tfBiUrlLoc.getText();
		String wLocale = tfBiUrlWLoc.getText();
		String rVer = tfBiUrlR.getText();
		String biCode = tfBiUrlBiCode.getText();
		int start = Integer.parseInt(_tfStartBook.getText());
		int end = Integer.parseInt(_tfEndBook.getText());
		wtt.generateBiFromWeb(outFile, locale, biCode, wLocale, rVer, year, tpLog,start,end);
	}
	
	private class GuiWorker extends SwingWorker<Integer, String>{

		@Override
		protected Integer doInBackground() throws Exception {
			tpLog.setText("");
			if (checkEs.isSelected()) generateEs();
			if (checkBi.isSelected()) generateBi();
			done();
			return null;
		}
		
	}

	public GuiWorker getWorker() {
		return worker;
	}

	public void setWorker(GuiWorker worker) {
		this.worker = worker;
	}

}
