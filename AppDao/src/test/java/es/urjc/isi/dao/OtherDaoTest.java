package es.urjc.isi.dao;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class OtherDaoTest {
    
	AppDao dao;
	
	@Before
	public void InitDao() {
		dao = new AppDao();
		dao.resetDatabase();
	}
	
	
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
    public void UrlFilterTest() {
		
    	String nombre_practica = "P2";
    	assertEquals("[http://gitlab.com/laugon/P2, http://gitlab.com/pacfer/P2]", dao.filteredUrls(nombre_practica).toString());
    }

  
}

