package urjc.isi.jgit;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.util.List;

public class DaoAllTest {

	static AppDao dao;

	@BeforeClass
	public static void InitDao() {
		dao = new AppDao();
		dao.resetDatabase();
	}

	/**
	 *  Tests para probar el acceso a la base de datos
	 */
	@Test
	public void allAlumnosTest() {
		
		List<Alumno> alumnos = dao.allAlumnos();
		
		assertTrue(alumnos.contains(new Alumno("26237769H","Belén Rosa","brosaa")));
		assertTrue(alumnos.contains(new Alumno("46239069U","Juan Antonio Ortega","ja.ortega.2017")));
		assertTrue(alumnos.contains(new Alumno("95639423Y","César Borao","c.borao.2017")));
		
		// assertTrue(alumnos.contains(new Alumno("6788342J","Pablo Barquero","pablobv")));
		
		/*
		assertEquals("Belén Rosa",dao.allAlumnos().get(0).getNombre());
		assertEquals("46239069U",dao.allAlumnos().get(1).getDni());
		assertEquals("[Alumno [dni=26237769H, nombre=Belén Rosa, usuario_git=brosaa], "
				+ "Alumno [dni=46239069U, nombre=Juan Antonio Ortega, usuario_git=ja.ortega.2017], "
				+ "Alumno [dni=95639423Y, nombre=César Borao, usuario_git=c.borao.2017]]", dao.allAlumnos().toString());
	*/
	}
	
	@Test
	public void allPracticasTest() {
		List<Practica> practicas = dao.allPracticas();
		
		assertTrue(practicas.contains(new Practica("26237769H","P1","https://gitlab.etsit.urjc.es/brosaa/P1")));
		assertTrue(practicas.contains(new Practica("26237769H","P2","https://gitlab.etsit.urjc.es/brosaa/P2")));
		assertTrue(practicas.contains(new Practica("46239069U","P1","https://gitlab.etsit.urjc.es/ja.ortega.2017/P1")));
		assertTrue(practicas.contains(new Practica("46239069U","P2","https://gitlab.etsit.urjc.es/ja.ortega.2017/P2")));
		assertTrue(practicas.contains(new Practica("95639423Y","P1","https://gitlab.etsit.urjc.es/c.borao.2017/P1")));
		
		/*assertEquals("[Practica [dni=26237769H, nombre=P1, url=https://gitlab.etsit.urjc.es/brosaa/P1], "
				+ "Practica [dni=26237769H, nombre=P2, url=https://gitlab.etsit.urjc.es/brosaa/P2], "
				+ "Practica [dni=46239069U, nombre=P1, url=https://gitlab.etsit.urjc.es/ja.ortega.2017/P1], "
				+ "Practica [dni=46239069U, nombre=P2, url=https://gitlab.etsit.urjc.es/ja.ortega.2017/P2], "
				+ "Practica [dni=95639423Y, nombre=P1, url=https://gitlab.etsit.urjc.es/c.borao.2017/P1]]", dao.allPracticas().toString());
	*/
	}

	@Test
	public void allResultadosTest() {
		
		Resultado result = new Resultado("https://gitlab.etsit.urjc.es/brosaa/P1","https://gitlab.etsit.urjc.es/c.borao.2017/P1","P1","contenido de la comparación entre la P1 de brosaa y c.borao.2017");
		dao.saveResultado(result);
		List<Resultado> resultados = dao.allResultados();
		
		assertTrue(resultados.contains(result));
		
		/*assertEquals("[Resultado [url1=https://gitlab.etsit.urjc.es/brosaa/P1, "
				+ "url2=https://gitlab.etsit.urjc.es/c.borao.2017/P1, practica=P1, "
				+ "contenido=contenido de la comparación entre la P1 de brosaa y c.borao.2017]]", dao.allResultados().toString());
		*/
	}

	@Test
	public void allInformesTest() {

		Informe inf = new Informe("P1","Contenido del informe de comparación de copias");
		dao.saveInforme(inf);
		
		List<Informe> informes = dao.allInformes();
		
		assertTrue(informes.contains(inf));
		
		//assertEquals("[Informe [nombre=P1, "
		//		+ "contenido=Contenido del informe de comparación de copias]]", dao.allInformes().toString());
	}
}

