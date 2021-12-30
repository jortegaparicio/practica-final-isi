package es.urjc.isi.dao;

import java.util.List;
import java.util.Scanner;

public class copias {

	public static void main(String[] args) {
		
		AppDao dao = new AppDao();
		
		if (args.length == 1 && args[0].equals("--resetdb"))
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
	    
		dao.close();
	}
}
