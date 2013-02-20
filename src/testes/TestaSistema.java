package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.system.Sistema;
import data.user.ContaBasica;
import data.user.Usuario;
import data.util.OurTime;

import exceptions.EmailInvalidoException;
import exceptions.EmailJaExisteException;
import exceptions.LoginInvalidoException;
import exceptions.LoginJaExisteException;
import exceptions.NomeInvalidoException;
import exceptions.UsuarioInexistenteException;

public class TestaSistema {
	
	private Sistema sistema;

	
	@Before
	public void setUp(){
		sistema = new Sistema();
		sistema.criarUsuario("Joao.Silva", "joao123", "Joao Silva Diniz", "Joao.Silva@gmail.com");
	}

	@Test(expected = LoginInvalidoException.class)
	public void testaLoginInválido(){
		sistema.abrirSessao(null,"joao123");
		sistema.abrirSessao("","joao123");
		sistema.abrirSessao("Joao.Silva","jiao123");
		sistema.abrirSessao("Joao.Silva","ze123");
	}
	

	@Test(expected = UsuarioInexistenteException.class)
	public void testaUsuarioINexistente() {
		sistema.abrirSessao("Jiao.Silva","joao123");
	}
	
	@Test(expected = EmailJaExisteException.class)
	public void testaEmailJaExiste(){
		sistema.criarUsuario("Jiao.Silva", "jiao123", "Jiao Silva Diniz", "Joao.Silva@gmail.com");
	}
	
	@Test(expected = LoginJaExisteException.class )
	public void testaLoginJaExisteException(){
		sistema.criarUsuario("Joao.Silva", "jiao123", "Jiao Silva Diniz", "Jiao.Silva@gmail.com");
	}
	
	@Test(expected = DataDeCriacaoInvalidaException.class)
	public void testaDataDeCriacaoInvalida(){
		int idSessao = sistema.abrirSessao("Joao.Silva", "joao123");
		sistema.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", new OurTime(20, 12, 2012));
		//sistema.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", new OurTime(12, 30, 2012));
		//sistema.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", new OurTime(32, 11, 2012));
		//sistema.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", new OurTime(29, 02, 2015));
		//sistema.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", new OurTime(31, 04, 2015));
		sistema.postarSom(idSessao,"http://www.youtube.com/watch?v=r-fIOrUTIOQ", new OurTime(30, 04, 15));
	}
}
