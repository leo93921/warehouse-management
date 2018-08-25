package gestionale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import gestionale.Supply.State;
import gestionale.DAO.SupplyDAO;
import gestionale.UI.EmployeeWindow;
import gestionale.UI.ProjectSelectionWindow;

public class BuyActionListener implements ActionListener {
	
	private JFrame frame;
	private JTable table;
	private JTextField textField;
	private ShopperManager sm;
	private EmployeeWindow empWindow;
	
	int IDColumn = 0;
	Boolean riuscita = false;
	
	public void setEmployeeWindow(EmployeeWindow ew){
		empWindow = ew;
	}
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public void setSm(ShopperManager sm) {
		this.sm = sm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd == "add")
			addPressed();
		else{
			if(sm.getPurchases().isEmpty()){
				JOptionPane.showMessageDialog(frame, "You have to select at least an Item and buy it.");
				return;
			}
			ProjectSelectionWindow win = new ProjectSelectionWindow();
			win.setShopManager(sm);
			win.setEmployeeWindow(empWindow);
		}
			
	}
	
	private void addPressed(){
		int selectedRow;
		
		selectedRow = table.getSelectedRow();
		//Controllo che abbia selezionato almeno un elemento
		if (selectedRow == -1){
			JOptionPane.showMessageDialog(frame, "You have to select an element");
			return;
		}
		//Controllo che abbia inserito una quantità.
		String txtNum = textField.getText();
		if(txtNum == ""){
			JOptionPane.showMessageDialog(frame, "You have to insert a quantity");
			return;
		}
		int numAcquisti;
		try{
			numAcquisti = Integer.parseInt(txtNum);
		}catch(Exception e){
			JOptionPane.showMessageDialog(frame, "Insert a valid number");
			return;
		}
		
		
		int selectedID = (int)table.getValueAt(selectedRow, IDColumn);
		
		//Prendo la scorta selezionata
		Supply scorta = SupplyDAO.findByID(selectedID);
		
		//Creo l'acquisto
		Buy b = new Buy();
		b.setSupply(scorta);
		b.setQuantity(numAcquisti);
		b.setCost();
		
		riuscita = sm.addBuy(b);
		
		if (riuscita == false){
			JOptionPane.showMessageDialog(frame, "There was an error: " + sm.getMessage());
			return;
		}
		
		textField.setText("");
		
	}

}
