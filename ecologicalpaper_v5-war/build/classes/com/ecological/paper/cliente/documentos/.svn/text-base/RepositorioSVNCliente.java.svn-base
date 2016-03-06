package com.ecological.paper.cliente.documentos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.EJB;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNErrorMessage;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.util.SVNPathUtil;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCopyClient;
import org.tmatesoft.svn.core.wc.SVNCopySource;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.ecological.delegados.ServicioDelegado;
import com.ecological.paper.delegado.DelegadoEJBLocal;
import com.ecological.paper.documentos.Doc_maestro;
import com.ecological.paper.ecologicaexcepciones.EcologicaExcepciones;
import com.ecological.paper.ecologicaexcepciones.ExceptionSubVersion;
import com.ecological.paper.subversion.Repositorio;
import com.ecological.paper.subversion.RepositorioSVN;
import com.ecological.paper.subversion.SubVersionUsuario;
import com.ecological.paper.subversion.SvnExtension;
import com.ecological.paper.subversion.SvnRutasRelativasFiles;
import com.util.Utilidades;
import com.util.file.Archivo;
import com.util.file.ZipearDoc;

public class RepositorioSVNCliente {
	private ServicioDelegado delegado = new ServicioDelegado();
	private List<Repositorio> repositorioall = new ArrayList<Repositorio>();
	private List<String> files = new ArrayList<String>();

	public RepositorioSVNCliente() {
		setupLibrary();
	}

