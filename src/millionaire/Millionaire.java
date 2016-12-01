package millionaire;

import java.util.*;
import java.io.*;
import static java.lang.System.exit;
import java.nio.file.*;
/**
 *
 * @author Srikar Kashyap Pulipaka (srikar.kashyap@gmail.com)
 */
/**A
 * Class for running the Meelo Evaru Koteswarudu (Who Wants to be a Millionare) game show
 */
public class Millionaire {
    /**
     * @param prizemoney To calculate the amount of prize money earned
     * @param ques The current question number (automatically increments for every completed question)
     * @param fiftyfifty Boolean variable to check if 50-50 lifeline is invoked. If invoked, this variable is set to True so that it is not reused again.
     * @param doubledip Boolean variable to check if doubledip lifeline is invoked. If invoked, this variable is set to True so that it is not reused again.
     */
    Scanner s = new Scanner(System.in);
    int prizemoney = 0;
    int ques = 0;
    boolean fiftyfifty = false;
    boolean doubledip = false;
    /**
     * Used to read the question from the files and display it to the user sequentially.If lifeline is choosen, the method for lifeline is invoked. 
     * @throws IOException
     * @throws InterruptedException 
     */
    public void read() throws IOException, InterruptedException {
        String quesno[] = {"q1.txt", "q2.txt", "q3.txt", "q4.txt", "q5.txt", "q6.txt", "q7.txt", "q8.txt", "q9.txt", "q10.txt"};
        for (int i = 0; i < 10; i++) {
            try (BufferedReader br = new BufferedReader(new FileReader(quesno[i]))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
                ques++;
                boolean verified = false;
                System.out.print("\nPlease enter your option (L to take a lifeline): ");
                String ans = s.next();
                if(ans.equals("L"))
                {
                    lifeline(ques);
                    ans = s.next();
                }
                if(doubledip == true && verified == false)
                {
                    boolean verify = answerkey(ans,11);
                    verified = true;
                }
                boolean verify = answerkey(ans, i + 1);
                action(verify);
        }
    }
    /**
     * Method to verify if the answer by the user matches the key using the boolean verify
     * @param verify The boolean from answerkey which returns True if answer is right and False if not
     */
    public void action(boolean verify) {

        if (verify == true) {
            return;
        } else if (verify == false) {
            exit(0);
        }
    }
    /**
     * Gets the answer and question number from the user and verifies it with the key provided.Prints Corresponding messages per the result(Correct or Incorrect)
     * @param ans The user given answer for the question
     * @param quesno The current question number in the quiz
     * @return Returns a boolean: True if answer matches the key and False if it does not
     */
    boolean answerkey(String ans, int quesno) {
        String key[] = {"B", "B", "C", "D", "A", "B", "C", "A", "C", "D","B"};
        int money[] = {2500, 5000, 10000, 25000, 100000, 320000, 640000, 2500000, 5000000, 10000000};
        if (ans.equals(key[quesno - 1])) {
            if (quesno <= 10) {
                System.out.println((char)27 + "[32mCorrect!!!");
                System.out.println((char)27 + "[32mCongrats you have won ₹" + money[quesno - 1]);
                if (quesno == 10) {
                    System.out.println((char)27 + "[32mYou are a millionare now!!!");
                    credits();
                    return true;
                }
                System.out.println("Moving to next question......");
                prizemoney = money[quesno - 1];
                return true;
            }
        } else {
                System.out.println((char)27 + "[31mOops! Wrong answer! :(");
                System.out.println((char)27 + "[31mYou are now eliminated. Congrats for winning ₹" + prizemoney);
                credits();
                return false;
            }
        return true;
    }
    /**
     * The main function to execute all the other methods and for displaying the main menu of the quiz
     * @param args
     * @throws IOException
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Millionaire  k = new Millionaire ();
        Scanner s = new Scanner(System.in);
        System.out.println("-----------------------------------");
        System.out.println("Who Wants to be a Millionare!!");
        System.out.println("-----------------------------------");
        System.out.println("\n");
        System.out.print("Please type ENTER to start the game show!!: ");
        String enter = s.next();

        if ("ENTER".equals(enter) || "enter".equals(enter)) {
            System.out.println("Welcome! Lets begin!");
            System.out.println("Here's your first question!");
            k.read();
        }
        else {
            System.out.println("Wrong text entered....exiting..");
        }
    }
    /**
     * Method to invoke one of the two (or both) of the provided lifelines: Double-dip and 50-50.Checks if any of the two lifelines are already used up in the current session of quiz and displays the menu accordingly.
     * @param ques Takes the current question number as parameter to utilize it in the 50-50 lifeline to display only 2 options
     * @throws IOException
     * @throws InterruptedException for using the thread and sleep features
     */
    public void lifeline(int ques) throws IOException, InterruptedException
    {   
        int choice=0;
        Thread t = null;
        System.out.println("Please select which lifeline would you like to choose? ");
        if(fiftyfifty==true && doubledip==true)
                {
                    System.out.println((char)27 +  "[31m1. 50-50");
                    System.out.println((char)27 +  "[31m2. Double Dip (already used)");
                    
                }
             
        else if(fiftyfifty==true)
        {
            System.out.println(((char)27 + "[31m1. 50-50 (already used up)")+"\n 2. Double Dip");
        }
        else if(doubledip== true)
        {
         System.out.println( "1. 50-50");
         System.out.println((char)27 +  "[31m2. Double Dip (already used)");   
        }
        else
        {
            System.out.println("1. 50-50 2. Double Dip ");
        }
        choice = s.nextInt();
        switch(choice)
        {
            case 1: if(fiftyfifty==false)
                {
                    System.out.println("Processing....");
                    Thread.sleep(2000);
                    String fifty = Files.readAllLines(Paths.get("testing.txt")).get(ques-1);
                    System.out.println(fifty);
                    fiftyfifty  =true;
                 }
                else
                {
                    System.out.println("50-50 already used up.");
                    lifeline(ques);
                    break;
                }
                System.out.println("Please enter your option");
                break;   
                     
            case 2 : try (BufferedReader br = new BufferedReader(new FileReader("doubledip.txt"))) {
                     String line;
                     while ((line = br.readLine()) != null) {
                     System.out.println(line);
                     }
                    }
                    System.out.println("Please enter your option");
                    doubledip = true;
                    break;
                   
            default:
                     System.out.println("Invalid option selected. Please select again");
                     break;  
        }
    }
    /**
     *  For displaying the author credits      
     */
    public void credits()
    {
            System.out.println((char)27 + "[34mThank you for playing this game....\n");
            System.out.println((char)27 + "[35mDeveloped by Srikar Kashyap Pulipaka (srikar.kashyap@gmail.com)");
    }
    
}
