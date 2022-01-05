package urjc.isi.jgit;

public class Resultado {

    private String url1;
    private String url2;
    private String contenido;

    public Resultado(String url1, String url2, String contenido) {
        this.url1 = url1;
        this.url2 = url2;
        this.contenido = contenido;
    }

    public String getUrl1() {
        return url1;
    }
    
    public String getUrl2() {
        return url2;
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
		if (contenido == null) {
			if (other.contenido != null)
				return false;
		} else if (!contenido.equals(other.contenido))
			return false;
		if (url1 == null) {
			if (other.url1 != null)
				return false;
		} else if (!url1.equals(other.url1))
			return false;
		if (url2 == null) {
			if (other.url2 != null)
				return false;
		} else if (!url2.equals(other.url2))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contenido == null) ? 0 : contenido.hashCode());
		result = prime * result + ((url1 == null) ? 0 : url1.hashCode());
		result = prime * result + ((url2 == null) ? 0 : url2.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Resultado [url1=" + url1 + ", url2=" + url2 + ", contenido=" + contenido + "]";
	}
}
