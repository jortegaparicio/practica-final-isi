package urjc.isi.jgit;

import java.util.Objects;

public class Resultado {

    private String url1;
    private String url2;
    private String practica;
    private String contenido;

    public Resultado(String url1, String url2, String practica, String contenido) {
        this.url1 = url1;
        this.url2 = url2;
        this.practica = practica;
        this.contenido = contenido;
    }

    public String getUrl1() {
        return url1;
    }
    
    public String getUrl2() {
        return url2;
    }
    
    public String getPractica() {
        return practica;
    }
    
    public String getContenido() {
        return contenido;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resultado other = (Resultado) obj;
		return Objects.equals(contenido, other.contenido) && Objects.equals(practica, other.practica)
				&& Objects.equals(url1, other.url1) && Objects.equals(url2, other.url2);
	}

    @Override
	public int hashCode() {
		return Objects.hash(contenido, practica, url1, url2);
	}

	@Override
	public String toString() {
		return "Resultado [url1=" + url1 + ", url2=" + url2 + ", practica=" + practica + ", contenido=" + contenido
				+ "]";
	}
}
