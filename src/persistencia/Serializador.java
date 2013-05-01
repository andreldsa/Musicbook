package persistencia;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;

public class Serializador {
	private static final String MUSIC_BOOK = "MusicBook";

	public static void serializar(Object obj) throws IOException,
			FileNotFoundException {
		ObjectOutputStream s = new ObjectOutputStream(new BufferedOutputStream(
				new FileOutputStream(MUSIC_BOOK)));
		s.writeObject(obj);
		s.close();
	}
}
