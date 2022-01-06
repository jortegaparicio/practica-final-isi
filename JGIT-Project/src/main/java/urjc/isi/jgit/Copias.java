package urjc.isi.jgit;

import java.util.List;
import java.util.Scanner;

public class Copias {

	public static void main(String[] args) {
		
		AppDao dao = new AppDao();

		if (args.length != 0 && args[0].equals("--resetdb"))
			dao.resetDatabase();
		
		//System.out.println(dao.allAlumnos());
		
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
		
		List<String> informes_disponibles = dao.informesDisponibles();
		
		if (informes_disponibles.contains(nombre_practica)) {
			System.out.println(dao.getContenidoInforme(nombre_practica));
			
		} else {
			List<String> urls_filtradas = dao.filteredUrls(nombre_practica);
			//System.out.println(urls_filtradas.toString());
			
			// Hacemos las comparaciones entre las prácticas.
			
			Comparador comparador = new Comparador();
			
			for (int i=0; i < urls_filtradas.size(); i++) {
				for (int j=0; j < urls_filtradas.size(); j++) {

					if (j > i) {
						String resultado;
						
						// Aquí iran los métodos de comparación
						// System.out.println("compara");
						resultado = "brosaa con ja.ortega tienen 38% copia";
						
						// Aquí tenemos ya el resultado de las comparaciones de un repo con el resto
						dao.saveResultado(new Resultado(urls_filtradas.get(i),urls_filtradas.get(j),nombre_practica,resultado));	
					}
				}
			}
			
			// Acabamos las comparaciones y generamos el informe
			
			List<String> resultados = dao.generarResultados(nombre_practica);
			
			String informe = "\nINFORME DE COPIAS DE LA PRÁCTICA '" + nombre_practica + "'\n";
			String separator = "-";
			informe += separator.repeat(informe.length()) + "\n";
			
			for(String resultado: resultados) {
				informe += resultado + '\n';
			}
			dao.saveInforme(new Informe(nombre_practica, informe));
			
			// Imprimimos el informe
			System.out.println(informe);
			dao.close();
		}
	}
}
