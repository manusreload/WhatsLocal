package com.blacklight.whatslocal.db;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;

public class DbDecrypt {
	private File dbFile;
	private byte[] key;
	private byte[] dbContent;
	private final static String AESkey = "346a23652a46392b4d73257c67317e352e3372482177652c";
	
	public DbDecrypt(File dbFile) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		this(dbFile, DatatypeConverter.parseHexBinary(AESkey));
	}
	
	public DbDecrypt(File dbFile, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException
	{
		this.dbFile = dbFile;
		this.key = key;
		byte[] encryptedDb = FileUtils.readFileToByteArray(this.dbFile);
		
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(this.key, "AES"));
		dbContent = cipher.doFinal(encryptedDb);
	}
	
	public byte[] getDbContent()
	{
		return dbContent;
	}
}
