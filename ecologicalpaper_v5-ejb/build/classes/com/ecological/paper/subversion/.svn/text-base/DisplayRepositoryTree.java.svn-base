package com.ecological.paper.subversion;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;



public class DisplayRepositoryTree {

	/**
	 * @param args
	 */
	/*
	 * What you are doing is you are trying to open a local SVN repository over
	 * the file:/// protocol.. But SVNKit finds no repository layout under
	 * C:/bioportal. If I understand correctly, you have a working copy under
	 * this path.
	 * 
	 * SVNRepository class is intended for direct accessing a Subversion
	 * repository, not working copies. Set your targetURL to the repository URL
	 * (where you have checked out your working copy). If it's an svn:// URL,
	 * don't forget to call SVNRepositoryFactoryImpl.setup(), if http:// -
	 * DAVRepositoryFactory.setup() prior to any calls to SVNRepository.
	 */
	public static void main(String[] args) {
		/*
		 * SVNRepositoryFactoryImpl.setup (); / / para svn y svn + ssh
		 * protocolos DAVRepositoryFactory.setup (); / / para http (s) de
		 * protocolo FSRepositoryFactory.setup (); / / para el acceso local
		 * (archivo de protocolo).
		 */
		FSRepositoryFactory.setup();
		String url ="file:////wandisco/repositorioSVN/ecologicalpaper";  // "file:////opt/repo_ecological";
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
           int i=0;
			for (Iterator entries = lista.iterator(); entries.hasNext();) {
				i++;
				if (i==4){
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
						System.out.println("pathSimons:"+entryPath.getPath());
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

			 //listEntries( repository, url) ;
			SVNNodeKind nodeKind = repository.checkPath("", -1);
			if (nodeKind == SVNNodeKind.NONE) {
				System.err.println("There is no entry at '" + url + "'.");
				//System.exit(1);
			} else if (nodeKind == SVNNodeKind.FILE) {
				System.err.println("The entry at '" + url
						+ "' is a file while a directory was expected.");
				//System.exit(1);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static void listEntries(SVNRepository repository, String path)
			throws SVNException {
		Collection entries = repository.getDir(path, -1, null,
				(Collection) null);
		Iterator iterator = entries.iterator();
		while (iterator.hasNext()) {
			SVNDirEntry entry = (SVNDirEntry) iterator.next();
			System.out.println("/" + (path.equals("") ? "" : path + "/")
					+ entry.getName() + " ( author: '" + entry.getAuthor()
					+ "'; revision: " + entry.getRevision() + "; date: "
					+ entry.getDate() + ")");
			if (entry.getKind() == SVNNodeKind.DIR) {
				listEntries(repository, (path.equals("")) ? entry.getName()
						: path + "/" + entry.getName());
			}
		}
	}

}
