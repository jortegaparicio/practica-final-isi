package urjc.isi.jgit;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.util.List;

public class DaoSaveTest {

	@Test
	public void saveTest() {

		AppDao dao = new AppDao();
		
		Informe new_informe = new Informe("P10","Contenido del Informe");
		Resultado new_resultado = new Resultado("http://gitlab.com/laugon/P10","http://gitlab.com/pacfer/P10","P10","contenido de la comparación entre la P10 de laura y paco");
		
		List<Resultado> allResultados = dao.allResultados();
		List<Informe> allInformes = dao.allInformes();
		
		if (!allResultados.contains(new_resultado)) {
			dao.saveResultado(new_resultado);
			allResultados = dao.allResultados();
		}
		
		if (!allInformes.contains(new_informe)) {
			dao.saveInforme(new_informe);
			allInformes = dao.allInformes();
		}
		
		assertTrue(allResultados.contains(new_resultado));
		assertTrue(allInformes.contains(new_informe));
		
	}
}
