package urjc.isi.jgit;

import org.eclipse.jgit.api.Git;

//import java.util.ArrayList;
//import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.diff.*;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.apache.commons.io.FileUtils;


public class Comparador {
		
	static final int UMBRAL_COPIA_COMMIT = 5;

	private static String resultadosCopia(String gitDiffOutput, String url1, String url2) {
	   try {
			// Creamos el DAO para poder trabajar con la base de datos de la aplicación
			AppDao dao = new AppDao();
			
		   String[] parts = gitDiffOutput.split("\n");
		   //System.out.println(gitDiffOutput); //Salida del git diff 
		   
		   float porcentCopia = 0;
		   int count = 0;
		   String f1_nlines = "";
	       String f2_nlines = "";
		   
		   if  (gitDiffOutput.length() != 0) {
			   String lines = gitDiffOutput.split("@@")[1];
		       String[] partsF = lines.split(",");
		       
		       f1_nlines = partsF[1].split(" ")[0];
		       
		       if (partsF.length != 3) {
		    	   f2_nlines = "1";
		       } else {
		    	   f2_nlines = partsF[2].split(" ")[0];
		       }

		       count = 0;

		       for(int i = 0; i < parts.length; i++) {
			       if(Character.compare(parts[i].charAt(0), ' ') == 0) {
			    	   count++;
			       }
		       }
		       int minLines = Math.max(Integer.parseInt(f2_nlines), Integer.parseInt(f1_nlines));
		       
		       porcentCopia = (count*100/(float) minLines);
		   } else {
			   porcentCopia = 100;
		   }

	       String resultado = "Alumnos/as " + dao.nombreAlumno(url1);
	       resultado += " y " + dao.nombreAlumno(url2) + ":\n";
	       
	       resultado += "La práctica de " + dao.nombreAlumno(url1) + " tiene " + f1_nlines + " líneas\n";
	       resultado += "La práctica de " + dao.nombreAlumno(url2) + " tiene " + f2_nlines + " líneas\n";
	       resultado += "Un total de " + count + " líneas son iguales\n";
	       resultado += "Porcentaje de copia del " + porcentCopia + "% entre los trabajos\n";
	       
	       return resultado;
	   
	   } catch (Exception e) {
		   throw new RuntimeException(e);
	   }
	}

	private static String obtenerFichero(File f, String url) {
		
		FileFilter filter;
		File[] files;
		
		try {
			String [] names = url.split("/");
			String practica = names[names.length - 1] + ".";
			
			
			filter = new FileFilter() {
				 public boolean accept(File f) {
					 return f.getName().startsWith(practica);
				 }
			 };
			 
			 files = f.listFiles(filter);

			 if (files.length != 1){
				 System.out.println("Solo debe haber un archivo con el nombre " + practica);
			 }
			 
			 return files[0].getName();
			 
		} catch (Exception e) {
			throw new RuntimeException(e);
	    }
	}
	
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
			 
			 return result.getRepository();
		 } catch (Exception e) {
			 throw new RuntimeException(e);
	
	 } 
	 }
	
	public String hacerGitDiff(String url1, String url2){

	    try {
	    	File localRepo1 = clonarepo(url1);
			File localRepo2 = clonarepo(url2);
			
			File f1 = new File(localRepo1 + "/" + obtenerFichero(localRepo1, url1));
			File f2 = new File(localRepo2 + "/" + obtenerFichero(localRepo2, url2));
			
			
			OutputStream result = new ByteArrayOutputStream();
			String output = null; 
	    	
	        RawText raw1 = new RawText(f1);
	        RawText raw2 = new RawText(f2);
	        EditList diffList = new EditList();
	
	        diffList.addAll(new HistogramDiff().diff(RawTextComparator.DEFAULT, raw1, raw2));

	        DiffFormatter diffForm = new DiffFormatter(result);
	        diffForm.format(diffList, raw1, raw2);
	        
	        output = result.toString();
	        
	        String resultado = resultadosCopia(output, url1, url2);	        
	        
	        diffForm.close();
	        FileUtils.deleteDirectory(localRepo1);
	        FileUtils.deleteDirectory(localRepo2);
	        
	        return resultado;
	        
	    } catch (IOException e) {
	    	throw new RuntimeException(e);
	    }
	}

	//comparamos el último commit hecho en 2 urls distintas
	@SuppressWarnings("resource")
	public String compararUltimoCommit (String url1, String url2){
		try {
			AppDao dao = new AppDao();
			String nombre1= dao.nombreAlumno(url1);
			String nombre2= dao.nombreAlumno(url2);
			String resultado = new String();
			
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
			if ((latestCommitTime1-latestCommitTime2 >=0 && latestCommitTime1-latestCommitTime2 <=1800)||
					(latestCommitTime2-latestCommitTime1 >=0 && latestCommitTime2-latestCommitTime1 <=1800)) {
					resultado = "Posible copia por último commit entre " +nombre1 +" y " 
					+ nombre2 +", no se diferencian en media hora \n";
			}else {
					resultado = "No hay indicios de copia entre " + nombre1 + " y "+ nombre2+
						", tiempo entre commits mayor de 30 minutos \n";
			}
			return resultado;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Devuelve el numero de commits de un repositorio
	 * @param dirRepo
	 * @return
	 */
	public static int numCommit(String dirRepo){
		 
		int res = 0;
		try (Repository repository = new FileRepositoryBuilder()
				    .setGitDir(new File(dirRepo + "/.git"))
				    .build();) {
	            try (Git git = new Git(repository)) {
	                Iterable<RevCommit> commits = git.log().all().call();
	                int count = 0;
	                for (RevCommit commit : commits) {
	                    res = count++;
	                }
	            } catch (Exception e) {
					e.printStackTrace();
				}
	     } catch (IOException e) {
			e.printStackTrace();
		}
		 return res;
	}

	/**
	 * Este metodo devuelve el directorio con mas copias en caso de que supere el umbral de copias. "No hay suficiente diferencia de commit entre los repositorios".
	 * @param dir1
	 * @param dir2
	 * @return
	 */
	private static String umbralNumCommit(String dir1, String url1, String dir2, String url2) {
		int ncommit1 = numCommit(dir1);
		int ncommit2 = numCommit(dir2);
		int diff = ncommit1-ncommit2;
		AppDao dao = new AppDao();

		
		if (diff > UMBRAL_COPIA_COMMIT) {
			return dao.nombreAlumno(url1) + " ha hecho " + diff + " commit(s) más que " + dao.nombreAlumno(url2) + "\n";
		}
		
		diff = ncommit2 - ncommit1;
		
		if (diff > UMBRAL_COPIA_COMMIT) {
			return dao.nombreAlumno(url2) + " ha hecho " + diff + " commit(s) más que " + dao.nombreAlumno(url1) + "\n";
		}
		return "No hay suficiente diferencia de commit entre los repositorios de los alumnos " + dao.nombreAlumno(url1) + " y " + dao.nombreAlumno(url2) + "\n";
		
	}
	
	public String compNumCommit(String url1, String url2) {
		try { 
			File localRepo1 = clonarepo(url1); 
			File localRepo2 = clonarepo(url2);
			return umbralNumCommit(localRepo1.toString(), url1, localRepo2.toString(), url2);
	    } catch (IOException e) {
	    	throw new RuntimeException(e);
	    }
	}

}




