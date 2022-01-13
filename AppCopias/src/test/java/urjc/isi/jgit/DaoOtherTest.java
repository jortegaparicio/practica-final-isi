package urjc.isi.jgit;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

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
		assertEquals("[https://gitlab.etsit.urjc.es/brosaa/P1, "
				+ "https://gitlab.etsit.urjc.es/ja.ortega.2017/P1, https://gitlab.etsit.urjc.es/c.borao.2017/P1]", dao.urlsFiltradas(nombre_practica).toString());
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
		assertNotEquals("", dao.getContenidoInforme(nombre_practica));

	}

	@Test
	public void practicasDisponiblesTest() {
		assertEquals("[P1, P2]", dao.practicasDisponibles().toString());
	}

	@Test
	public void informesDisponiblesTest() {

		dao.saveInforme(new Informe("P2","Contenido del informe de la practica 2"));
		assertTrue(!dao.informesDisponibles().isEmpty());
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
