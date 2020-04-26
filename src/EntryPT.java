
public class EntryPT {
    private boolean presentBit;
    private boolean modifyBit;
    private int lastAccessTime;
    private int frameNummer;
    
    public EntryPT() {
        this.frameNummer = 0;
        this.modifyBit = false;
        this.lastAccessTime = -1;
        this.presentBit = false;
    }

    public boolean isPresent() {
        return presentBit;
    }

    public void setPresentBit(boolean b) {
        this.presentBit = b;
    }

    public boolean isModified() {
        return modifyBit;
    }

    public void setModifyBit(boolean m) {
        this.modifyBit = m;
    }

    public int getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(int l) {
        this.lastAccessTime = l;
    }

    public int getFrameNummer() {
        return frameNummer;
    }

    public void setFrameNummer(int i) {
        this.frameNummer = i;
    }
    
}