package com.ecological.paper.subversion;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.tmatesoft.svn.core.SVNException;

import com.ecological.paper.usuario.Usuario;
import com.util.file.Archivo;

import static org.junit.Assert.assertEquals;

public class RepositorioSVNTest {
	private static SubVersionUsuario subVersionUsuario;
	private static Usuario usuario;
	private static URI uri;
	private static Archivo archivo;

	@BeforeClass
	public static void setUpClass() throws Exception {
		subVersionUsuario = new SubVersionUsuario();
		subVersionUsuario
				.setUrlsubversion("http://linksys-sinergytec.dyndns.org:8080/seguros_caroni/");
		subVersionUsuario
				.setUrlsubversionUpload("http://linksys-sinergytec.dyndns.org:8080/seguros_caroni/");
		subVersionUsuario.setUsuariosubversion("Anonymous");
		subVersionUsuario.setPasswordsubversion("Anonymous");
		usuario = new Usuario();
		usuario.setId(new Long(4));
		subVersionUsuario.setUsuariosubversion("shirly");
		subVersionUsuario.setPasswordsubversion("123456");
		subVersionUsuario.setDir("test20");

		archivo = new Archivo();

		URL url = RepositorioSVNTest.class.getResource("./");
		uri = url.toURI().resolve(
				"../../../../../../test/com/ecological/paper/recursos/");
		System.out.println("****************************************************");
		System.out.println("patch="+uri.getPath());
		System.out.println("****************************************************");
	 

		InputStream inputStream = new FileInputStream(new File(
				uri.resolve("plantilla.zip")));

		File file = archivo.fileDesdeStream(inputStream, "plantilla", "zip");
		subVersionUsuario.setFile(file);
	}

	@Ignore
	@Test
	public void testSubirInRepositorio() {
		fail("Not yet implemented");
	}

	@Ignore  
	@Test
	public void testAddFile() {
		RepositorioSVN tester = new RepositorioSVN();
		try {
			tester.subirFileZipInRepositorio(subVersionUsuario);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 
	@Test
	public void testCrearDirInRepositorio() {
		RepositorioSVN tester = new RepositorioSVN();
		tester.crearDirInRepositorio(subVersionUsuario);
	}

	/*vrae un brancjh con la copia del anteroior.. no funciona
	 * */
	@Ignore 
	@Test
	public void createBranchesOrTagsRepositorio() {
		RepositorioSVN tester = new RepositorioSVN();
		try {
			tester.createBranchesOrTagsRepositorio(subVersionUsuario);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Ignore  
	@Test
	public void testDeleteInRepositorio() {
		RepositorioSVN tester = new RepositorioSVN();
		 
			tester.deleteInRepositorio(subVersionUsuario);
		 

	}

}
