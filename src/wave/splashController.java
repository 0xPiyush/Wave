package wave;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jnativehook.GlobalScreen;

import java.net.URL;
import java.util.ResourceBundle;

public class splashController implements Initializable {

    @FXML
    ImageView splashImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        splash();
    }

    private void fade(Node node, double duration, double from, double to, int cycles) {
        FadeTransition ft = new FadeTransition(Duration.millis(duration), node);
        ft.setFromValue(from);
        ft.setToValue(to);
        ft.setCycleCount(cycles);
        ft.play();
    }

    private void splash() {
        new Thread(() -> {
            try {
                // Fade in the splash screen
                fade(splashImage, 1000, 0, 1, 1);
                Thread.sleep(2500);
                // Fade out the splash screen
                fade(splashImage, 500, 1, 0, 1);
                // wait for the fade out to finish
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println(e);
            }
            Platform.runLater(() -> {
                try {

                    // Load the main view
                    Stage primaryStage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dash.fxml"));
                    AnchorPane root = loader.load();

                    primaryStage.setTitle("Wave");
                    primaryStage.setMinWidth(950);
                    primaryStage.setMinHeight(570);

                    Scene s = new Scene(root);

                    primaryStage.setScene(s);
                    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("assets/app_icon.png")));
                    primaryStage.show();

                    primaryStage.setOnCloseRequest(event -> {
                        try
                        {
                            GlobalScreen.unregisterNativeHook();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    });

                    primaryStage.show();
                    splashImage.getScene().getWindow().hide();
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            });
        }).start();
    }
}
