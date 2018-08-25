package gestionale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Table;

@Entity
@Table(name = "warehouse", catalog = "gestionale")
public class Warehouse {
	
	private Integer ID;
	private String name;
	private User warehouseman;
	
	public Warehouse() {
		super();
	}

	public Warehouse(String name) {
		super();
		this.name = name;
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

	@Column(name = "Name", length = 45, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
	@JoinColumn(referencedColumnName = "ID", name = "Warehouseman", nullable = false)
	public User getWarehouseman() {
		return warehouseman;
	}

	public void setWarehouseman(User warehouseman) {
		this.warehouseman = warehouseman;
	}

}
