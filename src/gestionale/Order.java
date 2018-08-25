package gestionale;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import gestionale.DAO.BuyDAO;

@Entity
@Table(name = "order", catalog = "gestionale")
public class Order {
	
	public static enum OrderState{NEW, DELIVERED};
	
	private int ID;
	private Date date;
	private Project project;
	private OrderState state;
	private User employee;
	
	public Order() {
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
	
	@Column(name = "Date")
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Project.class)
	@JoinColumn(name = "Project", referencedColumnName = "ID")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "State", nullable = false)
	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
	@JoinColumn(name = "Employee", referencedColumnName = "ID", nullable = false)
	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}
	
	@Transient
	public String getInfo(){
		String str = "";
		List<Buy> purchases = BuyDAO.listForOrder(this);
		for (Buy b : purchases){
			str = str + b.getQuantity() + " x " + b.getSupply().getProduct().getName() + " : " + b.getCost() + " €" + System.lineSeparator();
		}
		return str;
	}
}
