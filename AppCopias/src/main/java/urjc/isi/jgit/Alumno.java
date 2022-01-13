package urjc.isi.jgit;


import java.util.Objects;

public class Alumno {

    private String dni;
    private String nombre;
    private String usuario_git;

    public Alumno(String dni, String nombre, String usuario_git) {
        this.dni = dni;
        this.nombre = nombre;
        this.usuario_git = usuario_git;
    }

    public String getDni() {
        return dni;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getUsuario_git() {
        return usuario_git;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;
		return Objects.equals(dni, other.dni) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(usuario_git, other.usuario_git);
	}

    @Override 
	public int hashCode() {
		return Objects.hash(dni, nombre, usuario_git);
	}

	@Override
	public String toString() {
		return "Alumno [dni=" + dni + ", nombre=" + nombre + ", usuario_git=" + usuario_git + "]";
	}
}
