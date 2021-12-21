package es.urjc.isi.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppDao {

	private static Connection c;

    public AppDao() {
        try {
            if(c!=null) return;
            
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            c.setAutoCommit(false);

            c.prepareStatement("drop table if exists alumnos").execute();
            c.prepareStatement("drop table if exists practicas").execute();
            c.prepareStatement("drop table if exists resultados").execute();
            
            c.prepareStatement("create table alumnos (dni varchar(10) not null, nombre varchar(30) not null, usuario_Git varchar(30) not null, primary key(dni))").execute();
            c.prepareStatement("create table practicas (dni varchar(10) not null, nombre varchar(70) not null, url varchar(80) not null, primary key(url), foreign key (dni) references Alumnos(dni))").execute();
            c.prepareStatement("create table resultados (url1 varchar(80), url2 varchar(80), text, primary key(url1, url2))").execute();
            
            c.prepareStatement("INSERT INTO Alumnos(dni, nombre, usuario_Git) VALUES ('26237769H','Paco Fernandez','pacfer');").execute();
            c.prepareStatement("INSERT INTO Alumnos(dni, nombre, usuario_Git) VALUES ('46239069U','María Perez','mariaperez');").execute();
            c.prepareStatement("INSERT INTO Alumnos(dni, nombre, usuario_Git) VALUES ('95639423Y','Laura Gonzalez','lauGon');").execute();
            
            c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('95639423Y','P1','http://gitlab.com/laugon/P1');").execute();
            c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('95639423Y','P2','http://gitlab.com/laugon/P2');").execute();
            c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('26237769H','P1','http://gitlab.com/pacfer/P1');").execute();
            c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('26237769H','P2','http://gitlab.com/pacfer/P2');").execute();
            c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('46239069U','P1','http://gitlab.com/mariaperez/P1');").execute();
            c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('46239069U','P3','http://gitlab.com/mariaperez/P3');").execute();  
                  
            c.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
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
    
    public void save(Alumno al) {
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

    public void save(Practica prac) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into alumnos (dni, nombre, usuario_Git) values (?,?,?)");
            ps.setString(1, prac.getDni());
            ps.setString(2, prac.getNombre());
            ps.setString(3, prac.getUrl());	
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
    
    public static void main(String args[]) {
   	 
    	AppDao dao = new AppDao();
        String nombre_practica = "P1";
        List<String> urls_filtradas = dao.filteredUrls(nombre_practica);

        System.out.println(urls_filtradas.toString());
        
        List<String> nombresDePracticas = dao.practiceNames();
        System.out.println(nombresDePracticas.toString());
    }
}
