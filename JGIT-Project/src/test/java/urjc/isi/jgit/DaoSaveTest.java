package urjc.isi.jgit;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.util.List;

public class DaoSaveTest {

	static AppDao dao = new AppDao();

	@BeforeClass
	public static void InitDao() {
		// dao = new AppDao();
		dao.resetDatabase();
	}

	@Test
	public void saveInformeTest() {

		Informe new_informe = new Informe("P1","Contenido del Informe");
		dao.saveInforme(new_informe);

		List<Informe> allInformes = dao.allInformes();

		for(Informe inf: allInformes) {
			if(new_informe.equals(inf))
				return;
		}
		fail();
	}

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

		Resultado new_resultado = new Resultado("http://gitlab.com/laugon/P2","http://gitlab.com/pacfer/P2","P2","contenido de la comparación entre la P2 de laura y paco");
		dao.saveResultado(new_resultado);


		List<Resultado> allResultado = dao.allResultados();

		for(Resultado res: allResultado) {
			if(new_resultado.equals(res))
				return;
		}
		fail();
	}
}

