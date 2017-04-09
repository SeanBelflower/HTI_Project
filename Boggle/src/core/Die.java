
package core;

import java.util.Random;
import java.util.ArrayList;

public class Die {

    //Member Variable types declared
    private final int NUMBER_OF_SIDES = 6;
    String currentLetter;
    ArrayList<String> diceLetters = new ArrayList();
    
    //This method gives a random number between 1 to 6.
    public void randomLetter() {
        
        Random rand = new Random();
        
        int number = rand.nextInt(NUMBER_OF_SIDES);
        
        currentLetter = diceLetters.get(number);
        
    }
   
    //Method call to generate a letter.
    public String getLetter() {
        this.randomLetter();
        return currentLetter;
    }
    
    //Method call to add a letter.
    public void addLetter(String helper) {
        diceLetters.add(helper);
    }
   
    //Printing out the Letters.
   /* public void displayAllLeters() {
        
        String newString;
        int i = 0;
        
        while (i < NUMBER_OF_SIDES) {
            System.out.print(diceLetters.get(i) + " ");
            i++;
        }
        
    }*/
        
}