	public void crearDirInRepositorio(SubVersionUsuario subVersionUsuario) {
		long startRevision = 0;
		long endRevision = -1;
		ZipearDoc zipearDoc = new ZipearDoc();
		SVNRepository repository = null;
		try {
			repository = SVNRepositoryFactory
					.create(SVNURL.parseURIDecoded(subVersionUsuario
							.getUrlsubversionUpload()));

			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(
							subVersionUsuario.getUsuariosubversion(),
							subVersionUsuario.getPasswordsubversion());
			repository.setAuthenticationManager(authManager);

			/*
			 * Get type of the node located at URL we used to create
			 * SVNRepository.
			 * 
			 * "" (empty string) is path relative to that URL, -1 is value that
			 * may be used to specify HEAD (latest) revision.
			 */
			SVNNodeKind nodeKind = null;

			try {
				nodeKind = repository.checkPath("", -1);
				/*
				 * Checks up if the current path really corresponds to a
				 * directory. If it doesn't, the program exits. SVNNodeKind is
				 * that one who says what is located at a path in a revision.
				 */
				if (nodeKind == SVNNodeKind.NONE) {
					SVNErrorMessage err = SVNErrorMessage.create(
							SVNErrorCode.UNKNOWN, "No entry at URL ''{0}''",
							SVNURL.parseURIDecoded(subVersionUsuario
									.getUrlsubversionUpload()));
					throw new SVNException(err);
				} else if (nodeKind == SVNNodeKind.FILE) {
					SVNErrorMessage err = SVNErrorMessage
							.create(SVNErrorCode.UNKNOWN,
									"Entry at URL ''{0}'' is a file while directory was expected",
									SVNURL.parseURIDecoded(subVersionUsuario
											.getUrlsubversionUpload()));
					throw new SVNException(err);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			/*
			 * Get exact value of the latest (HEAD) revision.
			 */
			long latestRevision = repository.getLatestRevision();

			/*
			 * Gets an editor for committing the changes to the repository.
			 * NOTE: you must not invoke methods of the SVNRepository until you
			 * close the editor with the ISVNEditor.closeEdit() method.
			 * 
			 * commitMessage will be applied as a log message of the commit.
			 * 
			 * ISVNWorkspaceMediator instance will be used to store temporary
			 * files, when 'null' is passed, then default system temporary
			 * directory will be used to create temporary files.
			 */
			ISVNEditor editor = repository.getCommitEditor(
					"directory and file added", null);
			SVNCommitInfo commitInfo = null;
			try {

				commitInfo = addDirOnly(editor, subVersionUsuario.getDir());
			} catch (Exception e) {
				e.printStackTrace();

			}

		} catch (Exception e) {
			// /e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void verificarFileExisteInRepositorio(
			SubVersionUsuario subVersionUsuario) {
		long startRevision = 0;
		long endRevision = -1;
		ZipearDoc zipearDoc = new ZipearDoc();
		SVNRepository repository = null;
		Archivo archivoUtil = new Archivo();
		// siempre sera vacio
		subVersionUsuario.setDir("");

		try {
			repository = SVNRepositoryFactory
					.create(SVNURL.parseURIDecoded(subVersionUsuario
							.getUrlsubversionUpload()));

			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(
							subVersionUsuario.getUsuariosubversion(),
							subVersionUsuario.getPasswordsubversion());
			repository.setAuthenticationManager(authManager);

			repositorioall = new ArrayList<Repositorio>();
			RepositorioSVN repositorioSVN = new RepositorioSVN();
			// la collecion files.. la pasamos por referencia..
			files = new ArrayList<String>();
			metodoRecursivoDirectoriosFiles("", endRevision, null, files,
					repository);

			// obtenemos todos los archivos y chequeamos..

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void subirFileZipInRepositorio(SubVersionUsuario subVersionUsuario)
			throws SVNException,EcologicaExcepciones {
		long startRevision = 0;
		long endRevision = -1;
		ZipearDoc zipearDoc = new ZipearDoc();
		SVNRepository repository = null;
		Archivo archivoUtil = new Archivo();
		String fecha = Utilidades.sdfShowConvert.format(new Date());
		fecha = fecha.replace(" ", "_").toLowerCase();
		fecha = fecha.replace("-", "_").toLowerCase();
		fecha = fecha.replace(":", "_").toLowerCase();
		// siempre sera vacio
		subVersionUsuario.setDir("");
		List<File> archivos = null;
		

		try {

			repository = SVNRepositoryFactory
					.create(SVNURL.parseURIDecoded(subVersionUsuario
							.getUrlsubversionUpload()));

			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(
							subVersionUsuario.getUsuariosubversion(),
							subVersionUsuario.getPasswordsubversion());
			repository.setAuthenticationManager(authManager);

			/*
			 * Get type of the node located at URL we used to create
			 * SVNRepository.
			 * 
			 * "" (empty string) is path relative to that URL, -1 is value that
			 * may be used to specify HEAD (latest) revision.
			 */
			SVNNodeKind nodeKind = repository.checkPath("", -1);

			/*
			 * Checks up if the current path really corresponds to a directory.
			 * If it doesn't, the program exits. SVNNodeKind is that one who
			 * says what is located at a path in a revision.
			 */
			if (nodeKind == SVNNodeKind.NONE) {
				SVNErrorMessage err = SVNErrorMessage.create(
						SVNErrorCode.UNKNOWN, "No entry at URL ''{0}''", SVNURL
								.parseURIDecoded(subVersionUsuario
										.getUrlsubversionUpload()));
				throw new SVNException(err);
			} else if (nodeKind == SVNNodeKind.FILE) {
				SVNErrorMessage err = SVNErrorMessage
						.create(SVNErrorCode.UNKNOWN,
								"Entry at URL ''{0}'' is a file while directory was expected",
								SVNURL.parseURIDecoded(subVersionUsuario
										.getUrlsubversionUpload()));
				throw new SVNException(err);
			}

			/*
			 * Get exact value of the latest (HEAD) revision.
			 */
			long latestRevision = repository.getLatestRevision();

			/*
			 * Gets an editor for committing the changes to the repository.
			 * NOTE: you must not invoke methods of the SVNRepository until you
			 * close the editor with the ISVNEditor.closeEdit() method.
			 * 
			 * commitMessage will be applied as a log message of the commit.
			 * 
			 * ISVNWorkspaceMediator instance will be used to store temporary
			 * files, when 'null' is passed, then default system temporary
			 * directory will be used to create temporary files.
			 */
			ISVNEditor editor = null;
			SVNCommitInfo commitInfo = null;

			try {

				archivos = zipearDoc.unZip2(subVersionUsuario.getFile());

			} catch (Exception e) {
				if (subVersionUsuario.getFile() != null
						&& subVersionUsuario.getFile().length() > 0) {
					archivos = new ArrayList<File>();
					archivos.add(subVersionUsuario.getFile());
				}
			}

			byte[] bytes = null;
			// add /A directory
			RepositorioSVN repositorioSVN = new RepositorioSVN();
			for (File f : archivos) {
				bytes = new byte[(int) f.length()];
				try {
					FileInputStream fileInputStream = new FileInputStream(f);
					fileInputStream.read(bytes);

				} catch (FileNotFoundException e) {
					System.out.println("File Not Found.");
					e.printStackTrace();
				}

				boolean swExtension = true;
				String nombre = f.getName();

				subVersionUsuario.setDir("");
				boolean swUpload=true;
				long ultimaRevisionProduccion=repositorioSVN.lastInfoBranches(subVersionUsuario,swUpload);
				
				
				// en caso las rutas relativs vengan por un flow
				if (subVersionUsuario.getFlow_ParticipantesAttachment() != null) {
					SvnRutasRelativasFiles svnRutasRelativasFiles = delegado
							.findSvnRutasRelativasFiles(subVersionUsuario
									.getFlow_ParticipantesAttachment(), nombre);
					if (svnRutasRelativasFiles != null) {
						System.out.println("svnRutasRelativasFiles.getRevision()="+svnRutasRelativasFiles.getRevision());
						if (!subVersionUsuario.isSwChequeaRevision()){
							subVersionUsuario.setSwChequeaRevision(((svnRutasRelativasFiles.getRevision()-ultimaRevisionProduccion)==0));
						}
						if (!subVersionUsuario.isSwChequeaRevision()){
							throw new EcologicaExcepciones(ultimaRevisionProduccion+"");
						}
						
						subVersionUsuario.setDir(svnRutasRelativasFiles
								.getUrlArchivoRelativo());
					}
				}
				// en caso las rutas relativs vengan por un documento
				if (subVersionUsuario.getDoc_maestro() != null) {
					SvnRutasRelativasFiles svnRutasRelativasFiles = delegado
							.findSvnRutasRelativasFiles(
									subVersionUsuario.getDoc_maestro(), nombre);
					if (svnRutasRelativasFiles != null) {
						
						System.out.println("svnRutasRelativasFiles.getRevision()="+svnRutasRelativasFiles.getRevision());
						if (!subVersionUsuario.isSwChequeaRevision()){
							subVersionUsuario.setSwChequeaRevision(((svnRutasRelativasFiles.getRevision()-ultimaRevisionProduccion)==0));
						}
						if (!subVersionUsuario.isSwChequeaRevision()){
							throw new EcologicaExcepciones(ultimaRevisionProduccion+"");
						}

						
						subVersionUsuario.setDir(svnRutasRelativasFiles
								.getUrlArchivoRelativo());
					}
				}
				try {
				 
					String remplazoUrlRelativa = remplazoUrlRelativa(subVersionUsuario);
					subVersionUsuario.setDir(remplazoUrlRelativa);
					System.out.println("subVersionUsuario dir="
							+ subVersionUsuario.getDir());
					System.out.println("path completo="
							+ subVersionUsuario.getUrlsubversionUpload()
							+ subVersionUsuario.getDir());
					repository = SVNRepositoryFactory.create(SVNURL
							.parseURIDecoded(subVersionUsuario
									.getUrlsubversionUpload()
									+ subVersionUsuario.getDir()));

					authManager = SVNWCUtil.createDefaultAuthenticationManager(
							subVersionUsuario.getUsuariosubversion(),
							subVersionUsuario.getPasswordsubversion());
					repository.setAuthenticationManager(authManager);

					editor = repository.getCommitEditor(subVersionUsuario.getComentario()
							+ " file contents sumado " + nombre
							+" "+"ecologicalpaper:"
							+ subVersionUsuario.getUsuario() + ":" + fecha
							  , null);
					commitInfo = addFileOnly(editor,
							subVersionUsuario.getDir(), nombre, bytes);

					System.out.println("The file was added: " + nombre);
				} catch (Exception e) {
					/*
					 * Modify file added at the previous revision.
					 */

					String dir1 = "";
					if (subVersionUsuario.getDir() != null
							&& subVersionUsuario.getDir().endsWith("/")) {
						dir1 = subVersionUsuario.getDir();
					} else {
						dir1 = "/" + subVersionUsuario.getDir();
					}
					if ("/".equalsIgnoreCase(dir1)) {
						dir1 = "";
					}
					System.out.println("modificando...");
					System.out
							.println("subVersionUsuario.getUrlsubversionUpload()"
									+ subVersionUsuario
											.getUrlsubversionUpload());
					System.out.println("subVersionUsuario dir="
							+ subVersionUsuario.getDir());
					System.out.println("path completo="
							+ subVersionUsuario.getUrlsubversionUpload()
							+ subVersionUsuario.getDir());

					String filePath = nombre;
					System.out.println(filePath);

					repository = SVNRepositoryFactory.create(SVNURL
							.parseURIDecoded(subVersionUsuario
									.getUrlsubversionUpload()
									+ subVersionUsuario.getDir()));

					authManager = SVNWCUtil.createDefaultAuthenticationManager(
							subVersionUsuario.getUsuariosubversion(),
							subVersionUsuario.getPasswordsubversion());
					repository.setAuthenticationManager(authManager);
					nodeKind = repository.checkPath(filePath.trim(), -1);
					if (nodeKind == SVNNodeKind.NONE) {

					} else if (nodeKind == SVNNodeKind.DIR) {

					} else {
						SVNProperties fileProperties = new SVNProperties();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						repository.getFile(filePath, -1, fileProperties, baos);

						byte[] oldFile = baos.toByteArray();
						editor = repository.getCommitEditor(
								subVersionUsuario.getComentario()
								+ " file contents changed"+" "+"ecologicalpaper:"
								+ subVersionUsuario.getUsuario() + ":" + fecha
								+ " " , null);
						commitInfo = modifyFile(editor,
								subVersionUsuario.getDir(), nombre, oldFile,
								bytes);
						System.out.println("The file was changed: "
								+ commitInfo);

					}
				}

			}
		} catch (EcologicaExcepciones e) {
			throw new EcologicaExcepciones(e.getMessage());

		} catch (SVNException e) {
			SVNErrorMessage err = SVNErrorMessage.create(SVNErrorCode.UNKNOWN,
					e.getMessage(), SVNURL.parseURIDecoded(subVersionUsuario
							.getUrlsubversionUpload()));
			throw new SVNException(err);
			// TODO: handle exception
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			if (archivos != null) {
				for (File f : archivos) {
					f.delete();
				}
			}

		}
	}

	public String remplazoUrlRelativa(SubVersionUsuario subVersionUsuario) {
		System.out.println("*************************************");
		System.out.println("subVersionUsuario.getUrlarchivorelativoUpload()="+subVersionUsuario.getUrlarchivorelativoUpload());
		System.out.println("subVersionUsuario.getDir()="+subVersionUsuario.getDir());
		StringTokenizer urlSustituyeSTK = new StringTokenizer(
				subVersionUsuario.getUrlarchivorelativoUpload(), "/,\\");
		List<String> urlSustituye = new ArrayList<String>();
		while (urlSustituyeSTK.hasMoreElements()) {
			String st = (String) urlSustituyeSTK.nextElement();
			urlSustituye.add(st);
		}

		StringTokenizer urlRelativoSTK = new StringTokenizer(
				subVersionUsuario.getDir(), "/,\\");
		List<String> urlRelativo = new ArrayList<String>();
		while (urlRelativoSTK.hasMoreElements()) {
			String st = (String) urlRelativoSTK.nextElement();
			urlRelativo.add(st);
		}
		String newCadena = "";

		if (urlRelativo.size() <= urlSustituye.size()) {
			// se manda vacio, porque la urlUpload `rincipal ya tiene el nombvre
			// de la aplicacion y los modulos ya concatenados..
			System.out.println("newCadena="+newCadena);
			return newCadena;
		}

		if (urlRelativo.size() > urlSustituye.size()) {
			int i = 0;
			for (String cad : urlRelativo) {
				if (urlSustituye.size() >= (i + 1)) {
					// se manda vacio, porque la urlUpload `rincipal ya tiene el
					// nombvre
					// de la aplicacion y los modulos ya concatenados..
					++i;
					continue;
				}
				
				newCadena += "/" + cad;

			}
			System.out.println("newCadena="+newCadena);
			return newCadena;
		}

		return "";
	}

	// crear un branches o tags
	public void createBranchesOrTagsRepositorio(
			SubVersionUsuario subVersionUsuario) throws SVNException {
		long startRevision = 0;
		long endRevision = -1;
		ZipearDoc zipearDoc = new ZipearDoc();
		SVNRepository repository = null;
		try {

			repository = SVNRepositoryFactory
					.create(SVNURL.parseURIDecoded(subVersionUsuario
							.getUrlsubversionUpload()));

			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(
							subVersionUsuario.getUsuariosubversion(),
							subVersionUsuario.getPasswordsubversion());
			repository.setAuthenticationManager(authManager);

			/*
			 * Get type of the node located at URL we used to create
			 * SVNRepository.
			 * 
			 * "" (empty string) is path relative to that URL, -1 is value that
			 * may be used to specify HEAD (latest) revision.
			 */
			SVNNodeKind nodeKind = repository.checkPath("", -1);

			/*
			 * Checks up if the current path really corresponds to a directory.
			 * If it doesn't, the program exits. SVNNodeKind is that one who
			 * says what is located at a path in a revision.
			 */
			if (nodeKind == SVNNodeKind.NONE) {
				SVNErrorMessage err = SVNErrorMessage.create(
						SVNErrorCode.UNKNOWN, "No entry at URL ''{0}''", SVNURL
								.parseURIDecoded(subVersionUsuario
										.getUrlsubversionUpload()));
				throw new SVNException(err);
			} else if (nodeKind == SVNNodeKind.FILE) {
				SVNErrorMessage err = SVNErrorMessage
						.create(SVNErrorCode.UNKNOWN,
								"Entry at URL ''{0}'' is a file while directory was expected",
								SVNURL.parseURIDecoded(subVersionUsuario
										.getUrlsubversionUpload()));
				throw new SVNException(err);
			}

			SVNClientManager clientManager = SVNClientManager.newInstance();
			clientManager.setAuthenticationManager(authManager);

			SVNURL reposURL = SVNURL.parseURIDecoded(subVersionUsuario
					.getUrlsubversionUpload());

			// SVNURL targetURL =
			// SVNURL.parseURIDecoded(subVersionUsuario.getDir());

			// 3. copy A to A_copy in the repository (url-to-url copy)
			SVNCopyClient copyClient = clientManager.getCopyClient();

			SVNURL A_URL = reposURL.appendPath("", true);
			System.out.println(subVersionUsuario.getDir());
			SVNURL copyTargetURL = SVNURL.parseURIDecoded(subVersionUsuario
					.getDir());// reposURL.appendPath(subVersionUsuario.getDir(),
								// true);

			SVNCopySource copySource = new SVNCopySource(SVNRevision.UNDEFINED,
					SVNRevision.HEAD, A_URL);
			SVNCommitInfo info = copyClient.doCopy(
					new SVNCopySource[] { copySource }, copyTargetURL, false,
					false, true, subVersionUsuario.getComentario(), null);
			// print out new revision info
			// System.out.println(info);

		} catch (Exception e) {
			SVNErrorMessage err = SVNErrorMessage.create(SVNErrorCode.UNKNOWN,
					e.toString(), SVNURL.parseURIDecoded(subVersionUsuario
							.getUrlsubversionUpload()));
			throw new SVNException(err);
		}
	}

	/*
	 * This method performs how a directory in the repository can be copied to
	 * branch.
	 */
	private static SVNCommitInfo copyDir(ISVNEditor editor, String srcDirPath,
			String dstDirPath, long revision) throws SVNException {
		/*
		 * Always called first. Opens the current root directory. It means all
		 * modifications will be applied to this directory until a next entry
		 * (located inside the root) is opened/added.
		 * 
		 * -1 - revision is HEAD
		 */
		editor.openRoot(-1);

		/*
		 * Adds a new directory that is a copy of the existing one.
		 * 
		 * srcDirPath - the source directory path (relative to the root
		 * directory).
		 * 
		 * dstDirPath - the destination directory path where the source will be
		 * copied to (relative to the root directory).
		 * 
		 * revision - the number of the source directory revision.
		 */
		editor.addDir(dstDirPath, srcDirPath, revision);
		/*
		 * Closes the just added copy of the directory.
		 */
		editor.closeDir();
		/*
		 * Closes the root directory.
		 */
		editor.closeDir();
		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		return editor.closeEdit();
	}

	public void deleteInRepositorio(SubVersionUsuario subVersionUsuario) {
		long startRevision = 0;
		long endRevision = -1;
		ZipearDoc zipearDoc = new ZipearDoc();
		SVNRepository repository = null;
		try {

			repository = SVNRepositoryFactory
					.create(SVNURL.parseURIDecoded(subVersionUsuario
							.getUrlsubversionUpload()));

			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(
							subVersionUsuario.getUsuariosubversion(),
							subVersionUsuario.getPasswordsubversion());
			repository.setAuthenticationManager(authManager);

			/*
			 * Get type of the node located at URL we used to create
			 * SVNRepository.
			 * 
			 * "" (empty string) is path relative to that URL, -1 is value that
			 * may be used to specify HEAD (latest) revision.
			 */
			SVNNodeKind nodeKind = repository.checkPath("", -1);

			/*
			 * Checks up if the current path really corresponds to a directory.
			 * If it doesn't, the program exits. SVNNodeKind is that one who
			 * says what is located at a path in a revision.
			 */
			if (nodeKind == SVNNodeKind.NONE) {
				SVNErrorMessage err = SVNErrorMessage.create(
						SVNErrorCode.UNKNOWN, "No entry at URL ''{0}''", SVNURL
								.parseURIDecoded(subVersionUsuario
										.getUrlsubversionUpload()));
				throw new SVNException(err);
			} else if (nodeKind == SVNNodeKind.FILE) {
				SVNErrorMessage err = SVNErrorMessage
						.create(SVNErrorCode.UNKNOWN,
								"Entry at URL ''{0}'' is a file while directory was expected",
								SVNURL.parseURIDecoded(subVersionUsuario
										.getUrlsubversionUpload()));
				throw new SVNException(err);
			}

			/*
			 * Get exact value of the latest (HEAD) revision.
			 */
			long latestRevision = repository.getLatestRevision();

			/*
			 * Gets an editor for committing the changes to the repository.
			 * NOTE: you must not invoke methods of the SVNRepository until you
			 * close the editor with the ISVNEditor.closeEdit() method.
			 * 
			 * commitMessage will be applied as a log message of the commit.
			 * 
			 * ISVNWorkspaceMediator instance will be used to store temporary
			 * files, when 'null' is passed, then default system temporary
			 * directory will be used to create temporary files.
			 */
			ISVNEditor editor = repository.getCommitEditor("directory deleted",
					null);
			SVNCommitInfo commitInfo = deleteDir(editor,
					subVersionUsuario.getDir());
			System.out.println("The directory was deleted: " + commitInfo);

			// close /A

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Delete directory "test".
	 */
	/*
	 * This method performs committing a deletion of a directory.
	 */
	private static SVNCommitInfo deleteDir(ISVNEditor editor, String dirPath)
			throws SVNException {
		/*
		 * Always called first. Opens the current root directory. It means all
		 * modifications will be applied to this directory until a next entry
		 * (located inside the root) is opened/added.
		 * 
		 * -1 - revision is HEAD
		 */
		editor.openRoot(-1);
		/*
		 * Deletes the subdirectory with all its contents.
		 * 
		 * dirPath is relative to the root directory.
		 */
		editor.deleteEntry(dirPath, -1);
		/*
		 * Closes the root directory.
		 */
		editor.closeDir();
		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		return editor.closeEdit();
	}

	/*
	 * This method performs commiting an addition of a directory containing a
	 * file.
	 */
	private static SVNCommitInfo addFileOnly(ISVNEditor editor, String dirPath,
			String filePath, byte[] data) throws SVNException {
		/*
		 * Always called first. Opens the current root directory. It means all
		 * modifications will be applied to this directory until a next entry
		 * (located inside the root) is opened/added.
		 * 
		 * -1 - revision is HEAD (actually, for a comit editor this number is
		 * irrelevant)
		 */
		editor.openRoot(-1);

		/*
		 * Adds a new file to the just added directory. The file path is also
		 * defined as relative to the root directory.
		 * 
		 * copyFromPath (the 2nd parameter) is set to null and copyFromRevision
		 * (the 3rd parameter) is set to -1 since the file is not added with
		 * history.
		 */
		System.out.println(dirPath);
		editor.addFile(dirPath + "/" + filePath, null, -1);
		/*
		 * The next steps are directed to applying delta to the file (that is
		 * the full contents of the file in this case).
		 */
		editor.applyTextDelta(dirPath + "/" + filePath, null);
		/*
		 * Use delta generator utility class to generate and send delta
		 * 
		 * Note that you may use only 'target' data to generate delta when there
		 * is no access to the 'base' (previous) version of the file. However,
		 * using 'base' data will result in smaller network overhead.
		 * 
		 * SVNDeltaGenerator will call editor.textDeltaChunk(...) method for
		 * each generated "diff window" and then editor.textDeltaEnd(...) in the
		 * end of delta transmission. Number of diff windows depends on the file
		 * size.
		 */
		SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
		String checksum = deltaGenerator.sendDelta(dirPath + "/" + filePath,
				new ByteArrayInputStream(data), editor, true);

		/*
		 * Closes the new added file.
		 */
		editor.closeFile(dirPath + "/" + filePath, checksum);

		/*
		 * Closes the root directory.
		 */
		editor.closeDir();
		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		return editor.closeEdit();
	}

	/*
	 * This method performs commiting an addition of a directory containing a
	 * file.
	 */
	private static SVNCommitInfo addDir(ISVNEditor editor, String dirPath,
			String filePath, byte[] data) throws SVNException {
		/*
		 * Always called first. Opens the current root directory. It means all
		 * modifications will be applied to this directory until a next entry
		 * (located inside the root) is opened/added.
		 * 
		 * -1 - revision is HEAD (actually, for a comit editor this number is
		 * irrelevant)
		 */
		editor.openRoot(-1);
		/*
		 * Adds a new directory (in this case - to the root directory for which
		 * the SVNRepository was created). Since this moment all changes will be
		 * applied to this new directory.
		 * 
		 * dirPath is relative to the root directory.
		 * 
		 * copyFromPath (the 2nd parameter) is set to null and copyFromRevision
		 * (the 3rd) parameter is set to -1 since the directory is not added
		 * with history (is not copied, in other words).
		 */
		editor.addDir(dirPath, null, -1);
		/*
		 * Adds a new file to the just added directory. The file path is also
		 * defined as relative to the root directory.
		 * 
		 * copyFromPath (the 2nd parameter) is set to null and copyFromRevision
		 * (the 3rd parameter) is set to -1 since the file is not added with
		 * history.
		 */
		editor.addFile(filePath, null, -1);
		/*
		 * The next steps are directed to applying delta to the file (that is
		 * the full contents of the file in this case).
		 */
		editor.applyTextDelta(filePath, null);
		/*
		 * Use delta generator utility class to generate and send delta
		 * 
		 * Note that you may use only 'target' data to generate delta when there
		 * is no access to the 'base' (previous) version of the file. However,
		 * using 'base' data will result in smaller network overhead.
		 * 
		 * SVNDeltaGenerator will call editor.textDeltaChunk(...) method for
		 * each generated "diff window" and then editor.textDeltaEnd(...) in the
		 * end of delta transmission. Number of diff windows depends on the file
		 * size.
		 */
		SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
		String checksum = deltaGenerator.sendDelta(filePath,
				new ByteArrayInputStream(data), editor, true);

		/*
		 * Closes the new added file.
		 */
		editor.closeFile(filePath, checksum);
		/*
		 * Closes the new added directory.
		 */
		editor.closeDir();
		/*
		 * Closes the root directory.
		 */
		editor.closeDir();
		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		return editor.closeEdit();
	}

	/*
	 * This method performs commiting an addition of a directory containing a
	 * file.
	 */
	private static SVNCommitInfo addDirOnly(ISVNEditor editor, String dirPath)
			throws SVNException {
		try {
			/*
			 * Always called first. Opens the current root directory. It means
			 * all modifications will be applied to this directory until a next
			 * entry (located inside the root) is opened/added.
			 * 
			 * -1 - revision is HEAD (actually, for a comit editor this number
			 * is irrelevant)
			 */
			editor.openRoot(-1);
			/*
			 * Adds a new directory (in this case - to the root directory for
			 * which the SVNRepository was created). Since this moment all
			 * changes will be applied to this new directory.
			 * 
			 * dirPath is relative to the root directory.
			 * 
			 * copyFromPath (the 2nd parameter) is set to null and
			 * copyFromRevision (the 3rd) parameter is set to -1 since the
			 * directory is not added with history (is not copied, in other
			 * words).
			 */
			editor.addDir(dirPath, null, -1);

			/*
			 * Closes the new added directory.
			 */
			editor.closeDir();
			/*
			 * Closes the root directory.
			 */
			editor.closeDir();
			/*
			 * This is the final point in all editor handling. Only now all that
			 * new information previously described with the editor's methods is
			 * sent to the server for committing. As a result the server sends
			 * the new commit information.
			 */
		} catch (SVNException e) {
			e.printStackTrace();
			throw new SVNException(null);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return editor.closeEdit();
	}

	/*
	 * This method performs committing file modifications.
	 */
	private static SVNCommitInfo modifyFile(ISVNEditor editor, String dirPath,
			String filePath, byte[] oldData, byte[] newData)
			throws SVNException {
		/*
		 * Always called first. Opens the current root directory. It means all
		 * modifications will be applied to this directory until a next entry
		 * (located inside the root) is opened/added.
		 * 
		 * -1 - revision is HEAD
		 */
		editor.openRoot(-1);
		/*
		 * Opens a next subdirectory (in this example program it's the directory
		 * added in the last commit). Since this moment all changes will be
		 * applied to this directory.
		 * 
		 * dirPath is relative to the root directory. -1 - revision is HEAD
		 */
		editor.openDir(dirPath, -1);
		/*
		 * Opens the file added in the previous commit.
		 * 
		 * filePath is also defined as a relative path to the root directory.
		 */
		editor.openFile(filePath, -1);

		/*
		 * The next steps are directed to applying and writing the file delta.
		 */
		editor.applyTextDelta(filePath, null);

		/*
		 * Use delta generator utility class to generate and send delta
		 * 
		 * Note that you may use only 'target' data to generate delta when there
		 * is no access to the 'base' (previous) version of the file. However,
		 * here we've got 'base' data, what in case of larger files results in
		 * smaller network overhead.
		 * 
		 * SVNDeltaGenerator will call editor.textDeltaChunk(...) method for
		 * each generated "diff window" and then editor.textDeltaEnd(...) in the
		 * end of delta transmission. Number of diff windows depends on the file
		 * size.
		 */
		SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
		String checksum = deltaGenerator.sendDelta(filePath,
				new ByteArrayInputStream(oldData), 0, new ByteArrayInputStream(
						newData), editor, true);

		/*
		 * Closes the file.
		 */
		editor.closeFile(filePath, checksum);

		/*
		 * Closes the directory.
		 */
		editor.closeDir();

		/*
		 * Closes the root directory.
		 */
		editor.closeDir();

		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		return editor.closeEdit();
	}

	public List<Repositorio> mostrarInfoSVN(
			SubVersionUsuario subVersionUsuario, boolean swUpload)
			throws SVNException {
		long startRevision = 0;
		long endRevision = -1;

		String repositorioUrl = "";
		// este sw es si voy abajar o subir al repositorio
		if (swUpload) {
			repositorioUrl = subVersionUsuario.getUrlsubversionUpload();

		} else {
			repositorioUrl = subVersionUsuario.getUrlsubversion();
		}

		SVNRepository repository = null;
		try {
			// System.out.println("antes del error");
			// System.out.println(repositorioUrl);
			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIDecoded(repositorioUrl));

			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(
							subVersionUsuario.getUsuariosubversion(),
							subVersionUsuario.getPasswordsubversion());
			repository.setAuthenticationManager(authManager);

			repositorioall = new ArrayList<Repositorio>();
			RepositorioSVN repositorioSVN = new RepositorioSVN();

			// la collecion files.. la pasamos por referencia..
			files = new ArrayList<String>();
			metodoRecursivoDirectoriosFiles("", endRevision, null, files,
					repository);

			HashMap<String, String> unico = new HashMap<String, String>();
			long codigo = 0;
			for (String file : files) {
				if (file.lastIndexOf(".") != -1) {
					String extensionFile = "";
					String nombreFile = "";
					try {
						extensionFile = file.substring(
								file.lastIndexOf(".") + 1, file.length());

						// verificamos que trae un nombre
						if (file.lastIndexOf(".") > 0) {
							nombreFile = file.substring(0,
									file.lastIndexOf(".") - 1);
						}

						if (!unico.containsKey(file.trim())) {
							unico.put(file.trim(), file);
							// llenamos los datos del repositorio....
							Repositorio repositorio = new Repositorio();
							repositorio.setCodigo(++codigo);
							repositorio.setArchivo(file);
							repositorio.setAddmodifydelete("");

							// seleccionamos en la vista el check que se
							// marco
							if (file.equalsIgnoreCase(subVersionUsuario
									.getFilePath())) {
								repositorio.setSwArchivo(true);
							}
							// si estan buscando por una extension
							// especiifica....
							// validamos no sea nulo
							if (subVersionUsuario.getExtensionFiltrar() != null
									&& !"".equalsIgnoreCase(subVersionUsuario
											.getExtensionFiltrar())) {
								String soloNombre = "";
								String soloExtension = "";
								// LA EXTENSION.. PUEDE SER ELNOMBRE.EXTENSION O
								// NOMBRE SOLAMNETE O .EXTENSIONSOLAMENTE
								if (subVersionUsuario.getExtensionFiltrar()
										.lastIndexOf(".") != -1) {
									soloExtension = subVersionUsuario
											.getExtensionFiltrar()
											.substring(
													subVersionUsuario
															.getExtensionFiltrar()
															.lastIndexOf(".") + 1,
													subVersionUsuario
															.getExtensionFiltrar()
															.length());

									// verificamos que trae un nombre
									if (subVersionUsuario.getExtensionFiltrar()
											.lastIndexOf(".") > 0) {
										soloNombre = subVersionUsuario
												.getExtensionFiltrar()
												.substring(
														0,
														subVersionUsuario
																.getExtensionFiltrar()
																.lastIndexOf(
																		".") - 1);
									}
								} else {
									soloNombre = subVersionUsuario
											.getExtensionFiltrar();
								}

								// validamos que sea la extension a la que se
								// esta buscando...
								// solo si buscamos solo por extension
								if (("".compareTo(soloNombre) == 0)
										&& ("".compareTo(extensionFile) != 0)
										&& ("".compareTo(soloExtension) != 0)
										&& extensionFile
												.trim()
												.equalsIgnoreCase(soloExtension)) {
									repositorioall.add(repositorio);
								}
								// solo si buscamos solo por nombre
								if (("".compareTo(soloExtension) == 0)
										&& ("".compareTo(soloNombre) != 0)
										&& ("".compareTo(nombreFile) != 0)
										&& (nombreFile
												.trim()
												.toLowerCase()
												.indexOf(
														soloNombre.trim()
																.toLowerCase()) != -1)) {
									repositorioall.add(repositorio);
								}
								// filtrar por los dos
								if (("".compareTo(soloNombre) != 0)
										&& ("".compareTo(soloExtension) != 0)
										&& ("".compareTo(nombreFile) != 0)
										&& ("".compareTo(extensionFile) != 0)
										&& (nombreFile
												.trim()
												.toLowerCase()
												.indexOf(
														soloNombre.trim()
																.toLowerCase()) != -1)
										&& extensionFile
												.trim()
												.equalsIgnoreCase(soloExtension)) {
									repositorioall.add(repositorio);
								}

							} else {
								// se selecciona topdas las extensiones sin
								// aplicar filtrros
								repositorioall.add(repositorio);

							}

						}

					} catch (Exception e) {

					}
				}
			}

		} catch (SVNException e) {

			throw new SVNException(e.getErrorMessage());
			// e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}

		// Iterator it = repositorioall2.iterator();

		if (repositorioall == null) {
			repositorioall = new ArrayList<Repositorio>();
		}

		return repositorioall;
	}

	private void metodoRecursivoDirectoriosFiles(String path, long version,
			SVNProperties p, List<String> files, SVNRepository repository)
			throws SVNException {

		Collection children;

		try {

			children = repository
					.getDir(path, version, null, (Collection) null);
			for (Iterator childrenIter = children.iterator(); childrenIter
					.hasNext();) {
				SVNDirEntry dirEntry = (SVNDirEntry) childrenIter.next();
				if (dirEntry.getKind() == SVNNodeKind.DIR) {
					String dirPath = SVNPathUtil.append(path,
							dirEntry.getName());

					long childRevision = dirEntry.getRevision();
					// System.out.println("childPath=" + dirPath);
					metodoRecursivoDirectoriosFiles(dirPath, version, p, files,
							repository);
				} else {
					String filePath = SVNPathUtil.append(path,
							dirEntry.getName());
					long childRevision = dirEntry.getRevision();
					files.add(filePath);
				}
			}
		} catch (SVNException e) {

			throw new SVNException(e.getErrorMessage());
			// e.printStackTrace();
		}

		// return files;

	}

	/* ________________________________________________________________________________________________ */
	/* ________________________________________________________________________________________________ */
	/* ________________________________________________________________________________________________ */
	/* ________________________________________________________________________________________________ */

	public SubVersionUsuario obtenerFile(SubVersionUsuario subVersionUsuario,
			boolean swUpload) throws ExceptionSubVersion {
		SVNRepository repository = null;
		List<File> files = new ArrayList<File>();

		String repositorioUrl = "";
		if (swUpload) {
			repositorioUrl = subVersionUsuario.getUrlsubversionUpload();

		} else {
			repositorioUrl = subVersionUsuario.getUrlsubversion();
		}

		try {
			/*
			 * Creates an instance of SVNRepository to work with the repository.
			 * All user's requests to the repository are relative to the
			 * repository location used to create this SVNRepository. SVNURL is
			 * a wrapper for URL strings that refer to repository locations.
			 */

			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(repositorioUrl));
		} catch (SVNException svne) {
			/*
			 * Perhaps a malformed URL is the cause of this exception
			 */

			throw new ExceptionSubVersion(
					"error while creating an SVNRepository for the location '"
							+ repositorioUrl + "'");

		}

		ISVNAuthenticationManager authManager = SVNWCUtil
				.createDefaultAuthenticationManager(
						subVersionUsuario.getUsuariosubversion(),
						subVersionUsuario.getPasswordsubversion());
		repository.setAuthenticationManager(authManager);

		File archivo = null;
		String nombreArchivo = "";
		if (subVersionUsuario.getFilePaths().size() > 0) {
			subVersionUsuario.setMensaje("");
			for (String f : subVersionUsuario.getFilePaths()) {
				subVersionUsuario.setFilePath(f);
				int nom = subVersionUsuario.getFilePath().lastIndexOf("/") != -1 ? subVersionUsuario
						.getFilePath().lastIndexOf("/") + 1 : 0;
				nombreArchivo = subVersionUsuario.getFilePath().substring(nom,
						subVersionUsuario.getFilePath().length());

				/*
				 * This Map will be used to get the file properties. Each Map
				 * key is a property name and the value associated with the key
				 * is the property value.
				 */
				SVNProperties fileProperties = new SVNProperties();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				long latestRevision = -1;

				try {
					/*
					 * Checks up if the specified path really corresponds to a
					 * file. If doesn't the program exits. SVNNodeKind is that
					 * one who says what is located at a path in a revision. -1
					 * means the latest revision.
					 */
					SVNNodeKind nodeKind = repository.checkPath(
							subVersionUsuario.getFilePath(),
							subVersionUsuario.getVersion());

					/*
					 * Gets the contents and properties of the file located at
					 * filePath in the repository at the latest revision (which
					 * is meant by a negative revision number).
					 */
					repository.getFile(subVersionUsuario.getFilePath(),
							subVersionUsuario.getVersion(), fileProperties,
							baos);

					latestRevision = repository.getLatestRevision();

				} catch (SVNException svne) {

					// sacamos la ultima version (-1) que si es valida y la
					// mostramos al usuario
					try {
						repository.getFile(subVersionUsuario.getFilePath(), -1,
								fileProperties, baos);
						latestRevision = repository.getLatestRevision();
						throw new ExceptionSubVersion(
								"0 error while fetching the file contents and properties: "
										+ svne.getMessage()
										+ " Repository latest revision: "
										+ latestRevision + " ("
										+ nombreArchivo.toString() + ") ");
					} catch (SVNException e) {
						// TODO Auto-generated catch block
						throw new ExceptionSubVersion(
								"1 error while fetching the file contents and properties: "
										+ svne.getMessage()
										+ " Repository latest revision: "
										+ latestRevision + " ("
										+ nombreArchivo.toString() + ") ");

					}
				}

				/*
				 * Here the SVNProperty class is used to get the value of the
				 * svn:mime-type property (if any). SVNProperty is used to
				 * facilitate the work with versioned properties.
				 */
				String mimeType = fileProperties
						.getStringValue(SVNProperty.MIME_TYPE);

				/*
				 * SVNProperty.isTextMimeType(..) method checks up the value of
				 * the mime-type file property and says if the file is a text
				 * (true) or not (false).
				 */

				boolean isTextType = SVNProperty.isTextMimeType(mimeType);

				Iterator iterator = fileProperties.nameSet().iterator();
				/*
				 * Displays file properties.
				 */
				subVersionUsuario.setMensaje("");
				StringBuffer msg = new StringBuffer("");
				while (iterator.hasNext()) {
					String propertyName = (String) iterator.next();
					String propertyValue = fileProperties
							.getStringValue(propertyName);
					msg.append(propertyName + "=" + propertyValue + "\n");
				}
				if (msg != null) {
					subVersionUsuario.setMensaje(msg.substring(0, 300));
				}
				/*
				 * Displays the file contents in the console if the file is a
				 * text.
				 */
				// pasa de cuaqluier manera
				if (isTextType || !isTextType) {
					OutputStream out = null;

					try {

						archivo = new File(nombreArchivo);
						// File.createTempFile(
						// "archivo_" + identificador + "_", "." + extension);
						out = new FileOutputStream(archivo);
						baos.writeTo(out);
						out.close();
						//
						files.add(archivo);
						archivo.deleteOnExit();

					} catch (IOException ioe) {
						throw new ExceptionSubVersion(
								"File contents can not be displayed in the console since the mime-type property says that it's not a kind of a text file. "
										+ nombreArchivo.toString());
					} finally {
						if (out != null) {
							try {
								out.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} else {
					throw new ExceptionSubVersion(
							"File contents can not be displayed in the console since the mime-type property says that it's not a kind of a text file. "
									+ nombreArchivo.toString());
				}

			}

		}
		try {
			if (files != null && files.size() > 0) {
				ZipearDoc zipearDoc = new ZipearDoc();
				subVersionUsuario.setFile(zipearDoc.comprimirArchivos(files));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subVersionUsuario;
	}

	public List<File> obtenerListaFiles(SubVersionUsuario subVersionUsuario,
			boolean swUpload) throws ExceptionSubVersion {
		SVNRepository repository = null;
		List<File> files = new ArrayList<File>();

		String repositorioUrl = "";
		if (swUpload) {
			repositorioUrl = subVersionUsuario.getUrlsubversionUpload();

		} else {
			repositorioUrl = subVersionUsuario.getUrlsubversion();
		}

		try {
			/*
			 * Creates an instance of SVNRepository to work with the repository.
			 * All user's requests to the repository are relative to the
			 * repository location used to create this SVNRepository. SVNURL is
			 * a wrapper for URL strings that refer to repository locations.
			 */

			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(repositorioUrl));
		} catch (SVNException svne) {
			/*
			 * Perhaps a malformed URL is the cause of this exception
			 */

			throw new ExceptionSubVersion(
					"error while creating an SVNRepository for the location '"
							+ repositorioUrl + "'");

		}

		ISVNAuthenticationManager authManager = SVNWCUtil
				.createDefaultAuthenticationManager(
						subVersionUsuario.getUsuariosubversion(),
						subVersionUsuario.getPasswordsubversion());
		repository.setAuthenticationManager(authManager);

		File archivo = null;
		String nombreArchivo = "";
		if (subVersionUsuario.getFilePaths().size() > 0) {
			subVersionUsuario.setMensaje("");
			for (String f : subVersionUsuario.getFilePaths()) {
				subVersionUsuario.setFilePath(f);
				int nom = subVersionUsuario.getFilePath().lastIndexOf("/") != -1 ? subVersionUsuario
						.getFilePath().lastIndexOf("/") + 1 : 0;
				nombreArchivo = subVersionUsuario.getFilePath().substring(nom,
						subVersionUsuario.getFilePath().length());

				/*
				 * This Map will be used to get the file properties. Each Map
				 * key is a property name and the value associated with the key
				 * is the property value.
				 */
				SVNProperties fileProperties = new SVNProperties();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				long latestRevision = -1;

				try {
					/*
					 * Checks up if the specified path really corresponds to a
					 * file. If doesn't the program exits. SVNNodeKind is that
					 * one who says what is located at a path in a revision. -1
					 * means the latest revision.
					 */
					SVNNodeKind nodeKind = repository.checkPath(
							subVersionUsuario.getFilePath(),
							subVersionUsuario.getVersion());

					/*
					 * Gets the contents and properties of the file located at
					 * filePath in the repository at the latest revision (which
					 * is meant by a negative revision number).
					 */
					repository.getFile(subVersionUsuario.getFilePath(),
							subVersionUsuario.getVersion(), fileProperties,
							baos);

					latestRevision = repository.getLatestRevision();

				} catch (SVNException svne) {

					// sacamos la ultima version (-1) que si es valida y la
					// mostramos al usuario
					try {
						repository.getFile(subVersionUsuario.getFilePath(), -1,
								fileProperties, baos);
						latestRevision = repository.getLatestRevision();
						throw new ExceptionSubVersion(
								"0 error while fetching the file contents and properties: "
										+ svne.getMessage()
										+ " Repository latest revision: "
										+ latestRevision + " ("
										+ nombreArchivo.toString() + ") ");
					} catch (SVNException e) {
						// TODO Auto-generated catch block
						throw new ExceptionSubVersion(
								"1 error while fetching the file contents and properties: "
										+ svne.getMessage()
										+ " Repository latest revision: "
										+ latestRevision + " ("
										+ nombreArchivo.toString() + ") ");

					}
				}

				/*
				 * Here the SVNProperty class is used to get the value of the
				 * svn:mime-type property (if any). SVNProperty is used to
				 * facilitate the work with versioned properties.
				 */
				String mimeType = fileProperties
						.getStringValue(SVNProperty.MIME_TYPE);

				/*
				 * SVNProperty.isTextMimeType(..) method checks up the value of
				 * the mime-type file property and says if the file is a text
				 * (true) or not (false).
				 */

				boolean isTextType = SVNProperty.isTextMimeType(mimeType);

				Iterator iterator = fileProperties.nameSet().iterator();
				/*
				 * Displays file properties.
				 */
				subVersionUsuario.setMensaje("");
				StringBuffer msg = new StringBuffer("");
				while (iterator.hasNext()) {
					String propertyName = (String) iterator.next();
					String propertyValue = fileProperties
							.getStringValue(propertyName);
					msg.append(propertyName + "=" + propertyValue + "\n");
				}
				if (msg != null) {
					subVersionUsuario.setMensaje(msg.substring(0, 300));
				}
				/*
				 * Displays the file contents in the console if the file is a
				 * text.
				 */
				// pasa de cuaqluier manera
				if (isTextType || !isTextType) {
					OutputStream out = null;

					try {

						archivo = new File(nombreArchivo);
						// File.createTempFile(
						// "archivo_" + identificador + "_", "." + extension);
						out = new FileOutputStream(archivo);
						baos.writeTo(out);
						out.close();
						//
						files.add(archivo);
						archivo.deleteOnExit();

					} catch (IOException ioe) {
						throw new ExceptionSubVersion(
								"File contents can not be displayed in the console since the mime-type property says that it's not a kind of a text file. "
										+ nombreArchivo.toString());
					} finally {
						if (out != null) {
							try {
								out.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} else {
					throw new ExceptionSubVersion(
							"File contents can not be displayed in the console since the mime-type property says that it's not a kind of a text file. "
									+ nombreArchivo.toString());
				}

			}

		}

		return files;
	}

	/*
	 * Initializes the library to work with a repository via different
	 * protocols.
	 */
	private static void setupLibrary() {
		/*
		 * For using over http:// and https://
		 */
		DAVRepositoryFactory.setup();
		/*
		 * For using over svn:// and svn+xxx://
		 */
		SVNRepositoryFactoryImpl.setup();

		/*
		 * For using over file:///
		 */
		FSRepositoryFactory.setup();
	}

}
