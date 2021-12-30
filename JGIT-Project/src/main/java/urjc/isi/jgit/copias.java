package urjc.isi.jgit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.jgit.api.errors.GitAPIException;

public class copias {

	public static void main(String[] args) {
		
		AppDao dao = new AppDao();
		
		if (args.length != 0 && args[0].equals("--resetdb")) {
			dao.resetDatabase();
		}
				
		System.out.println(dao.allAlumnos());
		
		List<String> disponibles = dao.practiceNames();
		String nombre_practica;
		Scanner sc = new Scanner(System.in);
		
		do {
			System.out.println("Puedes consultar las siguientes prácticas: \n" + disponibles.toString());
			System.out.println();
				
			System.out.println("Introduce el nombre de la práctica a consultar: ");
			
			nombre_practica = sc.nextLine();
			
		} while(!disponibles.contains(nombre_practica));
		
		sc.close();
		List<String> urls_filtradas = dao.filteredUrls(nombre_practica);
		
		Comparador comparador = new Comparador();
		
		// Hacemos las comparaciones entre las prácticas.
		
		 List<File> localDocs = new ArrayList<File>();
		 try {
			localDocs.add(new File(comparador.clonarepo(urls_filtradas.get(0))));
			localDocs.add(new File(comparador.clonarepo(urls_filtradas.get(1))));
			comparador.hacerGitDiff(localDocs.get(0), localDocs.get(1));
			 System.out.println(urls_filtradas.toString());
			    
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		 //localDocs.add(new File("/tmp/TestGitRepository2249447536820241654/P1.txt"));
		 //localDocs.add(new File("/tmp/TestGitRepository17213631211084400426/P1.txt"));
		 
		 //System.out.println(localDocs);

		
		dao.close();
	}
}
