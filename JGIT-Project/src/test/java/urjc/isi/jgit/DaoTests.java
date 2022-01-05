package urjc.isi.jgit;


import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.List;

public class DaoTests {

	AppDao dao;

	@Before
	public void InitDao() {
		dao = new AppDao();
		dao.resetDatabase();
	}

	/**
	 *  Tests para probar el acceso a la base de datos
	 */
	@Test
	public void allAlumnosTest() {

		assertEquals("Paco Fernandez",dao.allAlumnos().get(0).getNombre());
		assertEquals("46239069U",dao.allAlumnos().get(1).getDni());
		assertEquals("[Alumno [dni=26237769H, nombre=Paco Fernandez, usuario_git=pacfer], "
				+ "Alumno [dni=46239069U, nombre=María Perez, usuario_git=mariaperez], "
				+ "Alumno [dni=95639423Y, nombre=Laura Gonzalez, usuario_git=lauGon]]", dao.allAlumnos().toString());
	}

	@Test
	public void allPracticasTest() {

		assertEquals("[Practica [dni=95639423Y, nombre=P1, url=http://gitlab.com/laugon/P1], "
				+ "Practica [dni=95639423Y, nombre=P2, url=http://gitlab.com/laugon/P2], "
				+ "Practica [dni=26237769H, nombre=P1, url=http://gitlab.com/pacfer/P1], "
				+ "Practica [dni=26237769H, nombre=P2, url=http://gitlab.com/pacfer/P2], "
				+ "Practica [dni=46239069U, nombre=P1, url=http://gitlab.com/mariaperez/P1], "
				+ "Practica [dni=46239069U, nombre=P3, url=http://gitlab.com/mariaperez/P3]]", dao.allPracticas().toString());
	}

	@Test
	public void allResultados() {
		dao.saveResultado(new Resultado("http://gitlab.com/laugon/P2","http://gitlab.com/pacfer/P2","contenido de la comparación entre la P2 de laura y paco"));

		assertEquals("[Resultado [url1=http://gitlab.com/laugon/P2, "
				+ "url2=http://gitlab.com/pacfer/P2, "
				+ "contenido=contenido de la comparación entre la P2 de laura y paco]]", dao.allResultados().toString());
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

		Resultado new_resultado = new Resultado("http://gitlab.com/laugon/P2","http://gitlab.com/pacfer/P2","contenido de la comparación entre la P2 de laura y paco");
		dao.saveResultado(new_resultado);

		List<Resultado> allResultado = dao.allResultados();

		for(Resultado res: allResultado) {
			if(new_resultado.equals(res))
				return;
		}
		fail();
	}

	@Test
	public void practiceNamesTest() {
		assertEquals("[P1, P2, P3]", dao.practiceNames().toString());
	}

	@Test
	public void urlFilterTest() {

		String nombre_practica = "P2";
		assertEquals("[http://gitlab.com/laugon/P2, http://gitlab.com/pacfer/P2]", dao.filteredUrls(nombre_practica).toString());
	}
}

