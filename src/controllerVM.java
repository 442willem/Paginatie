import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

public class controllerVM {

	private viewVM view;
	private modelVM model;

	public controllerVM(viewVM view, modelVM model) {
		this.view = view;
		this.model = model;
		this.view.addOneInstructionListener(new EenPerEenListener());
		this.view.addAllInstructionsListener(new AllemaalListener());
		this.view.addCancelListener(new CancelListener());
		this.view.addRadioButton1Listener(new FileSelectListener());
		this.view.addRadioButton2Listener(new FileSelectListener());
		this.view.addRadioButton3Listener(new FileSelectListener());
		this.model.init();
		this.view.update(model.getCurrentInstruction(), null);
	}

	class FileSelectListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try{
				String button = ((JRadioButton) e.getSource()).getActionCommand();
				if(button.equals("30_3")) {
					model.setFileName("Instructions_30_3.xml");
					reset();
				}else if(button.equals("20000_4")) {
					model.setFileName("Instructions_20000_4.xml"); 
					reset();
				}else if(button.equals("20000_20")) {
					model.setFileName("Instructions_20000_20.xml");
					reset();
				}
			}
			catch(Exception ex){
				System.out.println(ex);
			}
		}
	}

	public void reset() {
		model.cancel();
		view.initValues();	
		view.resetProcesTable();
		view.resetFrames();
		view.resetPageTable();
		view.update(model.getCurrentInstruction(), null);
	}

	class EenPerEenListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try{
				Instructie executedInstruction = model.performOneInstruction();
				Instructie currentInstruction = model.getCurrentInstruction();
				view.update(currentInstruction, executedInstruction);
				view.updatePageTable(model.getHuidigProces().pageTable);
				view.updateFrames(model.getFrames());
				view.updateProcesTable(model.getAllProces());
				view.updateTimer(model.timer);


				if(executedInstruction != null && model.paginaOffset != null){
					view.setFysiekAdres(model.getFysAdress(model.getFrameNumber(executedInstruction), model.paginaOffset[1])
							,model.getFrameNumber(executedInstruction),model.paginaOffset[1]);
				}
				else if(executedInstruction != null && model.paginaOffset == null) {
					view.setFysiekAdres(-1,-1,-1);
				}
			}
			catch(Exception ex){
				System.out.println(ex);
			}
		}
	}

	class AllemaalListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try{
				model.cancel();
				Instructie executedInstruction = null;
				for(int i=0; i<model.instructies.size(); i++) {
					executedInstruction = model.performOneInstruction();
				}
				Instructie currentInstruction = model.getCurrentInstruction();
				view.update(currentInstruction, executedInstruction);
				view.updatePageTable(model.getHuidigProces().pageTable);
				view.updateProcesTable(model.getAllProces());
				view.updateFrames(model.getFrames());
				view.updateTimer(model.timer);

				if(executedInstruction != null && model.paginaOffset != null){
					view.setFysiekAdres(model.getFysAdress(model.getFrameNumber(executedInstruction), model.paginaOffset[1])
							,model.getFrameNumber(executedInstruction), model.paginaOffset[1]);
				}
				else if(executedInstruction != null && model.paginaOffset == null) {
					view.setFysiekAdres(-1,-1,-1);
				}
			}
			catch(Exception ex){
				System.out.println(ex);
			}
		}
	}

	class CancelListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try{
				reset();
			}
			catch(Exception ex){
				System.out.println(ex);
			}
		}
	}

}