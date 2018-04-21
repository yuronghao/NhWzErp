package com.emi.common.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 关于文件上传下载用到的工具类
 * 
 */
@SuppressWarnings("unchecked")
public class FileUploadUtils {
	
	private static final int BUFFER_SIZE = 16 * 1024; // 上传文件的缓冲区大小
	
	private static Logger log = Logger.getLogger("FileUploadUtils");

	/**
	 * 读取本地文件
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void readFileByBytes(String filePathAndName) {
        File file = new File(filePathAndName);
        InputStream in = null;
        StringBuffer sfb = new StringBuffer();
     /*   try {
            //System.out.println("以字节为单位读取文件内容，一次读一个字节：");
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                System.out.write(tempbyte);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }*/
        try {
            //System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节
            byte[] tempbytes = new byte[1024*10];
            int byteread = 0;
            in = new FileInputStream(filePathAndName);
           
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
            	System.out.write(tempbytes, 0, byteread);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
    }
	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static boolean delFile(String filePathAndName) {
		File myDelFile = new java.io.File(filePathAndName);
		if (!myDelFile.exists()) {
			return true;
		}
		return myDelFile.delete();
	}

	/**
	 * 删除指定文件路径下面的所有文件和文件夹
	 * 
	 * @param file
	 */
	public static boolean delFiles(File file) {
		boolean flag = false;
		try {
			if (file.exists()) {
				if (file.isDirectory()) {
					String[] contents = file.list();
					for (int i = 0; i < contents.length; i++) {
						File file2X = new File(file.getAbsolutePath() + "/"
								+ contents[i]);
						if (file2X.exists()) {
							if (file2X.isFile()) {
								flag = file2X.delete();
							} else if (file2X.isDirectory()) {
								delFiles(file2X);
							}
						} else {
							throw new RuntimeException("File not exist!");
						}
					}
				}
				flag = file.delete();
			} else {
				throw new RuntimeException("File not exist!");
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 判断文件名是否已经存在，如果存在则在后面家(n)的形式返回新的文件名，否则返回原始文件名 例如：已经存在文件名 log4j.htm
	 * 则返回log4j(1).htm
	 * 
	 * @param fileName
	 *            文件名
	 * @param dir
	 *            判断的文件路径
	 * @return 判断后的文件名
	 */
	public static String checkFileName(String fileName, String dir) {
		boolean isDirectory = new File(dir + fileName).isDirectory();
		if (FileUploadUtils.isFileExist(fileName, dir)) {
			int index = fileName.lastIndexOf(".");
			StringBuffer newFileName = new StringBuffer();
			String name = isDirectory ? fileName : fileName.substring(0, index);
			String extendName = isDirectory ? "" : fileName.substring(index);
			int nameNum = 1;
			while (true) {
				newFileName.append(name).append("(").append(nameNum)
						.append(")");
				if (!isDirectory) {
					newFileName.append(extendName);
				}
				if (FileUploadUtils.isFileExist(newFileName.toString(), dir)) {
					nameNum++;
					newFileName = new StringBuffer();
					continue;
				}
				return newFileName.toString();
			}
		}
		return fileName;
	}

	/**
	 * 返回上传的结果，成功与否
	 * 
	 * @param uploadFileName
	 * @param savePath
	 * @param uploadFile
	 * @return
	 */
	public static boolean upload(String uploadFileName, String savePath,
			File uploadFile) {
		boolean flag = false;
		try {
			uploadForName(uploadFileName, savePath, uploadFile);
			flag = true;
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 上传文件并返回上传后的文件名
	 * 
	 * @param uploadFileName
	 *            被上传的文件名称
	 * @param savePath
	 *            文件的保存路径
	 * @param uploadFile
	 *            被上传的文件
	 * @return 成功与否
	 * @throws IOException
	 */
	public static String uploadForName(String uploadFileName, String savePath,
			File uploadFile) throws IOException {
		//String newFileName = checkFileName(uploadFileName, savePath);
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(savePath + uploadFileName);
			fis = new FileInputStream(uploadFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return uploadFileName;
	}

	
	
	public static String uploadForName(String uploadFileName, String savePath,FileInputStream fis) throws IOException {
		//String newFileName = checkFileName(uploadFileName, savePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(savePath + uploadFileName);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return uploadFileName;
	}
	
	
	
	/**
	 * 根据路径创建一系列的目录
	 * 
	 * @param path
	 */
	public static boolean mkDirectory(String path) {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			file = null;
		}
		return false;
	}


	/**
	 * 判断文件是否存在
	 * 
	 * @param fileName
	 * @param dir
	 * @return
	 */
	public static boolean isFileExist(String fileName, String dir) {
		File files = new File(dir + fileName);
		return (files.exists()) ? true : false;
	}

	/**
	 * 获得随机文件名,保证在同一个文件夹下不同名
	 * 
	 * @param fileName
	 * @param dir
	 * @return
	 */
	public static String getRandomName(String fileName, String dir) {
		String[] split = fileName.split("\\.");// 将文件名已.的形式拆分
		String extendFile = "." + split[split.length - 1].toLowerCase(); // 获文件的有效后缀

		Random random = new Random();
		int add = random.nextInt(1000000); // 产生随机数10000以内
		String ret = add + extendFile;
		while (isFileExist(ret, dir)) {
			add = random.nextInt(1000000);
			ret = fileName + add + extendFile;
		}
		return ret;
	}

	/**
	 * 创建缩略图
	 * 
	 * @param file
	 *            上传的文件流
	 * @param height
	 *            最小的尺寸
	 * @throws IOException
	 */
	public static void createMiniPic(File file, float width, float height)
			throws IOException {
		Image src = javax.imageio.ImageIO.read(file); // 构造Image对象
		int old_w = src.getWidth(null); // 得到源图宽
		int old_h = src.getHeight(null);
		int new_w = 0;
		int new_h = 0; // 得到源图长
		float tempdouble;
		if (old_w >= old_h) {
			tempdouble = old_w / width;
		} else {
			tempdouble = old_h / height;
		}

		if (old_w >= width || old_h >= height) { // 如果文件小于锁略图的尺寸则复制即可
			new_w = Math.round(old_w / tempdouble);
			new_h = Math.round(old_h / tempdouble);// 计算新图长宽
			while (new_w > width && new_h > height) {
				if (new_w > width) {
					tempdouble = new_w / width;
					new_w = Math.round(new_w / tempdouble);
					new_h = Math.round(new_h / tempdouble);
				}
				if (new_h > height) {
					tempdouble = new_h / height;
					new_w = Math.round(new_w / tempdouble);
					new_h = Math.round(new_h / tempdouble);
				}
			}
			BufferedImage tag = new BufferedImage(new_w, new_h,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src, 0, 0, new_w, new_h, null); // 绘制缩小后的图
			FileOutputStream newimage = new FileOutputStream(file); // 输出到文件流
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
			param.setQuality((float) (100 / 100.0), true);// 设置图片质量,100最大,默认70
			encoder.encode(tag, param);
			encoder.encode(tag); // 将JPEG编码
			newimage.close();
		}
	}

	/**
	 * 判断文件类型是否是合法的,就是判断allowTypes中是否包含contentType
	 * 
	 * @param contentType
	 *            文件类型
	 * @param allowTypes
	 *            文件类型列表
	 * @return 是否合法
	 */
	public static boolean isValid(String contentType, String[] allowTypes) {
		if (null == contentType || "".equals(contentType)) {
			return false;
		}
		for (String type : allowTypes) {
			if (contentType.equals(type)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unused")
	private static void getDir(String directory, String subDirectory) {
		String dir[];
		File fileDir = new File(directory);
		try {
			if (subDirectory == "" && fileDir.exists() != true)
				fileDir.mkdir();
			else if (!subDirectory.equals("")) {
				dir = subDirectory.replace('\\', '/').split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (subFile.exists() == false)
						subFile.mkdir();
					directory += File.separator + dir[i];
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 自己封装的一个把源文件对象复制成目标文件对象
	public static void copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 自己封装的计算上传文件的大小并格式化 以设置为文件属性
	 */
	public static String formatFileLength(long size) {

		Double a;
		a = Double.valueOf(size);
		StringBuffer sb = new StringBuffer();
		java.text.DecimalFormat myformat = new java.text.DecimalFormat("#0.00");
		if (a >= 1024 * 1024 * 1024) {
			a = a / 1024 / 1024 / 1024 * 1.00;
			sb.append(myformat.format(a));
			sb.append(" GB");
		} else if (a >= 1024 * 1024 && a < 1024 * 1024 * 1024) {
			a = a / 1024 / 1024 * 1.00;
			sb.append(myformat.format(a));
			sb.append(" MB");
		} else if (a >= 1024 && a < 1024 * 1024) {
			a = a / 1024 * 1.00;
			sb.append(myformat.format(a));
			sb.append(" KB");
		} else {
			sb.append(myformat.format(a));
			sb.append(" 字节");
		}

		return sb.toString();
	}

	/**
	 * 根据文件名得到文件的后缀名,不带.
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtension(String filename) {
		int i = filename.lastIndexOf(".");
		if ((i > -1) && (i < (filename.length() - 1))) {
			return filename.substring(i + 1);
		} else {
			return "data";
		}
	}

	/**
	 * 根据文件名 基路径 得到上传文件在服务器上存储的唯一路径,并创建存储目录
	 * 
	 * @param filename
	 *            文件名
	 * @param basePath1
	 *            文件保存的基路径，如“D:/file/”
	 * @param basePath2
	 *            文件保存的二级目录，如“OA_DOC_ATTACACHMENT/”
	 * @return
	 */
	public static String getRealFilePath(String fileName, String basePath1,
			String basePath2) {
		String uuid = UUID.randomUUID().toString();
		String fileType = getExtension(fileName);
		String fileName_UUID = uuid.toString() + "." + fileType;// 得到以唯一标识符重命名的文件名

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "/" + "MM" + "/"
				+ "dd" + "/");

		String rq = sdf.format(date);
		int i = basePath2.lastIndexOf("/");

		// 如果保存的文件夹路径不以"/"结尾,则添加上，如“OA_DOC_ATTACACHMENT/”
		if ((i >= -1) && (i != basePath2.length() - 1)) {
			basePath2 = basePath2 + "/";
		}

		int j = basePath1.lastIndexOf("/");

		// 如果基路径不以"/"结尾,则添加上，如“D:/file/”
		if ((j >= -1) && (j != basePath1.length() - 1)) {
			basePath1 = basePath1 + "/";
		}

		mkDirectory(basePath1 + basePath2 + rq); // 根据路径创建一系列的目录
		
		log.debug(basePath1 + basePath2 + rq);

		String realFilePath = basePath2 + rq + fileName_UUID; // 得到在服务器上存储的唯一路径
		
		log.debug(realFilePath);

		return realFilePath;
	}

	
	
	/**
	 * 描述：解析XML文件并返回内容<br>
	 *
	 * @param contentUrl
	 * @return String
	 *
	 * 作者:lvj<br>
	 * 创建时间:2012-8-24 下午04:48:23
	 */
	public static String parserXml4Content(String contentUrl){
		File inputXml = new File(contentUrl);
		SAXReader saxReader = new SAXReader();
		String content = "";
		try {
			Document document = saxReader.read(inputXml);
			Element contentElement = document.getRootElement();
			content = contentElement.getText();
		} catch (DocumentException e) {
			//System.out.println(e.getMessage());
		}
		return content;
	}
	
	public static void main(String[] args) {
			//String newFileName = checkFileName(uploadFileName, savePath);
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				fos = new FileOutputStream("d:/1.txt");
				String a = "11";
				a.getBytes();
				fos.write(a.getBytes());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
					if (fis != null) {
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
	
}
