
public class Pagina {
    private int id;
    private int paginaNummer;
    
    public Pagina(int procesID, int nummer) {
        this.id = procesID;
        this.paginaNummer = nummer;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
		this.id = id;
	}

	public int getPaginaNummer() {
		return paginaNummer;
    }

	public void setPaginaNummer(int paginaNummer) {
		this.paginaNummer = paginaNummer;
	}
}
