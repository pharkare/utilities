package org.harkare.utilities.encode;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Cipher;

public class EncodeString {
	public static void main(String[] args) throws Exception {
		EncodeString getEncodedValues = new EncodeString();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("String: ");
		String username = br.readLine();
		InputStream stream = getEncodedValues.getClass().getResourceAsStream("/public.cert");
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		Certificate cert = cf.generateCertificate(stream);
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, cert.getPublicKey());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date currDate = new Date();
		String date = dateFormat.format(currDate);
		System.out.println("User-Date: " + date);
		System.out.print("User-Name: ");
		System.out.println(new String(getEncodedValues.encrypt(cipher, username)));
		System.out.print("User-Date: ");
		System.out.println(new String(getEncodedValues.encrypt(cipher, date)));
	}

	private byte[] encrypt(Cipher cipher, String cleartext) throws Exception {
		byte[] cipherText = cipher.doFinal(cleartext.getBytes());
		return encodeBase64(cipherText);
	}
}
