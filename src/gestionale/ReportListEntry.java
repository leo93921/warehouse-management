package gestionale;

import java.math.BigDecimal;

public class ReportListEntry {
	
	private int ID;
	private String name;
	private BigDecimal total;
	
	public ReportListEntry(int iD, String name, BigDecimal total) {
		super();
		ID = iD;
		this.name = name;
		this.total = total;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getTotal() {
		return total;
	}

}
