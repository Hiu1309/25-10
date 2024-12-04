package application;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainpageController implements Initializable {

	@FXML
	private Button customers_btn;

	@FXML
	private Button cart_btn;

	@FXML
	private VBox home_form;

	@FXML
	private Button dashboard_btn;

	@FXML
	private Button home_btn;

	@FXML
	private Button inventory_addBtn;

	@FXML
	private Button inventory_btn;

	@FXML
	private Button inventory_clearBtn;

	@FXML
	private TableColumn<productData, String> inventory_col_date;

	@FXML
	private TableColumn<productData, String> inventory_col_price;

	@FXML
	private TableColumn<productData, String> inventory_col_productID;

	@FXML
	private TableColumn<productData, String> inventory_col_productName;

	@FXML
	private TableColumn<productData, String> inventory_col_status;

	@FXML
	private TableColumn<productData, String> inventory_col_stock;

	@FXML
	private TableColumn<productData, String> inventory_col_type;

	@FXML
	private VBox dashboard_form;

	@FXML
	private Button inventory_deleteBtn;

	@FXML
	private AnchorPane inventory_form;

	@FXML
	private ComboBox<?> inventory_brand;

	@FXML
	private ComboBox<?> inventory_type;

	@FXML
	private ImageView inventory_imageView;

	@FXML
	private Button inventory_importBtn;

	@FXML
	private TableView<productData> inventory_tableview;

	@FXML
	private Button inventory_updateBtn;

	@FXML
	private Button logout_btn;

	@FXML
	private BorderPane main_form;

	@FXML
	private TableColumn<productData, String> inventory_col_brand;

	@FXML
	private TextField inventory_price;

	@FXML
	private TextField inventory_productID;

	@FXML
	private TextField inventory_productName;

	@FXML
	private ComboBox<?> inventory_status;

	@FXML
	private TextField inventory_stock;

	@FXML
	private Label username;

	@FXML
	private TextField menu_amount;

	@FXML
	private Label menu_change;

	@FXML
	private TableColumn<productData, String> menu_col_price;

	@FXML
	private TableColumn<productData, String> menu_col_productName;

	@FXML
	private TableColumn<productData, String> menu_col_quantity;

	@FXML
	private AnchorPane menu_form;

	@FXML
	private GridPane menu_gridPane;

	@FXML
	private GridPane menu_gridPane2;

	@FXML
	private GridPane menu_gridPane3;

	@FXML
	private GridPane menu_gridPane4;

	@FXML
	private Button menu_payBtn;

	@FXML
	private Button menu_receiptBtn;

	@FXML
	private Button menu_removeBtn;

	@FXML
	private ScrollPane menu_scrollPane;

	@FXML
	private ScrollPane menu_scrollPane2;

	@FXML
	private ScrollPane menu_scrollPane3;

	@FXML
	private ScrollPane menu_scrollPane4;

	@FXML
	private TableView<productData> menu_tableView;

	@FXML
	private Label menu_total;
	
	@FXML
	private TableColumn<customersData, String> customers_col_cashier;

	@FXML
	private TableColumn<customersData, String> customers_col_customerID;

	@FXML
	private TableColumn<customersData, String> customers_col_date;

	@FXML
	private TableColumn<customersData, String> customers_col_total;

	@FXML
	private AnchorPane customers_form;

	@FXML
	private TableView<customersData> customers_tableView;
	   
	@FXML
	private BarChart<?, ?> dashboard_CC;

	@FXML
	private AreaChart<?, ?> dashboard_IC;

	@FXML
	private Label dashboard_NC;

	@FXML
	private Label dashboard_SP;

	@FXML
	private Label dashboard_TI;

    @FXML
	private Label dashboard_TotalI;

	private Alert alert;

	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	private Statement statement;
	private boolean isCartVisible = false;
	private Image image;

	private ObservableList<productData> cardListData = FXCollections.observableArrayList();

	public void dashboardDisplayNC() {
		String sql = "SELECT COUNT(id) FROM receipt";
		connect = database.connectDB();
		
		try {
			int nc = 0;
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			
			if(result.next()){
				nc = result.getInt("COUNT(id)");
			}
			dashboard_NC.setText(String.valueOf(nc));
		}catch(Exception e) {e.printStackTrace();}
	}
	
	public void dashboardDisplayTI() {
		Date date = new Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		
		String sql = "SELECT SUM(total) FROM receipt WHERE date = '" +
		sqlDate + "'";
		
		connect = database.connectDB();
		
		try {
			
			Long ti = 0L;
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			
			if(result.next()) {
				ti = result.getLong("SUM(total)");
			}
			
			dashboard_TI.setText(ti+"đ");
			
		}catch(Exception e) {e.printStackTrace();}
	}
	
	public void dashboardDisplayTotalI() {
		String sql = "SELECT SUM(total) FROM receipt";
		
		connect = database.connectDB();
		
		try {
			Long ti = 0L;
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			if(result.next()) {
				ti = result.getLong("SUM(total)");
			}
			
			dashboard_TotalI.setText(ti+"đ");
			
			
		}catch(Exception e) {e.printStackTrace();}
	}
	
	public void dashboardDisplaySP() {
		String sql = "SELECT COUNT(quantity) FROM customer";
		
		connect = database.connectDB();
		
		try {
			int q = 0;
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			if(result.next()) {
				q = result.getInt("COUNT(quantity)");
			}
			
			dashboard_SP.setText(String.valueOf(q));
			
			
		}catch(Exception e) {e.printStackTrace();}
	}
	
	
	public void dashboardDisplayIC() {
		dashboard_IC.getData().clear();
		String sql = "SELECT date, SUM(total) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date) ";
		connect = database.connectDB();
		XYChart.Series chart = new XYChart.Series();
		
		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			
			while(result.next()) {
				chart.getData().add(new XYChart.Data<>(result.getString(1),result.getLong(2)));
			}
			dashboard_IC.getData().add(chart);
			
			
		}catch(Exception e) {e.printStackTrace();}
		
	}
	
	public void dashboardCustomerChart() {
		dashboard_CC.getData().clear();
		String sql = "SELECT date, COUNT(id) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date) ";
		connect = database.connectDB();
		XYChart.Series chart = new XYChart.Series();
		
		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			
			while(result.next()) {
				chart.getData().add(new XYChart.Data<>(result.getString(1),result.getInt(2)));
			}
			dashboard_CC.getData().add(chart);
			
			
		}catch(Exception e) {e.printStackTrace();}
	}
	
	public void inventoryAddBtn() {

		if (inventory_productID.getText().isEmpty() || inventory_productName.getText().isEmpty()
				|| inventory_type.getSelectionModel().getSelectedItem() == null
				|| inventory_brand.getSelectionModel().getSelectedItem() == null || inventory_stock.getText().isEmpty()
				|| inventory_price.getText().isEmpty() || inventory_status.getSelectionModel().getSelectedItem() == null
				|| data.path == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields");
			alert.showAndWait();

		} else {
//kiểm tra product id
			String checkProdID = "SELECT prod_id FROM product WHERE prod_id = '" + inventory_productID.getText() + "'";

			connect = database.connectDB();

			try {

				statement = connect.createStatement();
				result = statement.executeQuery(checkProdID);

				if (result.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText(inventory_productID.getText() + "is already taken");
					alert.showAndWait();
				} else {
					String insertData = "INSERT INTO product"
							+ "(prod_id, prod_name, type, brand, stock, price, status, image, date)"
							+ "VALUES(?,?,?,?,?,?,?,?,?)";

					prepare = connect.prepareStatement(insertData);
					prepare.setString(1, inventory_productID.getText());
					prepare.setString(2, inventory_productName.getText());
					prepare.setString(3, (String) inventory_type.getSelectionModel().getSelectedItem());
					prepare.setString(4, (String) inventory_brand.getSelectionModel().getSelectedItem());
					prepare.setString(5, inventory_stock.getText());
					prepare.setString(6, inventory_price.getText());
					prepare.setString(7, (String) inventory_status.getSelectionModel().getSelectedItem());

					String path = data.path;
					path = path.replace("\\", "\\\\");
					prepare.setString(8, path);

					Date date = new Date();
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());

					prepare.setString(9, String.valueOf(sqlDate));

					prepare.executeUpdate();
					inventoryShowData();
					inventoryClearBtn();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public void inventoryUpdateBtn() {

		if (inventory_productID.getText().isEmpty() || inventory_productName.getText().isEmpty()
				|| inventory_type.getSelectionModel().getSelectedItem() == null
				|| inventory_brand.getSelectionModel().getSelectedItem() == null || inventory_stock.getText().isEmpty()
				|| inventory_price.getText().isEmpty() || inventory_status.getSelectionModel().getSelectedItem() == null
				|| data.path == null || data.id == 0) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields");
			alert.showAndWait();

		} else {
			String path = data.path;
			path = path.replace("\\", "\\\\");

			String updateData = "UPDATE product SET " + "prod_id = '" + inventory_productID.getText() + "', "
					+ "prod_name = '" + inventory_productName.getText() + "', " + "type = '"
					+ inventory_type.getSelectionModel().getSelectedItem() + "', " + "brand = '"
					+ inventory_brand.getSelectionModel().getSelectedItem() + "', " + "stock = '"
					+ inventory_stock.getText() + "', " + "price = '" + inventory_price.getText() + "', " + "status = '"
					+ inventory_status.getSelectionModel().getSelectedItem() + "', " + "image = '" + path + "', "
					+ "date = '" + data.date + "' " + "WHERE id = " + data.id;

			connect = database.connectDB();

			try {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Sure to UPDATE product id: " + inventory_productID.getText());
				Optional<ButtonType> option = alert.showAndWait();
				if (option.get().equals(ButtonType.OK)) {
					prepare = connect.prepareStatement(updateData);
					prepare.executeUpdate();
					// để cập nhật bảng
					inventoryShowData();
					// để xóa mấy ô điền
					inventoryClearBtn();
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Cancelled");
					alert.showAndWait();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public void inventoryClearBtn() {

		inventory_productID.setText("");
		inventory_productName.setText("");
		inventory_type.getSelectionModel().clearSelection();
		inventory_brand.getSelectionModel().clearSelection();
		inventory_stock.setText("");
		inventory_price.setText("");
		inventory_status.getSelectionModel().clearSelection();
		data.path = "";
		data.id = 0;
		inventory_imageView.setImage(null);
	}

	// để có thể lấy ảnh từ máy tính
	public void inventoryImportBtn() {

		FileChooser openFile = new FileChooser();
		openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg"));

		File file = openFile.showOpenDialog(main_form.getScene().getWindow());

		if (file != null) {

			data.path = file.getAbsolutePath();
			image = new Image(file.toURI().toString(), 158, 212, false, true);

			inventory_imageView.setImage(image);

		}

	}

	public void inventoryDeleteBtn() {

		if (inventory_productID.getText().isEmpty() || inventory_productName.getText().isEmpty()
				|| inventory_type.getSelectionModel().getSelectedItem() == null
				|| inventory_brand.getSelectionModel().getSelectedItem() == null || inventory_stock.getText().isEmpty()
				|| inventory_price.getText().isEmpty() || inventory_status.getSelectionModel().getSelectedItem() == null
				|| data.path == null || data.id == 0) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields");
			alert.showAndWait();

		} else {

			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Sure to Delete product id: " + inventory_productID.getText());
			Optional<ButtonType> option = alert.showAndWait();

			if (option.get().equals(ButtonType.OK)) {
				String deleteData = "DELETE FROM product WHERE id = " + data.id;

				try {
					prepare = connect.prepareStatement(deleteData);
					prepare.executeUpdate();
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Delete Success!");
					alert.showAndWait();

					inventoryShowData();
					inventoryClearBtn();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Cancelled");
				alert.showAndWait();
			}

		}

	}

//gộp tất cả datas
	public ObservableList<productData> inventoryDataList() {

		ObservableList<productData> listData = FXCollections.observableArrayList();

		String sql = "SELECT * FROM product";

		connect = database.connectDB();

		try {

			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			productData prodData;

			while (result.next()) {

				prodData = new productData(result.getInt("id"), result.getString("prod_id"),
						result.getString("prod_name"), result.getString("type"), result.getString("brand"),
						result.getInt("stock"), result.getLong("price"), result.getString("status"),
						result.getString("image"), result.getDate("date"));

				listData.add(prodData);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

//hiển thị database sản phẩm trên bảng 
	private ObservableList<productData> inventoryListData;

	public void inventoryShowData() {
		inventoryListData = inventoryDataList();

		inventory_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
		inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
		inventory_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
		inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
		inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
		inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

		inventory_tableview.setItems(inventoryListData);
	}

	public void inventorySelectData() {
		productData prodData = inventory_tableview.getSelectionModel().getSelectedItem();
		int num = inventory_tableview.getSelectionModel().getSelectedIndex();
		if ((num - 1) < -1)
			return;
		inventory_productID.setText(prodData.getProductId());
		inventory_productName.setText(prodData.getProductName());
		inventory_stock.setText(String.valueOf(prodData.getStock()));
		inventory_price.setText(String.valueOf(prodData.getPrice()));

		data.path = prodData.getImage();

		String path = "File:" + prodData.getImage();
		data.date = String.valueOf(prodData.getDate());

		data.id = prodData.getId();

		image = new Image(path, 158, 212, false, true);

		inventory_imageView.setImage(image);
	}

	private String[] typeList = { "Ios", "Android" };
	private String[] brandList = { "Apple", "Samsung", "Huawei", "p.Apple" };
	private String[] statusList = { "Available", "Unavailable" };

	public void inventoryTypeList() {

		List<String> typeL = new ArrayList<>();

		for (String data : typeList) {
			typeL.add(data);
		}

		ObservableList listData = FXCollections.observableArrayList(typeL);
		inventory_type.setItems(listData);

	}

	public void inventoryBrandList() {

		List<String> brandL = new ArrayList<>();

		for (String data : brandList) {
			brandL.add(data);
		}

		ObservableList listData = FXCollections.observableArrayList(brandL);
		inventory_brand.setItems(listData);

	}

	public void inventoryStatusList() {

		List<String> statusL = new ArrayList<>();

		for (String data : statusList) {
			statusL.add(data);
		}

		ObservableList listData = FXCollections.observableArrayList(statusL);
		inventory_status.setItems(listData);

	}

	public ObservableList<productData> menuGetData() {

		String sql = "SELECT * FROM product";
		ObservableList<productData> listData = FXCollections.observableArrayList();

		connect = database.connectDB();

		try {
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			productData prod;

			while (result.next()) {
				prod = new productData(result.getInt("id"), result.getString("prod_id"), result.getString("prod_name"),
						result.getString("type"), result.getInt("stock"), result.getString("brand"), result.getLong("price"),
						result.getString("image"), result.getDate("date"));

				listData.add(prod);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listData;
	}

	public void menuDisplayCard() {
		cardListData.clear();
		cardListData.addAll(menuGetData());

		int row1 = 0, column1 = 0;
		int row2 = 0, column2 = 0;
		int row3 = 0, column3 = 0;
		int row4 = 0, column4 = 0;

		menu_gridPane.getChildren().clear();
		menu_gridPane2.getChildren().clear();
		menu_gridPane3.getChildren().clear();
		menu_gridPane4.getChildren().clear();

		menu_gridPane.getRowConstraints().clear();
		menu_gridPane.getColumnConstraints().clear();
		menu_gridPane2.getRowConstraints().clear();
		menu_gridPane2.getColumnConstraints().clear();
		menu_gridPane3.getRowConstraints().clear();
		menu_gridPane3.getColumnConstraints().clear();
		menu_gridPane4.getRowConstraints().clear();
		menu_gridPane4.getColumnConstraints().clear();
		ObservableList<productData> productList = menuGetData();
		for (productData prod : productList) {
			try {

				FXMLLoader load = new FXMLLoader();
				load.setLocation(getClass().getResource("/application/fxml/cardProduct.fxml"));
				AnchorPane pane = load.load();
				cardProductController cardC = load.getController();
				cardC.setData(prod);

				// Thêm sản phẩm vào menu_gridPane (tất cả sản phẩm)
				if (column1 == 3) {
					column1 = 0;
					row1++;
				}
				menu_gridPane.add(pane, column1++, row1);

				// Phân loại sản phẩm dựa trên brand và tạo bản sao cho các GridPane khác
				String brand = prod.getBrand();
				if (brand.equalsIgnoreCase("Apple")) {
					FXMLLoader iphoneLoader = new FXMLLoader(
							getClass().getResource("/application/fxml/cardProduct.fxml"));
					AnchorPane iphonePane = iphoneLoader.load();
					cardProductController iphoneCard = iphoneLoader.getController();
					iphoneCard.setData(prod);

					if (column2 == 3) {
						column2 = 0;
						row2++;
					}
					menu_gridPane2.add(iphonePane, column2++, row2);
				} else if (brand.equalsIgnoreCase("Samsung")) {
					FXMLLoader samsungLoader = new FXMLLoader(
							getClass().getResource("/application/fxml/cardProduct.fxml"));
					AnchorPane samsungPane = samsungLoader.load();
					cardProductController samsungCard = samsungLoader.getController();
					samsungCard.setData(prod);

					if (column3 == 3) {
						column3 = 0;
						row3++;
					}
					menu_gridPane3.add(samsungPane, column3++, row3);
				} else if (brand.equalsIgnoreCase("Huawei")) {
					FXMLLoader huaweiLoader = new FXMLLoader(
							getClass().getResource("/application/fxml/cardProduct.fxml"));
					AnchorPane huaweiPane = huaweiLoader.load();
					cardProductController huaweiCard = huaweiLoader.getController();
					huaweiCard.setData(prod);

					if (column4 == 3) {
						column4 = 0;
						row4++;
					}
					menu_gridPane4.add(huaweiPane, column4++, row4);

					GridPane.setMargin(pane, new Insets(10));

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public ObservableList<productData> menuGetOrder(){
		customerID();
		ObservableList<productData> listData = FXCollections.observableArrayList();
		
		String sql = "SELECT * FROM customer WHERE customer_id = "+cID;
		
	    connect = database.connectDB();
		
		try {
			
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			
			productData prod;
			
			while(result.next()) {
				prod = new productData(result.getInt("id"), result.getString("prod_id"), 
				result.getString("prod_name"), result.getString("type"), result.getInt("quantity"),
				result.getString("brand"), result.getLong("price"), result.getString("image"), 
				result.getDate("date"));
				
				listData.add(prod);
			}
			
			
		}catch(Exception e) {e.printStackTrace();}
		return listData;
	}
	
	private ObservableList<productData> menuOrderListData;
	public void menuShowOrderData() {
		menuOrderListData = menuGetOrder();
		menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		menu_tableView.setItems(menuOrderListData);
	}
	
	private int getid;
	public void menuSelectOrder() {
		productData prod = menu_tableView.getSelectionModel().getSelectedItem();
		int num = menu_tableView.getSelectionModel().getSelectedIndex();
		
		if((num-1)<-1) return;
		getid = prod.getId();
		System.out.print("Oke");
	}
	
	private Long totalP;
	
	public void menuGetTotal() {
	    customerID(); // Đảm bảo `cID` đã được cập nhật đúng
	    String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;
	    connect = database.connectDB();
	    try {
	        prepare = connect.prepareStatement(total);
	        result = prepare.executeQuery();
	        if (result.next()) {
	            totalP = result.getLong("SUM(price)");
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void menuDisplayTotal() {
		menuGetTotal();
		menu_total.setText(totalP + "đ");
	}
	
	private Long amount;
	private Long change;
	public void menuAmount() {
		menuGetTotal();
		if(menu_amount.getText().isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Invalid");
			alert.showAndWait();
		}else {
			amount = Long.parseLong(menu_amount.getText());
			change = 0L;
			if(amount<totalP) {
				menu_amount.setText("");	
			}else {
				change = (amount-totalP);
				menu_change.setText(change+"đ");
			}
		}
	}
	
	public void menuPayBtn() {
		if(totalP != null && totalP == 0L) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Choose product first");
			alert.showAndWait();
		}else {
			menuGetTotal();
			String insertPay = "INSERT INTO receipt(customer_id,total,date,em_username) "
					+ "VALUES(?,?,?,?)";
			
			connect = database.connectDB();
			
			try {
				if(amount != null && amount == 0L) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Somthing wrong");
					alert.showAndWait();
				}else {
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Comfirmation Message");
					alert.setHeaderText(null);
					alert.setContentText("Comfirm ?");
					Optional<ButtonType> option = alert.showAndWait();
					
					if(option.get().equals(ButtonType.OK)) {
						customerID();
						menuGetTotal();
						prepare = connect.prepareStatement(insertPay);
						prepare.setString(1, String.valueOf(cID));
						prepare.setString(2, String.valueOf(totalP));
						
						Date date = new Date();
						java.sql.Date sqlDate = new java.sql.Date(date.getTime());
						
						prepare.setString(3, String.valueOf(sqlDate));
						prepare.setString(4, data.username);
						
						prepare.executeUpdate();
						
						alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Infomation Message");
						alert.setHeaderText(null);
						alert.setContentText("Successful.");
						alert.showAndWait();
						
						menuShowOrderData();
						menuRestart();
						
						
					}else {
						alert = new Alert(AlertType.WARNING);
						alert.setTitle("Infomation Message");
						alert.setHeaderText(null);
						alert.setContentText("Cancelled.");
						alert.showAndWait();
					}
				}
				
				
			}catch(Exception e) {e.printStackTrace();}
		}
	}
	
	public void menuRemoveBtn() {
		System.out.println("Oke2");
		if(getid == 0) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Select product to remove");
			alert.showAndWait();
		}else {
			String deleteData = "DELETE FROM customer WHERE id = " + getid;
			connect = database.connectDB();
			try {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Comfirmation Message");
				alert.setHeaderText(null);
				alert.setContentText("Comfirm ?");
				Optional<ButtonType> option = alert.showAndWait();
				
				if(option.get().equals(ButtonType.OK)) {
					prepare = connect.prepareStatement(deleteData);
					prepare.executeUpdate();
				}
				
				menuShowOrderData();
			}catch (Exception e) {e.printStackTrace();}
		}
	}
	
	public void menuRestart() {
		totalP = 0L;
		change = 0L;
		amount = 0L;
		menu_total.setText("0đ");
		menu_change.setText("0đ");
		menu_amount.setText("");
	}
	
	private int cID;
	
	public void customerID() {
		String sql = "SELECT MAX(customer_id) FROM customer";
		connect = database.connectDB();

		try {

			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			if (result.next()) {
				cID = result.getInt("MAX(customer_id)");
			}

			String checkCID = "SELECT MAX(customer_id) FROM receipt";
			prepare = connect.prepareStatement(checkCID);
			result = prepare.executeQuery();
			int checkID = 0;
			if (result.next()) {
				checkID = result.getInt("MAX(customer_id)");
			}

			if (cID == 0) {
				cID += 1;
			} else if (cID == checkID) {
				cID += 1;
			}

			data.cID = cID;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public ObservableList<customersData> customersDataList(){
		ObservableList<customersData> listData = FXCollections.observableArrayList();
		String sql = "SELECT * FROM receipt";
		connect = database.connectDB();
		try {
			
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			customersData cData;
			while(result.next()) {
				cData = new customersData(result.getInt("id"), result.getInt("customer_id"), 
						result.getLong("total"),result.getDate("date"),result.getString("em_username"));
				listData.add(cData);
			}
			
		}catch(Exception e) {e.printStackTrace();}
		return listData;
	}

	private ObservableList<customersData> customersListData;
	public void customersShowData() {
		customersListData = customersDataList();
		customers_col_customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		customers_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
		customers_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
		customers_col_cashier.setCellValueFactory(new PropertyValueFactory<>("emUsername"));
		customers_tableView.setItems(customersListData);
		
	}
	
	public void switchForm(ActionEvent event) {
		if (event.getSource() == dashboard_btn) {
			dashboard_form.setVisible(true);
			inventory_form.setVisible(false);
			menu_form.setVisible(false);
			home_form.setVisible(false);
			customers_form.setVisible(false);
			isCartVisible = false;
			dashboardDisplayNC();
			dashboardDisplayTI();
			dashboardDisplayTotalI();
			dashboardDisplaySP();
			dashboardDisplayIC();
			dashboardCustomerChart();
		} else if (event.getSource() == home_btn) {
			dashboard_form.setVisible(false);
			inventory_form.setVisible(false);
			menu_form.setVisible(false);
			home_form.setVisible(true);
			customers_form.setVisible(false);
			isCartVisible = false;
			menuDisplayCard();
		} else if (event.getSource() == inventory_btn) {
			dashboard_form.setVisible(false);
			inventory_form.setVisible(true);
			menu_form.setVisible(false);
			home_form.setVisible(false);
			customers_form.setVisible(false);
			isCartVisible = false;
			inventoryTypeList();
			inventoryBrandList();
			inventoryStatusList();
			inventoryShowData();
		} else if (event.getSource() == cart_btn) {
			isCartVisible = !isCartVisible;
			dashboard_form.setVisible(false);
			inventory_form.setVisible(false);
			menu_form.setVisible(isCartVisible);
			home_form.setVisible(true);
			customers_form.setVisible(false);
			menuGetOrder();
			menuDisplayTotal();
			menuShowOrderData();
		}else if(event.getSource() == customers_btn){
			dashboard_form.setVisible(false);
			inventory_form.setVisible(false);
			menu_form.setVisible(false);
			home_form.setVisible(false);
			customers_form.setVisible(true);
			isCartVisible = false;
			customersShowData();
		}
	}

	public void logout() {

		try {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Sure ko :))");
			Optional<ButtonType> option = alert.showAndWait();

			if (option.get().equals(ButtonType.OK)) {
				// tắt mainpage
				logout_btn.getScene().getWindow().hide();

				Parent root = FXMLLoader.load(getClass().getResource("/application/fxml/login.fxml"));

				Stage stage = new Stage();
				Scene scene = new Scene(root);

				stage.setTitle(" Phone Store Management System");

				stage.setScene(scene);
				stage.show();

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//hiển thị tên người đăng nhập
	public void displayUsername() {

		String user = data.username;
		user = user.substring(0, 1).toUpperCase() + user.substring(1);

		username.setText(user);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		displayUsername();
		dashboardDisplayNC();
		dashboardDisplayTI();
		dashboardDisplayTotalI();
		dashboardDisplaySP();
		dashboardDisplayIC();
		dashboardCustomerChart();
		inventoryTypeList();
		inventoryBrandList();
		inventoryStatusList();
		inventoryShowData();
		menuDisplayCard();
		customersShowData();
	}

}
