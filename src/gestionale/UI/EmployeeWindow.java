package gestionale.UI;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import gestionale.DAO.WarehouseDAO;
import gestionale.BuyActionListener;
import gestionale.ShopperManager;
import gestionale.Supply;
import gestionale.User;
import gestionale.Warehouse;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmployeeWindow {

	private JFrame frame;
	private JTable table;
	private JTextField textField;
	private ComboItem selectedItem = null;
	
	private User employee;
	private ShopperManager sm = new ShopperManager();
	private Warehouse warehouse;
	
	/**
	 * Create the application.
	 */
	public EmployeeWindow(User user) {
		
		employee = user;
		sm.setEmployee(employee);
		initialize();
		
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 661, 447);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String[] columnNames = {"ID", "Name", "Description", "Ordinable", "Price", "Availability"};
		DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames){

			private static final long serialVersionUID = -2690946965710253405L;

			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
			
		};
		
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel pnlMagazzini = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnlMagazzini.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		frame.getContentPane().add(pnlMagazzini, BorderLayout.NORTH);
		
		JLabel lblWarehouse = new JLabel("Warehouse");
		pnlMagazzini.add(lblWarehouse);
		
		JComboBox<ComboItem> comboBox = new JComboBox<ComboItem>();
		pnlMagazzini.add(comboBox);

		table = new JTable(model);
		
		JScrollPane js=new JScrollPane(table);
		frame.getContentPane().add(js, BorderLayout.CENTER);
		js.setVisible(true);
		
		table.setColumnSelectionAllowed(false);
		table.setCellSelectionEnabled(false);
		table.setRowSelectionAllowed(true);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lbInstructions = new JLabel("Select a product, insert a quantity and press the \"Add\" button. When you finish, press the \"Confirm\" button.");
		GridBagConstraints gbc_lbInstructions = new GridBagConstraints();
		gbc_lbInstructions.insets = new Insets(5, 0, 0, 0);
		gbc_lbInstructions.gridx = 0;
		gbc_lbInstructions.gridy = 0;
		panel.add(lbInstructions, gbc_lbInstructions);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel.add(panel_1, gbc_panel_1);
		BuyActionListener actionListener = new BuyActionListener();
		actionListener.setFrame(frame);
		actionListener.setSm(sm);
		actionListener.setTable(table);
		actionListener.setEmployeeWindow(this);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_2.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel_1.add(panel_2);
		
		textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);
		actionListener.setTextField(textField);
		
		JButton btnAdd = new JButton("Add");
		panel_2.add(btnAdd);
		
		btnAdd.setActionCommand("add");
		btnAdd.addActionListener(actionListener);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_1.add(panel_3);
		
		JButton btnModifyOrder = new JButton("Modify Order");
		btnModifyOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModifyOrderWindow mow = new ModifyOrderWindow(sm);
			}
		});
		panel_3.add(btnModifyOrder);
		
		JButton btnConfirm = new JButton("Confirm");
		panel_3.add(btnConfirm);
		btnConfirm.setActionCommand("confirm");
		btnConfirm.addActionListener(actionListener);
		
	
		
		//Listener per il cambio della selezione del combobox con i magazzini
		comboBox.addItemListener (new ItemListener () {
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				//Ho la selezione di un magazzino
				if (e.getStateChange() == ItemEvent.SELECTED){
					//Controllo che non abbia premuto lo stesso item
					if (selectedItem != (ComboItem)e.getItem()){
						
						//Chiedo conferma del cambio perchè perderei tutti gli acquisti effettuati
						if (sm.shoppingStarted() == true){
							int scelta = JOptionPane.showConfirmDialog(frame, "Changing the Warehouse you will loose all the purchases earlier made.", "Are you sure", JOptionPane.YES_NO_OPTION);
							if (scelta == 1){
								comboBox.setSelectedItem(selectedItem);
								return;
							}
							sm.clearPurchases();
						}
						
						selectedItem = (ComboItem)e.getItem();
						
						warehouse = WarehouseDAO.findByID(selectedItem.getID());
						
						loadSupply();
					}
				}
			}
		});
		
		/*Per il caricamento: 
		 * 1 - creo la Location con il CAP dell'utente
		 * 2 - Prendo il magazzino che è stato creato
		 * 3 - Carico le scorte del magazzino
		*/
		//Visualizzo tutti i magazzini disponibili
		showAllAvailableWarehouse(comboBox);
		
	}
	
	public void loadSupply(){
		//Tolgo le supply che c'erano prima nella tabella
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setRowCount(0);
		
		//Inserisco le nuove supply
		List<Supply> list = WarehouseDAO.listSupplyForWarehouse(warehouse);		
		for(Supply s : list){
			TableRow6E r = new TableRow6E();
			r.setSupply(s);
			model.addRow(r.toObj());
		}
		
	}

	private void showAllAvailableWarehouse(JComboBox<ComboItem> comboBox) {

		List<Warehouse> list = WarehouseDAO.listWarehouses();
		Warehouse nearWare = WarehouseDAO.getNearestWarehouse(employee);
		
		for (Warehouse w : list){
			comboBox.addItem(new ComboItem(w.getID(), w.getName()));
			System.out.println(w.getName() + " -> " + w.getID());
		}
		if (nearWare != null){
			int n = comboBox.getItemCount();
			ComboItem tmp = null;
			for(int i = 0; i < n; i++){
				tmp = (ComboItem) comboBox.getItemAt(i);
				if( tmp.getID() == nearWare.getID()){
					comboBox.setSelectedIndex(i);
					selectedItem = (ComboItem)comboBox.getItemAt(i);
					return;
				}
			}
			comboBox.setSelectedIndex(0);
		}
	}

}
