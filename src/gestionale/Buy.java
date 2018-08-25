package gestionale;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "buy", catalog = "gestionale")
public class Buy {
	
	private int ID;
	private Order order;
	private Supply supply;
	private int quantity;
	private BigDecimal cost;
	
	public Buy() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = Order.class)
	@JoinColumn(name = "OrderID", referencedColumnName = "ID", nullable = false)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = Supply.class)
	@JoinColumn(name = "Supply", referencedColumnName = "ID", nullable = false)
	public Supply getSupply() {
		return supply;
	}

	public void setSupply(Supply supply) {
		this.supply = supply;
	}

	@Column(name = "Quantity", nullable = false)
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(name = "Cost", nullable = false)
	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal c) {
		cost = c;
	}
	
	public void setCost() {
		BigDecimal quantity = new BigDecimal(getQuantity());
		BigDecimal price = getSupply().getPrice();
		setCost(price.multiply(quantity));
	}
	
	
}
