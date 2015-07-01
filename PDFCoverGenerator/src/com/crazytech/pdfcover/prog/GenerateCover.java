package com.crazytech.pdfcover.prog;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;

import org.apache.pdfbox.io.RandomAccess;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import com.crazytech.exception.LogException;
import com.crazytech.io.IOUtil;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public class GenerateCover {
	private String _srcDir;
	
	public GenerateCover(String filename, Boolean usePDFBox, Integer pageNo) {
		super();
		_srcDir = System.getProperty("user.dir");
		try {
			if (usePDFBox) convertToImagePDFBox(getPDDoc(_srcDir, filename), filename,pageNo);
			else convertPageToImage(getPDF(_srcDir, filename), filename, pageNo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogException.log(e,filename);
		}
	}
	
	private PDDocument getPDDoc(String dir, String filename) throws IOException {
		File pdfFile = new File(filename);
		return PDDocument.loadNonSeq(pdfFile, null);
	}
	
	private PDFFile getPDF (String dir, String filename) throws IOException{
		File pdfFile = new File(filename);
		RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
		FileChannel channel = raf.getChannel();
		ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
		PDFFile doc = new PDFFile(buf);
		raf.close();
		return doc;
	}
	
	private void convertPageToImage (PDFFile doc, String filename, Integer pageNo) throws IOException{
		PDFPage page = doc.getPage(pageNo);
		Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(),
                (int) page.getBBox().getHeight());
		BufferedImage bufferedImage = new BufferedImage(rect.width, rect.height,
		                 BufferedImage.TYPE_INT_RGB);
		
		Image image = page.getImage(rect.width, rect.height,    // width & height
		           rect,                       // clip rect
		           null,                       // null for the ImageObserver
		           true,                       // fill background with white
		           true                        // block until drawing is done
		);
		Graphics2D bufImageGraphics = bufferedImage.createGraphics();
		bufImageGraphics.drawImage(image, 0, 0, null);
		ImageIO.write(bufferedImage, "jpg", new File( filename.split(".pdf")[0]+".jpg"));
	}
	
	private void convertToImagePDFBox (PDDocument pdDoc, String filename, Integer pageNo) throws IOException {
		PDPage page = (PDPage) pdDoc.getDocumentCatalog().getAllPages().get(pageNo);
		BufferedImage bufferedImage = page.convertToImage(BufferedImage.TYPE_INT_RGB, 100);
		ImageIO.write(bufferedImage, "jpg", new File( filename.split(".pdf")[0]+".jpg"));
		pdDoc.close();
	}
	
	
}
