import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

public class viewVM extends JFrame {

	private JPanel contentPane;

	//timer en knoppen
	JPanel panel;
	JLabel lblTimer;
	JLabel labelTimer;
	JPanel panel_1;
	JRadioButton rdbtn303;
	JRadioButton rdbtn200004;
	JRadioButton rdbtn2000020;
	JButton btnVolgendeInstructie;
	JButton btnAlleInstructies;
	JButton btnReset;
	//tabbladen
	JTabbedPane tabbedPane;
	JPanel tabInstructies;
	JPanel panel_links;
	//volgende instructie
	JLabel lblVolgendeInstructie;
	JLabel lblId;
	JLabel lblIdValue;
	JLabel lblAdres;
	JLabel lblAdresValue;
	JLabel lblOperatie;
	JLabel lblOperatieValue;
	//vorige instructie
	JPanel panel_rechts;
	JLabel lblVorigeInstructie;
	JLabel lblId_1;
	JLabel lblIdValue_1;
	JLabel lblAdres_1;
	JLabel lblAdresValue_1;
	JLabel lblOperatie_1 ;
	JLabel lblOperatieValue_1;
	JLabel lblFysiekAdres;
	JLabel lblFysiekAdresValue;
	JLabel lblFrame;
	JLabel lblFrameValue;
	JLabel lblOffset;
	JLabel lblOffsetValue;
	//Page Table
	JPanel tabPT;
	JScrollPane scrollPane;
	private JTable table;
	//RAM
	JPanel tabRam;
	JScrollPane scrollPane_1;
	private JTable table_1;
	//Read/Write Count
	JPanel tabRWCount;
	JScrollPane jScrollPane2;
	private JTable table_2;


