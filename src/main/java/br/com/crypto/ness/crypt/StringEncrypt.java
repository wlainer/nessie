package br.com.crypto.ness.crypt;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class StringEncrypt {

	private final static String ALG = "AES";
	private final static String CI = "AES/CBC/PKCS5Padding";

	public static String encrypt(String key, String iv, String cleartext) throws Exception {
		Cipher cipher = Cipher.getInstance(CI);
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), ALG);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(cleartext.getBytes());
		return new String(DatatypeConverter.printBase64Binary(encrypted));
	}

	public static String decrypt(String key, String iv, String encrypted) throws Exception {
		Cipher cipher = Cipher.getInstance(CI);
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), ALG);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
		byte[] enc = DatatypeConverter.parseBase64Binary(encrypted);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
		byte[] decrypted = cipher.doFinal(enc);
		return new String(decrypted);
	}

	public static String getSHA256FromKey(String key) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(key.getBytes("UTF-8"));
			byte[] keyBytes = new byte[32];
			System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
			String encoded = DatatypeConverter.printBase64Binary(keyBytes);
			return encoded.substring(0, 16);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}