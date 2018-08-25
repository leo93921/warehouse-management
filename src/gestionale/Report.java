package gestionale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import gestionale.DAO.BuyDAO;
import gestionale.DAO.OrderDAO;

/**
 * Modella un report. E' un insieme di ordini in modo da poter dare la massima flessibilità
 * per quanto riguarda la gestione degli acquisti, utenti che li hanno effettuati e i progetti
 * scelti per appoggiarvi le spese.
 * @author Leonardo
 *
 */
public class Report {
	public static enum GroupingType {EMPLOYEE_BASE, PROJECT_BASE};
	private List<Order> orders;
	private GroupingType groupBy;

	private Report(List<Order> ord, GroupingType gr){
		this.orders = ord;
		this.groupBy = gr;
	}
	/**
	 * Crea un report basato sugli impiegati
	 * @param employee
	 */
	public Report(User employee){
		List<Order> orderList = OrderDAO.listByEmployee(employee);
		groupBy = GroupingType.EMPLOYEE_BASE;
		this.orders = orderList;
	}
	/**
	 * Crea un report basato sui progetti
	 * @param project
	 */
	public Report(Project project){
		orders = OrderDAO.listByProject(project);
		groupBy = GroupingType.PROJECT_BASE;
	}
	/**
	 * Restituisce una lista di report, ognuno dei quali ha i soli ordini degli utenti che hanno appoggiato le spese ai progetti del Project Leader
	 * @param projectLeader Project Leader connesso al sistema
	 * @return Report con i soli ordini degli utenti che hanno appoggiato le spese ai progetti del Project Leader
	 */
	public static List<Report> getByGroupingEmployees(User projectLeader){
		List<Order> orderList = OrderDAO.listGroupingByEmployee(projectLeader);
		List<Report> reports = new ArrayList<Report>();
		List<Order> tmp = new ArrayList<Order>();
		if (orderList.isEmpty())
			return reports;
		User prevUser = orderList.get(0).getEmployee();
		for (Order o : orderList){
			if (o.getEmployee() == prevUser){
				//Ho un ordine che appartiene all'utente precendente
				tmp.add(o);
			}else{
				Report r = new Report(tmp, GroupingType.EMPLOYEE_BASE);
				reports.add(r);
				tmp = new ArrayList<Order>();
				prevUser = o.getEmployee();
				tmp.add(o);
			}
		}
		Report r = new Report(tmp, GroupingType.EMPLOYEE_BASE);
		reports.add(r);
		//tmp.clear();
		
		return reports;
	}
	
	/**
	 * 
	 * @return Torna una stringa già formattata con tutti gli acquisti.
	 */
	public String getInfo(){
		String testo = "";
		for (Order order : orders){
			//Prendo un ordine alla volta e vedo gli acquisti per poi stamparli
			List<Buy> purchases = BuyDAO.listForOrder(order);
			testo = testo + System.lineSeparator() + "Project: " + order.getProject().getName() + System.lineSeparator();
			testo = testo + "Employee: " + order.getEmployee().getName() + " " + order.getEmployee().getSurname() + System.lineSeparator();
			for(Buy b : purchases){
				testo = testo + b.getQuantity() + " " + b.getSupply().getProduct().getName() + ": " + b.getCost() + "€" + System.lineSeparator();
			}
		}
		return testo;

	}
	
	/**
	 * 
	 * @return Una list entry con ID, nome e totale. Utile per le tabelle che visualizzano sinteticamente i totali dei costi
	 */
	public ReportListEntry getListEntry(){
		ReportListEntry rle;
		String name;
		int ID;
		BigDecimal total = getTotal();
		if(orders.isEmpty())
			return null;
		Order o = orders.get(0);
		if (groupBy == GroupingType.EMPLOYEE_BASE){
			name = o.getEmployee().getName() + " " +o.getEmployee().getSurname();
			ID = o.getEmployee().getID();
		}else{
			name = o.getProject().getName();
			ID = o.getProject().getID();
		}
		rle = new ReportListEntry(ID, name, total);
		return rle;
		
	}
	
	private BigDecimal getTotal(){
		BigDecimal total = new BigDecimal("0");
		for (Order order : orders){
			//Prendo un ordine alla volta e vedo gli acquisti per poi stamparli
			List<Buy> purchases = BuyDAO.listForOrder(order);
			for(Buy b : purchases){
				total = total.add(b.getCost());
			}
		}
		return total;
	}
	public boolean isEmpty() {
		return orders.isEmpty();
	}
	

}
