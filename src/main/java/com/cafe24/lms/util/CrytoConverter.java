package com.cafe24.lms.util;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CrytoConverter implements AttributeConverter<String, String>{
	private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final byte[] KEY = "MySuperSecretKey".getBytes();

    public String convertToDatabaseColumn(String ccNumber) {
      // do some encryption
      Key key = new SecretKeySpec(KEY, "AES");

      try {
         Cipher c = Cipher.getInstance(ALGORITHM);
         c.init(Cipher.ENCRYPT_MODE, key);
         return Base64.getEncoder().encodeToString(c.doFinal(ccNumber.getBytes()));
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
    }

    public String convertToEntityAttribute(String dbData) {
      // do some decryption
      Key key = new SecretKeySpec(KEY, "AES");
      try {
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        return new String(c.doFinal(Base64.getDecoder().decode(dbData)));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
}
