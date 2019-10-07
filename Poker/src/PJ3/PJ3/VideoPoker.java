package PJ3;
import java.util.*;

/*
 * Ref: http://en.wikipedia.org/wiki/Video_poker
 *      http://www.freeslots.com/poker.htm
 *
 *
 * Short Description and Poker rules:
 *
 * Video poker is also known as draw poker. 
 * The dealer uses a 52-card deck, which is played fresh after each playerHand. 
 * The player is dealt one five-card poker playerHand. 
 * After the first draw, which is automatic, you may hold any of the cards and draw 
 * again to replace the cards that you haven't chosen to hold. 
 * Your cards are compared to a table of winning combinations. 
 * The object is to get the best possible combination so that you earn the highest 
 * payout on the bet you placed. 
 *
 * Winning Combinations
 *  
 * 1. Jacks or Better: a pair pays out only if the cards in the pair are Jacks, 
 * 	Queens, Kings, or Aces. Lower pairs do not pay out. 
 * 2. Two Pair: two sets of pairs of the same card denomination. 
 * 3. Three of a Kind: three cards of the same denomination. 
 * 4. Straight: five consecutive denomination cards of different suit. 
 * 5. Flush: five non-consecutive denomination cards of the same suit. 
 * 6. Full House: a set of three cards of the same denomination plus 
 * 	a set of two cards of the same denomination. 
 * 7. Four of a kind: four cards of the same denomination. 
 * 8. Straight Flush: five consecutive denomination cards of the same suit. 
 * 9. Royal Flush: five consecutive denomination cards of the same suit, 
 * 	starting from 10 and ending with an ace
 *
 */


/* This is the video poker game class.
 * It uses OneDeck and Card objects to implement video poker game.
 * Please do not modify any data fields or defined methods
 * You may add new data fields and methods
 * Note: You must implement defined methods
 */



public class VideoPoker {

    // default constant values
    private static final int defaultBalance=100;
    private static final int numberCards=5;

