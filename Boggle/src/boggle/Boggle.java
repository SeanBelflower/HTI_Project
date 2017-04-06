package boggle;

import inputOutput.ReadDataFile;
import java.util.ArrayList;
import core.Board;
import java.io.IOException;
import userInterface.BoggleUi;

public class Boggle {
    
    //Member Variables
    public static ArrayList<String> boggleDice = new ArrayList<>();
    static final String inputFile = "BoggleData.txt";
    
    static final String inputDict = "TemporaryDictionary.txt";
    public static ArrayList<String> boggleDict = new ArrayList<>();
 
    
//Passing the file name to ReadDataFile.
    static ReadDataFile dataRead = new ReadDataFile(inputFile);
    public static Board diceBoard;
    static ReadDataFile dictRead = new ReadDataFile(inputDict);
    
    
    //Main
    public static void main(String[] args) throws IOException {
        
        //Method call for populateDictionary for dictRead
        dictRead.populateDictionary();
        
        //Method call for populateData for dataRead.
        dataRead.populateData();
        
        //Declared ArrayList is being set equal dataRead's getData method.
        boggleDice = dataRead.getData();
        
        //A new variable diceBoard is being created as a Board class.
        diceBoard = new Board(boggleDice);
        
        //User Interface for Boggle is being called
        BoggleUi userInter = new BoggleUi(diceBoard, boggleDict);
    }

}
