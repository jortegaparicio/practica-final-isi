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
		dao.resetDatabase();
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
	public void getContenidoInformeTest() {

		String nombre_practica = "P1";

		Informe new_informe = new Informe("P1","Contenido del informe");		
		dao.saveInforme(new_informe);
		assertEquals("Contenido del informe", dao.getContenidoInforme(nombre_practica));

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

	@Test
	public void generarResultadosTest() {
		Resultado new_resultado = new Resultado("http://gitlab.com/laugon/P1","http://gitlab.com/pacfer/P1","P1","contenido de la comparación entre la P1 de laura y paco");
		dao.saveResultado(new_resultado);
		
		Resultado new_resultado_2 = new Resultado("http://gitlab.com/laugon/P2","http://gitlab.com/pacfer/P2","P2","contenido de la comparación entre la P2 de laura y paco");
		dao.saveResultado(new_resultado_2);
		
		Resultado new_resultado_3 = new Resultado("http://gitlab.com/laugon/P2","http://gitlab.com/pepe99/P2","P2","contenido de la comparación entre la P2 de laura y Pepe");
		dao.saveResultado(new_resultado_3);

		assertEquals("[contenido de la comparación entre la P2 de laura y paco, contenido de la comparación entre la P2 de laura y Pepe]", dao.generarResultados("P2").toString());
	}
}
