package urjc.isi.jgit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * DAO para nuestra aplicación de detección de copias.
 * 
 * @author César Borao
 *
 */
public class AppDao {

	private static Connection c;

	/**
	 *  Crea una conexión con nuestra base de datos
	 */
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
    
    /**
     * Resetea la base de datos con los valores iniciales
     */
    public void resetDatabase() {
        
    	try {
	            		
	        // Creamos las tablas de la BD
	        c.prepareStatement("drop table if exists alumnos").execute();
	        c.prepareStatement("drop table if exists practicas").execute();
	        c.prepareStatement("drop table if exists resultados").execute();
	        c.prepareStatement("drop table if exists informes").execute();
	        
	        c.prepareStatement("create table alumnos (dni varchar(10) not null, nombre varchar(30) not null, usuario_Git varchar(30) not null, primary key(dni))").execute();
	        c.prepareStatement("create table practicas (dni varchar(10) not null, nombre varchar(70) not null, url varchar(80) not null, primary key(url), foreign key (dni) references Alumnos(dni))").execute();
	        c.prepareStatement("create table resultados (url1 varchar(80) not null, url2 varchar(80) not null, practica varchar(30) not null, contenido varchar(200))").execute();
	        c.prepareStatement("create table informes (nombre_practica varchar(80) not null, contenido varchar(200) not null, primary key(nombre_practica))").execute();
	        
	        // Insertamos el contenido inicial de la BD
	        c.prepareStatement("INSERT INTO Alumnos(dni, nombre, usuario_Git) VALUES ('26237769H','Belén Rosa','brosaa')").execute();
	        c.prepareStatement("INSERT INTO Alumnos(dni, nombre, usuario_Git) VALUES ('46239069U','Juan Antonio Ortega','ja.ortega.2017')").execute();
	        c.prepareStatement("INSERT INTO Alumnos(dni, nombre, usuario_Git) VALUES ('95639423Y','César Borao','c.borao.2017')").execute();
	        c.prepareStatement("INSERT INTO Alumnos(dni, nombre, usuario_Git) VALUES ('67883426J','Pablo Barquero','pablobv')").execute();
	        
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('26237769H','P1','https://gitlab.etsit.urjc.es/brosaa/P1')").execute();
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('26237769H','P2','https://gitlab.etsit.urjc.es/brosaa/P2')").execute();
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('26237769H','youtube-parser','https://gitlab.etsit.urjc.es/brosaa/youtube-parser')").execute();
	        
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('46239069U','P1','https://gitlab.etsit.urjc.es/ja.ortega.2017/P1')").execute();
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('46239069U','P2','https://gitlab.etsit.urjc.es/ja.ortega.2017/P2')").execute();
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('46239069U','youtube-parser','https://gitlab.etsit.urjc.es/ja.ortega.2017/youtube-parser')").execute();
	        
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('95639423Y','P1','https://gitlab.etsit.urjc.es/c.borao.2017/P1')").execute();
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('95639423Y','P2','https://gitlab.etsit.urjc.es/c.borao.2017/P2')").execute();
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('95639423Y','youtube-parser','https://gitlab.etsit.urjc.es/c.borao.2017/youtube-parser')").execute();
	        
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('67883426J','P1','https://gitlab.etsit.urjc.es/pablobv/P1')").execute();
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('67883426J','P2','https://gitlab.etsit.urjc.es/pablobv/P2')").execute();
	        c.prepareStatement("INSERT INTO Practicas(dni, nombre, url) VALUES ('67883426J','youtube-parser','https://gitlab.etsit.urjc.es/pablobv/youtube-parser')").execute();
	              
	        c.commit();
        
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
    }
    
    /**
     * Método que devuelve la tabla de alumnos
     * 
     * @return La lista de todos los alumnos
     */
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

    /**
     * Método que devuelve la tabla de prácticas
     * 
     * @return La lista de todos las prácticas
     */
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
    		c.commit();

    	} catch (SQLException e) {
    		throw new RuntimeException(e);
    	} finally {
    		return allPracticas;
    	}
    }
    
    /**
     * Método que devuelve la tabla con los resultados de las comparaciones entre dos repositorios.
     * 
     * @return La lista de todos las prácticas
     */
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
    
    /**
     * Método que devuelve la tabla con los informes de detección de copias generados
     * 
     * @return La lista de todos los informes
     */
    @SuppressWarnings("finally")
    public List<Informe> allInformes() {

    	List<Informe> allInformes = new ArrayList<Informe>();

    	try {
    		PreparedStatement ps = c.prepareStatement("select * from informes");

    		ResultSet rs = ps.executeQuery();

    		while(rs.next()) {
    			String nombre = rs.getString("nombre_practica");
    			String contenido = rs.getString("contenido");
    			allInformes.add(new Informe(nombre,contenido));
    		}

    	} catch (SQLException e) {
    		throw new RuntimeException(e);
    	} finally {
    		return allInformes;
    	}
    }
    
    /**
     * Método para guardar un informe en la BD
     * @param inf: El informe a guardar en la BD
     */
    public void saveInforme(Informe inf) {
    	try {
            PreparedStatement ps = c.prepareStatement("insert into informes (nombre_practica, contenido) values (?,?)");
            ps.setString(1, inf.getNombre());
            ps.setString(2, inf.getContenido());	
            ps.execute();

            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Método para guardar un nuevo Alumno en la BD.
     * @param al: El alumno a guardar
     */
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

    /**
     * Método para guardar una Práctica en la BD.
     * @param prac: La práctica a guardar en la BD
     */
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
    
    /**
     * Método para guardar un Resultado en la BD.
     * @param res: El resultado a guardar
     */
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
    
    /**
     * Método para cerrar la conexión con la base de datos.
     */
    public void close() {
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Método para obtener una lista de urls de la misma práctica para los diferentes alumnos
     * 
     * @param nombre_practica
     * @return Un lista con las urls de los repos de los alumnos que se corresponden con ese nombre de práctica
     */
    @SuppressWarnings("finally")
	public List<String> urlsFiltradas(String nombre_practica){
    	
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
    
    /**
     * Método para conocer el alumno que es dueño del repositorio que se pasa como parámetro.
     * 
     * @param url_repo: repositorio del cual queremos conocer su dueño
     * @return el nombre del alumno
     */
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
    
    /**
     * Método para conocer el contenido de un informe almacenado en la base de datos
     * 
     * @param nombre_practica: práctica de la cual queremos conocer su informe de copias.
     * @return el contenido del informe
     */
    @SuppressWarnings("finally")
    public String getContenidoInforme(String nombre_practica){

    	String nombre = null;

    	try {
    		PreparedStatement ps = c.prepareStatement("select informes.contenido from informes where informes.nombre_practica = '" + nombre_practica + "'");
    		ResultSet rs = ps.executeQuery();

    		nombre = rs.getString("contenido");  
    		c.commit();
    	} catch (SQLException e) {
    		throw new RuntimeException(e);
    	} finally {
              return nombre;
          }    
      }
    
    /**
     *  Método para conocer los nombres de las prácticas disponibles para consultar su informe de copias
     *  
     * @return una lista de los nombres de las prácticas
     */
    @SuppressWarnings("finally")
	public List<String> practicasDisponibles(){
    
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
    
    /**
     *  Método para consultar los nombres de las prácticas que tienen ya generado un informe de copias 
     * 
     * @return una lista de nombres de prácticas
     */
    @SuppressWarnings("finally")
   	public List<String> informesDisponibles(){
       	
       	List<String> names = new ArrayList<String>();
       	
       	try {
       		PreparedStatement ps = c.prepareStatement("select nombre_practica from informes");
               ResultSet rs = ps.executeQuery();

               while(rs.next()) {
                   String nombre = rs.getString("nombre_practica");  
                   names.add(nombre);
               }

           } catch (SQLException e) {
               throw new RuntimeException(e);
           } finally {
               return names;
           }    
       }
   
    /**
     * Método para consultar todos los resultados de las comparaciones entre las prácticas de los alumnos que se correspondan con un nombre de práctica en concreto
     * 
     * @param nombre_practica de la cual queremos conocer los resultados de las comparaciones
     * @return una lista con los resultados de las comparaciones
     */
    @SuppressWarnings("finally")
	public List<String> generarResultados(String nombre_practica){
    	
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
