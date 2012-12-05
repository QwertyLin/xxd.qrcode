package q.out;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import q.util.QLog;


public class QHttpUtil {
	
	public static String toString(HttpURLConnection conn) throws IOException{
    	return "response " + 
        		"url: " + conn.getURL()
    			+ " ContentEncoding: " + conn.getContentEncoding()
        		+ " ResponseCode: " + conn.getResponseCode()
        		+ " ResponseMessage: " + conn.getResponseMessage()
        		+ " ContentType: " + conn.getContentType()
        		+ " ConnectTimeout: " + conn.getConnectTimeout()
        		+ " ReadTimeout: " + conn.getReadTimeout()
        		+ " ContentLength: " + conn.getContentLength()
        	;
    	/*QLog.log();*/
		//System.out.println("method:"+conn.getRequestMethod());
		//System.out.println("defaultPort:"+conn.getURL().getDefaultPort());
		//System.out.println("file:"+conn.getURL().getFile());
		//System.out.println("host:"+conn.getURL().getHost());
		//System.out.println("path:"+conn.getURL().getPath());
		//System.out.println("port:"+conn.getURL().getPort());
		//System.out.println("protocol:"+conn.getURL().getProtocol());
		//System.out.println("query:"+conn.getURL().getQuery());
		//System.out.println("ref:"+conn.getURL().getRef());
		//System.out.println("userInfo:"+conn.getURL().getUserInfo());
    }

	public static String get(String urlStr) throws IOException {
		return get(urlStr, "utf-8");
	}
    
    /**
     * 发送HTTP GET请求
     * @param urlStr 如 http://www.baidu.com/
     * @param charset "UTF-8"或"GBK"
     * @return
     * @throws IOException
     */    
    public static String get(String urlStr, String charset) throws IOException {
    	HttpURLConnection conn = null;
    	InputStream in = null;
		BufferedReader bufferedReader = null;
    	try {
			URL url = new URL(urlStr);
			if (url.getProtocol().toLowerCase().equals("http")){
				conn = (HttpURLConnection) url.openConnection();
			}else if(url.getProtocol().toLowerCase().equals("https")){
				conn = initHttpsConn(url);
			}
			//
			conn.setRequestMethod("GET");
			QLog.log(QHttpUtil.class, toString(conn));
			//
			//Header
			//urlConnection.setRequestProperty("Host", "www.baidu.com");
			//urlConnection.setRequestProperty("Referer", "http://www.baidu.com");
			//urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6");
			//
			if(conn.getResponseCode() == 200){
				StringBuffer temp = new StringBuffer();
				in = conn.getInputStream();
				bufferedReader = new BufferedReader(new InputStreamReader(in, charset));
				String line = bufferedReader.readLine();
				while (line != null) {
					//temp.append(line).append("\r\n");
					temp.append(line);
					line = bufferedReader.readLine();
				}
				return temp.toString();
			}else{
				throw new IOException();
			}
    	} catch (IOException e) {
			throw e;
		} finally {
			if(bufferedReader != null){
				bufferedReader.close();
			}
			if(in != null){
				in.close();
			}
			if(conn != null){
				conn.disconnect();
			}
		}
	}
    
    public static void getFile(String urlStr, String file) throws IOException {
    	getFile(urlStr, new File(file), false);
    }
    
