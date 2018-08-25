package gestionale;

import java.util.ArrayList;
import java.util.List;

import gestionale.Supply.State;

public class ShopperManager {
	
	private List<Buy> purchases = new ArrayList<Buy>(); 
	private String message;
	private User employee;
	
	public List<Buy> getPurchases() {
		return purchases;
	}
	
	/**
	 * 
	 * @return torna true se si è gia cominciato ad effettuare acquisti, false altrimenti
	 */
	public Boolean shoppingStarted(){
		return !purchases.isEmpty();
	}
	
	/**
	 * Used to clear all the purchases made in earlier moments
	 */
	public void clearPurchases(){
		purchases.clear();
	}
	
	public Boolean addBuy(Buy b){
		
		if (b == null){
			message = "Pass a valid purchase";
			return false;
		}
		
		Boolean result;
		result = checkBuy(b);
		
		if (result == false)
			return false;
		
		purchases.add(b);
		return true;
	}

	private Boolean checkBuy(Buy b) {
		Supply supply = b.getSupply();
		
		if(b.getSupply().getState() == State.UNAVAILABLE){
			message = "The supply is unavailable.";
			return false;
		}
		
		if (b.getQuantity() > supply.getAvailability()){
			message = "No enough supplies for this purchase.";
			return false;
		}
		
		if ( b.getQuantity() > supply.getOrdinableUnits()){
			message = "You can order only " + supply.getOrdinableUnits() + " units for this product";
			return false;
		}
		
		return true;
		
	}

	public String getMessage() {
		return message;
	}

	public User getEmployee() {
		return employee;
	}
	
	public void setEmployee(User e){
		employee = e;
	}

}
