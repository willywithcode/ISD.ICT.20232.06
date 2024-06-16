
/**
 * 
 */
/**
 * 
 */
module AIMS{
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.media;
	requires javafx.swing;
	requires javafx.swt;
	requires javafx.web;
	requires java.logging;
	requires java.desktop;
	requires java.sql;
	
	exports Application;
	opens views.fxml to javafx.fxml;
    opens views.screen.home to javafx.fxml;
    opens views.screen to javafx.fxml;
    opens views.screen.popup to javafx.fxml;
    opens views.screen.manager to javafx.fxml;
    opens entity.user to javafx.base;
}