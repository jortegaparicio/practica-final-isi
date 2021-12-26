package es.urjc.isi.dao;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;


public class OtherDaoTest {
    
	AppDao dao;
	
	@Before
	public void InitDao() {
		dao = new AppDao();
		dao.resetDatabase();
	}
	
    @Test
    public void UrlFilterTest() {

    	//AppDao dao = new AppDao();
    	//dao.resetDatabase();
    	
    	//Practica p1 = new Practica("36576834J", "Practica_1", "http://git.es/pepe/Practica_1");
    	//Practica p2 = new Practica("36576834J", "Practica_2", "http://git.es/pepe/Practica_2");
    	//Practica p1_otra = new Practica("9877684Y", "Practica_1", "http://git.es/fede/Practica_1");
    
    	//dao.savePractica(p1);
    	//dao.savePractica(p2);
    	//dao.savePractica(p1_otra);
    	
    	String nombre_practica = "P2";
    	
    	assertEquals("[http://gitlab.com/laugon/P2, http://gitlab.com/pacfer/P2]", dao.filteredUrls(nombre_practica).toString());
    	
    }

    @Test
    public void allAlumnosTest() {

    	//Alumno manolo = new Alumno("36576834J", "manolo", "manolo_123");
    	//Alumno maria = new Alumno("67624561H", "maria", "meryy_h");
    	
    	//AppDao dao = new AppDao();
    	//dao.resetDatabase();
    	
    	//dao.saveAlumno(manolo);
    	//dao.saveAlumno(maria);
    
    	assertEquals("Paco Fernandez",dao.allAlumnos().get(0).getNombre());
    	assertEquals("46239069U",dao.allAlumnos().get(1).getDni());

    }
}

