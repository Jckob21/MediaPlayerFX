package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
//NOT working with javaFX correctly
/*import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;*/

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
//import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
	@FXML
	private Slider volumeSlider;
	@FXML
	private Slider timeSlider;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Openning the example openning file
		String filePath = new File("src/resources/testVideo.mp4").getAbsolutePath();
		loadClip (new File(filePath));// delete String and loadClip lines and replace with the one below
		//openClip(new ActionEvent());
		
		bindMVSizeToWindow ();		
		
		// Playing the video once loaded
		mediaPlayerMain.setAutoPlay(true);
		isPlaying = true;
		

		
		//set volumeSlider value to the default volume and bind the slider value to media player volume.
		volumeSlider.setValue(mediaPlayerMain.getVolume() * 100);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				mediaPlayerMain.setVolume(volumeSlider.getValue() / 100.0);
		}});
		
		if (mediaPlayerMain.getStatus() == MediaPlayer.Status.UNKNOWN) {
	        mediaPlayerMain.statusProperty().addListener((obs, oldStatus, newStatus) -> {
	            if (newStatus == MediaPlayer.Status.READY) {
	                initializeSlider();
	            } 
	        });
	    } else {
	        initializeSlider();
	    }
		
	}
	
	/**
	 * Sets media player to start time of the file and resets speed rate to 1.
	 * @param event
	 */
	public void beginning(ActionEvent event)
	{
		mediaPlayerMain.setRate(1);
		mediaPlayerMain.seek(mediaPlayerMain.getStartTime());
	}
	
	/**
	 * Sets rate to 10% decreased value and displays appropriate message.
	 * @param event
	 */
	public void slowDown(ActionEvent event)
	{
		mediaPlayerMain.setRate(mediaPlayerMain.getRate() * .9);
		displayMessage("Speed ratio: " + Math.round(mediaPlayerMain.getRate() * 100) / 100.0);
	}
	
	/**
	 * Sets rate to 10% increased value and displays appropriate message.
	 * @param event
	 */
	public void speedUp(ActionEvent event)
	{
		mediaPlayerMain.setRate(mediaPlayerMain.getRate() * 1.1);
		displayMessage("Speed ratio: " + Math.round(mediaPlayerMain.getRate() * 100) / 100.0);
	}
	
	/**
	 * Subtracts ten seconds from current duration and sets mediaPlayer to it.
	 * @param event
	 */
	public void minus10sec(ActionEvent event)
	{
		mediaPlayerMain.seek(mediaPlayerMain.getCurrentTime().subtract(new Duration(10000)));
	}
	
	/**
	 * Adds ten seconds to current duration and sets mediaPlayer to it.
	 * @param event
	 */
	public void plus10sec(ActionEvent event)
	{
		mediaPlayerMain.seek(mediaPlayerMain.getCurrentTime().add(new Duration(10000)));
	}
	
	/**
	 * Handles playPause button. Uses isPlayed global variable to define if the video is playing or not
	 * @param event
	 */
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
	
	
	//TODO hide message after some time period 
	private void displayMessage(String message)
	{
		System.out.println("called!");
		messageDisplayer.setText(message);
		
	}
	
	/**
	 * Handles button "open new file": Opens filechooser, and if the file is valid opens it in the mediaview.
	 * @param event
	 */
	public void openClip(ActionEvent event)
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("Select playable file");
		fc.getExtensionFilters().add(new ExtensionFilter("Movie files", "*.mp4", "*.avi", "*.mov", "*.gif"));
		File selectedFile = fc.showOpenDialog(null);
		if(selectedFile != null)
		{
			loadClip(selectedFile);
		}
	}
	
	/**
	 * Loads a new file to a MediaPlayer and passes it to mediaview.
	 * @param fileToLoad file to load to the mediaView
	 */
	private void loadClip (File fileToLoad)
	{
		media = new Media(fileToLoad.toURI().toString());
		mediaPlayerMain = new MediaPlayer(media);
		mvMain.setMediaPlayer(mediaPlayerMain);
	}
	
	/**
	 * Binds MediaView size to the window size.
	 */
	private void bindMVSizeToWindow ()
	{
		DoubleProperty width = mvMain.fitWidthProperty();
		DoubleProperty height = mvMain.fitHeightProperty();
		width.bind(Bindings.selectDouble(mvMain.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mvMain.sceneProperty(), "height"));
	}

	private void initializeSlider() {
	    timeSlider.setMax(mediaPlayerMain.getTotalDuration().toSeconds());
	    mediaPlayerMain.currentTimeProperty().addListener((obs, oldTime, newTime) -> 
	        timeSlider.setValue(newTime.toSeconds()));
	}
	
	////////////////////THIS IS WORKING REGARDING TIME
	//TODO If put in initialize method the duration is NAN so it is called to early, fix it!
	/*timeSlider.setMax(mediaPlayerMain.getTotalDuration().toSeconds());
	System.out.println("Max value: " + mediaPlayerMain.getTotalDuration().toSeconds());
	mediaPlayerMain.currentTimeProperty().addListener(new ChangeListener() {

		@Override
		public void changed(ObservableValue o, Object oldVal, Object newVal) {
			timeSlider.setValue(mediaPlayerMain.getCurrentTime().toSeconds());
		}
		
	});*/

}
