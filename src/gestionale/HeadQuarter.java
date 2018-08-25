package gestionale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="headquarter", catalog="gestionale")
public class HeadQuarter {
	
	private Integer ID;
	private String Name;
	
	public HeadQuarter() {
		super();
	}

	public HeadQuarter(String name) {
		super();
		Name = name;
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

	@Column(name = "Name", length = 45)
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
	
}
