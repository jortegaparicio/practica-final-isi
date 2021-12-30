package es.urjc.isi.dao;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import org.eclipse.jgit.diff.*;

public class CopiaJGIT {
	
	private static void resultadosCopia(String gitDiffOutput) throws IOException,
	 InvalidRemoteException, TransportException, GitAPIException {
		
       String[] parts = gitDiffOutput.split("\n");
       
       String[] partsF1 = gitDiffOutput.split("-");
       String[] partsF2 = gitDiffOutput.split("\\+");
       
       String f1_nlines = partsF1[1].split(",")[1].split(" ")[0];
       String f2_nlines = partsF2[1].split(",")[1].split(" ")[0];

       //System.out.println(gitDiffOutput); Salida del git diff
       //System.out.println(partsF1.length - 2); Líneas únicas en F1
       //System.out.println(partsF2.length - 2); Líneas únicas en F2
       

       int count = 0;
       for(int i = 0; i < parts.length; i++) {
       	if(Character.compare(parts[i].charAt(0), ' ') == 0) {
       		count++;
       	}
       }
       int minLines = Math.min(Integer.parseInt(f2_nlines), Integer.parseInt(f1_nlines));
       
       float porcentCopia = (count*100/(float) minLines);
       
       System.out.println("F1 tiene " + f1_nlines + " líneas");
       System.out.println("F2 tiene " + f2_nlines + " líneas");
       
       System.out.println("Un total de " + count + " líneas son iguales");
       
       System.out.println("Porcentaje de copia del " + porcentCopia + "% entre trabajos");
       
	}
	
	private static void hacerGitDiff(File f1, File f2) throws IOException,
	 InvalidRemoteException, TransportException, GitAPIException {
		OutputStream result = new ByteArrayOutputStream();
		String output = null; 
		
	    try {
	        RawText raw1 = new RawText(f1);
	        RawText raw2 = new RawText(f2);
	        EditList diffList = new EditList();
	
	        diffList.addAll(new HistogramDiff().diff(RawTextComparator.DEFAULT, raw1, raw2));

	        DiffFormatter diffForm = new DiffFormatter(result);
	        diffForm.format(diffList, raw1, raw2);
	        
	        output = result.toString();
	        
	        resultadosCopia(output);	        
	        
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
    
	private static List<String> fileToLines(File file) throws IOException {
        final List<String> lines = new ArrayList<String>();
        String line;
        final BufferedReader in = new BufferedReader(new FileReader(file));
        while ((line = in.readLine()) != null) {
            lines.add(line);
        }
        in.close();
 
        return lines;
    }
	
    public static void main(String[] args) throws Exception {

		 List<String> urls = new ArrayList<String>();
		 urls.add("https://gitlab.etsit.urjc.es/brosaa/P1");
		 urls.add("https://gitlab.etsit.urjc.es/ja.ortega.2017/P1");
		 
		 List<File> localDocs = new ArrayList<File>();
		 localDocs.add(new File(clonarepo(urls.get(0)))); 
		 localDocs.add(new File(clonarepo(urls.get(1))));
		 //localDocs.add(new File("/tmp/TestGitRepository2249447536820241654/P1.txt"));
		 //localDocs.add(new File("/tmp/TestGitRepository17213631211084400426/P1.txt"));
		 
		 //System.out.println(localDocs);

		 hacerGitDiff(localDocs.get(0), localDocs.get(1));
		
    }

}
