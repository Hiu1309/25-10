package application;

import java.sql.Date;

public class customersData {
	private Integer id;
	private Integer customerID;
	private Long total;
	private Date date;
	private String emUsername;
	
	public customersData(Integer id, Integer customerID, Long total,
			Date date, String emUsername) {
		this.id=id;
		this.customerID=customerID;
		this.total=total;
		this.date=date;
		this.emUsername=emUsername;
	}
	
	public Integer getID() {
		return id;
	}
	public Integer getCustomerID() {
		return customerID;
	}
	public Long getTotal() {
		return total;
	}
	public Date getDate() {
		return date;
	}
	public String getEmUsername() {
		return emUsername;
	}
	
}
