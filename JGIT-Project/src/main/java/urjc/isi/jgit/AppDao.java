package urjc.isi.jgit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppDao {

	private static Connection c;

    public AppDao() {
        try {
            if(c!=null) return;
           
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            c.setAutoCommit(false);
         
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
    }    
    
    public void resetDatabase() {
        
    	try {
	        c.setAutoCommit(false);
	
	        c.prepareStatement("drop table if exists alumnos").execute();
	        c.prepareStatement("drop table if exists practicas").execute();
	        c.prepareStatement("drop table if exists resultados").execute();
	        
	        c.prepareStatement("create table alumnos (dni varchar(10) not null, nombre varchar(30) not null, usuario_Git varchar(30) not null, primary key(dni))").execute();
	        c.prepareStatement("create table practicas (dni varchar(10) not null, nombre varchar(70) not null, url varchar(80) not null, primary key(url), foreign key (dni) references Alumnos(dni))").execute();
	        c.prepareStatement("create table resultados (url1 varchar(80) not null, url2 varchar(80) not null, practica varchar(30) not null, contenido varchar(200))").execute();
	        
	        c.prepareStatement("INSERT INTO Alumnos(dni, nombre, usuario_Git) VALUES ('26237769H','Belén Rosa','brosaa');").execute();
	        c.prepareStatement("INSERT INTO Alumnos(dni, nombre, usuario_Git) VALUES ('46239069U','Juan Antonio Ortega','ja.ortega.2017');").execute();
	        c.prepareStatement("INSERT INTO Alumnos(dni, nombre, usuario_Git) VALUES ('95639423Y','César Borao','c.borao.2017');").execute();
	        
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('26237769H','P1','https://gitlab.etsit.urjc.es/brosaa/P1');").execute();
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('26237769H','P2','https://gitlab.etsit.urjc.es/brosaa/P2');").execute();
	        
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('46239069U','P1','https://gitlab.etsit.urjc.es/ja.ortega.2017/P1');").execute();
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('46239069U','P2','https://gitlab.etsit.urjc.es/ja.ortega.2017/P2');").execute();
	        
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('95639423Y','P1','https://gitlab.etsit.urjc.es/c.borao.2017/P1');").execute();
	              
	        c.commit();
        
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
    }
    
    @SuppressWarnings("finally")
	public List<Alumno> allAlumnos() {

        List<Alumno> allAlumnos = new ArrayList<Alumno>();

        try {
            PreparedStatement ps = c.prepareStatement("select * from alumnos");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String dni = rs.getString("dni");
                String name = rs.getString("nombre");
                String git_user = rs.getString("usuario_Git");
                allAlumnos.add(new Alumno(dni, name, git_user));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return allAlumnos;
        }
    }

    @SuppressWarnings("finally")
    public List<Practica> allPracticas() {

    	List<Practica> allPracticas = new ArrayList<Practica>();

    	try {
    		PreparedStatement ps = c.prepareStatement("select * from practicas");

    		ResultSet rs = ps.executeQuery();

    		while(rs.next()) {
    			String dni = rs.getString("dni");
    			String name = rs.getString("nombre");
    			String url = rs.getString("url");
    			allPracticas.add(new Practica(dni, name, url));
    		}

    	} catch (SQLException e) {
    		throw new RuntimeException(e);
    	} finally {
    		return allPracticas;
    	}
    }
    
    @SuppressWarnings("finally")
    public List<Resultado> allResultados() {

    	List<Resultado> allResultados = new ArrayList<Resultado>();

    	try {
    		PreparedStatement ps = c.prepareStatement("select * from resultados");

    		ResultSet rs = ps.executeQuery();

    		while(rs.next()) {
    			String url1 = rs.getString("url1");
    			String url2 = rs.getString("url2");
    			String practica = rs.getString("practica");
    			String contenido = rs.getString("contenido");
    			allResultados.add(new Resultado(url1, url2, practica, contenido));
    		}

    	} catch (SQLException e) {
    		throw new RuntimeException(e);
    	} finally {
    		return allResultados;
    	}
    }
    
    public void saveAlumno(Alumno al) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into alumnos (dni, nombre, usuario_Git) values (?,?,?)");
            ps.setString(1, al.getDni());
            ps.setString(2, al.getNombre());
            ps.setString(3, al.getUsuario_git());	
            ps.execute();

            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePractica(Practica prac) {
        try {
            
            PreparedStatement ps = c.prepareStatement("insert into practicas (dni, nombre, url) values (?,?,?)");
            ps.setString(1, prac.getDni());
            ps.setString(2, prac.getNombre());
            ps.setString(3, prac.getUrl());	
            ps.execute();

            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void saveResultado(Resultado res) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into resultados (url1, url2, practica, contenido) values (?,?,?,?)");
            ps.setString(1, res.getUrl1());
            ps.setString(2, res.getUrl2());
            ps.setString(3, res.getPractica());
            ps.setString(4, res.getContenido());	
            ps.execute();

            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void close() {
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @SuppressWarnings("finally")
	public List<String> filteredUrls(String nombre_practica){
    	
    	List<String> filtered_urls = new ArrayList<String>();
    	
    	try {
    		PreparedStatement ps = c.prepareStatement("select url from practicas where nombre = '" + nombre_practica + "'");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String url = rs.getString("url");  
                filtered_urls.add(url);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return filtered_urls;
        }    
    }
    
    @SuppressWarnings("finally")
    public String nombreAlumno(String url_repo){

    	String nombre = null;

    	try {
    		PreparedStatement ps = c.prepareStatement("select alumnos.nombre from practicas join alumnos on practicas.dni = alumnos.dni where practicas.url = '" + url_repo + "'");
    		ResultSet rs = ps.executeQuery();

    		nombre = rs.getString("nombre");  
   
    	} catch (SQLException e) {
    		throw new RuntimeException(e);
    	} finally {
              return nombre;
          }    
      }
    
    @SuppressWarnings("finally")
	public List<String> practiceNames(){
    	
    	List<String> names = new ArrayList<String>();
    	
    	try {
    		PreparedStatement ps = c.prepareStatement("select nombre from practicas group by nombre");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String nombre = rs.getString("nombre");  
                names.add(nombre);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return names;
        }    
    }
   
    @SuppressWarnings("finally")
	public List<String> generarResultado(String nombre_practica){
    	
    	List<String> contenido = new ArrayList<String>();
    	
    	try {
    		PreparedStatement ps = c.prepareStatement("select contenido from resultados where practica = '" + nombre_practica + "'");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String info = rs.getString("contenido");  
                contenido.add(info);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return contenido;
        }    
    }
}
