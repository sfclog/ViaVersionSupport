package me.sfcevil.viaversionsupport.backup;
import java.util.Base64;

public class DecodeEndCore {
	public static String endcode(String s) {
		byte[] bytesEncoded = Base64.getEncoder().encode(s.getBytes());
		
		return new String(bytesEncoded);
	}
}
