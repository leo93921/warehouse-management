package gestionale.UI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gestionale.DAO.OrderDAO;
import gestionale.Order;
import gestionale.OrderManager;
import gestionale.Warehouse;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OrderManagementWindow {

	private JFrame frame;
	private JTable tblOrders;
	private DefaultTableModel model; 
	private Warehouse w;

	/**
	 * Create the application.
	 */
	public OrderManagementWindow(Warehouse warehouse) {
		w = warehouse;
		initialize();
		frame.setVisible(true);
		loadOrders(w);
	}

	private void loadOrders(Warehouse w2) {
		model.setRowCount(0);
		List<Order> orders = OrderManager.listOrderForWarehouse(w);
		
		for (Order o : orders){
			model.addRow(new Object[]{o.getID(), o.getDate(), o.getState()});
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		String[] columnNames = {"ID", "Date", "State"};
		
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 491);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBottom = new JPanel();
		FlowLayout fl_pnlBottom = (FlowLayout) pnlBottom.getLayout();
		fl_pnlBottom.setAlignment(FlowLayout.LEFT);
		frame.getContentPane().add(pnlBottom, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Mark as \"Delivered\"");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Prendo ordine selezionato
				int selectedRow = tblOrders.getSelectedRow();
				if (selectedRow == -1){
					JOptionPane.showMessageDialog(frame, "You have to select an Order.");
					return;
				}
				int ID = Integer.parseInt(model.getValueAt(selectedRow, 0).toString()) ;
				Order order = OrderDAO.getByID(ID);
				//Imposto stato e salvo
				order.setState(Order.OrderState.DELIVERED);
				OrderDAO.update(order);
				loadOrders(w);
				JOptionPane.showMessageDialog(frame, "The order has been marked as \"Delivered\"");
			}
		});
		pnlBottom.add(btnNewButton);
		
		JScrollPane scrollTable = new JScrollPane();
		frame.getContentPane().add(scrollTable, BorderLayout.CENTER);
		
		model = new DefaultTableModel(new Object[][]{}, columnNames){

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
			
		};
		
		tblOrders = new JTable(model);
		scrollTable.setViewportView(tblOrders);
	}

}
