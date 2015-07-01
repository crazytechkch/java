package com.crazytech.xslt.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.xml.transform.TransformerException;

import res.locale.LangMan;
import res.locale.MyLangMan;

import com.crazytech.io.IOUtil;
import com.crazytech.swing.browser.SimpleSwingBrowser;
import com.crazytech.swing.texteditor.DragDropTextEditor;
import com.crazytech.swing.texteditor.TextEditor;
import com.crazytech.xslt.XSLT;

import javax.swing.JSplitPane;

import java.awt.FlowLayout;

public class Main {
	private JFrame mainframe;
	private DragDropTextEditor xmlText, xslText;
	private TextEditor outputText;
	private JTabbedPane tabbedPane;
	private JButton btnTransform;
	private SimpleSwingBrowser browser;
	private JMenu mnLang;
	private JMenuItem mntmLangEn, mntmLangZhS;
	private LangMan lang;
	private MyLangMan myLang;
	private JSplitPane splitPane;
	private JPanel rightHost;
	private JPanel _panel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.mainframe.setVisible(true);
					window.mainframe.setTitle("XSLT Transformer");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Locale locale = Locale.getDefault();
		lang = new LangMan(locale);
		myLang = new MyLangMan(locale);
		
		mainframe = new JFrame();
		mainframe.getContentPane().setLayout(new BorderLayout(0, 0));
		mainframe.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/res/logo.png")));
		mainframe.setSize(1024, 768);
		mainframe.setLocationRelativeTo(null);
		
		browser = new SimpleSwingBrowser(locale);
		
		outputText = new TextEditor(lang.getString("sourcecode"),locale);
		
		xmlText = new DragDropTextEditor("Drop XML Here",locale);
		GridBagConstraints gbc_xmlScrollPane = new GridBagConstraints();
		gbc_xmlScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_xmlScrollPane.fill = GridBagConstraints.BOTH;
		gbc_xmlScrollPane.gridx = 0;
		gbc_xmlScrollPane.gridy = 0;

		xslText = new DragDropTextEditor("Drop XSL Here",locale);
		GridBagConstraints gbc_xslScrollPane = new GridBagConstraints();
		gbc_xslScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_xslScrollPane.fill = GridBagConstraints.BOTH;
		gbc_xslScrollPane.gridx = 0;
		gbc_xslScrollPane.gridy = 1;
		
		JMenuBar menuBar = new JMenuBar();
		mainframe.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		mnLang = new JMenu(lang.getString("language"));
		mnLang.setMnemonic('l');
		menuBar.add(mnLang);
		
		mntmLangEn = new JMenuItem(lang.getString("lang_en"));
		mntmLangEn.addActionListener(mntmChangeLocaleListener("en"));
		mnLang.add(mntmLangEn);
		
		mntmLangZhS = new JMenuItem(lang.getString("lang_zh_s"));
		mntmLangZhS.addActionListener(mntmChangeLocaleListener("zh"));
		mnLang.add(mntmLangZhS);
		
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.8);
		mainframe.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		rightHost = new JPanel();
		splitPane.setRightComponent(rightHost);
		rightHost.setLayout(new BorderLayout(0, 0));
		
		JPanel rightPane = new JPanel();
		rightHost.add(rightPane);
//		rightPane.setPreferredSize(new Dimension(300, 700));
		GridBagLayout gbl_rightPane = new GridBagLayout();
		gbl_rightPane.columnWidths = new int[]{0, 0};
		gbl_rightPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_rightPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_rightPane.rowWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		rightPane.setLayout(gbl_rightPane);
		rightPane.add(xmlText, gbc_xmlScrollPane);
		rightPane.add(xslText, gbc_xslScrollPane);
		
		btnTransform = new JButton("TRANSFORM");
		btnTransform.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ev) {
				Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String strXml = xmlText.getText();
						String strXsl = xslText.getText();
						try {
							String strOutput = XSLT.transform(strXsl, strXml, null, IOUtil.DEFAULT_CHARSET);
							browser.loadContent(strOutput);
							outputText.setText(strOutput);
						} catch (TransformerException | IOException e) {
							// TODO Auto-generated catch block
							String errorMsg = myLang.getString("error")+"\n"+e.getLocalizedMessage();
							outputText.setText(errorMsg);
							browser.loadContent(errorMsg);
						} finally {
							tabbedPane.setSelectedIndex(0);
							
						}
						
					}
				};
				runnable.run();
			}
		});
		GridBagConstraints gbc_btnTransform = new GridBagConstraints();
		gbc_btnTransform.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnTransform.gridx = 0;
		gbc_btnTransform.gridy = 2;
		rightPane.add(btnTransform, gbc_btnTransform);
		
		_panel_1 = new JPanel();
		splitPane.setLeftComponent(_panel_1);
		_panel_1.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		_panel_1.add(tabbedPane);
		tabbedPane.addTab("HTML", null, browser.getContentPane(), null);
		
		
		JScrollPane outputPane = new JScrollPane();
		tabbedPane.addTab(lang.getString("sourcecode"), null, outputPane, null);
		outputPane.setViewportView(outputText);
	}
	
	private ActionListener mntmChangeLocaleListener(final String locale) {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLocale(locale);
			}
		};
		
	}
	
	private void changeLocale(String locale) {
		Locale loc = new Locale(locale);
		Locale.setDefault(loc);
		lang = new LangMan(loc);
		
		myLang = new MyLangMan(loc);
		mainframe.setTitle(myLang.getString("appname"));
		xmlText.setHint(myLang.getString("dropxml"));
		xslText.setHint(myLang.getString("dropxsl"));
		btnTransform.setText(myLang.getString("transform"));
		mnLang.setText(lang.getString("language"));
		mntmLangEn.setText(lang.getString("lang_en"));
		mntmLangZhS.setText(lang.getString("lang_zh_s"));
		
		outputText.setHint(lang.getString("sourcecode"));
		outputText.onLocaleChange(loc);
		xmlText.onLocaleChange(loc);
		xslText.onLocaleChange(loc);
		tabbedPane.setTitleAt(1, lang.getString("sourcecode"));
		setUILang(loc);
	}
	
	private void setUILang(Locale locale) {
		LangMan lang = new LangMan(locale);
		UIManager.put("FileChooser.openDialogTitleText",lang.getString("open"));
	}
	
}
