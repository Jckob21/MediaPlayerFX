package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MainController implements Initializable {
	//Media player section
	@FXML
	private MediaView mvMain;
	private MediaPlayer mediaPlayerMain;
	private Media media;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Openning the example openning file
		String filePath = new File("src/resources/testVideo.mp4").getAbsolutePath();
		media = new Media(new File(filePath).toURI().toString());
		mediaPlayerMain = new MediaPlayer(media);
		mvMain.setMediaPlayer(mediaPlayerMain);
		
		// binding the Media Player's size to the size of the window
		DoubleProperty width = mvMain.fitWidthProperty();
		DoubleProperty height = mvMain.fitHeightProperty();
		width.bind(Bindings.selectDouble(mvMain.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mvMain.sceneProperty(), "height"));
		
		// Playing the video once loaded
		mediaPlayerMain.setAutoPlay(true);
	}
	

}
