package com.seidor.inventario.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.seidor.inventario.exception.BusinessException;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class EncryptUtil {
	
	private static final Integer RANDOM_MAX_INDEX = 8;
	private static final Integer HASH_LENGHT = 8;
	
	private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	//Encrypt/Decrypt
	private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    
	public static String encryptSHA(String pass){
		String cryptedPass = null;
		try	{
			if (pass!=null){
				
				MessageDigest sha1 = MessageDigest.getInstance("SHA1");
				sha1.update(pass.getBytes("ASCII"));
				byte[] mdDigested = sha1.digest();
				
				cryptedPass = Base64.getEncoder().encodeToString(mdDigested);
				
			}
		}
		catch (NoSuchAlgorithmException ex){ }
		catch (UnsupportedEncodingException ex){ }
		
		return cryptedPass;
	}
	
	public static String encryptMD5(Object input) {
		String cryptedTarget = null;
		byte[] messageDigest;
		MessageDigest md;
		String target;

		try {
			target = input.toString();
			//MD5
			md = MessageDigest.getInstance("MD5");
			md.update(target.getBytes());
			messageDigest = md.digest();
			cryptedTarget = Base64.getEncoder().encodeToString(messageDigest);

		}catch (NoSuchAlgorithmException ex){
			throw new RuntimeException(ex);
		}
		return cryptedTarget;
	}

	public static String encryptSHA256(Object input) {
		String cryptedTarget = null;
		byte[] messageDigest;
		MessageDigest md;
		String target;

		try {
			target = input.toString();
			//SHA-256
			md = MessageDigest.getInstance("SHA-256");
			md.update(target.getBytes());
			messageDigest = md.digest();
			cryptedTarget = Base64.getEncoder().encodeToString(messageDigest);

		}catch (NoSuchAlgorithmException ex){
			throw new RuntimeException(ex);
		}
		return cryptedTarget;
	}
	
	public static String getHash(String str){
		return getHash(str, null);
	}
	
	public static String getHash(String str, Integer lenght){
		if (lenght == null) lenght = HASH_LENGHT;
		String hash = EncryptUtil.encryptSHA(str);
		hash = hash.replaceAll("=", "").replaceAll("[+]", "").replaceAll("/", "");
		Integer rand = 0;
		rand = (int)(Math.random() * RANDOM_MAX_INDEX);
		hash = hash.substring(rand, rand + lenght);
		return hash.toUpperCase();
	}
	
	public static String encrypt(String toEncrypt) {
		try {
			String key = "123456%";
			byte[] encrypted = doCrypto(Cipher.ENCRYPT_MODE, key, toEncrypt.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace(); 
			throw new BusinessException("Ocurrió un error al realizar una encriptación."); 
		}
	}
	
	public static String decrypt(String encrypted) {
		try {
			String key = "123456%";
			byte[] decrypted = doCrypto(Cipher.DECRYPT_MODE, key, Base64.getDecoder().decode(encrypted));
			return new String(decrypted, "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace(); 
			throw new BusinessException("Ocurrió un error al realizar una desencriptación."); 
		}
	}
	
	public static byte[] encrypt(String key, byte[] file) {
		return doCrypto(Cipher.ENCRYPT_MODE, key, file);
	}
	
	public static byte[] decrypt(String key, byte[] file) {
		return doCrypto(Cipher.DECRYPT_MODE, key, file);
	}
	
	private static byte[] doCrypto(Integer cipherMode, String key, byte[] file){
		try {
			byte[] keybytes = (key).getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			keybytes = sha.digest(keybytes);
			keybytes = Arrays.copyOf(keybytes, 16); // use only first 128 bit
			
			Key secretKey = new SecretKeySpec(keybytes, ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);
			
			return cipher.doFinal(file);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Ocurri� un error al encriptar/desencriptar un archivo");
		}
	}
	
	/* WS Payment Encryption */
	
	public static byte[] encryptWSMessage(String key, String message) {
		try {
			byte[] keybytes = Base64.getDecoder().decode(key);
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keybytes);
		    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] messageBytes = message.getBytes("UTF-8");
			return cipher.doFinal(messageBytes);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Ocurri� un error al encriptar/desencriptar un archivo");
		}
	}
	
	
	public static String decryptWSMessage(String key, byte[] message) {
		try {
			byte[] keybytes = Base64.getDecoder().decode(key);
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keybytes);
		    PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			return new String(cipher.doFinal(message), "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Ocurri� un error al encriptar/desencriptar un archivo");
		}
	}
	
	public static String AESEncryptWSMessage(String key, String message) {
		try {
			byte[] encrypted = doCrypto(Cipher.ENCRYPT_MODE, key, message.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace(); 
			throw new BusinessException("Ocurrió un error al realizar una encriptación."); 
		}
	}
	
	public static String AESDecryptWSMessage(String key, String message) {
		try {
			byte[] decrypted = doCrypto(Cipher.DECRYPT_MODE, key, Base64.getDecoder().decode(message));
			return new String(decrypted, "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace(); 
			throw new BusinessException("Ocurrió un error al realizar una desencriptación."); 
		}
	}
	
	public static String encryptSecretKey(String key, String secretKey) {
		try {
			byte[] keybytes = Base64.getDecoder().decode(key);
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keybytes);
		    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] messageBytes = secretKey.getBytes("UTF-8");
			System.out.println("[Secret Key Lenght] : " + messageBytes.length);
			return  Base64.getEncoder().encodeToString(cipher.doFinal(messageBytes)).replace("\n", "");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Ocurri� un error al encriptar/desencriptar un archivo");
		}
	}
	
	public static String decryptSecretKey(String key, String secretKey) {
		try {
			byte[] keybytes = Base64.getDecoder().decode(key);
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keybytes);
		    PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] messageBytes = Base64.getDecoder().decode(secretKey);
			System.out.println("[Secret Key Lenght] : " + messageBytes.length);
			return new String(cipher.doFinal(messageBytes), "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Ocurri� un error al encriptar/desencriptar un archivo");
		}
	}
	
	public static String getUUIDV4() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest salt = MessageDigest.getInstance("SHA-256");
		salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
		String digest = bytesToHex(salt.digest());
		return digest;
	}
	
	public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
	
	public static void main(String args[]) {
		System.out.print(encryptSHA("123456"));
	}
	
}