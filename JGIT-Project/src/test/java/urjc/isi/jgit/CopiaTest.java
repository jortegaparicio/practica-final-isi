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

	@Test
	public void hacerGitDiffTest_100copia() { //Dos prácticas iguales
		String resultado = comparador.hacerGitDiff("https://gitlab.etsit.urjc.es/brosaa/P1", "https://gitlab.etsit.urjc.es/brosaa/P1");

		assertTrue(resultado.contains("100.0%"));
	}
	
	@Test
	public void hacerGitDiffTest_0copia() { //Dos prácticas completamente distintas
		String resultado = comparador.hacerGitDiff("https://gitlab.etsit.urjc.es/brosaa/P1", "https://gitlab.etsit.urjc.es/brosaa/P2");

		assertTrue(resultado.contains("0.0%"));
	}
	
	@Test
	public void hacerGitDiffTest_porcentajecopia() { //Dos prácticas con partes similares
		String resultado = comparador.hacerGitDiff("https://gitlab.etsit.urjc.es/brosaa/P2", "https://gitlab.etsit.urjc.es/ja.ortega.2017/P2");

		assertFalse(resultado.contains("100.0%"));
		assertFalse(resultado.contains("0.0%"));
		assertTrue(resultado.contains("%"));
	}
	
	@Test
	public void hacerGitDiffTest_archivoVacio() { //Dos prácticas con partes similares
		String resultado = comparador.hacerGitDiff("https://gitlab.etsit.urjc.es/brosaa/P2", "https://gitlab.etsit.urjc.es/brosaa/P3");

		assertTrue(resultado.contains("0 líneas"));
		assertTrue(resultado.contains("0.0%"));
	}
	
	/**
	 * Tests para probar la función compararUltimoCommit del objeto comparador
	 */
	
	
	
	
	/**
	 * Tests para probar la función umbralNumCommit del objeto comparador
	 */
	
	
}
