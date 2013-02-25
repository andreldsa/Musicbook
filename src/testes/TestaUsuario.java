package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import data.user.Conta;
import data.user.ContaBasica;
import data.user.Usuario;
import exceptions.user.EmailInvalidoException;
import exceptions.user.LoginInvalidoException;
import exceptions.user.NomeInvalidoException;

public class TestaUsuario {
	
	private Usuario usuario1;
	private Conta contaUsuario;

	@Before
	public void setUp() throws EmailInvalidoException, LoginInvalidoException, NomeInvalidoException{
		contaUsuario = new ContaBasica("Joazito", "joao123");
		usuario1 = new Usuario("João da Silva Pinto", contaUsuario , "joao.silva@gmail.com");
	}

	@Test
	public void testaGetNome() {
		assertEquals("João da Silva Pinto", usuario1.getNome());
	}

	@Test
	public void testGetLogin() {	
		assertEquals("Joazito", usuario1.getLogin());
	}

	@Test
	public void testGetEmail() {
		assertEquals("joao.silva@gmail.com", usuario1.getEmail());
	}

	@Test(expected = NomeInvalidoException.class)
	public void testaNomeInvalido1() throws EmailInvalidoException, LoginInvalidoException, NomeInvalidoException{
		contaUsuario = new ContaBasica("Jiaozito", "jiao123");
		usuario1 = new Usuario(null, contaUsuario , "jiao.silva@gmail.com");
	}

	@Test(expected = NomeInvalidoException.class)
	public void testaNomeInvalido2() throws EmailInvalidoException, LoginInvalidoException, NomeInvalidoException{
		contaUsuario = new ContaBasica("Zezim", "ze123");
		usuario1 = new Usuario("", contaUsuario , "zezim.silva@gmail.com");
	}

	@Test(expected = EmailInvalidoException.class)
	public void testaEmailInvalido1() throws EmailInvalidoException, LoginInvalidoException, NomeInvalidoException{
		contaUsuario = new ContaBasica("Jiaozito", "jiao123");
		usuario1 = new Usuario("Jião da Silva Pinto", contaUsuario , null);
	}


	@Test(expected = EmailInvalidoException.class)
	public void testaEmailInvalido2() throws EmailInvalidoException, LoginInvalidoException, NomeInvalidoException{
		contaUsuario = new ContaBasica("Zezim", "ze123");
		usuario1 = new Usuario("Zé da Silva Pinto", contaUsuario , "");

	}
}
