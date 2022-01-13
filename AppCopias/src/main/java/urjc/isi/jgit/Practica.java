package urjc.isi.jgit;

import java.util.Objects;

public class Practica {

    private String dni;
    private String nombre;
    private String url;

    public Practica(String dni, String nombre, String url) {
        this.dni = dni;
        this.nombre = nombre;
        this.url = url;
    }

    public String getDni() {
        return dni;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getUrl() {
        return url;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Practica other = (Practica) obj;
		return Objects.equals(dni, other.dni) && Objects.equals(nombre, other.nombre) && Objects.equals(url, other.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni, nombre, url);
	}

	@Override
	public String toString() {
		return "Practica [dni=" + dni + ", nombre=" + nombre + ", url=" + url + "]";
	}
}
