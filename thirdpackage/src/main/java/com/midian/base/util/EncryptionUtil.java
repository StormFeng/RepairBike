package com.midian.base.util;

import java.io.ByteArrayOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Base64;

public class EncryptionUtil {
	private final static String encoding = "utf-8";
	// key驴迹A_iAS5d583PiR3kt2UQ,
	private final static String key = "mengzhup";
	// 向量A_iAS5d583PiR3kt2UQA
	private final static String iv = "83792805";

	public static String getEncryptionStr() {
		String ressult = encryptString("A_U8L3B7kePj9Xj993Ee"+System.currentTimeMillis());
		return ressult.replace("+", "%2B");//.replace("+", "%2B")
	}

	/**
	 * 加密字符串
	 */
	@SuppressLint("NewApi")
	private static String encryptString(String str) {
		String sKey = key;
		String result = str;
		// 原字符串不为空
		if (!TextUtils.isEmpty(str)) {
			try {
				// 对称加密
				byte[] encodeByte = symmetricEncrypt(str.getBytes(encoding),
						sKey);
				// base64编码
				result = Base64.encodeToString(encodeByte, Base64.DEFAULT);
			} catch (Exception e) {
				e.printStackTrace();
				return "-1";
			}
		}
		return result;
	}

	/**
	 * 对称加密方法
	 * 
	 * @param byteSource
	 *            需要加密的数据
	 * @return 经过加密的数据
	 * @throws Exception
	 */
	private static byte[] symmetricEncrypt(byte[] byteSource, String sKey)
			throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int mode = Cipher.ENCRYPT_MODE;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			byte[] keyData = sKey.getBytes();
			DESKeySpec keySpec = new DESKeySpec(keyData);
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(mode, key, ips);
			byte[] result = cipher.doFinal(byteSource);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			baos.close();
		}
	}
}
