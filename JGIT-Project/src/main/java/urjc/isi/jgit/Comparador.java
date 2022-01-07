
package urjc.isi.jgit;

import org.eclipse.jgit.api.Git;

//import java.util.ArrayList;
//import java.util.List;
import java.io.*;
import org.eclipse.jgit.diff.*;
import org.apache.commons.io.FileUtils;


public class Comparador {
	
	private static String resultadosCopia(String gitDiffOutput) {
	   try {
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
	       
	       String resultado = "F1 tiene " + f1_nlines + " líneas\n";
	       resultado += "F2 tiene " + f2_nlines + " líneas\n";
	       resultado += "Un total de " + count + " líneas son iguales\n";
	       resultado += "Porcentaje de copia del " + porcentCopia + "% entre trabajos\n";
	       
	       return resultado;
	   
	   } catch (Exception e) {
		   throw new RuntimeException(e);
	   }
	}

	private static String obtenerFicheroTXT(File f) {
		
		FileFilter filter;
		File[] files;
		
		try {
			filter = new FileFilter() {
				 public boolean accept(File f) {
					 return f.getName().endsWith("txt");
				 }
			 };
			 
			 files = f.listFiles(filter);

			 if (files.length != 1){
				 System.out.println("Solo debe haber un archivo con extensión .txt");
			 }
			 
			 return files[0].getName();
			 
		} catch (Exception e) {
			throw new RuntimeException(e);
	    }
	}
	
	//@SuppressWarnings("finally")
	private static File clonarepo(String remoteUrl) throws IOException { 

		 File localPath;
		
		localPath = File.createTempFile("TestGitRepository", ""); 
	
		if(!localPath.delete()) { 
			throw new IOException("Could not delete temporary file " + localPath); 
		}

		 try (Git result = Git.
				 cloneRepository() 
				 .setURI(remoteUrl)
				 .setDirectory(localPath) 
				 .call()) { 
			 
			 //System.out.println("Having repository: " + result.getRepository().getDirectory());	
			 return localPath;
		 } catch (Exception e) {
			 throw new RuntimeException(e);
		 } 
	 }
	
	public String hacerGitDiff(String url1, String url2){

	    try {
	    	File localRepo1 = clonarepo(url1);
			File localRepo2 = clonarepo(url2);
			
			File f1 = new File(localRepo1 + "/" + obtenerFicheroTXT(localRepo1));
			File f2 = new File(localRepo2 + "/" + obtenerFicheroTXT(localRepo2));
			
			
			OutputStream result = new ByteArrayOutputStream();
			String output = null; 
	    	
	        RawText raw1 = new RawText(f1);
	        RawText raw2 = new RawText(f2);
	        EditList diffList = new EditList();
	
	        diffList.addAll(new HistogramDiff().diff(RawTextComparator.DEFAULT, raw1, raw2));

	        DiffFormatter diffForm = new DiffFormatter(result);
	        diffForm.format(diffList, raw1, raw2);
	        
	        output = result.toString();
	        
	        String resultado = resultadosCopia(output);	        
	        
	        diffForm.close();
	        FileUtils.deleteDirectory(localRepo1);
	        FileUtils.deleteDirectory(localRepo2);
	        
	        return resultado;
	        
	    } catch (IOException e) {
	    	throw new RuntimeException(e);
	    }
	}
	
  /*  public static void main(String[] args) throws Exception {
//
		 List<String> urls = new ArrayList<String>();
		 urls.add("https://gitlab.etsit.urjc.es/brosaa/P1");
		 urls.add("https://gitlab.etsit.urjc.es/ja.ortega.2017/P1");
//		 
////		 if (args.length != 3) {
////			 throw new Exception("Introduce las urls como argumentos");
////		 }
//
		 hacerGitDiff(urls.get(0), urls.get(1));
		
    }*/

}

