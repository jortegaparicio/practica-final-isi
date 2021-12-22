package es.urjc.isi.dao;


import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 * No estoy probando con Mocks
 * @author alumno
 *
 */
public class UrlFilterTest {

    @Test
    public void filterInvoices() {

    	// Ya está todo metido en la base de datos
        
        UrlFilter filter = new UrlFilter();
        String nombre_practica = "P1";
        List<String> urls_filtradas = filter.filterUrls(nombre_practica);

        assertEquals(3, urls_filtradas.size());
        assertEquals("http://gitlab.com/laugon/P1", urls_filtradas.get(0));
    }

}

