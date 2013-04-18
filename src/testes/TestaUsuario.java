package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dados.som.Som;
import dados.usuario.Conta;
import dados.usuario.ContaBasica;
import dados.usuario.Usuario;
import dados.util.CompositionRule;
import dados.util.OurTime;
import exceptions.system.SomInvalidoException;
import exceptions.user.EmailInvalidoException;
import exceptions.user.ListaInvalidaException;
import exceptions.user.LoginInvalidoException;
import exceptions.user.NomeInvalidoException;
import exceptions.user.UsuarioInvalidoException;

public class TestaUsuario {
	
	private Usuario usuario;
	private Conta contaUsuario;

	@Before
	public void setUp() throws EmailInvalidoException, LoginInvalidoException, NomeInvalidoException{
		contaUsuario = new ContaBasica("Joazito", "joao123");
		usuario = new Usuario("João da Silva Pinto", contaUsuario , "joao.silva@gmail.com", 0);
	}

	@Test
	public void testaGetNome() {
		assertEquals("João da Silva Pinto", usuario.getNome());
	}

	@Test
	public void testGetLogin() {	
		assertEquals("Joazito", usuario.getLogin());
	}

	@Test
	public void testGetEmail() {
		assertEquals("joao.silva@gmail.com", usuario.getEmail());
	}
	
	@Test
	public void testaGetConta(){
		assertEquals(contaUsuario, usuario.getConta());
	}
	
	@Test
	public void testaAutenticaConta(){
		assertTrue(usuario.autenticaConta("joao123"));
	}
	
	@Test
	public void testaAddGetFonteDeSom(){
		usuario.addFonteDeSom(1);
		List<Integer> fonteDeSons = new ArrayList<Integer>();
		fonteDeSons.add(1);
		assertEquals(fonteDeSons, usuario.getFontesDeSons());
	}
	
	@Test
	public void testaEquals() throws NomeInvalidoException, EmailInvalidoException, LoginInvalidoException{
		Usuario us1,us2,us3,us4;
		us1 = new Usuario("nome", new ContaBasica("nickname","senha"), "email", 1);
		us2 = new Usuario("nome", new ContaBasica("nickname","senha"), "gmail", 1);
		us3 = new Usuario("nome", new ContaBasica("nickname","senha"), "email", 2);
		us4 = new Usuario("nome", new ContaBasica("nickname","senha"), "email", 1);
		
		assertTrue(us1.equals(us1));
		assertTrue(us1.equals(us4));
		
		assertFalse(us1.equals(us2));
		assertFalse(us1.equals(us3));
		assertFalse(us1.equals(null));
		assertFalse(us1.equals(""));
	
	}
	
	@Test
	public void testaAddGetSeguidores() throws NomeInvalidoException, EmailInvalidoException, LoginInvalidoException{
		Usuario seguidor = new Usuario("Nome", new ContaBasica("nickname", "senha"), "email", 1);
		List<Integer> listaSeguidores = new ArrayList<Integer>();
		
		usuario.addSeguidor(seguidor);
		listaSeguidores.add(seguidor.getID());
	
		assertEquals(listaSeguidores, usuario.getListaDeSeguidores());
	}
	
	@Test
	public void testaVisaoDeSons(){
		List<Integer> visaoDeSons = new ArrayList<Integer>();
		
		visaoDeSons.add(1);	
		usuario.addSomNaVisao(1);
		
		assertEquals(visaoDeSons,usuario.getVisaoDeSons());
		
		visaoDeSons.add(2); visaoDeSons.add(3);
		usuario.addSonsNaVisao(visaoDeSons);
		
		visaoDeSons.add(1);
		assertEquals(visaoDeSons,usuario.getVisaoDeSons());
	}
	
	@Test
	public void testaFeedPrincipal(){
		List<Integer> feedPrincipal = new ArrayList<Integer>();
		
		feedPrincipal.add(1);	
		usuario.addSomNoFeedPrincipal(1);
		
		assertEquals(feedPrincipal,usuario.getFeedPrincipal());
		
		feedPrincipal.add(2); feedPrincipal.add(3);
		usuario.addSonsNoFeedPrincipal(feedPrincipal);
		
		feedPrincipal.add(1);
		assertEquals(feedPrincipal,usuario.getFeedPrincipal());
	}
	
	@Test
	public void testaFeedSecundario(){
		List<Integer> feedSecundario = new ArrayList<Integer>();
		
		feedSecundario.add(1);	
		usuario.addSomNoFeedSecundario(1);
		
		assertEquals(feedSecundario,usuario.getFeedExtra());
		
		feedSecundario.add(2); feedSecundario.add(3);
		usuario.addSonsNoFeedSecundario(feedSecundario);
		
		feedSecundario.add(1);
		assertEquals(feedSecundario,usuario.getFeedExtra());
	}
	

	@Test
	public void testaSonsFavoritos(){
		List<Integer> sonsFavoritos = new ArrayList<Integer>();
		
		sonsFavoritos.add(1);	
		usuario.favoritaSom(1);
		
		assertEquals(sonsFavoritos,usuario.getSonsFavoritos());
		
		sonsFavoritos.add(2); 
		usuario.favoritaSom(2);
		
		Collections.sort(sonsFavoritos, new Comparator<Integer>(){

			@Override
			public int compare(Integer arg0, Integer arg1) {
				return arg1 - arg0;
			}
		}
			);
		
		assertEquals(sonsFavoritos,usuario.getSonsFavoritos());
	}
	
