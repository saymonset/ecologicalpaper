package com.ecological.paper.subversion;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;
import org.tmatesoft.svn.core.SVNException;

import com.ecological.paper.usuario.Usuario;
import com.util.file.Archivo;


public class HistoryTest {
	private static SubVersionUsuario subVersionUsuario;
	private static Usuario usuario;
	private static URI uri;
	private static Archivo archivo;
	@BeforeClass
	public static void setUpClass() throws Exception {
		/*subVersionUsuario = new SubVersionUsuario();
		//subVersionUsuario
			//	.setUrlsubversion("http://linksys-sinergytec.dyndns.org:8080/seguros_caroni/test/");
		subVersionUsuario
		.setUrlsubversion("http://localhost/repositorioSVN/cunaguaro/produccion");
		
		subVersionUsuario
				.setUrlsubversionUpload("http://localhost/repositorioSVN/cunaguaro/produccion");
		subVersionUsuario.setUsuariosubversion("simon");
		subVersionUsuario.setPasswordsubversion("123456");
		usuario = new Usuario();
		usuario.setId(new Long(4));
		subVersionUsuario.setUsuario(usuario);
		subVersionUsuario.setDir("http://localhost/repositorioSVN/cunaguaro/produccion");
		subVersionUsuario.setComentario("nueva foto de produccion");

		archivo = new Archivo();

		URL url = RepositorioSVNTest.class.getResource("./");
		uri = url.toURI().resolve(
				"../../../../../../test/com/ecological/paper/recursos/");
		// System.out.println("patch="+uri.getPath());

		InputStream inputStream = new FileInputStream(new File(
				uri.resolve("plantilla.zip")));

		File file = archivo.fileDesdeStream(inputStream, "plantilla", "zip");
		subVersionUsuario.setFile(file);*/
	}
	
	@Test
	public void replicaRepositorio() {
		History history = new History();
		history.principal();

	

	}
}
