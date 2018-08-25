package gestionale.UI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JComboBox;
import gestionale.ReportListEntry;
import gestionale.ReportManager;
import gestionale.User;
import gestionale.DAO.UserDAO;
import gestionale.helper.SystemHelper;
import gestionale.Report;
import gestionale.Report.GroupingType;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class ProjectLeaderWindow {
	
	final int EMPLOYEE_BASE = 0;
	final int PROJECT_BASE = 1;
	
	private JFrame frame;
	private JTable tblReport;
	private DefaultTableModel model;
	private JComboBox<String> comboBox;
	private JButton btnPrint;
	
	private User user;
	List<Report> reports;
	
	/**
	 * Create the application.
	 */
	public ProjectLeaderWindow(User projectLeader) {
		user = projectLeader;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String[] columnNames = {"ID", "Name", "Total"};
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Group by");
		panel_1.add(lblNewLabel);
		
		comboBox = new JComboBox<String>();
		comboBox.addItem("Employee");
		comboBox.addItem("Project");
		
		comboBox.addItemListener(new ItemListener(){
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					String selected = (String)e.getItem();
					if (selected == "Employee"){
						loadByEmployee();
						btnPrint.setActionCommand("employee");
					}
					else{
						loadByProject();
						btnPrint.setActionCommand("project");
					}
				}
				
			}
			
		});
		
		panel_1.add(comboBox);
		
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		model = new DefaultTableModel(new Object[][]{}, columnNames){

			private static final long serialVersionUID = 5725390637527892686L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		};
		tblReport = new JTable(model);
		scrollPane.setViewportView(tblReport);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Controllo che abbia selezionato almeno un elemento
				int selectedRow = tblReport.getSelectedRow();
				if(selectedRow == -1){
					JOptionPane.showMessageDialog(frame, "Select an element of the table.");
					return;
				}
				
				if (e.getActionCommand() == "employee"){
					printReport(selectedRow, GroupingType.EMPLOYEE_BASE);
				}
				else{
					printReport(selectedRow, GroupingType.PROJECT_BASE);
				}
			}
		});
		panel.add(btnPrint);
		
		comboBox.setSelectedIndex(1);
	}
	
	private void loadByProject() {
		clearTable();
		reports = ReportManager.list(user, GroupingType.PROJECT_BASE);
		for (Report r : reports){
			ReportListEntry rle = r.getListEntry();
			if (rle != null){
				model.addRow(new Object[]{rle.getID(), rle.getName(), rle.getTotal() + " €"});
			}
		}
		
	}

	private void loadByEmployee() {
		clearTable();
		reports = ReportManager.list(user, GroupingType.EMPLOYEE_BASE);
		if (reports.isEmpty())
			return;
		for (Report r : reports){
			ReportListEntry rle = r.getListEntry();
			if (rle != null){
				model.addRow(new Object[]{rle.getID(), rle.getName(), rle.getTotal() + " €"});
			}
		}
	}
	
	private void clearTable() {
		model.setRowCount(0);
	}
	
	/**
	 * 
	 * @param mode Mode used to print, read id_base
	 * @param id_base if mode is EMPLOYEE_BASE, then id_base is the ID of the Employee selected, if it's PROJECT_BASE, then the id_base is the ID of the Project selected
	 */
	private void printReport(int selectedRow, GroupingType gr){
		Report r = reports.get(selectedRow);
		boolean res = ReportManager.printReport(r);
		if (!res)
			JOptionPane.showMessageDialog(frame, "Something went wrong");
		else
			JOptionPane.showMessageDialog(frame, "The report has been printed");
	}

}
