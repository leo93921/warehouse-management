package gestionale.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gestionale.DAO.ProjectDAO;
import gestionale.helper.SystemHelper;
import gestionale.Order;
import gestionale.OrderManager;
import gestionale.Project;
import gestionale.ShopperManager;
import gestionale.User;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class ProjectSelectionWindow{

	private JFrame frame;
	private JTable table;
	DefaultTableModel tableModel;
	private ShopperManager sm;
	private EmployeeWindow empWindow;
	
	public void setEmployeeWindow(EmployeeWindow ew){
		empWindow = ew;
	}
	
	public void setShopManager(ShopperManager p){
		sm = p;
	}
	
	/**
	 * Create the application.
	 */
	public ProjectSelectionWindow() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		//BorderLayout borderLayout = (BorderLayout) frame.getContentPane().getLayout();
		frame.setBounds(100, 100, 551, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		
		String[] columnNames = {"ID", "Name", "Description", "Project Leader"};
		tableModel = new DefaultTableModel(new Object[][]{}, columnNames){

			private static final long serialVersionUID = 8839950418265349326L;

			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
			
		};
		
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnConfirm = new JButton("Confirm");
		panel.add(btnConfirm);
		btnConfirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1){
					JOptionPane.showMessageDialog(frame, "You have to select a Project or Cancel.");
					return;
				}
				int ProjectID = (int)table.getValueAt(selectedRow, 0);
				
				Project pr = ProjectDAO.findByID(ProjectID);
				Order createdOrder;
				createdOrder = OrderManager.createOrder(sm, pr);
				if (createdOrder == null)
					JOptionPane.showMessageDialog(frame, "There was an error. Contact the support for information.");
				//Stampo ordine
				String filename = SystemHelper.getPathByUser();
				String toWrite = "Print this file, you'll need it when the warehouseman will give you the products" +
						System.lineSeparator() + createdOrder.getInfo();
				boolean res = SystemHelper.writeFile(filename, toWrite);
				if(!res)
					JOptionPane.showMessageDialog(frame, "There was an error printing the report. Contact the support for information.");
				else
					JOptionPane.showMessageDialog(frame, "The order has been saved. Print the file created before and give it to the warehouseman when he will give you the products you ordered.\nIf you didn't selected any path to save the file, you'll find the report on your desktop.");
				sm.clearPurchases();
				empWindow.loadSupply();
				hideWindow();
			}
			
		});
		
		
		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JLabel lblSelectTheProject = new JLabel("Select the project from this list. The cost will be on the project that you choose.");
		panel_1.add(lblSelectTheProject);
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				
			}
			
		});
		
		loadProjects();
	}

	protected void hideWindow() {
		frame.dispose();
		
	}

	private void loadProjects() {

		List<Project> projects = ProjectDAO.listProjects();
		String userName;
		
		for(Project p : projects){
			User user = p.getLeadership();
			userName = user.getName() + " " + user.getSurname();
			tableModel.addRow(new Object[]{p.getID(), p.getName(), p.getDescription(), userName});
		}
	}

}
