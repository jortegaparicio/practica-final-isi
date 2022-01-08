package urjc.isi.jgit;

import java.util.List;
import java.util.Scanner;

public class Copias {

	/**
	 * Punto de entrada de nuestro programa de copias.
	 * 
	 * @param args: si queremos resetar la base de datos args[0] == --resetdb
	 */
	public static void main(String[] args) {
		
		// Creamos el DAO para poder trabajar con la base de datos de la aplicación
		AppDao dao = new AppDao();
	
		// Comprobamos si el programa se ha lanzado con el flag "--resetdb".
		// Si es así, se reinicia la base de datos con el contenido original.
		if (args.length != 0 && args[0].equals("--resetdb"))
			dao.resetDatabase();
		
		// Se muestran las prácticas disponibles para conocer su informe.
		List<String> disponibles = dao.practicasDisponibles();
		String nombre_practica;
		Scanner sc = new Scanner(System.in);
		
		do {
			System.out.println("Puedes consultar las siguientes prácticas: \n" + disponibles.toString());
			System.out.println();
				
			System.out.println("Introduce el nombre de la práctica a consultar: ");
			
			nombre_practica = sc.nextLine();
			
			// Si el nombre de práctica introducido no se corresponde con ninguna almacenada en la BD,
			// se vuelve a pedir al usuarios que introduca un nuevo nombre.
		} while(!disponibles.contains(nombre_practica));
		sc.close();
		
		// Consulta a la BD qué prácticas tienen ya un informe generado (de consultas previas)
		List<String> informes_disponibles = dao.informesDisponibles();
		
		// Si se ha pedido una práctica que ya tenía el informe generado, se devuelve el informe.
		// Si no hay un informe ya generado, se procede a generarlo.
		if (informes_disponibles.contains(nombre_practica)) {
			System.out.println(dao.getContenidoInforme(nombre_practica));
			
		} else {
			
			// Consulta a la BD las urls de los repositorios de las prácticas de los alumnos correspondientes a ese nombre de práctica.
			List<String> urls_filtradas = dao.urlsFiltradas(nombre_practica);
			
			// Creamos un objeto comparador para comparar estas prácticas.
			Comparador comparador = new Comparador();
			
			// Hacemos comparaciones de dos en dos entre prácticas sin repetición.
			for (int i=0; i < urls_filtradas.size(); i++) {
				for (int j=0; j < urls_filtradas.size(); j++) {
					if (j > i) {
						String resultado;
						
						// Llamamos a los métodos de comparación de la clase comparador (programados con JGit)
						resultado = comparador.compNumCommit(urls_filtradas.get(i), urls_filtradas.get(j));
						//System.out.println(resultado);

						// Guardamos el resultado de la comparación entre dos prácticas en la BD
						dao.saveResultado(new Resultado(urls_filtradas.get(i),urls_filtradas.get(j),nombre_practica,resultado));	
					}
				}
			}
			
			// Una vez hemos acabado las comparaciones, generamos el informe y lo guardamos en la BD.
			List<String> resultados = dao.generarResultados(nombre_practica);
			
			String informe = "\nINFORME DE COPIAS DE LA PRÁCTICA '" + nombre_practica + "'\n";
			String separator = "-";
			informe += separator.repeat(informe.length()) + "\n";
			
			for(String resultado: resultados) {
				informe += resultado + '\n';
			}
			dao.saveInforme(new Informe(nombre_practica, informe));
			
			// Imprimimos el informe generado
			System.out.println(informe);
			dao.close();
		}
	}
}
