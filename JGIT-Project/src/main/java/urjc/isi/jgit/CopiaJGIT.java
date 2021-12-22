/**
 * 
 */
package urjc.isi.jgit;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.ProgressMonitor;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
/**
 * @author jaortega
 *
 */
public class CopiaJGIT {


    private static void clonarepo(String remoteUrl) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
        // prepare a new folder for the cloned repository
        File localPath = File.createTempFile("TestGitRepository", "");
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        // then clone
        System.out.println("Cloning from " + remoteUrl + " to " + localPath);
        try (Git result = Git.cloneRepository()
                .setURI(remoteUrl)
                .setDirectory(localPath)
                .call()) {
	        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
	        System.out.println("Having repository: " + result.getRepository().getDirectory());
        }

        // clean up here to not keep using more and more disk-space for these samples
        //FileUtils.deleteDirectory(localPath);
    }
    
    public static void main(String[] args) throws IOException, GitAPIException {
    	List<String> urls = new ArrayList<String>();
    	urls.add("https://gitlab.etsit.urjc.es/brosaa/P1");
    	urls.add("https://gitlab.etsit.urjc.es/ja.ortega.2017/P1");
    	clonarepo(urls.get(0));
    	clonarepo(urls.get(1));
    }

}
