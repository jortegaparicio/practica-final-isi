package es.urjc.isi.dao;

import java.util.ArrayList;
import java.util.List;

public class UrlFilter {

    public List<String> filterUrls(String nombre_practica) {

    	AppDao dao = new AppDao();
        List<Practica> practicas = dao.allPracticas();

        List<String> filtered_urls = new ArrayList<String>();

        for(Practica prac : practicas) {
            if(prac.getNombre().equals(nombre_practica))
            	filtered_urls.add(prac.getUrl());
        }
        
        return filtered_urls;
    }
    
    public static void main(String args[]) {
    	 
        UrlFilter filter = new UrlFilter();
        String nombre_practica = "P1";
        List<String> urls_filtradas = filter.filterUrls(nombre_practica);

        System.out.println(urls_filtradas.toString());
    }
}
