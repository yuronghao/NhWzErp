package com.emi.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.ImageIcon;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.Sides;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class ImagePrint {
	private static final Integer WIDTH=1200;
	private static final Integer HEIGHT=1200;
    private static final String FONT_FAMILY = "宋体";
    private static final int FONT_SIZE = 7;
	public static  void print(File file) throws WriterException, IOException, PrintException {
		HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		pras.add(OrientationRequested.PORTRAIT);
		pras.add(MediaSizeName.INVOICE);
		DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
		PrintService defaultService = PrintServiceLookup
				.lookupDefaultPrintService();
		DocPrintJob job = defaultService.createPrintJob(); // 创建打印作业
		FileInputStream fis = new FileInputStream(file); // 构造待打印的文件流
		DocAttributeSet das = new HashDocAttributeSet();
		Doc doc = new SimpleDoc(fis, flavor, das);
		job.print(doc, pras);
	}
	
	/**
	 * 打印标签
	 * @param printName
	 * @param file
	 * @throws WriterException
	 * @throws IOException
	 * @throws PrintException
	 */
	public static void printMask(String printName,File file) throws WriterException, IOException, PrintException {
		HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		pras.add(OrientationRequested.PORTRAIT);
		pras.add(MediaSizeName.INVOICE);
		DocFlavor flavor = DocFlavor.INPUT_STREAM.GIF;
		PrintService defaultService = getPrintServiceByName(printName);
		if(defaultService!=null){
			DocPrintJob job = defaultService.createPrintJob(); // 创建打印作业
			FileInputStream fis = new FileInputStream(file); // 构造待打印的文件流
			DocAttributeSet das = new HashDocAttributeSet();
			Doc doc = new SimpleDoc(fis, flavor, das);
			job.print(doc, pras);
		}
	}
	
	public static PrintService getPrintServiceByName(String printName){
		HashPrintRequestAttributeSet pras=new HashPrintRequestAttributeSet();
		   DocFlavor flavor=DocFlavor.INPUT_STREAM.AUTOSENSE;
		   PrintService[] printService=PrintServiceLookup.lookupPrintServices(flavor, pras);
		   List<String> retList=new ArrayList<String>();
		   if(printService!=null){
			   for(PrintService p:printService){
				   if(printName.equals(p.getName())){
					   	return p;
				   }
			   }
		   }
		return null;
	}
	
	
	public static List<String> getAllPrintService(){
		 HashPrintRequestAttributeSet pras=new HashPrintRequestAttributeSet();
		   DocFlavor flavor=DocFlavor.INPUT_STREAM.AUTOSENSE;
//		   DocFlavor flavor=DocFlavor.INPUT_STREAM.GIF;
		   PrintService[] printService=PrintServiceLookup.lookupPrintServices(flavor, pras);
		   List<String> retList=new ArrayList<String>();
		   if(printService!=null){
			   for(PrintService p:printService){
				   retList.add(p.getName());  
			   }
		   }
		   return retList;

	}
	/**二维码打印*/
	public Boolean printQRCode(String code,String filePath) {
		try {
			String format = "png";
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(code,
					BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
			 File outputFile = new File(filePath);
			MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
			
			print(outputFile);
			return true;
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PrintException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	/**一维码打印*/
	public static Boolean printDimensionalCode(String code,String filePath) {
	      try {
	    	  
    	    JBarcode localJBarcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(), BaseLineTextPainter.getInstance());
			BufferedImage localBufferedImage = localJBarcode.createBarcode(code);
			//localJBarcode.setEncoder(Code39Encoder.getInstance());
			localJBarcode.setPainter(WidthCodedPainter.getInstance());
			//localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
			localJBarcode.setShowCheckDigit(false);
			localJBarcode.setBarHeight(12);
			localJBarcode.setTextPainter(CustomTextPainter.getInstance());
		    FileOutputStream localFileOutputStream = new FileOutputStream(filePath);
		    ImageUtil.encodeAndWrite(localBufferedImage, "png", localFileOutputStream,ImageUtil.DEFAULT_DPI, ImageUtil.DEFAULT_DPI);
		    localFileOutputStream.close();
		   // print(new File(filePath));
		    return true;
		} catch (InvalidAtributeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} /*catch (WriterException e) {
			e.printStackTrace();
		} catch (PrintException e) {
			e.printStackTrace();
		}*/
	      return false;
	}
	 /**
     * 自定义的 TextPainter， 允许定义字体，大小等等。
     */
    static class CustomTextPainter implements TextPainter {
        public static CustomTextPainter instance = new CustomTextPainter();
 
        public static CustomTextPainter getInstance() {
            return instance;
        }
 
        @Override
        public void paintText(BufferedImage barCodeImage, String text, int width) {
            Graphics g2d = barCodeImage.getGraphics();
 
            Font font = new Font(FONT_FAMILY, Font.ROMAN_BASELINE, FONT_SIZE * width);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int height = fm.getHeight();
            int center = (barCodeImage.getWidth() - fm.stringWidth(text)) / 2;
 
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, barCodeImage.getWidth(), barCodeImage.getHeight() * 1 / 20);
            g2d.fillRect(0, barCodeImage.getHeight() - (height * 9 / 10), barCodeImage.getWidth(), (height * 9 / 10));
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, center, barCodeImage.getHeight() - (height / 10) - 2);
        }
    }
	
	public static void buildMask(String p1,String p2,String name,String size,String weight,String maskPath){
		try {
			   // 读取第一张图片
			   File fileOne = new File(p1);
			   BufferedImage ImageOne = ImageIO.read(fileOne);
			   int width = ImageOne.getWidth();// 图片宽度
			   System.out.println("width:"+width);
			   int height = ImageOne.getHeight();// 图片高度
			   // 从图片中读取RGB
			    int[] ImageArrayOne = new int[width * height];
			    ImageArrayOne = ImageOne.getRGB(0, 0, width, height, ImageArrayOne,0, width);
			   // 对第二张图片做相同的处理
			   File fileTwo = new File(p2);
			   BufferedImage ImageTwo = ImageIO.read(fileTwo);
			   int width2 = ImageTwo.getWidth();// 图片宽度
			   System.out.println("width2:"+width2);
			   int height2 = ImageTwo.getHeight();// 图片高度
			   int   width3=width2+50;
			   int[] ImageArrayTwo = new int[width3 * height2];
			   ImageArrayTwo = ImageTwo.getRGB(0, 0, width2, height2, ImageArrayTwo,0, width2);
			
			   // 生成新图片
			   // BufferedImage ImageNew = new BufferedImage(width * 2, height,
			   // BufferedImage.TYPE_INT_RGB);
			   BufferedImage ImageNew = new BufferedImage(width3, height+height2,BufferedImage.TYPE_4BYTE_ABGR);
			   ImageNew.setRGB(width3-width, 0, width, height, ImageArrayOne, 0, width);// 设置左半部分的RGB
			   ImageNew.setRGB(25, height, width2, height2, ImageArrayTwo, 0, width2);// 设置右半部分的RGB
	      	    Graphics g = ImageNew.getGraphics();//
			   	g.fillRect(10, 0, width3-width, height);
				g.setColor(Color.black);
				Font mFont = new Font("宋体", Font.PLAIN, 12);
				g.setFont(mFont);
				String text = "名称:"+name;
				g.drawString(text, width3-width-120, 20);
				String text2 = "规格:"+size;
				g.drawString(text2, width3-width-120, 40);
				String text3 = "重量:"+weight+"kg";
				g.drawString(text3, width3-width-120, 60);
				File outFile = new File(maskPath);
				ImageIO.write(ImageNew, "png", outFile);// 写图片
			   }catch (Exception e) {
			      e.printStackTrace();
			   }
	}
	
	
		//public static void main(String[] args){
			//System.out.print(getAllPrintService());
			//QrCodeUtil.createQrcode("http://www.baidu.com", "G:/qrcode/test.png",100,100);12345678901234567890
			//ImagePrint test=new ImagePrint();
			//File outputFile = new File("C:/Users/Administrator/Desktop/092I4E50-6.jpg");
		
				//try {
				//test.print(outputFile);
					//test.printMask("\\\\Pc-201506150823\\GPUSB002",outputFile);
			//} catch (WriterException e) {
				// TODO 自动生成的 catch 块
				//.printStackTrace();
			//} catch (IOException e) {
				// TODO 自动生成的 catch 块
				//e.printStackTrace();
			//} catch (PrintException e) {
				// TODO 自动生成的 catch 块
				//e.printStackTrace();
			//}
			//ImagePrint test=new ImagePrint();
			//test.printDimensionalCode("VBCD5678901234567890","F:/qrcode/barcode_s.png");
			//test2();
			/*String[] ss=new String[2];
			ss[0]="F:/qrcode/test.png";
			ss[1]="F:/qrcode/qrcode.png";
			merge(ss,"png","F:/qrcode/test3.png");*/
			//ImageUtils.scale2("F:/qrcode/qrcode.jpg", 80, 80, true);
			
			
	//	}
    /*public static void print(String path){   
    	ComThread.InitSTA();   
    	ActiveXComponent xl = new ActiveXComponent("Excel.Application");   
    	try {   
    	// System.out.println("version=" + xl.getProperty("Version"));   
    	//不打开文档   
    	Dispatch.put(xl, "Visible", new Variant(true));   
    	Dispatch workbooks = xl.getProperty("Workbooks").toDispatch();   
    	//打开文档   
    	Dispatch excel=Dispatch.call(workbooks,"Open",path).toDispatch();   
    	//开始打印   
    	Dispatch.get(excel,"PrintOut");   
    	} catch (Exception e) {   
    	e.printStackTrace();   
    	} finally {   
    	//始终释放资源   
    	ComThread.Release();   
    	}   
    	}*/ 
    
    public static void printExcel(String path,int copies,String printername){
		if(path.isEmpty()||copies<1){
			return;
		}
		//初始化COM线程
		ComThread.InitSTA();
		//新建Excel对象
		ActiveXComponent xl=new ActiveXComponent("Excel.Application");
		try { 
			System.out.println("Version=" + xl.getProperty("Version"));
			//设置是否显示打开Excel  
			Dispatch.put(xl, "Visible", new Variant(false));
			//打开具体的工作簿
			Dispatch workbooks = xl.getProperty("Workbooks").toDispatch(); 
			Dispatch excel=Dispatch.call(workbooks,"Open",path).toDispatch(); //System.getProperty("user.dir")+
			
			//设置打印属性并打印
		/*	Dispatch.callN(excel,"PrintOut",new Object[]{Variant.VT_MISSING, Variant.VT_MISSING, new Integer(copies),
					new Boolean(false),PRINT_NAMEVariant.VT_MISSING, new Boolean(true),Variant.VT_MISSING, ""});*/
			Dispatch.callN(excel,"PrintOut",new Object[]{Variant.VT_MISSING, Variant.VT_MISSING, new Integer(copies),
					new Boolean(false),printername, new Boolean(true),Variant.VT_MISSING, ""});
			
			//关闭文档
			//Dispatch.call(excel, "Close", new Variant(false));  
		} catch (Exception e) { 
			e.printStackTrace(); 
		} finally{
			//xl.invoke("Quit",new Variant[0]);
			//始终释放资源 
			ComThread.Release(); 
		} 
	}
    
    /*public static void getAllPrints(){
    	 HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet(); 
         DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;  
         //查找所有的可用的打印服务  
         PrintService[] printService = PrintServiceLookup.lookupPrintServices(flavor, pras);  
  
         for (int i =0; i<printService.length ;i++ )
         {
             System.out.println(printService[i].getName());
         }   
    }*/
    //public static void main(String[] args) {
    	/*    
    }
        FileInputStream psStream;    
        try {    
           psStream = new FileInputStream("file.ps");    
        } catch (FileNotFoundException ffne) {    
        }    
        if (psStream == null) {    
            return;    
        }    
        DocFlavor psInFormat = DocFlavor.INPUT_STREAM.POSTSCRIPT;    
        Doc myDoc = new SimpleDoc(psStream, psInFormat, null);      
        PrintRequestAttributeSet aset =     
                new HashPrintRequestAttributeSet();    
        aset.add(new Copies(5));    
     //   aset.add(MediaSize.A4);    
        aset.add(Sides.DUPLEX);    
        PrintService[] services =     
          PrintServiceLookup.lookupPrintServices(psInFormat, aset);    
        if(services.length<1){    
            throw new RuntimeException("找不到打印机");    
        }    
        if (services.length > 0) {    
           DocPrintJob job = services[0].createPrintJob();    
           try {    
                job.print(myDoc, aset);    
           } catch (PrintException pe) {}    
        }    
    
    */
    	//print("E:/test.xls");	
    	//getAllPrints();
    	//printExcel("C:/Users/Administrator/Desktop/abc.xls",1,"Microsoft XPS Document Writer");
    //}
}
