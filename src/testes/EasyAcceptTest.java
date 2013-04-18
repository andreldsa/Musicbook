package testes;

import java.util.ArrayList;
import java.util.List;

import dados.sistema.Sistema;
import easyaccept.EasyAcceptFacade;
import facade.easyAccept.OurEasyAcceptFacade;

public class EasyAcceptTest {

	public static void main(String[] args) throws Exception {

		List<String> files = new ArrayList<String>();

		// Put the user stories files into the "test scripts" list

		files.add("US01.txt");

		files.add("US02.txt");

		files.add("US03.txt");

		files.add("US04.txt");

		files.add("US05.txt");

		files.add("US06.txt");
		
		files.add("US07.txt");

		// Instantiate your software façade

		OurEasyAcceptFacade yourTestFacade = new OurEasyAcceptFacade(
				Sistema.getInstance());

		// Instantiate EasyAccept façade

		EasyAcceptFacade eaFacade = new EasyAcceptFacade(yourTestFacade, files);

		// Execute the tests

		eaFacade.executeTests();

		// Print the tests execution results

		System.out.println(eaFacade.getCompleteResults());

	}

}