    // default constant payout value and playerHand types
    private static final int[]    winningMultipliers={1,2,3,5,6,9,25,50,250};
    private static final String[] winningHands={ 
	  "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	  "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

    // use one deck of cards
    private final OneDeck thisDeck;

    // holding current player 5-card hand, balance, bet    
    private List<Card> playerHand;
    private int playerBalance;
    private int playerBet;

    /** default constructor, set balance = defaultBalance */
    public VideoPoker()
    {
	this(defaultBalance);
    }

    /** constructor, set given balance */
    public VideoPoker(int balance)
    {
	this.playerBalance= balance;
        thisDeck = new OneDeck();
    }

    /** This display the payout table based on winningMultipliers and 
      * winningHands arrays */
    private void showPayoutTable()
    { 
	System.out.println("\n\n");
	System.out.println("Payout Table   	      Multiplier   ");
	System.out.println("=======================================");
	int size = winningMultipliers.length;
	for (int i=size-1; i >= 0; i--) {
		System.out.println(winningHands[i]+"\t|\t"+winningMultipliers[i]);
	}
	System.out.println("\n\n");
    }

    /** Check current playerHand using winningMultipliers and winningHands arrays
     *  Must print yourHandType (default is "Sorry, you lost") at the end of function.
     *  This can be checked by testCheckHands() and main() method.
     */
    private void checkHands()
    {
	// implement this method
        boolean winHand = false;
        if (handleRoyalFlush()){
            System.out.println(winningHands[8] + "!");
            playerBalance += playerBet * winningMultipliers[8];
            winHand = true;
        }
        
        if (!winHand && handleStraightFlush()){
            System.out.println(winningHands[7] + "!");
            playerBalance += playerBet * winningMultipliers[7];
            winHand = true;
        }
        
        if (!winHand && handleThreeFourOfaKind(4)){
            System.out.println(winningHands[6] + "!");
            playerBalance += playerBet * winningMultipliers[6];
            winHand = true;
        }
        
        if (!winHand && handleFullHouse()){
            System.out.println(winningHands[5] + "!");
            playerBalance += playerBet * winningMultipliers[5];
            winHand = true;
        }
        
        if (!winHand && handleFlush()){
            System.out.println(winningHands[4] + "!");
            playerBalance += playerBet * winningMultipliers[4];
            winHand = true;
        }
        
        if (!winHand && handleStraight()){
            System.out.println(winningHands[3] + "!");
            playerBalance += playerBet * winningMultipliers[3];
            winHand = true;
        }
        
        if (!winHand && handleThreeFourOfaKind(3)){
            System.out.println(winningHands[2] + "!");
            playerBalance += playerBet * winningMultipliers[2];
            winHand = true;
        }
        
        if (!winHand && handleTwoPair()){
            System.out.println (winningHands[1] + "!");
            playerBalance += playerBet * winningMultipliers[1];
            winHand = true;    
        }
        
        if (!winHand && handleRoyalPair()){
            System.out.println(winningHands[0] + "!");
            playerBalance += playerBet * winningMultipliers[0];
            winHand = true;
        }
        
        if (!winHand){
            System.out.println("Sorry, you lost.");
        }
        
        
    }

    /*************************************************
     *   add additional private methods here ....
     *
     *************************************************/
    private boolean handleRoyalFlush (){
        for (Card card : playerHand){
            if (card.getRank() < 10 && card.getRank() != 1) {
                return false;
            }
        }
        if (!handleFlush()){
            return false;
        }
        return true;
    }
    
    private boolean handleFlush (){
        for (Card card : playerHand){
           if (playerHand.get(0).getSuit() != card.getSuit()){
               return false;
           } 
        }
        return true;
    }
    
    private boolean handleStraightFlush (){
       return !(!handleStraight() || !handleStraight());
    }
    
    private boolean handleStraight(){
        List <Integer> arrayRank = new ArrayList<>();
        for (Card card : playerHand){ 
            arrayRank.add(card.getRank());
        }
        Collections.sort(arrayRank);
        if(arrayRank.get(0)== 1){
            if ((arrayRank.get(1)!= 2 || arrayRank.get(2)!=3 || arrayRank.get(3)!=4 || arrayRank.get(4)!=5) 
                    && (arrayRank.get(1)!=10 || arrayRank.get(2)!= 11 ||arrayRank.get(3)!= 12 || arrayRank.get(4) != 13)){
                return false;
            }
        }
        else {
            for (int i = 0; i < arrayRank.size(); i ++){
                if (arrayRank.get(i-1)!= arrayRank.get(i)-1){
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean handleFullHouse(){
        if (handleThreeFourOfaKind(2) && handleThreeFourOfaKind(3)){
            return true;
        }
        return false;
    }
    
    private boolean handleThreeFourOfaKind(int frequency){
        List<Integer> arrayRank = new ArrayList<>();
        for (Card card : playerHand){
            arrayRank.add(card.getRank());
        }
        for (int i = 0; i < arrayRank.size();i++){
            if(Collections.frequency(arrayRank, arrayRank.get(i)) == frequency){
              return true;
            }
        }
        return false;
    }
    
    private boolean handleTwoPair(){
        int count = 0;
        List<Integer> arrayRank = new ArrayList<>();
        for (Card card : playerHand){
            arrayRank.add(card.getRank());
        }
        for (int i = 0; i < arrayRank.size(); i++){
            if (Collections.frequency(arrayRank, arrayRank.get(i)) == 2){
                count +=1;
            }
        }
        return false;
    }
    
    private boolean handleRoyalPair(){
        List <Integer> arrayRank = new ArrayList<>();
        for (Card card : playerHand){
            arrayRank.add(card.getRank());
        }
        for (int i = 0; i < arrayRank.size(); i++){
            if(Collections.frequency(arrayRank, arrayRank.get(i)) ==2 && (arrayRank.get(i) == 10 || arrayRank.get(i) ==1)){
                return true;
            }
        }
        return false;
    }

    public void play() 
    {
    /** The main algorithm for single player poker game 
     *
     * Steps:
     * 		showPayoutTable()
     *
     * 		++	
     * 		show balance, get bet 
     *		verify bet value, update balance
     *		reset deck, shuffle deck, 
     *		deal cards and display cards
     *		ask for positions of cards to keep  
     *          get positions in one input line
     *		update cards
     *		check hands, display proper messages
     *		update balance if there is a payout
     *		if balance = O:
     *			end of program 
     *		else
     *			ask if the player wants to play a new game
     *			if the answer is "no" : end of program
     *			else : showPayoutTable() if user wants to see it
     *			goto ++
     */

	// implement this method
        boolean running = true;
        Scanner input = new Scanner(System.in);
        List<Integer> x = new ArrayList<>();
        
        showPayoutTable();
        while (running){
            System.out.println("---------------------------");
            System.out.println("Balance: $" + playerBalance);
            System.out.println("Enter Bet");
            playerBet =Integer.parseInt(input.next());
            
            while (playerBet > playerBalance){
                System.out.println("Invalid Bet");
                System.out.println("Enter Bet:");
                playerBet = Integer.parseInt(input.next());
            }
            playerBalance -= playerBet;
            
            thisDeck.reset();
            thisDeck.shuffle();
            
            try {playerHand = thisDeck.deal(5);}
            catch (PlayingCardException ex){}
            System.out.println("Hand:" + playerHand);
            
            System.out.println("Enter placements of cards you want to keep (ex:1 2 5):");
            input.nextLine();
            
            String text = input.nextLine();
            
            if(text.equals("")){
                try {playerHand = thisDeck.deal(5); }
                catch (PlayingCardException ex){}
            }
            else {
                for (String string : text.split(" ")){
                    x.add(Integer.parseInt(string));
                }
            }
            Collections.sort(x);
            
            for (int i = 0; i < playerHand.size(); i++){
                if (!x.contains(i)){
                    try {playerHand.set(i-1, thisDeck.deal(1).get(0));}
                    catch (PlayingCardException ex){}
                }
            }
            System.out.println("Hand: " + playerHand);
            checkHands();
            
            if (playerBalance == 0){
                System.out.println("Your balance is " + playerBalance);
                System.out.println("Goodbye.");
                running = false;
            }
            else {
                System.out.println("Current balance: $"+ playerBalance + ", play again(yes/no)");
                text = input.next();
                
                while(!text.equals("no") && !text.equals("yes")){
                    System.out.println("Invalid input");
                    System.out.println("Enter 'yes' or 'no':");
                    text = input.next();
                }
                if (text.equals("no")){
                    running = false;
                }
                else {
                    System.out.println("Do you want to see the payout table? (yes/no)");
                    text = input.next();
                    while (!text.equals("no") && !text.equals("yes")){
                        System.out.println("Invalid input");
                        System.out.println("Enter 'yes' or 'no':");
                        text = input.next();
                        
                    }
                    if (text.equals("yes")){
                        showPayoutTable();
                    }
                }
            }
        }
    }


    /*************************************************
     *   do not modify methods below
     *   methods are used for testing your program.
     *
     *************************************************/

    /** testCheckHands is used to test checkHands() method 
     *  checkHands() should print your current hand type
     */ 
    public void testCheckHands()
    {
      	try {
    		playerHand = new ArrayList<Card>();

		// set Royal Flush
		playerHand.add(new Card(1,4));
		playerHand.add(new Card(10,4));
		playerHand.add(new Card(12,4));
		playerHand.add(new Card(11,4));
		playerHand.add(new Card(13,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight Flush
		playerHand.set(0,new Card(9,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight
		playerHand.set(4, new Card(8,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Flush 
		playerHand.set(4, new Card(5,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	 	// "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

		// set Four of a Kind
		playerHand.clear();
		playerHand.add(new Card(8,4));
		playerHand.add(new Card(8,1));
		playerHand.add(new Card(12,4));
		playerHand.add(new Card(8,2));
		playerHand.add(new Card(8,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Three of a Kind
		playerHand.set(4, new Card(11,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Full House
		playerHand.set(2, new Card(11,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Two Pairs
		playerHand.set(1, new Card(9,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Royal Pair
		playerHand.set(0, new Card(3,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// non Royal Pair
		playerHand.set(2, new Card(3,4));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");
      	}
      	catch (Exception e)
      	{
		System.out.println(e.getMessage());
      	}
    }

    /** testOneDeck() is used to test OneDeck class  
     *  testOneDeck() should execute OneDeck's main()
     */ 
    public void testOneDeck()
    {
    	OneDeck tmp = new OneDeck();
        tmp.main(null);
    }

    /* Quick testCheckHands() */
    public static void main(String args[]) 
    {
	VideoPoker pokergame = new VideoPoker();
	pokergame.testCheckHands();
    }
}
