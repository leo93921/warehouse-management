package gestionale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "location", catalog = "gestionale")
public class Location {
	
	private Integer ID;
	private City city;
	private Warehouse warehouse;
	private HeadQuarter headQuarter;
	
	public Location() {
		super();
	}

	public Location(City city, Warehouse warehouse, HeadQuarter headQuarter) {
		super();
		this.city = city;
		this.warehouse = warehouse;
		this.headQuarter = headQuarter;
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

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = City.class)
	@JoinColumn(name="City", referencedColumnName="ID")
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Warehouse.class)
	@JoinColumn(name="Warehouse", referencedColumnName="ID")
	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = HeadQuarter.class)
	@JoinColumn(name="Headquarter", referencedColumnName = "ID")
	public HeadQuarter getHeadQuarter() {
		return headQuarter;
	}

	public void setHeadQuarter(HeadQuarter headQuarter) {
		this.headQuarter = headQuarter;
	}

}
