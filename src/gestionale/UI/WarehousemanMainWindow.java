package gestionale.UI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import gestionale.DAO.SupplyDAO;
import gestionale.DAO.WarehouseDAO;
import gestionale.Supply;
import gestionale.User;
import gestionale.Warehouse;

import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class WarehousemanMainWindow {

	private JFrame frame;
	private JTextField txtAddSupply;
	private JTable table;
	private DefaultTableModel model;
	private Warehouse warehouse;
	private User user;

	/**
	 * Create the application.
	 */
	public WarehousemanMainWindow(User warehouseman) {
		this.user = warehouseman;
		initialize();
		getWarehouse();
		loadSupply();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String[] columnNames = {"ID", "Name", "Description", "Ordinable", "Price", "Availability"};
		
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 551);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTop = new JPanel();
		FlowLayout fl_pnlTop = (FlowLayout) pnlTop.getLayout();
		fl_pnlTop.setAlignment(FlowLayout.RIGHT);
		frame.getContentPane().add(pnlTop, BorderLayout.NORTH);
		
		JButton btnOrders = new JButton("Order Management");
		btnOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderManagementWindow window = new OrderManagementWindow(warehouse);				
			}
		});
		pnlTop.add(btnOrders);
		
		JPanel pnlBottom = new JPanel();
		frame.getContentPane().add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel pnlRefillGroup = new JPanel();
		FlowLayout fl_pnlRefillGroup = (FlowLayout) pnlRefillGroup.getLayout();
		fl_pnlRefillGroup.setAlignment(FlowLayout.LEFT);
		pnlBottom.add(pnlRefillGroup);
		
		JLabel lblQuantity = new JLabel("Quantity");
		pnlRefillGroup.add(lblQuantity);
		
		txtAddSupply = new JTextField();
		pnlRefillGroup.add(txtAddSupply);
		txtAddSupply.setColumns(10);
		
		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1){
					JOptionPane.showMessageDialog(frame, "You have to select a product.");
					return;
				}
				String numStr = txtAddSupply.getText();
				int quantity;
				try{
					quantity = Integer.parseInt(numStr);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(frame, "You have to inser a valid number.");
					return;
				}
				if (quantity < 0){
					JOptionPane.showMessageDialog(frame, "You have to inser a valid number.");
					return;
				}
				//Prendo l'ID
				int ID = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
				//Prendo la supply
				Supply s = SupplyDAO.findByID(ID);
				s.addSupply(quantity);
				//Carico in DB
				boolean res = SupplyDAO.updateSupply(s);
				if (res == false){
					JOptionPane.showMessageDialog(frame, "There was an error. Try again or contact the support.");
					return;
				}
				//Ricarico la tabella e cancello la quantità
				loadSupply();
				txtAddSupply.setText("");
				//Visualizzo avviso
				JOptionPane.showMessageDialog(frame, "The new quantity was saved.");
				
			}
		});
		pnlRefillGroup.add(btnReload);
		
		JPanel pnlExitGroup = new JPanel();
		FlowLayout fl_pnlExitGroup = (FlowLayout) pnlExitGroup.getLayout();
		fl_pnlExitGroup.setAlignment(FlowLayout.RIGHT);
		pnlBottom.add(pnlExitGroup);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		pnlExitGroup.add(btnExit);
		
		JScrollPane scrlTable = new JScrollPane();
		frame.getContentPane().add(scrlTable, BorderLayout.CENTER);
		
		
		
		
		model = new DefaultTableModel(new Object[][]{}, columnNames){

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
			
		};
		
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrlTable.setViewportView(table);
		frame.setVisible(true);
	}
	
	protected void loadSupply(){
		
		model.setRowCount(0);
		
		List<Supply> suppplies = WarehouseDAO.listSupplyForWarehouse(warehouse);
		
		for (Supply s : suppplies){
			TableRow6E r = new TableRow6E();
			r.setSupply(s);
			model.addRow(r.toObj());
		}
	}
	
	private void getWarehouse(){
		warehouse = WarehouseDAO.findByWarehouseman(user);
	}

}
