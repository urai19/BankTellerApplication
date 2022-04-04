/**
 * Application class for the Bank Teller Application which initializes the scene and connects the code with fxml file.
 * @author Udayan Rai, Garvit Gupta
 */
package bankteller.project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class bankTellerApplication extends Application {
    public static final int WIDTH=1000;
    public static final int HEIGHT=500;
    /**
     * Initializes scene and sets the fxml file
     * @param stage The stage window of the application
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(bankTellerApplication.class.getResource("BankTellerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("Bank Teller Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method for the application class.
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}