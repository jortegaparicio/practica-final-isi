package urjc.isi.jgit;


import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.List;

public class DaoTests {

	AppDao dao;

	@Before
	public void InitDao() {
		dao = new AppDao();
		dao.resetDatabase();
	}

	/**
	 *  Tests para probar el acceso a la base de datos
	 */
	@Test
	public void allAlumnosTest() {

		assertEquals("Belén Rosa",dao.allAlumnos().get(0).getNombre());
		assertEquals("46239069U",dao.allAlumnos().get(1).getDni());
		assertEquals("[Alumno [dni=26237769H, nombre=Belén Rosa, usuario_git=brosaa], "
				+ "Alumno [dni=46239069U, nombre=Juan Antonio Ortega, usuario_git=ja.ortega.2017], "
				+ "Alumno [dni=95639423Y, nombre=César Borao, usuario_git=c.borao.2017]]", dao.allAlumnos().toString());
	}

	
	@Test
	public void allPracticasTest() {

		assertEquals("[Practica [dni=26237769H, nombre=P1, url=https://gitlab.etsit.urjc.es/brosaa/P1], "
				+ "Practica [dni=26237769H, nombre=P2, url=https://gitlab.etsit.urjc.es/brosaa/P2], "
				+ "Practica [dni=46239069U, nombre=P1, url=https://gitlab.etsit.urjc.es/ja.ortega.2017/P1], "
				+ "Practica [dni=46239069U, nombre=P2, url=https://gitlab.etsit.urjc.es/ja.ortega.2017/P2], "
				+ "Practica [dni=95639423Y, nombre=P1, url=https://gitlab.etsit.urjc.es/c.borao.2017/P1]]", dao.allPracticas().toString());
	}

	@Test
	public void allResultados() {
		dao.saveResultado(new Resultado("https://gitlab.etsit.urjc.es/brosaa/P1","https://gitlab.etsit.urjc.es/c.borao.2017/P1","P1","contenido de la comparación entre la P1 de brosaa y c.borao.2017"));

		assertEquals("[Resultado [url1=https://gitlab.etsit.urjc.es/brosaa/P1, "
				+ "url2=https://gitlab.etsit.urjc.es/c.borao.2017/P1, practica=P1, "
				+ "contenido=contenido de la comparación entre la P1 de brosaa y c.borao.2017]]", dao.allResultados().toString());
	}
/*
	@Test
	public void saveAlumnoTest() {

		Alumno new_alumno = new Alumno("8776883J","Ignacio Pons", "igpons");
		dao.saveAlumno(new_alumno);

		List<Alumno> allAlumnos = dao.allAlumnos();

		for(Alumno al: allAlumnos) {
			if(new_alumno.equals(al))
				return;
		}
		fail();
	}

	@Test
	public void savePracticaTest() {

		Practica new_practica = new Practica("95639423Y", "P10", "url=http://gitlab.com/laugon/P10");
		dao.savePractica(new_practica);

		List<Practica> allPracticas = dao.allPracticas();

		for(Practica prac: allPracticas) {
			if(new_practica.equals(prac))
				return;
		}
		fail();
	}

	@Test
	public void saveResultadoTest() {

		Resultado new_resultado = new Resultado("http://gitlab.com/laugon/P2","http://gitlab.com/pacfer/P2","contenido de la comparación entre la P2 de laura y paco");
		dao.saveResultado(new_resultado);

		List<Resultado> allResultado = dao.allResultados();

		for(Resultado res: allResultado) {
			if(new_resultado.equals(res))
				return;
		}
		fail();
	}

	@Test
	public void practiceNamesTest() {
		assertEquals("[P1, P2, P3]", dao.practiceNames().toString());
	}

	@Test
	public void urlFilterTest() {

		String nombre_practica = "P2";
		assertEquals("[http://gitlab.com/laugon/P2, http://gitlab.com/pacfer/P2]", dao.filteredUrls(nombre_practica).toString());
	}*/
}

