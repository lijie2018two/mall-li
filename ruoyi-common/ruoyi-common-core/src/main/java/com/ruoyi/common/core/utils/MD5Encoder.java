package com.ruoyi.common.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encoder {

	static private final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

	/**
	 * 作为参数给出的字符串（原始密码）是针对该用户的
	 * 检查它是否正确。
	 *
	 * @param checkPwd   您要验证的字符串
	 * @return           检查结果
	 */
	public static boolean checkPwd(String checkPwd, String strPwd){
		String strHashed = getHashedPwd(checkPwd);
		if( strPwd == null || strHashed == null ||
			! strPwd.equals( strHashed) ){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 散列作为参数给出的字符串（密码）。
	 *
	 * @param checkPwd   您要加密的字符串
	 * @return           您要加密的字符串
	 */
	public static String getHashedPwd(String checkPwd){
		if(checkPwd==null){
			return null;
		}
		//
		MessageDigest md;
		try{
			md = MessageDigest.getInstance("MD5");
		}catch(NoSuchAlgorithmException e){
			// MD5
			return null;
		}
		// checkPwd のハッシュ値取得
		byte[] digest = md.digest(checkPwd.getBytes());
		//
		char[] arr = new char[ digest.length * 2];
		for(int i=0;i<digest.length;i++){
			arr[i*2] = HEX_CHARS[((digest[i]&255) >> 4)];
			arr[i*2+1] = HEX_CHARS[(digest[i]&15)];
		}
		return new String( arr);
	}
}
