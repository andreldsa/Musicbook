package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.user.ContaBasica;
import exceptions.LoginJaExisteException;
import exceptions.LoginInvalidoException;

public class TestaContaBasica {
	private ContaBasica conta1,conta2;

	@Before
	public void setUp(){
		conta1 = new ContaBasica("login1", "senha1");
		conta2 = new ContaBasica("login2", "senha_2");
	}
	
	@Test(expected = LoginInvalidoException.class)
	public void testaUsuarioInvalidoException(){
		conta1 = new ContaBasica("", "senha");
		conta1 = new ContaBasica(null, "senha");
		assertFalse(conta1.autentica(null));
		assertFalse(conta2.autentica(""));
	}
	
	@Test
	public void testaAutentica(){
		assertTrue(conta1.autentica("senha1"));
		assertTrue(conta2.autentica("senha_2"));
		assertFalse(conta1.autentica("senha 1"));
		assertFalse(conta2.autentica("senha2"));
		assertFalse(conta1.autentica("senha_1"));
		assertFalse(conta2.autentica("senha 2"));
	}
	
	@Test
	public void testaGetLogin(){
		assertEquals("login1", conta1.getLogin());
		assertEquals("login2", conta2.getLogin());
	}
	
	@Test
	public void testaAlteraSenha(){
		assertTrue(conta1.alteraSenha("senha1", "senha_1"));
		assertTrue(conta2.alteraSenha("senha_2","senha2"));
		
		assertTrue(conta1.autentica("senha_1"));
		assertTrue(conta2.autentica("senha2"));
		
		assertFalse(conta1.alteraSenha("senha1", "senha1"));
		assertFalse(conta2.alteraSenha("senha_2","senha_2"));
	}
}
