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
		
		if (resultados.size() == 0) {
			assertTrue(true);
		} else {
			assertTrue(resultados.get(0).getPractica().toString().equals("P2") 
					|| resultados.get(0).getPractica().toString().equals("P1") 
					|| resultados.get(0).getPractica().toString().equals("youtube-parser") 
					|| resultados.get(0).getPractica().toString().equals("P10") );
		}
	}

	@Test
	public void allInformesTest() {
		List<Informe> informes = dao.allInformes();
		
		if (informes.size() == 0) {
			assertTrue(true);
		} else {
			assertTrue(informes.get(0).getNombre().toString().equals("P2") 
					|| informes.get(0).getNombre().toString().equals("P1") 
					|| informes.get(0).getNombre().toString().equals("youtube-parser") 
					|| informes.get(0).getNombre().toString().equals("P10") );
		}
		
	}
}

