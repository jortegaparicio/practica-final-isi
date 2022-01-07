package urjc.isi.jgit;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

public class DaoOtherTest {

	static AppDao dao = new AppDao();

	@BeforeClass
	public static void InitDao() {
		// dao = new AppDao();
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

		Informe new_informe = new Informe("P1","Contenido del informe");		
		dao.saveInforme(new_informe);

		String nombre_practica = "P1";
		assertEquals("Contenido del informe", dao.getContenidoInforme(nombre_practica));

	}

	@Test
	public void practicasDisponiblesTest() {
		assertEquals("[P1, P2]", dao.practicasDisponibles().toString());

	}

	/*@Test
	public void informesDisponiblesTest() {

	}

	@Test
	public void generarResultadosTest() {

	}*/
}
