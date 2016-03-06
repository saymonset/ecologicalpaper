package com.ecological.paper.subversion;
 /*
2  * ====================================================================
3  * Copyright (c) 2004-2008 TMate Software Ltd.  All rights reserved.
4  *
5  * This software is licensed as described in the file COPYING, which
6  * you should have received as part of this distribution.  The terms
7  * are also available at http://svnkit.com/license.html.
8  * If newer versions of this license are posted there, you may use a
9  * newer version instead, at your option.
10  * ====================================================================
11  */
   
   import java.io.File;
   import java.io.IOException;
   import java.util.Collections;
   
   import org.tmatesoft.svn.core.SVNCommitInfo;
   import org.tmatesoft.svn.core.SVNDepth;
   import org.tmatesoft.svn.core.SVNException;
   import org.tmatesoft.svn.core.SVNPropertyValue;
   import org.tmatesoft.svn.core.SVNURL;
   import org.tmatesoft.svn.core.wc.SVNClientManager;
   import org.tmatesoft.svn.core.wc.SVNCommitClient;
   import org.tmatesoft.svn.core.wc.SVNCopyClient;
   import org.tmatesoft.svn.core.wc.SVNCopySource;
   import org.tmatesoft.svn.core.wc.SVNDiffClient;
   import org.tmatesoft.svn.core.wc.SVNRevision;
   import org.tmatesoft.svn.core.wc.SVNRevisionRange;
   import org.tmatesoft.svn.core.wc.SVNWCClient;
   
   
   /**
    * @version 1.2.0
    * @author  TMate Software Ltd.
    */
   public class Merge {
   
       /**
        * Pass the absolute path of the base directory where all example data will be created in 
        * arg[0]. The sample will create:
        *  
        *  - arg[0]/exampleRepository - repository with some test data
        *  - arg[0]/exampleWC         - working copy checked out from exampleRepository
        */
       public static void main (String[] args) {
           //0. initialize SVNKit to work through file:/// protocol
           Merge0.initializeFSFSprotocol();
           
//           File baseDirectory = new File(args[0]);
           File baseDirectory = new File("/wandisco/repositorioSVN");

           ///wandisco/repositorioSVN

           File reposRoot = new File(baseDirectory, "exampleRepository");
           File wcRoot = new File(baseDirectory, "exampleWC");
           
           try {
               //1. first create a repository
               Merge0.createRepository(reposRoot);
               //2. fill it with data
               SVNCommitInfo info = Merge0.createRepositoryTree(reposRoot);
               //print out new revision info
               System.out.println(info);
   
               SVNClientManager clientManager = SVNClientManager.newInstance();
               
               SVNURL reposURL = SVNURL.fromFile(reposRoot);
   
               //3. copy A to A_copy in the repository (url-to-url copy)
               SVNCopyClient copyClient = clientManager.getCopyClient();
               SVNURL A_URL = reposURL.appendPath("A", true);
               SVNURL copyTargetURL = reposURL.appendPath("A_copy", true);
               SVNCopySource copySource = new SVNCopySource(SVNRevision.UNDEFINED, SVNRevision.HEAD, A_URL); 
               info = copyClient.doCopy(new SVNCopySource[] { copySource }, copyTargetURL, false, false, true, 
                       "copy A to A_copy", null);
               //print out new revision info
               System.out.println(info);
               
               //4. checkout the entire repository tree
               Merge0.checkOutWorkingCopy(reposURL, wcRoot);
   
               
               //5. now make some changes to the A tree and commit them
               Merge0.writeToFile(new File(wcRoot, "iota"), "New text appended to 'iota'", true);
               Merge0.writeToFile(new File(wcRoot, "A/mu"), "New text in 'mu'", false);
               
               SVNWCClient wcClient = SVNClientManager.newInstance().getWCClient();
               wcClient.doSetProperty(new File(wcRoot, "A/B"), "spam", SVNPropertyValue.create("egg"), false, 
                       SVNDepth.EMPTY, null, null);
   
               //commit local changes
               SVNCommitClient commitClient = clientManager.getCommitClient();
               commitClient.doCommit(new File[] { wcRoot }, false, "committing changes", null, null, false, 
                                     false, SVNDepth.INFINITY);
               
               //6. now merge changes from trunk to the branch.
               SVNDiffClient diffClient = clientManager.getDiffClient();
               SVNRevisionRange rangeToMerge = new SVNRevisionRange(SVNRevision.create(1), SVNRevision.HEAD);
               
              diffClient.doMerge(A_URL, SVNRevision.HEAD, Collections.singleton(rangeToMerge), 
                       new File(wcRoot, "A_copy"), SVNDepth.INFINITY, true, false, false, false);
               
                
                //7. now make some changes to the A tree again and commit them
                //change file contents of iota and A/D/gamma
                Merge0.writeToFile(new File(wcRoot, "iota"), "New text2 appended to 'iota'", true);
                Merge0.writeToFile(new File(wcRoot, "A/D/gamma"), "New text in 'gamma'", false);
                //remove A/C from version control
                wcClient.doDelete(new File(wcRoot, "A/C"), false, true, false);
    
                //commit local changes
                commitClient.doCommit(new File[] { wcRoot }, false, "committing changes again", null, null, 
                                      false, false, SVNDepth.INFINITY);
    
                /* 8. do the same merge call, merge-tracking feature will merge only those revisions
                 * which were not merged yet.
                 */ 
                diffClient.doMerge(A_URL, SVNRevision.HEAD, Collections.singleton(rangeToMerge), 
                        new File(wcRoot, "A_copy"), SVNDepth.INFINITY, true, false, false, false);
                
            } catch (SVNException svne) {
                System.out.println(svne.getErrorMessage());
                System.exit(1);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.exit(1);
            }
        }
    }
