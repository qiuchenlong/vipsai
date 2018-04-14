package com.vs.library.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 文件工具
 * @author chends
 *
 */
public class FileUtil {

	private static final String Tag = FileUtil.class.getSimpleName();
	
	private static ThreadPoolExecutor writeLogExecutor;
	private static BlockingQueue<Runnable> writeLogBlock;
	

	private static HashMap<String, String> INTENT_FILE_TYPE = new HashMap<>();
	static {
		INTENT_FILE_TYPE.put("spl", "application/futuresplash");	//视频文件
		INTENT_FILE_TYPE.put("hta", "application/hta");	//HTML应用程序
		INTENT_FILE_TYPE.put("hqx", "application/mac-binhex40");	//苹果文件
		INTENT_FILE_TYPE.put("doc", "application/msword");
		INTENT_FILE_TYPE.put("dot", "application/msword");
		INTENT_FILE_TYPE.put("bin", "application/octet-stream");
		INTENT_FILE_TYPE.put("exe", "application/octet-stream");
		INTENT_FILE_TYPE.put("pdf", "application/pdf");
		INTENT_FILE_TYPE.put("prf", "application/pdf");
		INTENT_FILE_TYPE.put("crl", "application/pkix-crl");
		INTENT_FILE_TYPE.put("ai", "application/postscript");
		INTENT_FILE_TYPE.put("eps", "application/postscript");
		INTENT_FILE_TYPE.put("ps", "application/postscript");
		INTENT_FILE_TYPE.put("rtf", "application/rtf");
		INTENT_FILE_TYPE.put("xla", "application/vnd.ms-excel");
		INTENT_FILE_TYPE.put("xlc", "application/vnd.ms-excel");
		INTENT_FILE_TYPE.put("xlm", "application/vnd.ms-excel");
		INTENT_FILE_TYPE.put("xls", "application/vnd.ms-excel");
		INTENT_FILE_TYPE.put("xlt", "application/vnd.ms-excel");
		INTENT_FILE_TYPE.put("xlw", "application/vnd.ms-excel");
		INTENT_FILE_TYPE.put("msg", "application/vnd.ms-outlook");
		INTENT_FILE_TYPE.put("ppt", "application/vnd.ms-powerpoint");
		INTENT_FILE_TYPE.put("hlp", "application/winhlp");
		INTENT_FILE_TYPE.put("tgz", "application/x-compressed");
		INTENT_FILE_TYPE.put("gtar", "application/x-gtar");
		INTENT_FILE_TYPE.put("gz", "application/x-gzip");
		INTENT_FILE_TYPE.put("mdb", "application/x-msaccess");
		INTENT_FILE_TYPE.put("wmf", "application/x-msmetafile");
		INTENT_FILE_TYPE.put("swf", "application/x-shockwave-flash");
		INTENT_FILE_TYPE.put("tar", "application/x-tar");
		INTENT_FILE_TYPE.put("crt", "application/x-x509-ca-cert");
		INTENT_FILE_TYPE.put("cer", "application/x-x509-ca-cert");
		INTENT_FILE_TYPE.put("zip", "application/zip");
		INTENT_FILE_TYPE.put("mid", "audio/mid");
		INTENT_FILE_TYPE.put("mp3", "audio/mpeg");
		INTENT_FILE_TYPE.put("wav", "audio/x-wav");
		INTENT_FILE_TYPE.put("bmp", "image/bmp");
		INTENT_FILE_TYPE.put("gif", "image/gif");
		INTENT_FILE_TYPE.put("jpe", "image/jpeg");
		INTENT_FILE_TYPE.put("jpeg", "image/jpeg");
		INTENT_FILE_TYPE.put("jpg", "image/jpeg");
		INTENT_FILE_TYPE.put("svg", "image/svg+xml");
		INTENT_FILE_TYPE.put("tif", "image/tiff");
		INTENT_FILE_TYPE.put("tiff", "image/tiff");
		INTENT_FILE_TYPE.put("ico", "image/x-icon");
		INTENT_FILE_TYPE.put("xbm", "image/x-xbitmap");
		INTENT_FILE_TYPE.put("htm", "text/html");
		INTENT_FILE_TYPE.put("html", "text/html");
		INTENT_FILE_TYPE.put("txt", "text/plain");
		INTENT_FILE_TYPE.put("rtx", "text/richtext");
		INTENT_FILE_TYPE.put("vcf", "text/x-vcard");
		INTENT_FILE_TYPE.put("mpeg", "video/mpeg");
		INTENT_FILE_TYPE.put("mpg", "video/mpeg");
		INTENT_FILE_TYPE.put("mov", "video/quicktime");
		INTENT_FILE_TYPE.put("qt", "video/quicktime");
		INTENT_FILE_TYPE.put("asf", "video/x-ms-asf");
		INTENT_FILE_TYPE.put("avi", "video/x-msvideo");
		INTENT_FILE_TYPE.put("movie", "video/x-sgi-movie");
		INTENT_FILE_TYPE.put("3gp", "video/3gpp");
		INTENT_FILE_TYPE.put("apk", "application/vnd.android.package-archive");
		INTENT_FILE_TYPE.put("c", "text/plain");
		INTENT_FILE_TYPE.put("jar", "application/java-archive");
		INTENT_FILE_TYPE.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		INTENT_FILE_TYPE.put("mp4", "video/mp4");
		INTENT_FILE_TYPE.put("ogg", "audio/ogg");
		INTENT_FILE_TYPE.put("png", "image/png");
		INTENT_FILE_TYPE.put("rmvb", "audio/x-pn-realaudio");
		INTENT_FILE_TYPE.put("rmvb", "audio/x-pn-realaudio");
	}

