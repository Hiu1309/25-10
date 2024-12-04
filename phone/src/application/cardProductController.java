package application;

import java.net.URL;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class cardProductController implements Initializable {

	@FXML
	private AnchorPane card_form;

	@FXML
	private Button prod_addBtn;

	@FXML
	private ImageView prod_imageView;

	@FXML
	private Label prod_name;

	@FXML
	private Label prod_price;

	@FXML
	private Spinner<Integer> prod_spinner;

	private SpinnerValueFactory<Integer> spin;
	private String prodID;
	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	private Alert alert;
	private String type;
	private String prod_image;
	private String prod_date;
	private String brand;

	private productData prodData;
	private Image image;

	public void setData(productData prodData) {
		this.prodData = prodData;
		type = prodData.getType();
		prod_image = prodData.getImage();
		brand = prodData.getBrand();
		prod_date = String.valueOf(prodData.getDate());
		prodID = prodData.getProductId();
		prod_name.setText(prodData.getProductName());
		prod_price.setText(String.valueOf(prodData.getPrice()) + "đ");
		String path = "File:" + prodData.getImage();
		image = new Image(path, 230, 250, false, true);
		prod_imageView.setImage(image);
		pr = prodData.getPrice();

	}

	private int qty;

	public void setQuantity() {
		spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
		prod_spinner.setValueFactory(spin);
	}

	private long pr;
	private long totalP;

	public void addBtn() {

		MainpageController mPage = new MainpageController();
		mPage.customerID();

		qty = prod_spinner.getValue();
		String check = "";
		String checkAvailable = "SELECT status FROM product WHERE prod_id = '" + prodID + "'";

		connect = database.connectDB();
		try {
			int checkStck = 0;
			String checkStock = "SELECT stock FROM product WHERE prod_id = '" + prodID + "'";

			prepare = connect.prepareStatement(checkStock);
			result = prepare.executeQuery();

			if (result.next()) {
				checkStck = result.getInt("stock");
			}
			
			if(checkStck == 0) {
				
				String updateStock =  "UPDATE product SET " + "prod_name = '" + prod_name.getText() + "', "
						+ "type = '" + type + "', " + "brand = '" + brand + "', " + "stock = 0, "
						+ "price = " + pr + ", " + "status = 'Unavailable', " + "image = '" + prod_image + "', "
						+ "date = '" + prod_date + "' " + "WHERE prod_id = '" + prodID + "'";
				prepare = connect.prepareStatement(updateStock);
				prepare.executeUpdate();
			}

			prepare = connect.prepareStatement(checkAvailable);
			result = prepare.executeQuery();

			if (result.next()) {
				check = result.getString("status");
			}
			if (!check.equals("Available") || qty == 0) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("no no no");
				alert.showAndWait();
			} else {

				if (checkStck < qty) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Out of stock");
					alert.showAndWait();
				} else {
					prod_image = prod_image.replace("\\", "\\\\");
					String insertData = "INSERT INTO customer"
							+ "(customer_id, prod_id,prod_name, type, brand, quantity, price, date, image, em_username)"
							+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
					prepare = connect.prepareStatement(insertData);
					prepare.setString(1, String.valueOf(data.cID));
					prepare.setString(2, prodID);
					prepare.setString(3, prod_name.getText());
					prepare.setString(4,type);
					prepare.setString(5,brand);
					prepare.setString(6, String.valueOf(qty));
					totalP = (qty * pr);
					prepare.setString(7, String.valueOf(totalP));

					Date date = new Date();
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					prepare.setString(8, String.valueOf(sqlDate));
					prepare.setString(9, prod_image);
					prepare.setString(10, data.username);
					prepare.executeUpdate();

					int upStock = checkStck - qty;

					

					System.out.println("Date: " + prod_date);
					System.out.println("Image: " + prod_image);

					String updateStock = "UPDATE product SET " + "prod_name = '" + prod_name.getText() + "', "
							+ "type = '" + type + "', " + "brand = '" + brand + "', " + "stock = " + upStock + ", "
							+ "price = " + pr + ", " + "status = '" + check + "', " + "image = '" + prod_image + "', "
							+ "date = '" + prod_date + "' " + "WHERE prod_id = '" + prodID + "'";

					prepare = connect.prepareStatement(updateStock);
					prepare.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("INFORMATION Message");
					alert.setHeaderText(null);
					alert.setContentText("Added");
					alert.showAndWait();
					mPage.menuGetOrder();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setQuantity();

	}

}