    /**
     * @param urlStr
     * @param filePath
     * @param isCheckExist 检测已存在的文件跟远程文件是否大小一样
     * @throws IOException
     */
    public static void getFile(String urlStr, File file, boolean isCheckExist) throws IOException {
    	HttpURLConnection conn = null;
    	InputStream in = null;
    	FileOutputStream out = null;
    	try {
    		URL url = new URL(urlStr);
			if (url.getProtocol().toLowerCase().equals("http")){
				conn = (HttpURLConnection) url.openConnection();
			}else if(url.getProtocol().toLowerCase().equals("https")){
				conn = initHttpsConn(url);
			}
			//
			conn.setRequestMethod("GET");
			QLog.log(QHttpUtil.class, toString(conn));
			//
			if(conn.getResponseCode() == 200){
				//文件大小不变时,不更新
				if(isCheckExist && file.exists() && file.length() == conn.getContentLength()){
					QLog.log(QHttpUtil.class, "文件无变化");
					return;
				}
				//
				in = conn.getInputStream();
				File temp = new File(file.getPath() + ".temp");
				out = new FileOutputStream(temp);
				byte[] buffer = new byte[1024];
		        int len = 0;		        
		        while((len = in.read(buffer)) != -1){
		        	out.write(buffer, 0, len);
				}
		        if(temp.length() == 0 || !temp.renameTo(file)){
					throw new IOException();
				}
			}else{
				throw new IOException();
			}
    	} catch (IOException e) {
			throw e;
		} finally {
			if(out != null){
				out.close();
			}
			if(in != null){
				in.close();
			}
			if (conn != null){
				conn.disconnect();
			}
		}
	}
        
    public static String post(String urlStr, String param) throws IOException {
    	return post(urlStr, param, null, null, "utf-8");    	
    }
    
    public static String post(String urlStr, String param, String boundary, String filePath) throws IOException {
    	return post(urlStr, param, boundary, filePath, "utf-8");
    }
    
    /**
     * 发送HTTP POST请求
     * @param urlStr 如 http://www.baidu.com/
     * @param charset "UTF-8"或"GBK"
     * @param param &key=value&key2=value
     * @return
     * @throws IOException 若输出为空，抛出异常
     */
    public static String post(String urlStr, String param, String boundary, String filePath, String charset) throws IOException {
    	HttpURLConnection conn = null;
    	OutputStream output = null;
    	try {
    		URL url = new URL(urlStr);
			if (url.getProtocol().toLowerCase().equals("http")){
				conn = (HttpURLConnection) url.openConnection();
			}else if(url.getProtocol().toLowerCase().equals("https")){
				conn = initHttpsConn(url);
			}
			//
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			//
			//Header
			/*if(header != null){
				for(String key : header.keySet()){
					conn.setRequestProperty(key, header.get(key));
				}
			}*/
			//
			if(filePath == null){
				//请求参数
				if(param != null){
					output = conn.getOutputStream();
					output.write(param.getBytes());
					output.flush();
				}
			}else{
				conn.setConnectTimeout(5000);// （单位：毫秒）jdk
				conn.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
				conn.setRequestProperty("connection", "keep-alive");
				conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);		
				//conn.setRequestProperty("Host", "www.baidu.com");
				//conn.setRequestProperty("Referer", "http://www.baidu.com");
				//conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6");
				//
				//请求参数
				File f = new File(filePath);  
				FileInputStream fileStream = new FileInputStream(f);  
		        byte[] file = new byte[(int)f.length()];  
		        fileStream.read(file); 
				if(param != null){
					output = conn.getOutputStream();
					output.write(param.getBytes());
					output.write(file);
					output.write(("\r\n--" + boundary + "--\r\n").getBytes());  //end
					output.flush();
				}
			}
			//
			toString(conn);
			//
			if(conn.getResponseCode() == 200){
				StringBuffer temp = new StringBuffer();
				
					InputStream in = conn.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(in, charset));
					String line = bufferedReader.readLine();
					while (line != null) {
						temp.append(line);
						line = bufferedReader.readLine();
					}
					bufferedReader.close();
					//
					System.out.println("content:"+temp.toString());
				
				if(temp.length() != 0){
					return temp.toString();
				}else{
					throw new IOException();
				}
			}else{
				throw new IOException();
			}
    	} catch (IOException e) {
			throw e;
		} finally {
			if(output != null){
				output.close();
			}
			if (conn != null){
				conn.disconnect();
			}
		}
		
	}
    
    /**
     * @param url
     * @return
     * @throws IOException
     */
    private static HttpsURLConnection initHttpsConn(URL url) throws IOException {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}
		} };
		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
	    https.setHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
	    return https;
	}
	
}
