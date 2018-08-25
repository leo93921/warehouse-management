package gestionale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="category", catalog="gestionale")
public class Category {
	
	private Integer ID;
	private String name;
	private String description;
	
	
	public Category() {
		super();
	}

	public Category(String aName, String aDescription) {
		super();
		name = aName;
		description = aDescription;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="ID", nullable=false, unique=true)
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	@Column(name="Name", length = 45)
	public String getName() {
		return name;
	}
	public void setName(String aName) {
		name = aName;
	}
	@Column(columnDefinition = "TEXT", name="Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String aDescription) {
		description = aDescription;
	}
	
	

}
