package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
//NOT working with javaFX correctly
/*import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;*/

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
//import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MainController implements Initializable {
	public static boolean isPlaying = false;	
	
	//Media player section
	@FXML
	private MediaView mvMain;
	private MediaPlayer mediaPlayerMain;
	private Media media;
	
	@FXML
	private Label messageDisplayer;
	
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
		isPlaying = true;
		
		// bind to duration milliseconds to show the time on the label?
		//DoubleProperty timeMili = new SimpleDoubleProperty(mediaPlayerMain.getCurrentTime().toMillis());
		//timeMili.bind(mediaPlayerMain.getCurrentTime().toMillis());
	}
	
	public void beginning(ActionEvent event)
	{
		mediaPlayerMain.setRate(1);
		mediaPlayerMain.seek(mediaPlayerMain.getStartTime());
	}
	
	public void slowDown(ActionEvent event)
	{
		mediaPlayerMain.setRate(mediaPlayerMain.getRate() * .9);
		displayMessage("Speed ratio: " + Math.round(mediaPlayerMain.getRate() * 100) / 100.0);
	}
	
	public void speedUp(ActionEvent event)
	{
		mediaPlayerMain.setRate(mediaPlayerMain.getRate() * 1.1);
		displayMessage("Speed ratio: " + Math.round(mediaPlayerMain.getRate() * 100) / 100.0);
	}
	
	public void minus10sec(ActionEvent event)
	{
		mediaPlayerMain.seek(mediaPlayerMain.getCurrentTime().subtract(new Duration(10000)));
	}
	
	public void plus10sec(ActionEvent event)
	{
		mediaPlayerMain.seek(mediaPlayerMain.getCurrentTime().add(new Duration(10000)));
	}
	
	public void playPause(ActionEvent event)
	{
		if(isPlaying)
		{
			mediaPlayerMain.pause();
		} else
		{
			mediaPlayerMain.play();
		}
		isPlaying = !isPlaying;
	}
	
	
	//not working?
	private void displayMessage(String message)
	{
		System.out.println("called!");
		messageDisplayer.setText(message);

		// NOT working with javaFX correctly
		/*final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				messageDisplayer.setText("");
			}
		}, 0, 1, TimeUnit.SECONDS);*/
		
	}

}
