package persistencia;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserializador {
	private static final String MUSIC_BOOK = "MusicBook";

	public static Object deserializar() throws IOException, ClassNotFoundException {
		ObjectInputStream d = new ObjectInputStream(new BufferedInputStream(
				new FileInputStream(MUSIC_BOOK)));
		Object o = d.readObject();
		d.close();
		return o;
	}
}
