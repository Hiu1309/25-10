package application;

import java.math.BigInteger;
import java.sql.Date;

public class productData {

	private Integer id;
	private String productId;
	private String productName;
	private String type;
	private String brand;
	private Integer stock;
	private Long price;
	private String status;
	private String image;
	private Date date;
	private Integer quantity;
	
	public productData(Integer id, String productId, String productName, String type, String brand, Integer stock, Long price, String status, String image, Date date) {
		this.id = id;
		this.productId = productId;
		this.productName =productName;
		this.type = type;
		this.brand = brand;
		this.stock = stock;
		this.price = price;
		this.status = status;
		this.image = image;
		this.date = date;
		
	}
	
	public productData(Integer id, String productId, String productName, String type, Integer quantity, String brand,Long price, String image, Date date) {
		this.id= id;
		this.productId = productId;
		this.productName = productName;
		this.type = type;
		this.brand = brand;
		this.price = price;
		this.image = image;
		this.date = date;
		this.quantity = quantity;
	}
	
	public Integer getId() {
		return id;
	}
	public String getProductId() {
		return productId;
	}
	public String getProductName() {
		return productName;
	}
	public String getType() {
		return type;
	}
	public String getBrand() {
		return brand;
	}
	public Integer getStock() {
		return stock;
	}
	public Long getPrice() {
		return price;
	}
	public String getStatus() {
		return status;
	}
	public String getImage() {
		return image;
	}
	public Date getDate() {
		return date;
	}
	public Integer getQuantity() {
		return quantity;
	}
	
}