	public static String getMIMEType(String ext) {
		String result = "";
		if(!TextUtils.isEmpty(ext)) {
			result = INTENT_FILE_TYPE.get(ext);
		}
		if(TextUtils.isEmpty(result)) {
			result = "*/*";
		}
		return result;
	}

	/****************************	file log	*****************************************/
	
	public static void fileLog(Context context, String tag, String log) {
		
		if(!TextUtils.isEmpty(tag)) {
			tag = tag.trim();
			
			getWriteLogExecutor().execute(new FileLog(context, tag, log));
		}
		
	}
	
	private static ThreadPoolExecutor getWriteLogExecutor(){
		if(writeLogExecutor == null ||
				writeLogExecutor.isShutdown()||
				writeLogExecutor.isTerminated()||
				writeLogExecutor.isTerminating() ){
			
			writeLogBlock = new LinkedBlockingQueue<Runnable>();
			writeLogExecutor = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 30, TimeUnit.SECONDS, writeLogBlock);
		}
		return writeLogExecutor;
	}
	
	private static class FileLog implements Runnable{
		
		private String mFileName;
		private String mLog;
		private Context mContext;
		
		public FileLog(Context context, String fileName, String log) {
			mFileName = fileName;
			mLog = log;
			mContext = context.getApplicationContext();
		}
		
		@Override
		public void run() {
			
			Log.i(Tag, "FileLog runnable");
			
			if(!TextUtils.isEmpty(mFileName)) {
				
				File file = getFile(mContext, mFileName);
				
				if(file == null || !file.canWrite() || TextUtils.isEmpty(mLog)) {
					return;
				}
				FileWriter writer = null;
				BufferedWriter bufWriter = null;
				try {

					String nowTime = DateUtil.nowDate(DateUtil.DEFAULT_FORMAT);
					
					Log.i(Tag, "FileLog(to file:" + file.getAbsolutePath() + "):" + mLog);
					
					writer = new FileWriter(file, true);
					bufWriter = new BufferedWriter(writer);
					bufWriter.write(nowTime + "	" + mLog);
					bufWriter.write("\n");
					bufWriter.newLine();
					bufWriter.flush();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (bufWriter != null) {
							bufWriter.close();
							bufWriter = null;
						}
						if (writer != null) {
							writer.close();
							bufWriter = null;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		private File getFile(Context context, String fileName) {
			File result = null;
			synchronized (FileLog.class) {
				

				File root = getOrCreateExternalFolder(context, "log");
				
				if(root != null && root.isDirectory() && !TextUtils.isEmpty(fileName)) {
					
					String[] lists = root.list();
					
					if(lists != null && lists.length > 0) {
						
						for(int i = 0; i < lists.length; i++) {
							String[] fileNames = lists[i].split("\\.");
							
							if(fileName.equals(fileNames[0])) {
								result = new File(root, lists[i]);
								break;
							}
						}
						
					}
					
					if(result == null) {
						result = new File(root, fileName + ".txt" );
					}
					
					if(!result.exists()) {
						
						try {
							result.createNewFile();
						}catch(IOException e){}
					}
				}
				
				return result;
			}
		}
		
	}
	
	/**************************** end file log	*****************************************/

	public static boolean isFileExist(String path) {
		if (!TextUtils.isEmpty(path) && new File(path).exists()) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isExternalStorageExist() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 外部存储目录
	 * @return file or null
	 */
	public static File getExternalStorage(Context context) {
		File result = null;
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			result = Environment.getExternalStorageDirectory();
		}

		if(result == null && context != null) {
			result = context.getExternalCacheDir();
		}

		if(result == null && context != null) {
			result = context.getFilesDir();
		}

		if(Build.VERSION.SDK_INT >= 23) {
			result = context.getCacheDir();
		}
		
		return result;
	}
	
	public static void clearDir(File dir) {
		
		if(dir != null && dir.canWrite()) {
		
			File files[] = dir.listFiles();  
	        if (files != null)  
	            for (File f : files) {  
	                if (f.isDirectory()) { // 判断是否为文件夹  
	                	clearDir(f);  
	                    try {  
	                        f.delete();  
	                    } catch (Exception e) {  
	                    }  
	                } else {  
	                   f.delete();  
	                }  
	            } 
		}
	}

	/**
	 * 获取或创建目录
	 * @param folderPath 外卡目录
	 * @return file or null
	 */
	public static File getOrCreateExternalFolder(Context context, String folderPath) {
		File externalRoot = getExternalStorage(context);
		if (externalRoot != null && !TextUtils.isEmpty(folderPath)) {

//			String[] paths = folderPath.split(File.separator);
//			
//			int length = paths.length;
//			
//			File folder = externalRoot;
//			
//			for (int i = 0; i < length; i++) {
//				
//				if(TextUtils.isEmpty(paths[i])) {
//					continue;
//				}
//				
//				folder = new File(folder, paths[i]);
//				if (!folder.exists() && !folder.mkdir()) {
//					return null;
//				}
//			}

			try {
				File folder = new File(externalRoot, folderPath);

				if (!folder.exists() && !folder.mkdirs()) {
					return null;
				}

				return folder;

			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 读取asset文件字符串
	 * @param context
	 * @param fileName
	 * @return null or string
	 */
	public static String readAssetAsString(Context context, String fileName) {
		
		String result = null;
		InputStream is = null;

		try {
			is = context.getAssets().open(fileName);
			
			BufferedReader br = null;
	        StringBuilder sb = new StringBuilder();
	        String line;
	        br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            
	        result = sb.toString();
			
		}catch(IOException e){
		}finally {
			if(is != null) {
				try {
					is.close();
				}catch(IOException e){}
			}
		}
		
		return result;
	}
	
	public static final boolean copyTo(String oldFilePath, String newFilePath) {
		boolean result = false;
		
		if(!StringUtil.isEmpty(oldFilePath) && !StringUtil.isEmpty(newFilePath)) {
			InputStream inStream = null;
			FileOutputStream fs = null;
			try {   
		           int bytesum = 0;   
		           int byteread = 0;   
		           File oldFile = new File(oldFilePath);
		           
		           if (oldFile.exists()) {    
		        	   
		        	   File outFile = new File(newFilePath);
		        	   if(!outFile.exists()) {
		        		   outFile.createNewFile();
		        	   }
		        	   
		              inStream = new FileInputStream(oldFile); //读入原文件   
		                fs = new FileOutputStream(outFile);   
		               byte[] buffer = new byte[2* 2014];   
		               while ( (byteread = inStream.read(buffer)) != -1) {   
		                   bytesum += byteread; //字节数 文件大小   
		                   System.out.println(bytesum);   
		                   fs.write(buffer, 0, byteread);   
		               }   
		               
		               fs.flush();
		           }   
		           result = true;
		           
		       }catch (Exception e) {
		    	}finally {
		    		if(inStream != null) {
		    			try {
		    				inStream.close();
		    			}catch(Exception e){}
		    		}
		    		
		    		if(fs != null) {
		    			try {
		    				fs.close();
		    			}catch(Exception e){}
		    		}
		    	}
		}
		
		return result;
	}

	public static boolean hasExtentsion(String filename) {
		int dot = filename.lastIndexOf('.');
		if ((dot > -1) && (dot < (filename.length() - 1))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取文件扩展名
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return "";
	}

	// 获取文件名
	public static String getFileNameFromPath(String filepath) {
		if ((filepath != null) && (filepath.length() > 0)) {
			int sep = filepath.lastIndexOf('/');
			if ((sep > -1) && (sep < filepath.length() - 1)) {
				return filepath.substring(sep + 1);
			}
		}
		return filepath;
	}

	public static String formatFileSize(long size) {
		return formatFileSize(size, SizeUnit.Auto);
	}

	public static String formatFileSize(long size, SizeUnit unit) {
		if (size < 0) {
			return "";
		}

		final double KB = 1024;
		final double MB = KB * 1024;
		final double GB = MB * 1024;
		final double TB = GB * 1024;
		if (unit == SizeUnit.Auto) {
			if (size < KB) {
				unit = SizeUnit.Byte;
			} else if (size < MB) {
				unit = SizeUnit.KB;
			} else if (size < GB) {
				unit = SizeUnit.MB;
			} else if (size < TB) {
				unit = SizeUnit.GB;
			} else {
				unit = SizeUnit.TB;
			}
		}

		switch (unit) {
			case Byte:
				return size + "B";
			case KB:
				return String.format(Locale.US, "%.2fKB", size / KB);
			case MB:
				return String.format(Locale.US, "%.2fMB", size / MB);
			case GB:
				return String.format(Locale.US, "%.2fGB", size / GB);
			case TB:
				return String.format(Locale.US, "%.2fPB", size / TB);
			default:
				return size + "B";
		}
	}

	public enum SizeUnit {
		Byte,
		KB,
		MB,
		GB,
		TB,
		Auto,
	}

	// 获取不带扩展名的文件名
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	public static long getFileSize(String filePath) {
		if(!TextUtils.isEmpty(filePath)) {
			FileInputStream fis = null;
			try {
				File file = new File(filePath);
				if (file.exists()) {
					fis = new FileInputStream(file);
					return fis.available();
				}

			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return 0;
	}

	public static long getFolderSize(File file) {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++){
				if (fileList[i].isDirectory()){
					size = size + getFolderSize(fileList[i]);
				}else{
					size = size + fileList[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	public static File getFileFromUri(Context context, Uri uri) {
		String selection = null;
		String[] selectionArgs = null;
		// Uri is different in versions after KITKAT (Android 4.4), we need to
		// deal with different Uris.
		if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + split[1]);
			} else if (isDownloadsDocument(uri)) {
				final String id = DocumentsContract.getDocumentId(uri);
				uri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
			} else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];
				if ("image".equals(type)) {
					uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}
				selection = "_id=?";
				selectionArgs = new String[]{ split[1] };
			}
		}
		if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = null;
			try {
				cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				if (cursor.moveToFirst()) {
					return new File(cursor.getString(column_index));
				}
			} catch (Exception e) {
			}finally {
				if(cursor != null) {
					try{
						cursor.close();
					}catch (Exception e){}
				}
			}
		} else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {
			return new File(uri.getPath());
		}
		return null;
	}
}
