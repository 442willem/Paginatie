import java.util.ArrayList;
import java.util.List;

public class Ram {
	private int frameCount;
	List<Proces> processen = new ArrayList<>();
	Pagina[] frameArray = new Pagina[12];

	public Ram(int frameCount) {
		this.frameCount = frameCount;
	}

	public void voegToe(Proces proces, int timer) {
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
		Pagina oldPage = frameArray[frame];
		if(oldPage != null) {
			Proces oldPageProcess = findProcesByPage(oldPage);
			// Modify bit gets set to false if it was true
			oldPageProcess.updatePageTable(oldPage.getPaginaNummer(), 0, false, false);
			oldPageProcess.increaseWriteToRAM();
		}
		Proces newPageProcess = findProcesByPage(nieuw);
		newPageProcess.updatePageTable(nieuw.getPaginaNummer(), frame, true, false);
		newPageProcess.read();
		frameArray[frame] = nieuw;
	}

	public Proces findProcesByPage(Pagina page) {
		for(Proces p : processen) {
			if(p.getId() == page.getId()) {
				return p;
			}
		}
		return null;
	}

	public void setModifybitTrue(Proces proces, int[] paginaOffset) {
		int page = paginaOffset[0];
		proces.pageTable.get(page).setModifyBit(true);
	}

	private void setLastAccessTime(int time, Proces proces, int[]paginaOffset) {
		int page = paginaOffset[0];
		proces.pageTable.get(page).setLastAccessTime(time);
	}  

	public void verwijderProces(int id) {
		Proces proces = new Proces(id);
		for(int i=0; i<processen.size(); i++) {
			if (processen.get(i).getId() == id) {
				proces = processen.get(i);
			}
		}
		for(int i=0; i<frameArray.length; i++) {
			if(frameArray[i].getId() == proces.getId()) {
				verwijderPagina(frameArray[i]);
			}
		}
		processen.remove(proces);
	}

	public void verwijderPagina(Pagina page) {
		Proces proces = findProcesByPage(page);
		int frameNumber = proces.pageTable.get(page.getPaginaNummer()).getFrameNummer();
		proces.updatePageTable(page.getPaginaNummer(), frameNumber, false, false);
		frameArray[frameNumber] = null;
	}

	public void pasAan() {
		if(processen.size() > 0) {
			int aantalFrames = frameCount/processen.size();
			for(Proces p : processen) {
				while (p.getAantalPaginas() != aantalFrames) {
					if(p.getAantalPaginas() > aantalFrames) {
						verwijderPagina(p.getLRUPage());
					}
					else {
						Pagina nieuw = p.vindPagina();
						boolean volgende = false;
						int emptyFrameIndex = 0;
						while(!volgende) {
							if(frameArray[emptyFrameIndex] == null) {
								volgende = true;
							}
							else {
								emptyFrameIndex++;
							}
						}
						frameArray[emptyFrameIndex] = nieuw;
						p.updatePageTable(nieuw.getPaginaNummer(), emptyFrameIndex, true, false);
					}
				}
			}
		}
	}

	public int[] splitsAdres(int virtualAdres){
		String binair = Integer.toString(virtualAdres,2);
		int nullen = 16-binair.length();
		StringBuilder binairAdress16= new StringBuilder();
		for(int i = 0; i < nullen; i++){
			binairAdress16.append("0");
		}
		binairAdress16.append(binair);
		String binair16 = binairAdress16.toString();
		String pageNr = binair16.substring(0,binair16.length()-12);
		String offset = binair16.substring(binair16.length()-12);
		return new int [] {Integer.parseInt(pageNr,2),Integer.parseInt(offset,2)};   
	}

	public Proces isProcesInRam(int id){
		for(Proces p: processen){
			if(p.getId() == id) return p;
		}
		return null;
	}

	public void write(int pid, int[] pageNrAndOffset, int time){
		Proces proc = isProcesInRam(pid);
		if(proc == null){
			proc = new Proces(pid);
			voegToe(proc,time); 
		}
		if(proc.heeftPagina(pageNrAndOffset)){
			setModifybitTrue(proc,  pageNrAndOffset);
			setLastAccessTime(time, proc, pageNrAndOffset);
		}else{
			Pagina oldPage = proc.getLRUPage();
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
			voegToe(proc,time); 
		}
		if(proc.heeftPagina(pageNrAndOffset)){
			setLastAccessTime(time, proc, pageNrAndOffset);
		}else{
			Pagina oldPage = proc.getLRUPage();
			int oldPageFrameNumber = proc.pageTable.get(oldPage.getPaginaNummer()).getFrameNummer();
			verwisselPagina(oldPageFrameNumber, proc.pageList.get(pageNrAndOffset[0]));
			proc.updatePageTable(proc.pageList.get(pageNrAndOffset[0]).getPaginaNummer(), oldPageFrameNumber, true, false);
			proc.pageTable.get(pageNrAndOffset[0]).setLastAccessTime(time);
		}
		proc.setLastAccessTime(time);
	}  
}