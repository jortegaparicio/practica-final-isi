package urjc.isi.jgit;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.util.List;

public class DaoSaveTest {

	static AppDao dao;

	@BeforeClass
	public static void InitDao() {
		dao = new AppDao();
		dao.resetDatabase();
	}

	@Test
	public void saveTest() {

		Informe new_informe = new Informe("P1","Contenido del Informe");
		Alumno new_alumno = new Alumno("8776883J","Ignacio Pons", "igpons");
		Practica new_practica = new Practica("95639423Y", "P10", "url=http://gitlab.com/laugon/P10");
		Resultado new_resultado = new Resultado("http://gitlab.com/laugon/P2","http://gitlab.com/pacfer/P2","P2","contenido de la comparación entre la P2 de laura y paco");
		
		dao.saveResultado(new_resultado);
		dao.saveInforme(new_informe);
		dao.saveAlumno(new_alumno);
		dao.savePractica(new_practica);
		
		List<Resultado> allResultado = dao.allResultados();
		List<Informe> allInformes = dao.allInformes();
		List<Alumno> allAlumnos = dao.allAlumnos();
		List<Practica> allPracticas = dao.allPracticas();
		
		assertTrue(allResultado.contains(new_resultado));
		assertTrue(allAlumnos.contains(new_alumno));
		assertTrue(allInformes.contains(new_informe));
		assertTrue(allPracticas.contains(new_practica));
		
	}
}
