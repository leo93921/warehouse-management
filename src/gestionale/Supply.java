package gestionale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "supply", catalog = "gestionale")
public class Supply {
	
	public static enum State{AVAILABLE, UNAVAILABLE};
	
	private Integer ID;
	private BigDecimal price;
	private Integer ordinableUnits;
	private Integer availability;
	private Warehouse warehouse;
	private Product product;
	private State state;
	
	public Supply() {
		super();
	}

	public Supply(Warehouse warehouse, Product product, BigDecimal price, Integer ordinableUnits, Integer availability) {
		super();
		this.price = price;
		this.ordinableUnits = ordinableUnits;
		this.availability = availability;
		this.warehouse = warehouse;
		this.product = product;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	@Column(name = "Price", nullable = false)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "Ordinable_Units", nullable = false)
	public Integer getOrdinableUnits() {
		return ordinableUnits;
	}

	public void setOrdinableUnits(Integer ordinableUnits) {
		this.ordinableUnits = ordinableUnits;
	}

	@Column(name = "Availability", nullable = false)
	public int getAvailability() {
		return availability;
	}

	public void setAvailability(Integer availability) {
		this.availability = availability;
		if (this.availability == 0)
			this.state = State.UNAVAILABLE;
		else
			this.state = State.AVAILABLE;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Warehouse", nullable = false)
	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Product", nullable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public void addSupply(int valueToAdd){
		this.setAvailability(availability + valueToAdd);
	}
	
	public void decrementQuantity(int quantity){
		this.availability = this.availability - quantity;
	}
	
	@Transient
	public State getState(){
		return this.state;
	}
}
