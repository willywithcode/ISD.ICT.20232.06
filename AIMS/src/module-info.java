
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
	requires java.activation;
	requires java.mail;
	requires org.json;

	exports Application;
	exports views.screen;
	exports views.screen.mediaDetail;
	
	opens views.screen to javafx.fxml;
	opens views.screen.home to javafx.fxml;
	opens views.screen.popup to javafx.fxml;
	opens views.screen.manager to javafx.fxml;
	opens views.screen.mediaDetail to javafx.fxml;
	opens views.screen.cart to javafx.fxml;
	opens views.screen.invoice to javafx.fxml;
	opens views.screen.payment to javafx.fxml;
	opens views.screen.shipping to javafx.fxml;
	opens entity.user to javafx.base;
	opens entity.media to javafx.base;
	opens views.screen.viewOrder to javafx.fxml;
}