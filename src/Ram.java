import java.util.ArrayList;
import java.util.List;

public class Ram {
	private int frameCount;
	List<Proces> processen = new ArrayList<>();
	Pagina[] frameArray = new Pagina[12];

	public Ram(int frameCount) {
		this.frameCount = frameCount;
	}

	public void voegToe(Proces proces){
		if(processen.size() >= 4) {
			int min = Integer.MAX_VALUE;
			int index = -1;
			for( int i=0; i<processen.size(); i++) {
				if(processen.get(i).getLastAccessTime() < min) {
					min = processen.get(i).getLastAccessTime();
					index = i;
				}
			}
			verwijderProces(processen.get(index).getId());
		}
		processen.add(proces);
		pasAan();
	}

	public void verwisselPagina(int frame, Pagina nieuw) {
		Pagina oud = frameArray[frame];
		if(oud != null) {
			Proces oudProces = vindProces(oud);
			oudProces.updatePageTable(oud.getPaginaNummer(), 0, false, false);
			oudProces.increaseWriteToRAM();
		}
		Proces nieuwProces = vindProces(nieuw);
		nieuwProces.updatePageTable(nieuw.getPaginaNummer(), frame, true, false);
		nieuwProces.read();
		frameArray[frame] = nieuw;
	}

	public Proces vindProces(Pagina pagina) {
		for(Proces p : processen) {
			if(p.getId() == pagina.getId()) {
				return p;
			}
		}
		return null;
	}

	public void setModifybitTrue(Proces proces, int[] paginaOffset) {
		proces.pageTable.get(paginaOffset[0]).setModifyBit(true);
	}

	private void setLastAccessTime(int t, Proces proces, int[] paginaOffset) {
		proces.pageTable.get(paginaOffset[0]).setLastAccessTime(t);
	}  

	public void verwijderProces(int id) {
		Proces proces = new Proces(id);
		for(int i=0; i<processen.size(); i++) {
			if (processen.get(i).getId() == id) {
				proces = processen.get(i);
			}
		}
		for(int i=0; i<frameArray.length; i++) {
			if(frameArray[i]!=null&&frameArray[i].getId() == proces.getId()) {
				verwijderPagina(frameArray[i]);
			}
		}
		processen.remove(proces);
	}

	public void verwijderPagina(Pagina page) {
		Proces proces = vindProces(page);
		proces.updatePageTable(page.getPaginaNummer(), proces.pageTable.get(page.getPaginaNummer()).getFrameNummer(), false, false);
		frameArray[proces.pageTable.get(page.getPaginaNummer()).getFrameNummer()] = null;
	}

	public void pasAan() {
		if(!processen.isEmpty()) {
			int aantalFrames = frameCount/processen.size();
			for(Proces p : processen) {
				while (p.getAantalPaginas() != aantalFrames) {
					if(p.getAantalPaginas() > aantalFrames) {
						verwijderPagina(p.getLRUPagina());
					}
					else {
						Pagina nieuw = p.vindPagina();
						boolean hulp = true;
						int index=0;
						while(hulp) {
							if(frameArray[index] != null)index++;
							else hulp=false;
						}
						frameArray[index] = nieuw;
						p.updatePageTable(nieuw.getPaginaNummer(), index, true, false);
					}
				}
			}
		}
	}

	public int[] splitsAdres(int adres){
		String binair = Integer.toString(adres,2);
		StringBuilder binairAdres= new StringBuilder(binair);
		while(binairAdres.length()<16){
			binairAdres.insert(0,"0");
		}
		String binairString = binairAdres.toString();
		String pageNr = binairString.substring(0,binairString.length()-12);
		String offset = binairString.substring(binairString.length()-12);
		return new int [] {Integer.parseInt(pageNr,2),Integer.parseInt(offset,2)};   
	}

	public Proces isProcesInRam(int id){
		for(Proces p: processen)if(p.getId() == id) return p;
		return null;
	}

	public void write(int pid, int[] pageNrAndOffset, int time){
		Proces proc = isProcesInRam(pid);
		if(proc == null){
			proc = new Proces(pid);
			voegToe(proc); 
		}
		if(proc.heeftPagina(pageNrAndOffset)){
			setModifybitTrue(proc,  pageNrAndOffset);
			setLastAccessTime(time, proc, pageNrAndOffset);
		}else{
			Pagina oldPage = proc.getLRUPagina();
			int oldPageFrameNumber = proc.pageTable.get(oldPage.getPaginaNummer()).getFrameNummer();
			verwisselPagina(oldPageFrameNumber, proc.pageList.get(pageNrAndOffset[0]));
			proc.updatePageTable(proc.pageList.get(pageNrAndOffset[0]).getPaginaNummer(), oldPageFrameNumber, true, true);
			proc.pageTable.get(pageNrAndOffset[0]).setLastAccessTime(time);
		}
		proc.setLastAccessTime(time);
	}

	public void read(int pid, int[] pageNrAndOffset, int time) {
		Proces proc = isProcesInRam(pid);
		if(proc == null){
			voegToe(proc); 
		}
		if(proc.heeftPagina(pageNrAndOffset)){
			setLastAccessTime(time, proc, pageNrAndOffset);
		}else{
			Pagina oldPage = proc.getLRUPagina();
			int oldPageFrameNumber = proc.pageTable.get(oldPage.getPaginaNummer()).getFrameNummer();
			verwisselPagina(oldPageFrameNumber, proc.pageList.get(pageNrAndOffset[0]));
			proc.updatePageTable(proc.pageList.get(pageNrAndOffset[0]).getPaginaNummer(), oldPageFrameNumber, true, false);
			proc.pageTable.get(pageNrAndOffset[0]).setLastAccessTime(time);
		}
		proc.setLastAccessTime(time);
	}  
}