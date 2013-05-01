package dados.util;

import java.util.List;

public class ListUtils {

	public static <T> int elementosEmComum(List<T> lista1, List<T> lista2){
		int emComum = 0;
		for(T elemento: lista1){
			if( lista2.contains(elemento)){
				emComum++;
			}
		}
		return emComum;
		
	}
}
