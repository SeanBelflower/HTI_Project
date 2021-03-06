package core;

import java.util.Collections;
import java.util.ArrayList;
import javax.swing.*;


public final class Board {

    //Declare constant fields
    final int NUMBER_OF_DICE = 16;
    final int NUMBER_OF_SIDES = 6;
    final int GRID = 4;

    //ArrayList Member Variables
    ArrayList<String> diceData;
    ArrayList<Die> diceObjects = new ArrayList<>();

    //Constructor for Board
    public Board(ArrayList<String> boggleDice) {
        diceData = boggleDice;
        this.populateDice();
    }

    //Method populateDice with two while loops.
    public void populateDice() {

        int i = 0;
        int j = 0;

        //Loop until all 16 Dice is made.
        while (i < NUMBER_OF_DICE) {

            int k = 0;
            Die helper = new Die();

            //Loops to make all 6 sides
            while (k < NUMBER_OF_SIDES) {
                String temp = (char)((int)(Math.random() * 26) + 65) + ""; 
                helper.addLetter(temp);
                k++;
            }

            diceObjects.add(helper);
            i++;
        }
    }

    //This method prints out the 4x4 grid Boggle Board to the console and JFrame.
    public ArrayList shakeDice(JButton button[], JButton spot[], JPanel buttonPanel) {

        int i = 0;
        
        buttonPanel.removeAll();
        buttonPanel.revalidate();

        while (i < NUMBER_OF_DICE) {     
            button[i] = new JButton(diceObjects.get(i).getLetter());
            i++;
        }
        
        //Shuffle Dice 
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (i = 0; i < 16; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (i = 0; i < 16; i++) {
            spot[i] = button[list.get(i)];
            spot[i].revalidate();
            buttonPanel.add(spot[i]);
        }

        buttonPanel.revalidate();

        return diceObjects;
    }

}
