package gestionale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Table;


@Entity
@Table(name = "user", catalog = "gestionale")
public class User {
	
	public static enum Type {EMPLOYEE, WAREHOUSEMAN, PROJECTLEADER;};
	
	private Integer ID;
	private String username;
	private String password;
	private Type type = null;
	private String name;
	private String surname;
	private City city;
	
	public User() {
		super();
	}

	public User(String aUsername, String aPassword) {
		super();
		username = aUsername;
		password = aPassword;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="ID", nullable = false, unique = true)
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	@Column(name = "Username", length = 45, nullable = false, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String aUsername) {
		username = aUsername;
	}
	
	@Column(name = "Password", length = 45, nullable = false)
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String aPassword) {
		password = aPassword;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "Type", nullable = false)
	public Type getType() {
		return type;
	}

	public void setType(Type aType) {
		this.type = aType;
	}

	@Column(name = "Name", length = 45, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Surname", length = 45, nullable = false)
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = City.class)
	@JoinColumn(name = "City", referencedColumnName="ID")
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	

}
