
package urjc.isi.jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import org.eclipse.jgit.diff.*;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class Comparador {
	
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
	
	public static void hacerGitDiff(File f1, File f2) throws IOException,
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
	private static Repository clonarepo2(String remoteUrl) throws IOException { 

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
			 return result.getRepository();
		 } catch (Exception e) {
			 throw new RuntimeException(e);
	
	 } 
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
	//comparamos el último commit hecho en 2 urls distintas
	@SuppressWarnings("resource")
	public static void compararUltimoCommit (String url1, String url2) throws Exception{
		try {
			AppDao dao = new AppDao();
			String nombre1= dao.nombreAlumno(url1);
			String nombre2= dao.nombreAlumno(url2);
			
			Repository localRepo1 = clonarepo2(url1);
			Repository localRepo2 = clonarepo2(url2);
			//seleccionamos el último commit de cada url
			RevCommit latestCommitF1 = new Git(localRepo1).log().setMaxCount(1).call().iterator().next();
			int latestCommitTime1 = latestCommitF1.getCommitTime();
			
			RevCommit latestCommitF2 = new Git(localRepo2).log().setMaxCount(1).call().iterator().next();
			int latestCommitTime2 = latestCommitF2.getCommitTime();
			
			/*
			 * En latestCommitTime tenemos el tiempo en segundos.
			 * Ahora comparamos ambos - Vamos a estipular como media hora de diferencia el tiempo
			 * para el que consideramos que es posible que haya copia
			 */
			System.out.println(latestCommitTime1);
			System.out.println(latestCommitTime2);
			if ((latestCommitTime1-latestCommitTime2 >=0 && latestCommitTime1-latestCommitTime2 <=1800)||
					(latestCommitTime2-latestCommitTime1 >=0 && latestCommitTime2-latestCommitTime1 <=1800)) {
				System.out.println("Posible copia por último commit entre " +nombre1 +" y " 
					+ nombre2 +", no se diferencian en media hora");
			}else {
				System.out.println("No hay indicios de copia entre " + nombre1 + " y "+ nombre2+
						", tiempo entre commits mayor de 30 minutos");
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
    public static void main(String[] args) throws Exception {

		 List<String> urls = new ArrayList<String>();
		 urls.add("https://gitlab.etsit.urjc.es/brosaa/P1");
		 urls.add("https://gitlab.etsit.urjc.es/ja.ortega.2017/P1");
		 
/*		 List<Repository> localDocs = new ArrayList<Repository>();
		 localDocs.add(clonarepo2(urls.get(0)));
		 localDocs.add(clonarepo2(urls.get(1)));
	*/	 
		 //localDocs.add(new Repository(clonarepo2(urls.get(0)))); 
		 //localDocs.add(new Repository(clonarepo2(urls.get(1))));
		 //localDocs.add(new File("/tmp/TestGitRepository2249447536820241654/P1.txt"));
		 //localDocs.add(new File("/tmp/TestGitRepository17213631211084400426/P1.txt"));
		 
//		 System.out.println(localDocs);
		 compararUltimoCommit(urls.get(0),urls.get(1));
		 //hacerGitDiff(localDocs.get(0), localDocs.get(1));
		
    }

}

