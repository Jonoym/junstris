package Launcher;

import Controller.Controller;
import Logic.Logic;
import View.View;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Launcher extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Junstris");
        Logic gameLogic = new Logic();
        View view = new View(gameLogic, stage);
        Controller controller = new Controller(view, gameLogic);

        stage.getIcons().add(new Image("Images/JunstrisIcon.png"));

        stage.show();
    }
}

