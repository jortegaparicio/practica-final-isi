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
		
		if (args.length != 0 && args[0].equals("--resetdb"))
			dao.resetDatabase();
			
		
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
		System.out.println(urls_filtradas.toString());
		
		// Hacemos las comparaciones entre las prácticas.
		
		Comparador comparador = new Comparador();
		
		for (int i=0; i < urls_filtradas.size(); i++) {
			for (int j=0; j < urls_filtradas.size(); j++) {

				if (j > i) {
					String resultado;
					System.out.println("compara");
					resultado = "resultado de la comparación"
					/*try {
						; //comparador.hacerGitDiff(new File (comparador.clonarepo(urls_filtradas.get(i))), new File (comparador.clonarepo(urls_filtradas.get(j))));
						    
					} catch (IOException | GitAPIException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} */
					
				}
			}
		}
		dao.close();
	}
}
