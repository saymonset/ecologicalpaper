package com.ecological.paper.subversion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipException;

import javax.ejb.EJB;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.oro.io.AwkFilenameFilter;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNErrorMessage;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
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

import com.ecological.paper.delegado.DelegadoEJBLocal;
import com.ecological.paper.ecologicaexcepciones.ExceptionSubVersion;

import com.util.Utilidades;
import com.util.file.Archivo;
import com.util.file.ZipearDoc;

public class RepositorioSVN {
	private List<Repositorio> repositorioall = new ArrayList<Repositorio>();
	private List<String> files = new ArrayList<String>();

	public RepositorioSVN() {
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
			Map<String,Object> repositorioAllMap = new HashMap<String,Object>();
			repositorioSVN.metodoRecursivoDirectoriosFiles("", endRevision,
					null, files, repository,repositorioAllMap);

			// obtenemos todos los archivos y chequeamos..

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void subirFileZipInRepositorio(SubVersionUsuario subVersionUsuario)
			throws SVNException {
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

				System.out.println(subVersionUsuario.getFile());
				archivos = zipearDoc.unZip2(subVersionUsuario.getFile());
			} catch (Exception e) {
				// System.out.println("error.. llendo como stan");
				// e.printStackTrace();
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
				String ext = archivoUtil.nombrExtensionFile(f, swExtension);
				if (ext == null || "".equalsIgnoreCase(ext)) {
					ext = "txt";
				}
				swExtension = false;
				String nombre = archivoUtil.nombrExtensionFile(f, swExtension);
				if (nombre == null || "".equalsIgnoreCase(nombre)) {
					nombre = "nombre" + ".";// el punto es de la extension
				}

				// el archivo me trae dos extensiones.. la que se queda con el
				// nombre la eliminamos...
				if (nombre.lastIndexOf(".") != -1) {
					nombre = nombre.substring(0, nombre.lastIndexOf("."));
				}

				nombre = nombre + "." + ext;

				try {
					editor = repository.getCommitEditor(
							subVersionUsuario.getComentario()
							+ " file contents sumado " + nombre+" "+
							"ecologicalpaper:"
							+ subVersionUsuario.getUsuario() + ":" + fecha
							, null);
					commitInfo = addFileOnly(editor,
							subVersionUsuario.getDir(), nombre, bytes);

					System.out.println("The file was added: " + nombre);
				} catch (Exception e) {

					// e.printStackTrace();
					System.out
							.println("modificand subVersionUsuario.getUrlsubversionUpload()="
									+ subVersionUsuario
											.getUrlsubversionUpload());
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
					String filePath = dir1 + nombre;
					System.out.println(filePath);

					repository = SVNRepositoryFactory.create(SVNURL
							.parseURIDecoded(subVersionUsuario
									.getUrlsubversionUpload()));

					authManager = SVNWCUtil.createDefaultAuthenticationManager(
							subVersionUsuario.getUsuariosubversion(),
							subVersionUsuario.getPasswordsubversion());
					repository.setAuthenticationManager(authManager);
					nodeKind = repository.checkPath(filePath.trim(), -1);
					if (nodeKind == SVNNodeKind.NONE) {
						System.out.println("---none----");
					} else if (nodeKind == SVNNodeKind.DIR) {
						System.out.println("---dir----");
					} else {
						SVNProperties fileProperties = new SVNProperties();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						repository.getFile(filePath, -1, fileProperties, baos);

						byte[] oldFile = baos.toByteArray();
						editor = repository.getCommitEditor(
								subVersionUsuario.getComentario()
								+ " file contents changed "+
								"ecologicalpaper:"
								+ subVersionUsuario.getUsuario() + ":" + fecha
								, null);
						commitInfo = modifyFile(editor,
								subVersionUsuario.getDir(), nombre, oldFile,
								bytes);
						System.out.println("The file was changed: "
								+ commitInfo);

					}
				}

			}
			// close /A

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

			RepositorioSVN repositorioSVN = new RepositorioSVN();

			// la collecion files.. la pasamos por referencia..
			files = new ArrayList<String>();
			Map<String,Object> repositorioAllMap = new HashMap<String,Object>();
			repositorioall = new ArrayList<Repositorio>();

			if (subVersionUsuario.isSwBusquedaRecursiva()){
				repositorioAllMap=repositorioSVN.metodoRecursivoDirectoriosFiles("", endRevision,
						null, files, repository, repositorioAllMap);
			}else{
				repositorioAllMap=repositorioSVN.ultimoscambiosSvn("", endRevision, null, files,
						repository, repositorioUrl, subVersionUsuario,
						repositorioAllMap);
			}
			 

			

			repositorioall = filtrarArchivos(subVersionUsuario, files,
					repositorioAllMap);

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
	
	
	public long lastInfoBranches(
			SubVersionUsuario subVersionUsuario, boolean swUpload)
			throws SVNException {
		long startRevision = 0;
		long endRevision = -1;
		long salida=0;

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

			RepositorioSVN repositorioSVN = new RepositorioSVN();

 
		 
			salida=repositorioSVN.lastInfoBranchesSVN("", endRevision, null, 
						repository, repositorioUrl, subVersionUsuario
						);
	 

		} catch (SVNException e) {

			throw new SVNException(e.getErrorMessage());
			// e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}

	 

		return salida;
	}
	

	private boolean filtrarArchivo(SubVersionUsuario subVersionUsuario,
			String file) {
		HashMap<String, String> unico = new HashMap<String, String>();
		long codigo = 0;
		if (file.lastIndexOf(".") != -1) {
			String extensionFile = "";
			String nombreFile = "";
			try {
				extensionFile = file.substring(file.lastIndexOf(".") + 1,
						file.length());

				// verificamos que trae un nombre
				if (file.lastIndexOf(".") > 0) {
					nombreFile = file.substring(0, file.lastIndexOf(".") - 1);
				}

				if (!unico.containsKey(file.trim())) {
					unico.put(file.trim(), file);
					// llenamos los datos del repositorio....
					/*
					 * Repositorio repositorio = new Repositorio();
					 * repositorio.setCodigo(++codigo);
					 * repositorio.setArchivo(file);
					 * repositorio.setAddmodifydelete("");
					 */
					// seleccionamos en la vista el check que se
					// marco

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
									.getExtensionFiltrar().substring(
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
										.getExtensionFiltrar().substring(
												0,
												subVersionUsuario
														.getExtensionFiltrar()
														.lastIndexOf(".") - 1);
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
								&& extensionFile.trim().equalsIgnoreCase(
										soloExtension)) {
							// repositorioall.add(repositorio);
							return true;
						}
						// solo si buscamos solo por nombre
						if (("".compareTo(soloExtension) == 0)
								&& ("".compareTo(soloNombre) != 0)
								&& ("".compareTo(nombreFile) != 0)
								&& (nombreFile
										.trim()
										.toLowerCase()
										.indexOf(
												soloNombre.trim().toLowerCase()) != -1)) {
							// repositorioall.add(repositorio);
							return true;
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
												soloNombre.trim().toLowerCase()) != -1)
								&& extensionFile.trim().equalsIgnoreCase(
										soloExtension)) {
							// epositorioall.add(repositorio);
							return true;
						}

					} else {
						// se selecciona topdas las extensiones sin
						// aplicar filtrros
						// repositorioall.add(repositorio);
						return true;

					}

				}

			} catch (Exception e) {

			}
		}

		return false;
	}

	private List<Repositorio> filtrarArchivos(
			SubVersionUsuario subVersionUsuario, List<String> files,
			Map<String,Object> repositorioAllMap) {
		HashMap<String, String> unico = new HashMap<String, String>();
		List<Repositorio> repositorioallFiltrado= new ArrayList<Repositorio>();
		long codigo = 0;
		for (String file : files) {
			if (file.lastIndexOf(".") != -1) {
				String extensionFile = "";
				String nombreFile = "";
				try {
					extensionFile = file.substring(file.lastIndexOf(".") + 1,
							file.length());

					// verificamos que trae un nombre
					if (file.lastIndexOf(".") > 0) {
						nombreFile = file.substring(0,
								file.lastIndexOf(".") - 1);
					}

					if (!unico.containsKey(file.trim())) {
						unico.put(file.trim(), file);
						Repositorio repositorio=repositorioAllMap.get(file.trim())!=null?(Repositorio)repositorioAllMap.get(file.trim()):null;
						if (repositorio!=null){
							if (file.trim().compareTo(repositorio.getArchivo().trim())==0){
								repositorio.setCodigo(++codigo);
								repositorio.setAddmodifydelete("");
								// llenamos los datos del repositorio....

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
												.getExtensionFiltrar().substring(
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
																	.lastIndexOf(".") - 1);
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
											&& extensionFile.trim().equalsIgnoreCase(
													soloExtension)) {
										repositorioallFiltrado.add(repositorio);
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
										repositorioallFiltrado.add(repositorio);
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
											&& extensionFile.trim().equalsIgnoreCase(
													soloExtension)) {
										repositorioallFiltrado.add(repositorio);
									}

								} else {
									// se selecciona topdas las extensiones sin
									// aplicar filtrros
									repositorioallFiltrado.add(repositorio);

								}
							}
						}
						

					}

				} catch (Exception e) {

				}
			}
		}
		return repositorioallFiltrado;
	}
	

	private Long  lastInfoBranchesSVN(String path, long version, SVNProperties p,
			SVNRepository repository,
				String repositorioUrl, SubVersionUsuario subVersionUsuario) throws SVNException {
			long startRevision = 0;
			long endRevision = -1;// HEAD (the latest) revision
			Long salida=0l;

			 
			try {
				endRevision = repository.getLatestRevision();
			} catch (SVNException svne) {
				System.err
						.println("error while fetching the latest repository revision: "
								+ svne.getMessage());
				//System.exit(1);
			}
			Collection logEntries = null;
			try {
				 
				if(subVersionUsuario.getStartRevision()!=0){
					startRevision=subVersionUsuario.getStartRevision();
				} 
				 
				logEntries = repository.log(new String[] { "" }, null,
						startRevision, endRevision, true, true);

			} catch (SVNException svne) {
				System.out.println("error while collecting log information for '"
						+ repositorioUrl + "': " + svne.getMessage());

			}

			//PARA COLOCARLO EN RFEVERSO
			List lista = new ArrayList();
			for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
				SVNLogEntry logEntry = (SVNLogEntry) entries.next();
				lista.add(logEntry);
			}
				
	       Collections.reverse(lista);
			for (Iterator entries = lista.iterator(); entries.hasNext();) {
				
				/*
				 * gets a next SVNLogEntry
				 */
				SVNLogEntry logEntry = (SVNLogEntry) entries.next();
				
				/*
				 * gets the revision number
				 */
				salida=logEntry.getRevision();
				System.out.println("revision: " + logEntry.getRevision());
				/*
				 * gets the author of the changes made in that revision
				 */
				System.out.println("author: " + logEntry.getAuthor());
			
				/*
				 * gets the time moment when the changes were committed
				 */
				System.out.println("date: " + logEntry.getDate());

				 break;
			}
			return salida;

		}

	private Map<String,Object>  ultimoscambiosSvn(String path, long version, SVNProperties p,
			List<String> files, SVNRepository repository,
			String repositorioUrl, SubVersionUsuario subVersionUsuario,
			Map<String,Object> repositorioAllMap) throws SVNException {
		long startRevision = 0;
		long endRevision = -1;// HEAD (the latest) revision

		 
		try {
			endRevision = repository.getLatestRevision();
		} catch (SVNException svne) {
			System.err
					.println("error while fetching the latest repository revision: "
							+ svne.getMessage());
			//System.exit(1);
		}
		Collection logEntries = null;
		try {
			/*
			 * Collects SVNLogEntry objects for all revisions in the range
			 * defined by its start and end points [startRevision, endRevision].
			 * For each revision commit information is represented by
			 * SVNLogEntry.
			 * 
			 * the 1st parameter (targetPaths - an array of path strings) is set
			 * when restricting the [startRevision, endRevision] range to only
			 * those revisions when the paths in targetPaths were changed.
			 * 
			 * the 2nd parameter if non-null - is a user's Collection that will
			 * be filled up with found SVNLogEntry objects; it's just another
			 * way to reach the scope.
			 * 
			 * startRevision, endRevision - to define a range of revisions you
			 * are interested in; by default in this program - startRevision=0,
			 * endRevision= the latest (HEAD) revision of the repository.
			 * 
			 * the 5th parameter - a boolean flag changedPath - if true then for
			 * each revision a corresponding SVNLogEntry will contain a map of
			 * all paths which were changed in that revision.
			 * 
			 * the 6th parameter - a boolean flag strictNode - if false and a
			 * changed path is a copy (branch) of an existing one in the
			 * repository then the history for its origin will be traversed; it
			 * means the history of changes of the target URL (and all that
			 * there's in that URL) will include the history of the origin
			 * path(s). Otherwise if strictNode is true then the origin path
			 * history won't be included.
			 * 
			 * The return value is a Collection filled up with SVNLogEntry
			 * Objects.
			 */
			if(subVersionUsuario.getStartRevision()!=0){
				startRevision=subVersionUsuario.getStartRevision();
			} 
			//	if(subVersionUsuario.getVersion()!=-1){
				//	startRevision=subVersionUsuario.getVersion();
			//	}	
			

			
			
			logEntries = repository.log(new String[] { "" }, null,
					startRevision, subVersionUsuario.getVersion(), true, true);

		} catch (SVNException svne) {
			System.out.println("error while collecting log information for '"
					+ repositorioUrl + "': " + svne.getMessage());

		}
		for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
			
			/*
			 * gets a next SVNLogEntry
			 */
			SVNLogEntry logEntry = (SVNLogEntry) entries.next();
			
			/*
			 * gets the revision number
			 */
			System.out.println("revision: " + logEntry.getRevision());
			/*
			 * gets the author of the changes made in that revision
			 */
			System.out.println("author: " + logEntry.getAuthor());
		
			/*
			 * gets the time moment when the changes were committed
			 */
			System.out.println("date: " + logEntry.getDate());

			/*
			 * gets the commit log message
			 */
			System.out.println("log message: " + logEntry.getMessage());

			/*
			 * displaying all paths that were changed in that revision; cahnged
			 * path information is represented by SVNLogEntryPath.
			 */
			if (logEntry.getChangedPaths().size() > 0) {
				System.out.println();
				System.out.println("changed paths:");
				/*
				 * keys are changed paths
				 */
				Set changedPathsSet = logEntry.getChangedPaths().keySet();

				for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths
						.hasNext();) {
					/*
					 * obtains a next SVNLogEntryPath
					 */
					SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry
							.getChangedPaths().get(changedPaths.next());
					/*
					 * SVNLogEntryPath.getPath returns the changed path itself;
					 * 
					 * SVNLogEntryPath.getType returns a charecter describing
					 * how the path was changed ('A' - added, 'D' - deleted or
					 * 'M' - modified);
					 * 
					 * If the path was copied from another one (branched) then
					 * SVNLogEntryPath.getCopyPath &
					 * SVNLogEntryPath.getCopyRevision tells where it was copied
					 * from and what revision the origin path was at.
					 */
					if (entryPath.getPath()!=null){
						System.out.println(" "
								+ entryPath.getType()
								+ "	"
								+ entryPath.getPath()
								+ ((entryPath.getCopyPath() != null) ? " (from "
										+ entryPath.getCopyPath() + " revision "
										+ entryPath.getCopyRevision() + ")" : ""));
						Repositorio repositorio = new Repositorio();
						repositorio.setAuthor(logEntry.getAuthor());
						repositorio.setDate( Utilidades.sdfShowWithoutHour.format( logEntry.getDate()));
						repositorio.setLog(logEntry.getMessage());
						repositorio.setTipo(entryPath.getType() + "");
						repositorio.setArchivo(entryPath.getPath());
						repositorio.setRevision(logEntry.getRevision()+"");
						//if ("-1".equalsIgnoreCase(subVersionUsuario.getVersion()+"")){
							//repositorio.setRevision("Ultima version");							
						//}
						
						 
						repositorioAllMap.put(entryPath.getPath().trim(), repositorio);
						files.add(entryPath.getPath().trim());
					}
				
				}
			}
		}
		return repositorioAllMap;

	}

	private Map<String,Object> metodoRecursivoDirectoriosFiles(String path, long version,
			SVNProperties p, List<String> files, SVNRepository repository,
			Map<String,Object> repositorioAllMap) throws SVNException {

		Collection children;

		try {
			SVNDirEntry entry = repository.info(".", -1);

			/*
			 * System.out.println("Latest Rev: " + entry.getRevision());
			 * System.out.println("version="+version);
			 * System.out.println("repository.getLatestRevision()="
			 * +repository.getLatestRevision());
			 * System.out.println("path="+path);
			 */
			 
			children = repository.getDir(path, repository.getLatestRevision(),
					null, (Collection) null);

			for (Iterator childrenIter = children.iterator(); childrenIter
					.hasNext();) {
				SVNDirEntry dirEntry = (SVNDirEntry) childrenIter.next();
				/*
				 * System.out.println("hola**********************************");
				 * System.out.println("/" + (path.equals("") ? "" : path + "/")
				 * + entry.getName() + " (author: '" + entry.getAuthor() +
				 * "'; revision: " + entry.getRevision() + "; date: " +
				 * entry.getDate() + ")");
				 * System.out.println("entry.getCommitMessage()=" +
				 * entry.getCommitMessage());
				 * System.out.println("hola******************fin****************"
				 * );
				 */

				if (dirEntry.getKind() == SVNNodeKind.DIR) {
					String dirPath = SVNPathUtil.append(path,
							dirEntry.getName());

					long childRevision = dirEntry.getRevision();

					// System.out.println("childPath=" + dirPath);
					metodoRecursivoDirectoriosFiles(dirPath, version, p, files,
							repository, repositorioAllMap);
				} else {
					String filePath = SVNPathUtil.append(path,
							dirEntry.getName());

					//System.out.println(entry.getAuthor());
					long childRevision = dirEntry.getRevision();

					
					if (filePath!=null){
						Repositorio repositorio = new Repositorio();
						repositorio.setArchivo(filePath);
						repositorio.setAuthor(entry.getAuthor());
						repositorio.setRevision(dirEntry.getRevision()+"");
						
						repositorio.setDate( Utilidades.sdfShowWithoutHour.format( entry.getDate()));
						repositorio.setLog(entry.getCommitMessage());
						repositorioAllMap.put(filePath.trim(), repositorio);
						files.add(filePath.trim());
					}
					

					/*
					 * Checking up if the entry is a directory.
					 */

					// listEntries(repository, filePath );
				}
			}
		} catch (SVNException e) {

			throw new SVNException(e.getErrorMessage());
			// e.printStackTrace();
		}

		return repositorioAllMap;

	}

	private void metodoRecursivoDirectoriosFilesWithLog(String path,
			long version, SVNProperties p, List<String> files,
			SVNRepository repository) throws SVNException {

		Collection children;

		try {
			SVNDirEntry entry = repository.info(".", -1);

			/*
			 * System.out.println("Latest Rev: " + entry.getRevision());
			 * System.out.println("version="+version);
			 * System.out.println("repository.getLatestRevision()="
			 * +repository.getLatestRevision());
			 * System.out.println("path="+path);
			 */
			children = repository.getDir(path, repository.getLatestRevision(),
					null, (Collection) null);

			for (Iterator childrenIter = children.iterator(); childrenIter
					.hasNext();) {
				SVNDirEntry dirEntry = (SVNDirEntry) childrenIter.next();
				//System.out.println("hola**********************************");
				System.out.println("/" + (path.equals("") ? "" : path + "/")
						+ entry.getName() + " (author: '" + entry.getAuthor()
						+ "'; revision: " + entry.getRevision() + "; date: "
						+ entry.getDate() + ")");
				System.out.println("entry.getCommitMessage()="
						+ entry.getCommitMessage());
			//	System.out.println("hola******************fin****************");

				if (dirEntry.getKind() == SVNNodeKind.DIR) {
					String dirPath = SVNPathUtil.append(path,
							dirEntry.getName());

					long childRevision = dirEntry.getRevision();
					// System.out.println("childPath=" + dirPath);
					metodoRecursivoDirectoriosFilesWithLog(dirPath, version, p,
							files, repository);
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

	public static void listEntries(SVNRepository repository, String path)
			throws SVNException {
		System.out.println("path=" + path);
		/*
		 * Gets the contents of the directory specified by path at the latest
		 * revision (for this purpose -1 is used here as the revision number to
		 * mean HEAD-revision) getDir returns a Collection of SVNDirEntry
		 * elements. SVNDirEntry represents information about the directory
		 * entry. Here this information is used to get the entry name, the name
		 * of the person who last changed this entry, the number of the revision
		 * when it was last changed and the entry type to determine whether it's
		 * a directory or a file. If it's a directory listEntries steps into a
		 * next recursion to display the contents of this directory. The third
		 * parameter of getDir is null and means that a user is not interested
		 * in directory properties. The fourth one is null, too - the user
		 * doesn't provide its own Collection instance and uses the one returned
		 * by getDir.
		 */
		Collection entries = repository.getDir(path, -1, null,
				(Collection) null);
		Iterator iterator = entries.iterator();
		while (iterator.hasNext()) {
			SVNDirEntry entry = (SVNDirEntry) iterator.next();
			System.out.println("/" + (path.equals("") ? "" : path + "/")
					+ entry.getName() + " (author: '" + entry.getAuthor()
					+ "'; revision: " + entry.getRevision() + "; date: "
					+ entry.getDate() + ")");
			/*
			 * Checking up if the entry is a directory.
			 */
			if (entry.getKind() == SVNNodeKind.DIR) {
				listEntries(repository, (path.equals("")) ? entry.getName()
						: path + "/" + entry.getName());
			}
		}
	}

	private void metodoRecursivoDirectoriosFilesPath(String path, long version,
			SVNProperties p, List<Repositorio> repositorioall,
			SVNRepository repository, SubVersionUsuario subVersionUsuario,
			long codigo) throws SVNException {

		Collection children;

		try {

			List listaReversa = new ArrayList<List>();
			children = repository
					.getDir(path, version, null, (Collection) null);
			for (Iterator childrenIter = children.iterator(); childrenIter
					.hasNext();) {
				SVNDirEntry dirEntry = (SVNDirEntry) childrenIter.next();
				listaReversa.add(dirEntry);
			}
			// /COLOCAMOS DE MAYOR A MENOSR
			Collections.reverse(listaReversa);
			/*
			 * children = repository .getDir(path, version, null, (Collection)
			 * null);
			 */
			for (Iterator childrenIter = listaReversa.iterator(); childrenIter
					.hasNext();) {
				SVNDirEntry dirEntry = (SVNDirEntry) childrenIter.next();
				if (dirEntry.getKind() == SVNNodeKind.DIR) {
					String dirPath = SVNPathUtil.append(path,
							dirEntry.getName());

					long childRevision = dirEntry.getRevision();
					// System.out.println("childPath=" + dirPath);
					// repositorioall.addAll(obtenInfo(dirPath, repository,
					// subVersionUsuario));
					System.out.println("obtenida del directorio recursivo");
					System.out.println("dirPath=" + dirPath);
					metodoRecursivoDirectoriosFilesPath(dirPath, version, p,
							repositorioall, repository, subVersionUsuario,
							codigo);
				} else {
					String filePath = SVNPathUtil.append(path,
							dirEntry.getName());
					System.out.println("obtenida del archivo recursivo");
					System.out.println("filePath=" + filePath);

					repositorioall.addAll(obtenInfo(filePath, repository,
							subVersionUsuario, codigo));
				}
			}
		} catch (SVNException e) {

			throw new SVNException(e.getErrorMessage());
			// e.printStackTrace();
		}

		// return files;

	}

	public List<Repositorio> mostrarInfoSVNFiltrado(
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
			System.out.println("----------------------------");
			long codigo = 0;
			metodoRecursivoDirectoriosFilesPath("",
					subVersionUsuario.getVersion(), null, repositorioall,
					repository, subVersionUsuario, codigo);
			System.out.println("-------------------------------");

		} catch (Exception e) {
			e.printStackTrace();

		}

		/*
		 * repositorioall = new ArrayList<Repositorio>(); RepositorioSVN
		 * repositorioSVN = new RepositorioSVN();
		 * 
		 * // la collecion files.. la pasamos por referencia.. files = new
		 * ArrayList<String>();
		 * repositorioSVN.metodoRecursivoDirectoriosFiles("", endRevision, null,
		 * files, repository);
		 * 
		 * repositorioall=filtrarArchivos(subVersionUsuario,files,repositorioall)
		 * ;
		 */

		// Iterator it = repositorioall2.iterator();

		if (repositorioall == null) {
			repositorioall = new ArrayList<Repositorio>();
		}

		return repositorioall;
	}

	private List<Repositorio> obtenInfo(String path, SVNRepository repository,
			SubVersionUsuario subVersionUsuario, long codigo) {
		int startRevision = 0;
		Collection logEntries = null;
		List<Repositorio> repositorioallInterno = new ArrayList<Repositorio>();
		try {
			logEntries = repository.log(new String[] { path }, null,
					startRevision, subVersionUsuario.getVersion(), true, true);

			// ESTO ES PARA COLOCARLO EN REVERSA
			List lista = new ArrayList();
			for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
				/*
				 * gets a next SVNLogEntry
				 */
				SVNLogEntry logEntry = (SVNLogEntry) entries.next();
				lista.add(logEntry);
			}
			// LO COLOCARWEMOS REVERSO PARA TENER LAS ULTIMAS REVISIONES
			Collections.reverse(lista);
			Repositorio repositorio = null;
			HashMap<String, String> unico = new HashMap<String, String>();
			for (Iterator entries = lista.iterator(); entries.hasNext();) {

				repositorio = new Repositorio();
				/*
				 * gets a next SVNLogEntry
				 */
				SVNLogEntry logEntry = (SVNLogEntry) entries.next();

				// llenamos los datos del
				// repositorio....
				unico.put(path, path);
				Repositorio repositorioInfo = new Repositorio();
				repositorioInfo.setCodigo(++codigo);
				repositorioInfo.setRevision(logEntry.getRevision() + "");
				repositorioInfo.setAuthor(logEntry.getAuthor());
				String fecha = Utilidades.sdfShowConvert.format(logEntry
						.getDate());
				fecha = fecha.replace(" ", "_").toLowerCase();
				fecha = fecha.replace("-", "_").toLowerCase();
				fecha = fecha.replace(":", "_").toLowerCase();
				repositorioInfo.setDate(fecha);
				repositorioInfo.setSwInfo(true);
				repositorioInfo.setLog(logEntry.getMessage());

				repositorioInfo.setArchivo(path);
				repositorioallInterno.add(repositorioInfo);

			}

			// listEntries( repository, url) ;
			SVNNodeKind nodeKind = repository.checkPath("", -1);
			if (nodeKind == SVNNodeKind.NONE) {
				// System.err.println("There is no entry at '" + repositorioUrl
				// + "'.");
				// System.exit(1);
			} else if (nodeKind == SVNNodeKind.FILE) {
				// System.err.println("The entry at '" + repositorioUrl
				// + "' is a file while a directory was expected.");
				// System.exit(1);
			}
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repositorioallInterno;
	}

	private String nuevaLista() {
		String url = "file:////wandisco/repositorioSVN/ecologicalpaper"; // "file:////opt/repo_ecological";
		String name = "anonymous";
		String password = "anonymous";
		long startRevision = 0;
		long endRevision = -1;
		SVNRepository repository = null;
		try {
			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIDecoded(url));

			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(name, password);
			repository.setAuthenticationManager(authManager);
			System.out.println("Repository Root: "
					+ repository.getRepositoryRoot(true));
			System.out.println("Repository UUID: "
					+ repository.getRepositoryUUID(true));
			Collection logEntries = null;
			logEntries = repository.log(new String[] { "" }, null,
					startRevision, endRevision, true, true);
			List lista = new ArrayList();
			for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
				/*
				 * gets a next SVNLogEntry
				 */
				SVNLogEntry logEntry = (SVNLogEntry) entries.next();
				lista.add(logEntry);
			}

			Collections.reverse(lista);
			int i = 0;
			for (Iterator entries = lista.iterator(); entries.hasNext();) {
				i++;
				if (i == 4) {
					break;
				}
				/*
				 * gets a next SVNLogEntry
				 */
				SVNLogEntry logEntry = (SVNLogEntry) entries.next();
				System.out
						.println("---------------------------------------------");
				/*
				 * gets the revision number
				 */
				System.out.println("revision: " + logEntry.getRevision());
				/*
				 * gets the author of the changes made in that revision
				 */
				System.out.println("author: " + logEntry.getAuthor());
				/*
				 * gets the time moment when the changes were committed
				 */
				System.out.println("date: " + logEntry.getDate());
				/*
				 * gets the commit log message
				 */
				System.out.println("log message: " + logEntry.getMessage());
				/*
				 * displaying all paths that were changed in that revision;
				 * cahnged path information is represented by SVNLogEntryPath.
				 */
				if (logEntry.getChangedPaths().size() > 0) {
					System.out.println();
					System.out.println("changed paths:");
					/*
					 * keys are changed paths
					 */
					Set changedPathsSet = logEntry.getChangedPaths().keySet();

					for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths
							.hasNext();) {
						/*
						 * obtains a next SVNLogEntryPath
						 */
						SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry
								.getChangedPaths().get(changedPaths.next());
						/*
						 * SVNLogEntryPath.getPath returns the changed path
						 * itself;
						 * 
						 * SVNLogEntryPath.getType returns a charecter
						 * describing how the path was changed ('A' - added, 'D'
						 * - deleted or 'M' - modified);
						 * 
						 * If the path was copied from another one (branched)
						 * then SVNLogEntryPath.getCopyPath &
						 * SVNLogEntryPath.getCopyRevision tells where it was
						 * copied from and what revision the origin path was at.
						 */
						System.out.println("pathSimons:" + entryPath.getPath());
						System.out
								.println(" "
										+ entryPath.getType()
										+ "	"
										+ entryPath.getPath()
										+ ((entryPath.getCopyPath() != null) ? " (from "
												+ entryPath.getCopyPath()
												+ " revision "
												+ entryPath.getCopyRevision()
												+ ")"
												: ""));
					}
				}
			}

			// listEntries( repository, url) ;
			SVNNodeKind nodeKind = repository.checkPath("", -1);
			if (nodeKind == SVNNodeKind.NONE) {
				System.err.println("There is no entry at '" + url + "'.");
				// System.exit(1);
			} else if (nodeKind == SVNNodeKind.FILE) {
				System.err.println("The entry at '" + url
						+ "' is a file while a directory was expected.");
				// System.exit(1);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return "";
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
		if (subVersionUsuario.getRepositorioLst().size() > 0) {
			subVersionUsuario.setMensaje("");
			for (Repositorio repositorio : subVersionUsuario.getRepositorioLst()) {
				subVersionUsuario.setFilePath(repositorio.getArchivo().trim());
				int nom = subVersionUsuario.getFilePath().lastIndexOf("/") != -1 ? subVersionUsuario
						.getFilePath().lastIndexOf("/") + 1 : 0;
                nombreArchivo = subVersionUsuario
						.getFilePath()
						.substring(nom,
								subVersionUsuario.getFilePath().length())
						.trim();
            	try {
    				Integer.parseInt(repositorio.getRevision());
    				subVersionUsuario.setVersion(new Long(repositorio.getRevision()));
    			} catch (Exception ex) {
    				subVersionUsuario.setVersion(-1l);
    			}
                
                

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
							subVersionUsuario.getFilePath().trim(),
							subVersionUsuario.getVersion());
					
					/*
					 * Gets the contents and properties of the file located at
					 * filePath in the repository at the latest revision (which
					 * is meant by a negative revision number).
					 */
					repository.getFile(subVersionUsuario.getFilePath().trim(),
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
 						// e.printStackTrace();
						// TODO Auto-generated catch block
						throw new ExceptionSubVersion(
								nombreArchivo.toString()
										+ "-> error while fetching the file contents and properties: "
										+ svne.getMessage()
										+ " Repository latest revision: "
										+ latestRevision);

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
				/*StringBuffer msg = new StringBuffer("");
				while (iterator.hasNext()) {
					String propertyName = (String) iterator.next();
					String propertyValue = fileProperties
							.getStringValue(propertyName);
					msg.append(propertyName + "=" + propertyValue + "\n");
				}
				if (msg != null) {
					if (msg.length() > 300) {
						subVersionUsuario.setMensaje(msg.substring(0, 300));
					} else {
						subVersionUsuario.setMensaje(msg.toString());
					}

				}*/
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
				subVersionUsuario.setFile(zipearDoc.comprimirArchivos(files,"(rev:"+subVersionUsuario.getNumeroRevision()+")"+nombreArchivo));
			}
		} catch (ZipException e) {
			throw new ExceptionSubVersion(e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new ExceptionSubVersion(e.getMessage());
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
					if (msg.length() > 300) {
						subVersionUsuario.setMensaje(msg.substring(0, 300));
					} else {
						subVersionUsuario.setMensaje(msg.toString());
					}

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