	public viewVM() {
		init();
		initValues();
		groupInstructionSetButtons();
	}
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1119, 601);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		lblTimer = new JLabel("Timer:");
		panel.add(lblTimer);

		labelTimer = new JLabel("");
		panel.add(labelTimer);

		panel_1 = new JPanel();
		panel.add(panel_1);

		rdbtn303 = new JRadioButton("30_3");
		panel_1.add(rdbtn303);

		rdbtn200004 = new JRadioButton("20000_4");
		panel_1.add(rdbtn200004);

		rdbtn2000020 = new JRadioButton("20000_20");
		panel_1.add(rdbtn2000020);

		btnVolgendeInstructie = new JButton("volgende instructie");
		panel.add(btnVolgendeInstructie);

		btnAlleInstructies = new JButton("alle instructies");
		panel.add(btnAlleInstructies);

		btnReset = new JButton("reset");
		panel.add(btnReset);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		tabInstructies = new JPanel();
		tabbedPane.addTab("Instructie", null, tabInstructies, null);
		tabInstructies.setLayout(null);

		panel_links = new JPanel();
		panel_links.setBounds(0, 0, 440, 204);
		tabInstructies.add(panel_links);
		panel_links.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("160px"),
				ColumnSpec.decode("238px"),
				ColumnSpec.decode("19px"),
				ColumnSpec.decode("66px"),
				ColumnSpec.decode("-52px"),},
				new RowSpec[] {
						RowSpec.decode("27px"),
						RowSpec.decode("20px"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,}));

		lblVolgendeInstructie = new JLabel("Volgende Instructie");
		panel_links.add(lblVolgendeInstructie, "1, 1, left, center");
		lblVolgendeInstructie.setFont(new Font("Tahoma", Font.BOLD, 16));

		lblId = new JLabel("Id: ");
		panel_links.add(lblId, "1, 2, left, center");

		lblIdValue = new JLabel("");
		panel_links.add(lblIdValue, "2, 2, left, center");

		lblAdres = new JLabel("Adres:");
		panel_links.add(lblAdres, "1, 4, left, center");

		lblAdresValue = new JLabel("");
		panel_links.add(lblAdresValue, "2, 4, left, center");

		lblOperatie = new JLabel("Operatie:");
		panel_links.add(lblOperatie, "1, 6, left, center");

		lblOperatieValue = new JLabel("");
		panel_links.add(lblOperatieValue, "2, 6, 3, 1, left, center");

		panel_rechts = new JPanel();
		panel_rechts.setBounds(443, 0, 556, 204);
		tabInstructies.add(panel_rechts);
		panel_rechts.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("177px"),
				ColumnSpec.decode("368px"),},
				new RowSpec[] {
						RowSpec.decode("27px"),
						RowSpec.decode("20px"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,}));

		lblVorigeInstructie = new JLabel("Vorige Instructie");
		panel_rechts.add(lblVorigeInstructie, "1, 1, fill, top");
		lblVorigeInstructie.setFont(new Font("Tahoma", Font.BOLD, 16));

		lblId_1 = new JLabel("Id: ");
		panel_rechts.add(lblId_1, "1, 2, fill, top");

		lblIdValue_1= new JLabel("");
		panel_rechts.add(lblIdValue_1, "2, 2, left, fill");

		lblAdres_1 = new JLabel("Adres:");
		panel_rechts.add(lblAdres_1, "1, 4, left, fill");

		lblAdresValue_1 = new JLabel("");
		panel_rechts.add(lblAdresValue_1, "2, 4");

		lblOperatie_1 = new JLabel("Operatie:");
		panel_rechts.add(lblOperatie_1, "1, 6");

		lblOperatieValue_1 = new JLabel("");
		panel_rechts.add(lblOperatieValue_1, "2, 6");

		lblOffset = new JLabel("Offset:");
		panel_rechts.add(lblOffset, "1, 12");

		lblOffsetValue = new JLabel("");
		panel_rechts.add(lblOffsetValue, "2, 12");

		lblFrame = new JLabel("Frame Nummer:");
		panel_rechts.add(lblFrame, "1, 8");

		lblFrameValue = new JLabel("");
		panel_rechts.add(lblFrameValue, "2, 8");

		lblFysiekAdres = new JLabel("Fysiek Adres:");
		panel_rechts.add(lblFysiekAdres, "1, 10");

		lblFysiekAdresValue = new JLabel("");
		panel_rechts.add(lblFysiekAdresValue, "2, 10");

		tabPT = new JPanel();
		tabbedPane.addTab("Page Table", null, tabPT, null);
		tabPT.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1082, 452);
		tabPT.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {

				},
				new String [] {
						"Page ", "Present bit", "Modify bit", "Last access time", "Framenummer"
				}
				));

		tabRam = new JPanel();
		tabbedPane.addTab("RAM", null, tabRam, null);
		tabRam.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 1082, 452);
		tabRam.add(scrollPane_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		table_1.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {

				},
				new String [] {
						"Frame", "Id", "Paginanummer"
				}
				));

		tabRWCount = new JPanel();
		tabbedPane.addTab("Read/Write Count", null, tabRWCount, null);
		tabRWCount.setLayout(null);

		jScrollPane2 = new JScrollPane();
		jScrollPane2.setBounds(0, 0, 1082, 452);
		tabRWCount.add(jScrollPane2);

		table_2 = new JTable();
		jScrollPane2.setViewportView(table_2);
		table_2.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {

				},
				new String [] {
						"PID", "Read Count", "Write Count"
				}
				));
	}

	public void addOneInstructionListener(ActionListener EenPerEenListener){
		btnVolgendeInstructie.addActionListener(EenPerEenListener);
	}

	public void addAllInstructionsListener(ActionListener allInstructionsListener){
		btnAlleInstructies.addActionListener(allInstructionsListener);
	}

	public void addCancelListener(ActionListener cancelListener){
		btnReset.addActionListener(cancelListener);
	}

	public void addRadioButton1Listener(ActionListener radioButtonListener){
		rdbtn303.addActionListener(radioButtonListener);
	}

	public void addRadioButton2Listener(ActionListener radioButtonListener){
		rdbtn200004.addActionListener(radioButtonListener);
	}

	public void addRadioButton3Listener(ActionListener radioButtonListener){
		rdbtn2000020.addActionListener(radioButtonListener);
	}

	public void update(Instructie currentInstruction, Instructie previousInstruction) {

		// Update current Instruction
		if(currentInstruction != null) {
			this.lblIdValue.setText(Integer.toString(currentInstruction.getId()));
			this.lblAdresValue.setText(Integer.toString(currentInstruction.getAdres()));
			this.lblOperatieValue.setText(currentInstruction.getOperatie());
		}
		else {
			this.lblIdValue.setText("");
			this.lblAdresValue.setText("");
			this.lblOperatieValue.setText("");
		}
		//Update previous instruction
		if(previousInstruction != null) {
			this.lblIdValue_1.setText(Integer.toString(previousInstruction.getId()));
			this.lblAdresValue_1.setText(Integer.toString(previousInstruction.getAdres()));
			this.lblOperatieValue_1.setText(previousInstruction.getOperatie());
		}
	}

	public void updateTimer(int timer){
		this.labelTimer.setText(Integer.toString(timer));
	}

	public void updatePageTable(List<EntryPT> pageTable){

		int page = 0;
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);

		Object rowData[] = new Object[5];
		for(EntryPT pte: pageTable){

			rowData[0] = page;
			rowData[1] = pte.isPresent();
			rowData[2] = pte.isModified();
			rowData[3] = pte.getLastAccessTime();
			rowData[4] = pte.getFrameNummer();

			tableModel.addRow(rowData);        
			page++;


		}
	}
	public void updateFrames(Pagina[] frames){
		DefaultTableModel tableModel = (DefaultTableModel) table_1.getModel();
		tableModel.setRowCount(0);

		Object rowData[] = new Object[3];
		for(int i=0;i<frames.length;i++){
			if(frames[i]!=null) {
				rowData[0] = i;
				rowData[1] = frames[i].getId();
				rowData[2] = frames[i].getPaginaNummer();

				tableModel.addRow(rowData);                
			}
		}
	}
	public void updateProcesTable(ArrayList<Proces> processList) {
		DefaultTableModel tableModel = (DefaultTableModel) table_2.getModel();
		tableModel.setRowCount(0);
		Object rowData[] = new Object[3];

		for(Proces p: processList){
			rowData[0] = p.getId();
			rowData[1] = p.getReadCount();
			rowData[2] = p.getWriteCount();
			tableModel.addRow(rowData);         
		}
	}

	public void resetPageTable() {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		int rowCount = tableModel.getRowCount();
		for (int i=rowCount-1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
	}
	public void resetFrames() {
		DefaultTableModel tableModel = (DefaultTableModel) table_1.getModel();
		int rowCount = tableModel.getRowCount();
		for (int i=rowCount-1; i >= 0; i--) {
			tableModel.removeRow(i);
		}

	}
	public void resetProcesTable() {
		DefaultTableModel tableModel = (DefaultTableModel) table_2.getModel();
		int rowCount = tableModel.getRowCount();
		for (int i=rowCount-1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
	}    

	public void setFysiekAdres(int fys, int frame, int offset){
		if(fys != -1 && frame != -1) {
			lblFysiekAdresValue.setText(Integer.toString(fys));
			lblFrameValue.setText(Integer.toString(frame));
			lblOffsetValue.setText(Integer.toString(offset));
		}
		else {
			lblFysiekAdresValue.setText("Geen adres");
			lblFrameValue.setText("Geen frame nummer");
			lblOffsetValue.setText("Geen offset");

		}
	}

	public void initValues() {
		this.labelTimer.setText("0");
		this.lblIdValue.setText("");
		this.lblAdresValue.setText("");
		this.lblOperatieValue.setText("");
		this.lblIdValue_1.setText("");
		this.lblAdresValue_1.setText("");
		this.lblOperatieValue_1.setText("");
		resetFrames();
	}

	private void groupInstructionSetButtons( ) {
		ButtonGroup bg = new ButtonGroup( );
		bg.add(rdbtn303);
		bg.add(rdbtn200004);
		bg.add(rdbtn2000020);
		rdbtn303.setSelected(true);
	}

}
