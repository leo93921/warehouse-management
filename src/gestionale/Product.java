/**
 * 
 */
package gestionale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Leonardo
 *
 */

@Entity
@Table(name="product", catalog="gestionale")
public class Product {
	
	private Integer ID;
	private String name;
	private String description;
	private Category category;
	private ExternalAgency producer;
	private ExternalAgency suppplier;
	
	
	
	public Product() {
		super();
	}

	/**
	 * Constructor for a product
	 * @param aName Name of the product
	 * @param aDescription Description of the product
	 * @param aCategory Category of the product
	 */
	public Product(String aName, String aDescription, Category aCategory){
		this.name = aName;
		this.description = aDescription;
		this.category = aCategory;
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

	/**
	 * Method used to get the name
	 * @return Name of the product
	 */
	@Column(name="Name", length = 45)
	public String getName() {
		return name;
	}
	
	/**
	 * Method used to set the product's name
	 * @param name Name of product
	 */
	public void setName(String aName) {
		name = aName;
	}

	/**
	 * Method used to get the product's description
	 * @return The description of the product
	 */
	@Column(name="Description", columnDefinition="TEXT")
	public String getDescription() {
		return description;
	}
	
	/**
	 * Method used to set the product's description
	 * @param description The product's description
	 */
	
	public void setDescription(String aDescription) {
		description = aDescription;
	}
	
	
	/**
	 * Used to get the Category of the product
	 * @return	The category of the product
	 */
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Category.class)
	@JoinColumn(name="Category", referencedColumnName="ID")
	public Category getCategory() {
		return category;
	}
	
	/**
	 * Method used to set the product's category
	 * @param category The product's Category
	 */
	public void setCategory(Category aCategory) {
		category = aCategory;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = ExternalAgency.class)
	@JoinColumn(name="Producer", referencedColumnName="ID")
	public ExternalAgency getProducer() {
		return producer;
	}

	public void setProducer(ExternalAgency producer) {
		this.producer = producer;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = ExternalAgency.class)
	@JoinColumn(name="Supplier", referencedColumnName="ID")
	public ExternalAgency getSuppplier() {
		return suppplier;
	}

	public void setSuppplier(ExternalAgency suppplier) {
		this.suppplier = suppplier;
	}
	
	

}
