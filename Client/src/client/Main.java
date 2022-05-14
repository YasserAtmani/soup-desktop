package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("player.fxml"));
        primaryStage.setTitle("Ice & Soup");
        Scene scene = new Scene(root, 1595, 900);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);

        /**
         * Title bar draggable
         */
        AnchorPane titleBar = (AnchorPane) primaryStage.getScene().lookup("#titleBar");
        if(titleBar!=null){
            titleBar.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            titleBar.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            });
        }

        /**
         * Title bar draggable sur toutes les scenes
         */
        primaryStage.sceneProperty().addListener((observable, oldScene, newScene) -> {
            AnchorPane titleBar2 = (AnchorPane) primaryStage.getScene().lookup("#titleBar");
            if(titleBar2!=null){
                titleBar2.setOnMousePressed(event -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });
                titleBar2.setOnMouseDragged(event -> {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                });
            }
            String css = this.getClass().getResource("styles.css").toExternalForm();
            newScene.getStylesheets().add(css);
        });

        //Supprimer la title bar
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
