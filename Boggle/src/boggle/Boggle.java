package boggle;


import java.util.ArrayList;
import core.Board;
import java.io.IOException;
import userInterface.BoggleUi;

public class Boggle {
    
    //Member Variables
    public static ArrayList<String> boggleDice = new ArrayList<>();
    public static ArrayList<String> boggleDict = new ArrayList<>();
    public static Board diceBoard;
    
    
    //Main
    public static void main(String[] args) throws IOException {
        
        //A new variable diceBoard is being created as a Board class.
        diceBoard = new Board(boggleDice);
        
        //User Interface for Boggle is being called
        BoggleUi userInter = new BoggleUi(diceBoard, boggleDict);
    }

}
