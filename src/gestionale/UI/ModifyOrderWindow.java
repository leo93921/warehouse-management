package gestionale.UI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gestionale.Buy;
import gestionale.ShopperManager;

public class ModifyOrderWindow {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	private JTextField textField;
	private ShopperManager sm;

	/**
	 * Create the application.
	 */
	public ModifyOrderWindow(ShopperManager sm) {
		this.sm = sm;
		initialize();
		frame.setVisible(true);
		loadElements();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String[] columnNames = {"Product", "Quantity"};
		frame = new JFrame();
		frame.setBounds(100, 100, 584, 355);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Modify quantity");
		panel.add(btnNewButton);
		
		JButton btnRemove = new JButton("Remove");
		panel.add(btnRemove);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Buy b = getBuy();
				if (b == null){
					JOptionPane.showMessageDialog(frame, "Select an element from the list");
					return;
				}
				sm.getPurchases().remove(b);
				loadElements();
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txtNum = textField.getText();
				int newQuantity;
				try{
					newQuantity = Integer.parseInt(txtNum);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(frame, "Insert a valid number");
					return;
				}
				Buy b = getBuy();
				if (b == null){
					JOptionPane.showMessageDialog(frame, "Select an element from the list");
					return;
				}
				b.setQuantity(newQuantity);
				textField.setText("");
				loadElements();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		model = new DefaultTableModel(new Object[][]{}, columnNames);
		table = new JTable(model);
		scrollPane.setViewportView(table);
	}
	
	private Buy getBuy(){
		int selectedRow = table.getSelectedRow();
		if(selectedRow == -1){
			return null;
		}
		List<Buy> purchases = sm.getPurchases();
		return purchases.get(selectedRow);
	}
	
	private void loadElements() {

		model.setRowCount(0);
		for(Buy b : sm.getPurchases()){
			model.addRow(new Object[]{b.getSupply().getProduct().getName(), b.getQuantity()});
		}
		
	}

}
