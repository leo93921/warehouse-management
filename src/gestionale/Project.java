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
@Table(name = "project", catalog = "gestionale")
public class Project {
	
	private int ID;
	private String name;
	private String description;
	private User leadership;
	
	public Project() {
		super();
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	@Column(name = "Name", length = 45, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "Description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
	@JoinColumn(name = "Leadership", referencedColumnName = "ID")
	public User getLeadership() {
		return leadership;
	}

	public void setLeadership(User leadership) {
		this.leadership = leadership;
	}
	
	

}
