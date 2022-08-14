package mines;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller {

    @FXML
    private TextArea height;

    @FXML
    private TextArea mines;

    @FXML
    private Button resetButton;

    @FXML
    private TextArea width;
    public TextArea getMines()
    {
    	return mines;
    }
    public TextArea getWidth()
    {
    	return width;
    }
    public TextArea getHeight()
    {
    	return height;
    }
    public Button getButton()
    {
    	return resetButton;
    }
}
