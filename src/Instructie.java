
public class Instructie {
    private int id;
    private String operatie;
    private int adres;
    
    public Instructie(int i, String o, int a) {
        this.id = i;
        this.operatie = o;
        this.adres = a;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int i) {
    	this.id=i;
    }

	public String getOperatie() {
        return operatie;
    }

    public void setOperatie(String operatie) {
		this.operatie = operatie;
	}

    public int getAdres() {
        return adres;
    }

	public void setAdres(int adres) {
		this.adres = adres;
	}

    @Override
    public String toString() {
        return "processID= " + this.id + "\n"+"operation= " + this.operatie+"\n"+"address= " + this.adres + "\n"; //To change body of generated methods, choose Tools | Templates.
    }
    
}