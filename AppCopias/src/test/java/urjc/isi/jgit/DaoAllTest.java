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
	}
	
	@Test
	public void allPracticasTest() {
		List<Practica> practicas = dao.allPracticas();
		
		assertTrue(practicas.contains(new Practica("26237769H","P1","https://gitlab.etsit.urjc.es/brosaa/P1")));
		assertTrue(practicas.contains(new Practica("26237769H","P2","https://gitlab.etsit.urjc.es/brosaa/P2")));
		assertTrue(practicas.contains(new Practica("46239069U","P1","https://gitlab.etsit.urjc.es/ja.ortega.2017/P1")));
		assertTrue(practicas.contains(new Practica("46239069U","P2","https://gitlab.etsit.urjc.es/ja.ortega.2017/P2")));
		assertTrue(practicas.contains(new Practica("95639423Y","P1","https://gitlab.etsit.urjc.es/c.borao.2017/P1")));
	}

	@Test
	public void allResultadosTest() {
		
		List<Resultado> resultados = dao.allResultados();
		
		assertTrue(resultados.toString().equals("[]"));
		
	}

	@Test
	public void allInformesTest() {
		List<Informe> informes = dao.allInformes();
		
		assertTrue(informes.toString().equals("[]"));
		
	}
}

