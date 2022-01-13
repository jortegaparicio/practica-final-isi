package urjc.isi.jgit;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.util.List;

public class DaoSaveTest {

	@Test
	public void saveTest() {

		AppDao dao = new AppDao();
		dao.resetDatabase();
		
		Informe new_informe = new Informe("P1","Contenido del Informe");
		Resultado new_resultado = new Resultado("http://gitlab.com/laugon/P2","http://gitlab.com/pacfer/P2","P2","contenido de la comparación entre la P2 de laura y paco");
		
		dao.saveResultado(new_resultado);
		dao.saveInforme(new_informe);
		
		List<Resultado> allResultado = dao.allResultados();
		List<Informe> allInformes = dao.allInformes();
	
		assertTrue(allResultado.contains(new_resultado));
		assertTrue(allInformes.contains(new_informe));
		
	}
}
