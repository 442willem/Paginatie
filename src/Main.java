
public class Main {
    public static void main(String[] args) {
        
    	modelVM model = new modelVM();
    	viewVM view = new viewVM();
        controllerVM controller = new controllerVM(view,model);
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                view.setVisible(true);
            }
        });
        
    }
}