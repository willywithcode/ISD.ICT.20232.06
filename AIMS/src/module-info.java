<<<<<<< HEAD:AIMS/src/module-info.java
/**
 * 
 */
/**
 * 
 */
module AIMS {
	
=======
module AIMS{
>>>>>>> 5b5c76c46a4906a53fb320a4286b17a93bfe3aa9:AIMS/src/main/java/module-info.java
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
<<<<<<< HEAD:AIMS/src/module-info.java
	requires junit;
	requires org.junit.jupiter.api;
	
	exports Application;
=======
	
	exports Application;
	opens views.fxml to javafx.fxml;
    opens views.screen.home to javafx.fxml;
>>>>>>> 5b5c76c46a4906a53fb320a4286b17a93bfe3aa9:AIMS/src/main/java/module-info.java
}