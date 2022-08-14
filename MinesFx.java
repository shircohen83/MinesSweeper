package mines;

import java.io.IOException;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MinesFx extends Application 
{//loading and starting the stages to the game

	private int height = 10, width = 10, mines = 10;
	private Controller controller;// to create connection between the java loader file and the controller
	private Stage stage;
	private Mines game;
	private GridPane board = new GridPane(); //create a new GridPane
	private final Media loseSound = new Media(getClass().getResource("loseSound.mp3").toString());
	private final Media winSound = new Media(getClass().getResource("win95.mp3").toString());
	private final Image win = new Image(getClass().getResourceAsStream("thumbsUp.png"), 96, 96, false, false);
    private final Image lose = new Image(getClass().getResourceAsStream("thumbsDown.png"), 96, 96, false, false);
	
    private final Image base = new Image(getClass().getResourceAsStream("button.png"), 20, 20, false, false);
    private final Image mine = new Image(getClass().getResourceAsStream("boom.png"), 20, 20, false, false);
    private final Image flag = new Image(getClass().getResourceAsStream("flag1.png"), 20, 20, false, false);
   
   
    
    public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		HBox hbox = new HBox();// hbox is our base in the fxml
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("shule.fxml"));
		try {
			hbox = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//hbox.setBackground();
		controller = loader.getController(); // load the controller
		// hbox.setPrefSize(795, 620); //set the size of the root
		controller.getHeight().setText(String.valueOf(height));// update text to the current sizes
		controller.getWidth().setText(String.valueOf(width));
		controller.getMines().setText((String.valueOf(mines)));
		game = new Mines(height, width, mines);
		
		CreateReset();
		createBoard();
		
		/*Setting the background for hbox side panel for the game*/
	    BackgroundSize backgroundSize = new BackgroundSize(140,200,true,true,true,false);
		BackgroundImage pic = new BackgroundImage(new Image("mines/background.png"),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,backgroundSize);
		hbox.setBackground(new Background(pic));
		
		hbox.getChildren().add(board);
		Scene scene1 = new Scene(hbox);
		stage.setScene(scene1);
		stage.setTitle("The Amazing Mines Sweeper");
		stage.show();
	}

	private void createBoard()
	{
	        board.getChildren().clear();//deleting all the existing buttons(unwanted buttons from the previous game)
	        board.setAlignment(Pos.CENTER);
	        //create the buttons for the board
	        for (int i = 0; i < height; i++) {
	            for (int j = 0; j < width; j++) 
	            {
	                Button b = new Button();    //create a new button
	                b.setPrefSize(55, 55);//set the size of the button
	                imageToButton(b, i, j);   //set the icon of the button
	                b.setOnMouseClicked(new PlacePressed(i, j));   //set the handler for the button
	                board.add(b, j, i);//add the button to the board
	            }
	        }
	}
	 //inside class for handling a button press
    class PlacePressed implements EventHandler<MouseEvent> {
        private  int x;
        private int y;

        //constructor
        private PlacePressed(int i, int j) {
            this.x = i;
            this.y = j;
        }

		@Override
		public void handle(MouseEvent event) 
		{//this method actions when opening a button
			 //if the click is with the left side
            if (event.getButton() == MouseButton.PRIMARY) 
            {
                if (!game.open(x, y)) //if its a mine
                {
                    game.setShowAll(true); //show the board
                    //to string
                    creatBanner("lost:(!",lose,loseSound); //create a pop up message
                }
                else if (game.isDone()) 
                {//if won
                    game.setShowAll(true); //show the board
                    creatBanner("winer:)!",win,winSound);    //create a pop up message
                }
                //if the click is with the right side
            } 
            else if (event.getButton() == MouseButton.SECONDARY) {
                game.toggleFlag(x, y); //toggle flag
            }
            //update the board
            //clear
            createBoard();
		}
    }
	private void creatBanner(String msg,Image img,Media sound) {
		Stage newStage = new Stage();
		VBox banner = new VBox();
		Label message = new Label();
		Label picLabel=new Label();
		MediaPlayer music = new MediaPlayer(sound);    //create a music player
		banner.setPrefWidth(230);
		message.setText(msg);//setting the label
		picLabel.setGraphic(new ImageView(img));    //set the image for the second label
		banner.getChildren().addAll(message,picLabel);// adding the label to the vbox
		banner.setAlignment(Pos.CENTER);  //set the alignment of the vertical box
		Scene newScene = new Scene(banner); // create a new scene with the new banner
		newStage.setScene(newScene);
		music.play();
		newStage.show();
	}

	public void CreateReset() {// when the button is pressed, we will create a new Mines show
		Button resetButton = controller.getButton(); // get the button from the controller

		resetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				height = Integer.parseInt(controller.getHeight().getText());
				width = Integer.parseInt(controller.getWidth().getText());
				mines = Integer.parseInt(controller.getMines().getText());
				mines = Math.min(mines, height * width);// in case the new amount of mines is bigger
														// then the amount of places in the board
				start(stage); // reset the stage
			}
		});
	}
	
	private void imageToButton(Button b, int x, int y) {
        String res=game.get(x, y);
	
            if(res==".")
                b.setGraphic(new ImageView(base));
            else if(res=="X")
                b.setGraphic(new ImageView(mine));
            else if(res=="F")
                b.setGraphic(new ImageView(flag));
            else
            	b.setText(game.get(x,y));
            	
          
           
    }

}
