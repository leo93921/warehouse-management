package gestionale;

import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="external_agency", catalog="gestionale")
public class ExternalAgency {
	private Integer ID;
	private String name;
	private String address;
	private String phoneNumber;
	private City city;
	
	public ExternalAgency() {
		super();
	}

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="ID", nullable=false, unique=true)
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	@Column(name="Name", length = 45, nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 45, name="address", nullable=false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="Phone_Number", length = 15, nullable=false)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="City", nullable=false)
	public City getCity() {
		return city;
	}

	public void setCity(City aCity) {
		this.city = aCity;
	}
	
	
}
