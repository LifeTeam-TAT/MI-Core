package org.ace.java.component.common;

import java.nio.charset.Charset;

public class Unicode {
	public final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	public String decodeUTF8(byte[] bytes) {
		return new String(bytes, UTF8_CHARSET);
	}

	public byte[] encodeUTF8(String string) {
		return string.getBytes(UTF8_CHARSET);
	}
}
