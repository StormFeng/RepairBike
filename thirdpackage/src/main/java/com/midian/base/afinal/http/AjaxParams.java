package com.midian.base.afinal.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 使用方法:
 * <p>
 * <pre>
 * AjaxParams params = new AjaxParams();
 * params.put("username", "michael");
 * params.put("password", "123456");
 * params.put("email", "test@tsz.net");
 * params.put("profile_picture", new File("/mnt/sdcard/pic.jpg")); // 上传文件
 * params.put("profile_picture2", inputStream); // 上传数据流
 * params.put("profile_picture3", new ByteArrayInputStream(bytes)); // 提交字节流
 *
 * FinalHttp fh = new FinalHttp();
 * fh.post("http://www.yangfuhai.com", params, new AjaxCallBack<String>(){
 * 		@Override
 *		public void onLoading(long count, long current) {
 *				textView.setText(current+"/"+count);
 *		}
 *
 *		@Override
 *		public void onSuccess(String t) {
 *			textView.setText(t==null?"null":t);
 *		}
 * });
 * </pre>
 */
public class AjaxParams {
	private static String ENCODING = "UTF-8";

	//是否有文件
	private boolean hasFile = false;

	/**
	 * 该请求是否有文件上传，当文件为空时需要设置true
	 * @param hasFile
	 */
	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}

	public ConcurrentHashMap<String, String> urlParams;
	public List<FileWrapper> fileLists = new ArrayList<FileWrapper>();

	public AjaxParams() {
		init();
	}

	public AjaxParams(Map<String, String> source) {
		init();

		for (Map.Entry<String, String> entry : source.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	public AjaxParams(String key, String value) {
		init();
		put(key, value);
	}

	public AjaxParams(Object... keysAndValues) {
		init();
		int len = keysAndValues.length;
		if (len % 2 != 0)
			throw new IllegalArgumentException("Supplied arguments must be even");
		for (int i = 0; i < len; i += 2) {
			String key = String.valueOf(keysAndValues[i]);
			String val = String.valueOf(keysAndValues[i + 1]);
			put(key, val);
		}
	}

	public void put(String key, String value) {
		if (key != null && value != null) {
			urlParams.put(key, value);
		}
	}

	public void put(String key, File file) throws FileNotFoundException {
		put(key, new FileInputStream(file), file.getName());
	}
	
	public void put(String key, File file,String type) throws FileNotFoundException {
		put(key, new FileInputStream(file), file.getName(),type);
	}

	public void put(String key, InputStream stream) {
		put(key, stream, null);
	}

	public void put(String key, InputStream stream, String fileName) {
		put(key, stream, fileName, null);
	}

	/**
	 * 添加 inputStream 到请求中.
	 * @param key the key name for the new param.
	 * @param stream the input stream to add.
	 * @param fileName the name of the file.
	 * @param contentType the content type of the file, eg. application/json
	 */
	public void put(String key, InputStream stream, String fileName, String contentType) {
		if (key != null && stream != null) {
			FileWrapper f = new FileWrapper(stream, fileName, contentType, key);
			fileLists.add(f);
		}
	}

	public void remove(String key) {
		urlParams.remove(key);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
			if (result.length() > 0)
				result.append("&");

			result.append(entry.getKey());
			result.append("=");
			result.append(entry.getValue());
		}

		for (FileWrapper iterable_element : fileLists) {
			if (result.length() > 0)
				result.append("&");

			result.append(iterable_element.key);
			result.append("=");
			result.append("FILE");

		}

		return result.toString();
	}

	/**
	  * Returns an HttpEntity containing all request parameters
	  */
	public HttpEntity getEntity() {
		HttpEntity entity = null;

		if (fileLists.size() > 0 || hasFile) {
			MultipartEntity multipartEntity = new MultipartEntity();

			// Add string params
			for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
				multipartEntity.addPart(entry.getKey(), entry.getValue());
			}

			// Add file params
			int currentIndex = 0;
			int lastIndex = fileLists.size() - 1;
			for (FileWrapper file : fileLists) {
				//				FileWrapper file = entry.getValue();
				if (file.inputStream != null) {
					boolean isLast = currentIndex == lastIndex;
					if (file.contentType != null) {
						multipartEntity.addPart(file.key, file.getFileName(), file.inputStream, file.contentType, isLast);
					} else {
						multipartEntity.addPart(file.key, file.getFileName(), file.inputStream, isLast);
					}
				}
				currentIndex++;
			}
			if (hasFile && urlParams.size() > 0) {
				multipartEntity.last();
			}
			entity = multipartEntity;
		} else {
			try {
				entity = new UrlEncodedFormEntity(getParamsList(), ENCODING);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return entity;
	}

	private void init() {
		urlParams = new ConcurrentHashMap<String, String>();
	}

	protected List<BasicNameValuePair> getParamsList() {
		List<BasicNameValuePair> lparams = new LinkedList<BasicNameValuePair>();

		for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
			lparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		return lparams;
	}

	public String getParamString() {
		return URLEncodedUtils.format(getParamsList(), ENCODING);
	}

	public static class FileWrapper {
		public InputStream inputStream;
		public String fileName;
		public String contentType;
		public String key;

		public FileWrapper(InputStream inputStream, String fileName, String contentType, String key) {
			this.inputStream = inputStream;
			this.fileName = fileName;
			this.contentType = contentType;
			this.key = key;
		}

		public String getFileName() {
			if (fileName != null) {
				return fileName;
			} else {
				return "nofilename";
			}
		}
	}
}