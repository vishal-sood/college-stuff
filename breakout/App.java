package breakout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Breakout");
		
		Breakout _breakout = new Breakout(false);
		Scene _scene = new Scene(_breakout.getRoot());
		_scene.setOnMouseMoved(_breakout);
		
		primaryStage.setScene(_scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
