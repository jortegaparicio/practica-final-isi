/**
 * 
 */
package urjc.isi.jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import org.eclipse.jgit.diff.*;

public class CopiaJGIT {
	
	private static void hacerGitDiff(File f1, File f2) throws IOException,
	 InvalidRemoteException, TransportException, GitAPIException {
		OutputStream result = new ByteArrayOutputStream();
	    try {
	        RawText raw1 = new RawText(f1);
	        RawText raw2 = new RawText(f2);
	        EditList diffList = new EditList();
	
	        diffList.addAll(new HistogramDiff().diff(RawTextComparator.DEFAULT, raw1, raw2));

	        DiffFormatter diffForm = new DiffFormatter(result);
	        diffForm.format(diffList, raw1, raw2);
	        System.out.println(result.toString());
	        diffForm.close();
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static String obtenerFicheroTXT(File f) throws IOException,
	 InvalidRemoteException, TransportException, GitAPIException {
		FileFilter filter = new FileFilter() {
			 public boolean accept(File f) {
				 return f.getName().endsWith("txt");
			 }
		 };
		 
		 File[] files = f.listFiles(filter);
		 //System.out.println(files[i].getName());
		 if (files.length != 1){
			 System.out.println("Solo debe haber un archivo con extensión .txt");
		 }
		 
		 return files[0].getName();
	}
	
	 public static String clonarepo(String remoteUrl) throws IOException,
	 InvalidRemoteException, TransportException, GitAPIException { 
		 // prepare a new folder for the cloned repository 
		 File localPath = File.createTempFile("TestGitRepository", "");
		 
		 if(!localPath.delete()) { 
			 throw new IOException("Could not delete temporary file " + localPath); 
		 }
		 	
		 // then clone 
		 //System.out.println("Cloning from " + remoteUrl + " to " + localPath); 
		 try (Git result = Git.
				 cloneRepository() 
				 .setURI(remoteUrl)
				 .setDirectory(localPath) 
				 .call()) { 
			 // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
			 	System.out.println("Having repository: " + result.getRepository().getDirectory());		 	
		 }

		 return localPath + "/" + obtenerFicheroTXT(localPath);
		 // clean up here to not keep using more and more disk-space for these samples
		 //FileUtils.deleteDirectory(localPath);  
	 }
    
    public static void main(String[] args) throws Exception {

		 List<String> urls = new ArrayList<String>();
		 urls.add("https://gitlab.etsit.urjc.es/brosaa/P1");
		 urls.add("https://gitlab.etsit.urjc.es/ja.ortega.2017/P1");
		 
		 List<File> docs = new ArrayList<File>();
		 docs.add(new File(clonarepo(urls.get(0)))); 
		 docs.add(new File(clonarepo(urls.get(1))));
		 
		 System.out.println(docs);

		 hacerGitDiff(docs.get(0), docs.get(1));
		
    }

}
