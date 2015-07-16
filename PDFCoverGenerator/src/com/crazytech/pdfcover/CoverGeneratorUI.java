package com.crazytech.pdfcover;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import com.crazytech.io.IOUtil;
import com.crazytech.pdfcover.prog.GenerateCover;
import com.crazytech.pdfcover.prog.GenerateJson;
import com.crazytech.pdfcover.prog.GenerateSQL;
import com.crazytech.pdfcover.prog.Pubs;
import com.crazytech.swing.texteditor.DragDropTextEditor;
import com.crazytech.swing.texteditor.TextEditor;

import net.miginfocom.swing.MigLayout;

public class CoverGeneratorUI extends JFrame implements ClipboardOwner{

	private JPanel _contentPane;
	private JTextArea _tfUrls;
	private DragDropTextEditor teUrl;
	private JPanel _panel;
	private JCheckBox _cbCover;
	private JCheckBox _cbSQL;
	private JCheckBox _cbJson;
	private JCheckBox _cbPdfbox;
	private JTextArea _tfPage;
	private JTextField _tfTime;
	private JPanel _panel_1;
	private JLabel _label;
	private JTextField _tfFilename;
	private JScrollPane _scrollPane;
	private JScrollPane _scrollPane_1;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CoverGeneratorUI frame = new CoverGeneratorUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	/**
	 * Create the frame.
	 * @throws InterruptedException 
	 */
	public CoverGeneratorUI() throws InterruptedException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		_contentPane.setLayout(new MigLayout("", "[221px,grow][221px]", "[grow][273px][grow][250px][grow]"));
		init();
		//detectClipboardChanges();
		ScheduledExecutorService schEx = Executors.newSingleThreadScheduledExecutor();
		Runnable updateTime = new Runnable() {
			@Override
			public void run() {
				
				_tfTime.setText(new Date().getTime()+"");
			}
		};
		schEx.scheduleAtFixedRate(updateTime, 0, 3, TimeUnit.SECONDS);
		setContentPane(_contentPane);
	}
	
	private void init() {
		_scrollPane = new JScrollPane();
		teUrl = new DragDropTextEditor("PDF URL", new Locale("en"),"D:/download/temp");
		_contentPane.add(teUrl, "cell 0 0 2 2,grow");
		
		_panel = new JPanel();
		_contentPane.add(_panel, "cell 0 4 2 1,grow");
		_panel.setLayout(new MigLayout("", "[1px,grow][33px][83px,grow][51px][][grow]", "[1px,grow][grow][23px][23px][23px]"));
		
		_cbCover = new JCheckBox("Cover");
		_cbCover.setSelected(false);
		_panel.add(_cbCover, "cell 0 0,alignx left,aligny center");
		
		_cbSQL = new JCheckBox("SQL");
		_cbSQL.setSelected(true);
		_panel.add(_cbSQL, "cell 2 0,alignx left,aligny center");
		
		_cbJson = new JCheckBox("JSON");
		_cbJson.setSelected(false);
		_panel.add(_cbJson, "flowx,cell 3 0,alignx left,aligny center");
		
		_cbPdfbox = new JCheckBox("use PDFBox");
		_panel.add(_cbPdfbox, "cell 4 0,alignx left,aligny center");
		
		_tfPage = new JTextArea();
		_tfPage.setToolTipText("Page No.");
		_tfPage.setText("1");
		_panel.add(_tfPage, "cell 5 0,grow");
		
		_tfTime = new JTextField();
		_tfTime.setToolTipText("Time in Long");
		_panel.add(_tfTime, "cell 0 1 2 1,growx");
		_tfTime.setColumns(10);
		_tfTime.setText(new Date().getTime()+"");
		
		_panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) _panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		_panel.add(_panel_1, "cell 2 1 4 1,grow");
		
		_label = new JLabel("Filename");
		_panel_1.add(_label);
		
		_tfFilename = new JTextField();
		_tfFilename.setText("crazypdf");
		_panel_1.add(_tfFilename);
		_tfFilename.setColumns(10);
		
		JButton btnGenerate = new JButton("GENERATE");
		_panel.add(btnGenerate, "cell 0 3 6 1,growx,aligny center");
		btnGenerate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					startConvert();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	private void startConvert() throws IOException {
		String tffilename = _tfFilename.getText();
		String src = System.getProperty("user.dir");
		File dir = new File(src);
		String[] urls = teUrl.getText().replaceAll(";", "").split("\n");
		String imgUrl = teUrl.getText()/*.split("\n")*/;
		
		/*if (_cbJson.isSelected()) new File(tffilename+".json").delete();
		if (_cbSQL.isSelected()) new File(tffilename+".sql").delete();
		for (File file : dir.listFiles()) {
			String filename = file.getName();
			String filename2 = filename.replace(".pdf", "");
			for (String urlStr : urls) {
				if (urlStr.contains(filename)) {
					url = urlStr;
					break;
				}
			}
			for (String urlStr : imgUrls) {
				if (urlStr.contains(filename2)) {
					imgurl = urlStr;
					break;
				}
			}
			if (filename.endsWith(".pdf")) {
				if (_cbCover.isSelected())new GenerateCover(filename,_cbPdfbox.isSelected(),Integer.valueOf(_tfPage.getText())-1);
				if (_cbJson.isSelected())new GenerateJson(filename,url).generate(tffilename);
				if (_cbSQL.isSelected())new GenerateSQL(filename,url,imgurl).generate(tffilename);
			}
		}
		*/
		if (_cbSQL.isSelected()){
			String content = "";
			/*for (String string : urls) {
				String filename = string.substring(string.lastIndexOf("/")+1, string.length());
				System.out.println(filename);
				for (String string2 : imgUrls) {
					if (string2.contains(filename.replace(".pdf", ""))) {
						imgurl = string2;
						break;
					}
				}
				content += new GenerateSQL(filename,string,imgurl).generate();
			}*/
			for (String key : Pubs.LocaleMap().keySet()) {
				
			}
			IOUtil.overwriteFile(content, tffilename+".sql","UTF-8");
			//IOUtil.writeFile("update miniwl.app_info set db_refresh = "+new Date().getTime()+";", tffilename+".sql","UTF-8");
		}
	}
	/*
	protected void detectClipboardChanges() throws InterruptedException {
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.addFlavorListener(new FlavorListener() {
			
			@Override
			public void flavorsChanged(FlavorEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e.getSource()+" "+e.toString());
				Transferable trans = clipboard.getContents(null);
				if (trans!=null && trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
					try {
						String clipText = (String)trans.getTransferData(DataFlavor.stringFlavor);
						if (clipText.startsWith("http://")&&clipText.endsWith(".pdf")) {
							new GuiWorker(teUrl.getText()+clipText+"\n").execute();
							//_tfUrls.setText(_tfUrls.getText()+clipText+";\n");
						} else if (clipText.startsWith("http://")&&clipText.endsWith(".jpg")) {
							clipText = clipText.replace("_xs", "_md").replace("www.", "assets.");
							new ImageGrabWorker(teImgUrl.getText()+clipText+"\n").execute();
							//_tfUrls.setText(_tfUrls.getText()+clipText+";\n");
						}
						System.out.println(clipText);
					} catch (UnsupportedFlavorException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}*/

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// TODO Auto-generated method stub
		
	}

	public JTextField getTfTime() {
		return _tfTime;
	}
	
	private class GuiWorker extends SwingWorker<String, String>{
		private String strParam;
		
		public GuiWorker(String str) {
			super();
			strParam = str;
		}



		@Override
		protected String doInBackground() throws Exception {
			teUrl.setText(strParam);
			return null;
		}
		
	}
	
	private class ImageGrabWorker extends SwingWorker<String, String>{
		private String url;
		
		public ImageGrabWorker(String url) {
			this.url = url;
		}
		
		@Override
		protected String doInBackground() throws Exception {
			//teImgUrl.setText(url);
			return null;
		}
	}
}
