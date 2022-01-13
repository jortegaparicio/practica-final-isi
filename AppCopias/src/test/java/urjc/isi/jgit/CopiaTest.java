package urjc.isi.jgit;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class CopiaTest {
	
	static Comparador comparador;

	@BeforeClass
	public static void InitComparador() {
		comparador = new Comparador();
	}
	
	/**
	 * Tests para probar la función hacerGitDiff del objeto comparador
	 */

	@Test //Dos prácticas iguales
	public void hacerGitDiffTest_100copia() { //Dos prácticas iguales
		String resultado = comparador.hacerGitDiff("https://gitlab.etsit.urjc.es/brosaa/P1", "https://gitlab.etsit.urjc.es/brosaa/P1");

		assertTrue(resultado.contains("100.0%"));
	}
	
	@Test //Dos prácticas completamente distintas
	public void hacerGitDiffTest_0copia() { 
		String resultado = comparador.hacerGitDiff("https://gitlab.etsit.urjc.es/brosaa/P1", "https://gitlab.etsit.urjc.es/brosaa/P2");

		assertTrue(resultado.contains("0.0%"));
	}
	
	@Test //Dos prácticas con partes similares
	public void hacerGitDiffTest_porcentajecopia() {
		String resultado = comparador.hacerGitDiff("https://gitlab.etsit.urjc.es/brosaa/P2", "https://gitlab.etsit.urjc.es/ja.ortega.2017/P2");

		assertFalse(resultado.contains("100.0%"));
		assertFalse(resultado.contains("0.0%"));
		assertTrue(resultado.contains("%"));
	}
	
	@Test //Se identifica que se introduce un archivo vacío con 0 líneas y tiene 0% copia con un archivo normal
	public void hacerGitDiffTest_archivoVacio() { //Dos prácticas con partes similares
		String resultado = comparador.hacerGitDiff("https://gitlab.etsit.urjc.es/brosaa/P2", "https://gitlab.etsit.urjc.es/brosaa/P3");

		assertTrue(resultado.contains("0.0%"));
		assertTrue(resultado.contains("0 líneas"));
	}
	
	/**
	 * Tests para probar la función compararUltimoCommit del objeto comparador
	 */
	
	
	
	
	/**
	 * Tests para probar la función umbralNumCommit del objeto comparador
	 */
	
	@Test // Dos repos con commits que no llegan al umbral
	public void umbralNumCommit_noSuperaUmbral() {
		String resultado = comparador.compNumCommit("https://gitlab.etsit.urjc.es/ja.ortega.2017/P2", "https://gitlab.etsit.urjc.es/ja.ortega.2017/P2");

		assertTrue(resultado.contains("No hay suficiente diferencia"));
	}	
	
	@Test // Dos repos con commits en el que el primero tiene más commits que el segundo y sobrepasa el umbral
	public void umbralNumCommit_SuperaUmbral1() { 
		String resultado = comparador.compNumCommit("https://gitlab.etsit.urjc.es/brosaa/P1", "https://gitlab.etsit.urjc.es/ja.ortega.2017/P1");
		assertTrue(resultado.contains("commit(s) más que"));

	}	
	
	@Test // Dos repos con commits en el que el segundo tiene más commits que el primero y sobrepasa el umbral
	public void umbralNumCommit_SuperaUmbral2() {
		String resultado = comparador.compNumCommit("https://gitlab.etsit.urjc.es/ja.ortega.2017/P1", "https://gitlab.etsit.urjc.es/brosaa/P1");
		assertTrue(resultado.contains("commit(s) más que"));
	}	
	
	
	
}