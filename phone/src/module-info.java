module phone {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	requires javafx.base;
	requires java.sql;


	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
