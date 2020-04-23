import java.util.ArrayList;

public class Proces {
	private int id;
	private int lastAccessTime;
	private int readCount;
	private int writeCount;

	ArrayList<Pagina> pageList = new ArrayList<Pagina>();
	ArrayList <EntryPT> pageTable = new ArrayList<>();

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

	public void setLastAccessTime(int lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getWriteCount() {
		return writeCount;
	}

	public void setWriteCount(int writeCount) {
		this.writeCount = writeCount;
	}

	public int getAantalPaginas() {
		int aantalPaginas = 0;
		for(EntryPT pte : pageTable) {
			if(pte.getPresentBit()) {
				aantalPaginas++;
			}
		}
		return aantalPaginas;
	}

	 public Pagina vindPagina() {
		 for(int i=0; i<pageTable.size(); i++) {
			 if(!pageTable.get(i).getPresentBit()) {
				 return pageList.get(i);
			 }
		 }
		 return null;
	 }
	 
	 public boolean heeftPagina(int[] paginaOffset) {
		int pagina = paginaOffset[0];
		return pageTable.get(pagina).getPresentBit();
	 }


	 public int getLRUFrameNumber() {
		 EntryPT pte = new EntryPT();
		 for(EntryPT tijdelijk : pageTable) {
			 if(tijdelijk.getPresentBit() && tijdelijk.getLastAccessTime() < pte.getLastAccessTime()) {
				 pte = tijdelijk;
			 }
		 }
		 return pte.getFrameNummer();
	 }

	 public Pagina getLRUPage() {
		 int index = -1;
		 int min = Integer.MAX_VALUE;
		 for(int i=0; i<pageTable.size(); i++) {
			 if(pageTable.get(i).getPresentBit() && pageTable.get(i).getLastAccessTime() < min) {
				 min = pageTable.get(i).getLastAccessTime();
				 index = i;
			 }
		 }
		 return pageList.get(index);
	 }

	 public void updatePageTable(int paginaNummer, int frameNummer, boolean presentBit, boolean modifyBit) {
		 EntryPT pte = pageTable.get(paginaNummer);
		 pte.setFrameNummer(frameNummer);
		 pte.setPresentBit(presentBit);
		 pte.setModifyBit(modifyBit);
	 }

}