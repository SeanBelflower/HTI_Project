package inputOutput;

import static boggle.Boggle.boggleDict;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;


public class ReadDataFile {
    
    //Member Variables
    Scanner fileRead;
    String fileInput;
    ArrayList<String> dataFile = new ArrayList<>();
    Scanner fileDic;
    String dictInput;
    
    
    //ReadDataFile's constructor
    public ReadDataFile(String inFile) {

        fileInput = inFile;

    }
    
    //TemporaryDictionary.txt is being scanned
    public void populateDictionary() throws IOException {
        
        //Temporary String Holder
        String temp;

        try {
            
            //Declare URL object called "url".
            URL url;
            
            //URL object is being set to the given file name.
            url = getClass().getResource("TemporaryDictionary.txt");
            
            //Setting the File object to specific URL path.
            File urlData = new File(url.toURI());
            
            //Scanning the url file and set o fileRead.
            fileDic = new Scanner(urlData);
            
            //Loop through each elements in the file.
            while (fileDic.hasNext()) {

                temp = fileDic.next();

                boggleDict.add(temp);
            }
    }
        //Catch filenot found exception.
        catch(Exception e) {
            System.out.println(e.toString());
        }
        //Close the file when done.
        finally {
           fileDic.close();
        }
    }
        
    
    //BoggleData.txt is being Scanned
    public void populateData() throws IOException {
        
        //Temporary string holder.
        String temp;

        try {
            
            //Declare URL object called "url".
            URL url;
            
            //URL object is being set to the given file name.
            url = getClass().getResource(fileInput);
            
            //Setting the File object to specific URL path.
            File urlData = new File(url.toURI());
            
            //Scanning the url file and set o fileRead.
            fileRead = new Scanner(urlData);
            
            //Loop through each elements in the file.
            while (fileRead.hasNext()) {

                temp = fileRead.next();

                dataFile.add(temp);
            }
        }
        
        //Catch filenot found exception.
        catch(Exception e) {
            System.out.println(e.toString());
        }
        //Close the file when done.
        finally {
           fileRead.close();
        }
    }
    
    //Method call to get the data.
    public ArrayList<String> getData() {
        return dataFile;
    }

}
