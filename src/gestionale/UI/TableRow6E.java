package gestionale.UI;

import gestionale.Supply;

//Table Row from 6 Elements, used for the JTable in EmployeeWindow
public class TableRow6E{
	private Supply supply;
	
	public TableRow6E() {
		super();
	}

	public Supply getSupply() {
		return supply;
	}

	public void setSupply(Supply s) {
		supply = s;
	}


	public Object[] toObj() {
		return new Object[]{supply.getID(), supply.getProduct().getName() , supply.getProduct().getDescription(), supply.getOrdinableUnits(), supply.getPrice(), supply.getAvailability() };
	}
	
	
	
}
