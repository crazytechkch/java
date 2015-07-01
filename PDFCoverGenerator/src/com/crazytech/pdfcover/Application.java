package com.crazytech.pdfcover;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.crazytech.test.bies.BiesUI;

public class Application{

	private JFrame _frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final Application window = new Application();
					window._frame.setVisible(true);
				}  catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
