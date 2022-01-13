package urjc.isi.jgit;

import java.util.Objects;

public class Informe {

	private String nombre;
	private String contenido;
	
	public Informe(String nombre, String contenido) {
		super();
		this.nombre = nombre;
		this.contenido = contenido;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(contenido, nombre);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Informe other = (Informe) obj;
		return Objects.equals(contenido, other.contenido) && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Informe [nombre=" + nombre + ", contenido=" + contenido + "]";
	}
}
