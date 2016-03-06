package com.ecological.paper.subversion;

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


public class ReplicateTest {
	private static SubVersionUsuario subVersionUsuario;
	private static Usuario usuario;
	private static URI uri;
	private static Archivo archivo;
	@BeforeClass
	public static void setUpClass() throws Exception {
		subVersionUsuario = new SubVersionUsuario();
		subVersionUsuario
				.setUrlsubversion("http://linksys-sinergytec.dyndns.org:8080/seguros_caroni/test/");
		subVersionUsuario
				.setUrlsubversionUpload("http://linksys-sinergytec.dyndns.org:8080/seguros_caroni/test/");
		subVersionUsuario.setUsuariosubversion("shirly");
		subVersionUsuario.setPasswordsubversion("123456");
		usuario = new Usuario();
		usuario.setId(new Long(4));
		subVersionUsuario.setUsuario(usuario);
		subVersionUsuario.setDir("http://linksys-sinergytec.dyndns.org:8080/seguros_caroni/test5/");
		subVersionUsuario.setComentario("nueva foto de produccion");

		archivo = new Archivo();

		URL url = RepositorioSVNTest.class.getResource("./");
		uri = url.toURI().resolve(
				"../../../../../../test/com/ecological/paper/recursos/");
		// System.out.println("patch="+uri.getPath());

		InputStream inputStream = new FileInputStream(new File(
				uri.resolve("plantilla.zip")));

		File file = archivo.fileDesdeStream(inputStream, "plantilla", "zip");
		subVersionUsuario.setFile(file);
	}
	
 @Ignore
	@Test
	public void replicaRepositorio() {
		RepositorioSVN tester = new RepositorioSVN();
		try {
			tester.createBranchesOrTagsRepositorio(subVersionUsuario);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
