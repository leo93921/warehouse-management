package gestionale;

import java.util.List;

import gestionale.DAO.OrderDAO;

public class OrderManager {
	
	public static Order createOrder(ShopperManager sm, Project project){
		
		return OrderDAO.saveOrderAndPurchases(sm, project);
		
	}
	
	public static List<Order> listOrderForWarehouse(Warehouse w){
		return OrderDAO.listByWarehouse(w);
	}
	
}
