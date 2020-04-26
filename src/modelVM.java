import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class modelVM { 
	public Ram ram;
    public int timer;
    public int[] paginaOffset;
    public Proces huidigProces;
    public List<Instructie> instructies;
    
    public List<Proces> processen;
    public String fileName= "Instructions_30_3.xml";
    
    
    public void init() {
    	instructies = leesXML(fileName);
    	processen = new ArrayList<>();
        timer = 0;
        ram = new Ram(12);
        huidigProces = null;
        paginaOffset = null;
    }
    
    public void setFileName(String newFileName) {
        this.fileName = newFileName;
        init();
    }
    public String getFileName() {
        return this.fileName;
    }
    
    public List<Instructie> leesXML(String bestand){
        try {
            File file = new File(bestand);
            instructies =  new ArrayList<Instructie>();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize ();
            
            NodeList xmlList = document.getElementsByTagName("instruction");
            for (int i=0; i<xmlList.getLength(); i++) {
                Element element = (Element)xmlList.item(i);
                int processID = Integer.parseInt(element.getElementsByTagName("processID").item(0).getTextContent());
                String operation = element.getElementsByTagName("operation").item(0).getTextContent();
                int address = Integer.parseInt(element.getElementsByTagName("address").item(0).getTextContent());
                Instructie instruction = new Instructie(processID, operation, address);
                instructies.add(instruction);
            }
            return instructies;
        }
        catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
    public Instructie performOneInstruction() {
        if(timer < instructies.size()) {
        	Instructie currentInstruction = instructies.get(timer);
            String operation = currentInstruction.getOperatie();
            switch(operation){
                case "Start":
                	paginaOffset = null;
                    huidigProces = new Proces(currentInstruction.getId());
                    processen.add(huidigProces);
                    ram.voegToe(huidigProces);
                    System.out.println(currentInstruction.getId()+" start");
                    break;
                case "Write":
                    System.out.println(currentInstruction.getId()+" write");
                    setHuidigProces(currentInstruction.getId()); 
                    paginaOffset = ram.splitsAdres(currentInstruction.getAdres());
                    ram.write(currentInstruction.getId(), paginaOffset, timer);
                     
                    break;
                case "Read":
                    System.out.println(currentInstruction.getId()+" read");
                    setHuidigProces(currentInstruction.getId());
                    paginaOffset = ram.splitsAdres(currentInstruction.getAdres());
                    ram.read(currentInstruction.getId(), paginaOffset, timer);
                    
                    break;
                case "Terminate":
                    System.out.println(currentInstruction.getId()+" terminate");
                    setHuidigProces(currentInstruction.getId());
                    paginaOffset = null;
                    ram.verwijderProces(currentInstruction.getId());
                    ram.pasAan();

                    break;
                default:break;
            }
            timer++;
            return currentInstruction;
        }
        else {
            return null;
        }
    }
    
    public Instructie getCurrentInstruction() {
        if (timer < instructies.size()) {
            return instructies.get(timer);
        }
        return null;
    }
    
    public void reset() {
        init();
    }

    public Proces getHuidigProces() {    
        return huidigProces;
    }

    private void setHuidigProces(int procesID) {
        for(Proces p: processen){
            if(p.getId() == procesID){
            	huidigProces = p;
            }
        }
        
    }
    public Pagina[] getFrames(){
        if(ram != null){
            return ram.frameArray;
        }
        return null;
    }
    
    public ArrayList<Proces> getAllProces() {
        if(ram != null){
            return (ArrayList<Proces>) processen;
        }      
        return null;
    }

    public int getFrameNumber(Instructie executedInstruction) {
        int pid = executedInstruction.getId();
        int adress= executedInstruction.getAdres();
        int [] pnEnOffset = ram.splitsAdres(adress);
        int framenummer =0;
        for(Pagina p: ram.frameArray){
            if(p != null) {
                if(p.getId() == pid){
                    if(p.getPaginaNummer() == pnEnOffset[0]){
                        return framenummer;
                    }
                }
            }   
            framenummer++;          
        }
        return -1;
    }
    
    public Integer getFysAdress(int framenummer, int offsetDec){
        if(framenummer >= 0) {
            String offsetbin = Integer.toBinaryString(offsetDec);
            String frameBin = Integer.toBinaryString(framenummer);
            StringBuilder fysAdress = new StringBuilder();
            StringBuilder zeros = new StringBuilder();
            
            int aantalZeros = 4-frameBin.length();
            for(int i = 0; i < aantalZeros; i++){
                zeros.append("0");
            }
            fysAdress = fysAdress.append(zeros).append(frameBin);

            zeros= zeros.delete(0, zeros.length());
            aantalZeros = 12-offsetbin.length(); 
            for(int i = 0; i < aantalZeros; i++){
                zeros.append("0");
            }
            fysAdress = fysAdress.append(zeros).append(offsetbin);
            String fys = fysAdress.toString();
            return Integer.parseInt(fys,2);
        }
        return -1;
    }
}