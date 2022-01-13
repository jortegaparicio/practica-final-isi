package urjc.isi.jgit;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.util.List;

public class DaoOtherTest {

	static AppDao dao;

	@BeforeClass
	public static void InitDao() {
		dao = new AppDao();
	}

	/**
	 *  Tests para probar el acceso a la base de datos
	 */
	@Test
	public void urlsFiltradasTest() {

		String nombre_practica = "P1";
		List<String> filtradas = dao.urlsFiltradas(nombre_practica);
		
		assertTrue(filtradas.contains("https://gitlab.etsit.urjc.es/brosaa/P1"));
	
	}

	@Test
	public void nombreAlumnoTest() {

		String url_repo = "https://gitlab.etsit.urjc.es/c.borao.2017/P1";
		assertEquals("César Borao", dao.nombreAlumno(url_repo).toString());
	}

	@Test
	public void practicasDisponiblesTest() {
		
		List<String> practicas = dao.practicasDisponibles();
		
		assertTrue(practicas.contains("P1"));
		assertTrue(practicas.contains("P2"));
		assertTrue(practicas.contains("youtube-parser"));
	}

	@Test
	public void informesDisponiblesTest() {

		String inf = "P2";
		
		if(!dao.informesDisponibles().contains(inf)) {
			dao.saveInforme(new Informe("P2","Contenido del informe de la practica 2"));
		}
		
		assertTrue(dao.informesDisponibles().contains(inf));
	}
}
