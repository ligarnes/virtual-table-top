package net.alteiar.dao.util;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.LoggerFactory;

public class Util {

	public static void close(Closeable object) {

		if (object != null) {
			try {
				object.close();
			} catch (IOException e) {

				LoggerFactory.getLogger(Util.class).debug("Fail to close the element {}", object, e);
			}
		}
	}
}
