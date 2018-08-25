package gestionale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city", catalog = "gestionale")
public class City {
	
	private Integer ID;
	private String name;
	private String CAP;
	
	public City() {
		super();
	}

	public City(String name, String cAP) {
		super();
		this.name = name;
		CAP = cAP;
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

	@Column(name = "Name", length = 45, unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CAP", length = 5, unique = true)
	public String getCAP() {
		return CAP;
	}

	public void setCAP(String cAP) {
		CAP = cAP;
	}
	
	

}
