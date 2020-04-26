import java.util.ArrayList;

public class Proces {
	ArrayList<Pagina> pageList = new ArrayList<Pagina>();
	ArrayList <EntryPT> pageTable = new ArrayList<>();
	
	private int id;
	private int lastAccessTime;
	private int readCount;
	private int writeCount;

	public Proces(int k) {
		this.id = k;
		for(int i=0; i<16;i++) {
			this.pageList.add(new Pagina(this.id, i));
		}
		for(int i=0; i<16;i++) {
			this.pageTable.add(new EntryPT());
		}
		readCount = 0;
		writeCount = 0;
	}

	public void read() {
		readCount++;
	}

	public void increaseWriteToRAM() {
		writeCount++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(int l) {
		this.lastAccessTime = l;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int r) {
		this.readCount = r;
	}

	public int getWriteCount() {
		return writeCount;
	}

	public void setWriteCount(int w) {
		this.writeCount = w;
	}

	public int getAantalPaginas() {
		int aantalPaginas = 0;
		for(EntryPT pte : pageTable) {
			if(pte.isPresent()) {
				aantalPaginas++;
			}
		}
		return aantalPaginas;
	}

	 public Pagina vindPagina() {
		 for(int i=0; i<pageTable.size(); i++) {
			 if(!pageTable.get(i).isPresent()) {
				 return pageList.get(i);
			 }
		 }
		 return null;
	 }


	 public int getLRUFrameNummer() {
		 EntryPT pte = new EntryPT();
		 for(EntryPT tijdelijk : pageTable) {
			 if(tijdelijk.isPresent() && tijdelijk.getLastAccessTime() < pte.getLastAccessTime()) {
				 pte = tijdelijk;
			 }
		 }
		 return pte.getFrameNummer();
	 }

	 public Pagina getLRUPagina() {
		 int index = -1;
		 int min = Integer.MAX_VALUE;
		 for(int i=0; i<pageTable.size(); i++) {
			 if(pageTable.get(i).isPresent() && pageTable.get(i).getLastAccessTime() < min) {
				 min = pageTable.get(i).getLastAccessTime();
				 index = i;
			 }
		 }
		 if(index==-1)return pageList.get(0);
		 return pageList.get(index);
	 }

	 public void updatePageTable(int pagina, int f, boolean p , boolean m) {
		 EntryPT pte = pageTable.get(pagina);
		 pte.setPresentBit(p);
		 pte.setModifyBit(m);
		 pte.setFrameNummer(f);
	 }
	  
	 public boolean heeftPagina(int[] paginaOffset) {
		return pageTable.get(paginaOffset[0]).isPresent();
	 }

}