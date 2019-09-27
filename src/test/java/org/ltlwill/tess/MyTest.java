package org.ltlwill.tess;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageIOHelper;

import org.junit.Test;
import org.ltlwill.tess.util.ClassPathResourceUtils;
import org.ltlwill.tess.util.SimpleRequestHelper;

public class MyTest {
	
	private static final String DEFAULT_TESS_DATA_PATH = "E:\\work\\eclipse4AliMavenWorkSpace\\tess\\tessdata";

	@Test
	public void test01() throws Exception {
		String url = "http://47.244.185.75:8777/iupload/10102219 (19).jpg";
		// BufferedImage image =
		// ImageIO.read(URI.create(URLEncoder.encode(url,"UTF-8")).toURL());
		byte[] bytes = SimpleRequestHelper.doGet(url, null);
		doOCR(bytes);
	}

	@Test
	public void test02() throws Exception {
		byte[] bytes = ClassPathResourceUtils
				.getResounceAsByte("testdatas/eurotext.bmp");
		doOCR(bytes);
	}
	@Test
	public void test03() throws Exception {
		InputStream is= null;
		try{
			is = this.getClass().getClassLoader()
					.getResourceAsStream("testdatas/eurotext.pdf");
			BufferedImage bImage = ImageIO.read(is);
			List<IIOImage> images = ImageIOHelper.getIIOImageList(bImage);
			doOCR(images);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(is != null){
				is.close();
			}
		}
		
	}
	@Test
	public void test04() throws Exception {
		String path = "E:\\work\\eclipse4AliMavenWorkSpace\\tess\\src\\main\\java\\resources\\testdatas\\eurotext.pdf";
		InputStream is= null;
		try{
			File imageFile = new File(path);
	        List<IIOImage> imageList = ImageIOHelper.getIIOImageList(imageFile);
	        doOCR(imageList); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(is != null){
				is.close();
			}
		}
		
	}

	private void doOCR(byte[] bytes) throws Exception {
		ByteArrayInputStream is = null;
		try {
			is = new ByteArrayInputStream(bytes);
			BufferedImage image = ImageIO.read(is);
			ITesseract instance = new Tesseract();
			instance.setDatapath(DEFAULT_TESS_DATA_PATH);
			String result = instance.doOCR(image);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {

				}
			}
		}
	}
	
	private void doOCR(List<IIOImage> images) throws Exception {
			ITesseract instance = new Tesseract();
			instance.setDatapath(DEFAULT_TESS_DATA_PATH);
			String result = instance.doOCR(images, null);
			System.out.println(result);
	}
}