	@Test
	public void testaListaDeUsuario() throws NomeInvalidoException, ListaInvalidaException, UsuarioInvalidoException{
		usuario.criaListaDeUsuario(1, "Nome da Lista");
		
		usuario.adicionaNaListaDeUsuarios(1, 1);	
		assertTrue(usuario.getListaDeUsuarios(1).jaTemUsuario(1));
		
		usuario.adicionaNaListaDeUsuarios(1, 2);				
		assertTrue(usuario.getListaDeUsuarios(1).jaTemUsuario(2));
		
		
		List<Integer> usuariosNaLista = new ArrayList<Integer>();
		usuariosNaLista.add(2);  
		usuariosNaLista.add(1);
		assertEquals(usuariosNaLista, usuario.getListaDeUsuarios(1).getUsuarios());
	}
	
	
	

	
	@Test
	public void testaFavoritagensDeSons(){
		int contadorUser1Favoritado,contadorUser2Favoritado;
		
		for(contadorUser1Favoritado = 0; contadorUser1Favoritado < 10; contadorUser1Favoritado++)
			usuario.atualizaFavoritagensDoUsuario(1);
		for(contadorUser2Favoritado = 0; contadorUser2Favoritado < 15; contadorUser2Favoritado++)
			usuario.atualizaFavoritagensDoUsuario(2);
		
		assertEquals(contadorUser1Favoritado , (int) usuario.getFavoritagensDoUsuario(1));
		assertEquals(contadorUser2Favoritado , (int) usuario.getFavoritagensDoUsuario(2));
		assertEquals(0 , (int) usuario.getFavoritagensDoUsuario(3));
	}
	
	@Test
	public void testaRule(){
		usuario.setRule(CompositionRule.Rule1);
		assertEquals(CompositionRule.Rule1, usuario.getRule());
		
		usuario.setRule(CompositionRule.Rule2);
		assertEquals(CompositionRule.Rule2, usuario.getRule());
		
		usuario.setRule(CompositionRule.Rule3);
		assertEquals(CompositionRule.Rule3, usuario.getRule());
	}
	
	@Test
	public void testaSons() throws SomInvalidoException{
		Som s1,s2;
		List<Integer> perfilMusical = new ArrayList<Integer>();
		
		s1 = new Som("http://www.youtube.com/watch?v=jFS4kqOYbAk", new OurTime(),  1, 1);
		s2 = new Som("http://www.youtube.com/watch?v=GQCQuQtop-M&list=PL239AAED141C78529", new OurTime(), 2, 1);
		usuario.postaSom(s1);
		usuario.postaSom(s2);
		
		perfilMusical.add(s1.getID());perfilMusical.add(s2.getID());
		
		Collections.sort(perfilMusical, new Comparator<Integer>(){

			@Override
			public int compare(Integer arg0, Integer arg1) {
				return arg1 - arg0;
			}
		});
		
		assertEquals(perfilMusical, usuario.getPerfilMusical());
	}
	
	
	
	
	

	@Test(expected = NomeInvalidoException.class)
	public void testaNomeInvalido1() throws EmailInvalidoException, LoginInvalidoException, NomeInvalidoException{
		contaUsuario = new ContaBasica("Jiaozito", "jiao123");
		usuario = new Usuario(null, contaUsuario , "jiao.silva@gmail.com", 1);
	}

	@Test(expected = NomeInvalidoException.class)
	public void testaNomeInvalido2() throws EmailInvalidoException, LoginInvalidoException, NomeInvalidoException{
		contaUsuario = new ContaBasica("Zezim", "ze123");
		usuario = new Usuario("", contaUsuario , "zezim.silva@gmail.com", 1);
	}
	
	@Test(expected = NomeInvalidoException.class)
	public void testaNomeInvalido3() throws NomeInvalidoException{
		usuario.criaListaDeUsuario(1,"");
	}
	
	@Test(expected = NomeInvalidoException.class)
	public void testaNomeInvalido4() throws NomeInvalidoException{
		usuario.criaListaDeUsuario(1,null);
	}
	
	@Test(expected = NomeInvalidoException.class)
	public void testaNomeInvalido5() throws NomeInvalidoException{
		usuario.criaListaDeUsuario(1,"Nome da Lista");
		usuario.criaListaDeUsuario(2,"Nome da Lista");
	}
	
	@Test(expected = ListaInvalidaException.class)
	public void testalistaInvalida1() throws ListaInvalidaException {
		usuario.getListaDeUsuarios(-1);
	}
	
	@Test(expected = ListaInvalidaException.class)
	public void testaListaInvalida2() throws ListaInvalidaException, NomeInvalidoException, UsuarioInvalidoException {
		usuario.criaListaDeUsuario(1, "Nome da Lista");
		usuario.adicionaNaListaDeUsuarios(2, 1);
	}
	
	@Test(expected = UsuarioInvalidoException.class)
	public void testaUsuarioInvalido1() throws ListaInvalidaException, NomeInvalidoException, UsuarioInvalidoException {
		usuario.criaListaDeUsuario(1, "Nome da Lista");
		usuario.adicionaNaListaDeUsuarios(1, 1);
		usuario.adicionaNaListaDeUsuarios(1, 1);
	}
	
	

	@Test(expected = EmailInvalidoException.class)
	public void testaEmailInvalido1() throws EmailInvalidoException, LoginInvalidoException, NomeInvalidoException{
		contaUsuario = new ContaBasica("Jiaozito", "jiao123");
		usuario = new Usuario("Jião da Silva Pinto", contaUsuario , null, 1);
	}


	@Test(expected = EmailInvalidoException.class)
	public void testaEmailInvalido2() throws EmailInvalidoException, LoginInvalidoException, NomeInvalidoException{
		contaUsuario = new ContaBasica("Zezim", "ze123");
		usuario = new Usuario("Zé da Silva Pinto", contaUsuario , "", 1);
	}
	

	

	
}
