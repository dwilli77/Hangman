import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Hangman {

  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  String[] choices = {"washington", "adams", "jefferson", "madison", "monroe", "jackson", "harrison", "tyler", "polk", "taylor", "fillmore", "pierce", "buchanan", "lincoln", "johnson", "grant", "hayes", "garfield", "arthur", "cleveland", "mckinley", "roosevelt", "taft", "wilson", "harding", "coolidge", "hoover", "truman", "eisenhower", "kennedy", "nixon", "ford", "carter", "reagan", "bush", "clinton", "obama", "trump"};

  int guessesLeft = 6;
  int correctGuesses = 0;

  String word = "";
  String display = "";

  Word wordObj;

  public void chooseWord(String[] choices){
    Random rand = new Random();
    int randomIndex = rand.nextInt(38);
    word = choices[randomIndex];
  }

  public void initializeGame(Hangman obj) throws IOException{
    obj.chooseWord(obj.choices);
    wordObj = new Word(obj.word);
    wordObj.populateArr();
    display = wordObj.buildDisplay();
    System.out.println("Your word is: " + display);
    
    promptGuess();
  }

  public void displayResult(String a){
    if (a.equals("win")){
      System.out.println("Good job, you won!");
    }
    if (a.equals("lose")){
      System.out.println("Sorry, you lost");
      System.out.println("The correct word was " + word);
    }
  }

  public void promptGuess() throws IOException {
    if (display.indexOf("_") == -1){
      displayResult("win");
    } else if (guessesLeft == 0){
      displayResult("lose");
    } else {
      System.out.print("Guess a letter: ");
      String userGuess = reader.readLine();
      if (userGuess.length() != 1){
        System.out.println();
        System.out.println("Invalid Guess - try again");
        System.out.println();
        promptGuess();
      } else {
        System.out.println();
        wordObj.guess(userGuess.toLowerCase());
        String newDisplay = wordObj.buildDisplay();
        checkScore(newDisplay, display);
        System.out.println("Your word is: " + display);
        promptGuess();
      }
    }
  }

  public void checkScore(String newStr, String oldStr){
    int oldDashCount = 0;
    int newDashCount = 0;

    for (int i = 0; i < oldStr.length(); i++){
      if(Character.toString(oldStr.charAt(i)).equals("_")){
        oldDashCount++;
      }
    }
    for (int i = 0; i < newStr.length(); i++){
      if(Character.toString(newStr.charAt(i)).equals("_")){
        newDashCount++;
      }
    }
    if(newDashCount < oldDashCount){
      display = newStr;
    } else {
      guessesLeft--;
      System.out.println("Wrong, remaining guesses: "+ guessesLeft);
    }
  }
  public static void main(String[] args) throws IOException {
    var game = new Hangman();
    game.initializeGame(game);
    
  }
}

class Word {
  String word = "default";
  public Word(String word){
    this.word = word;
    this.letterArr = new Letter[word.length()];
  }
  
  public Letter[] letterArr;
  
  void populateArr(){
    for(int i = 0; i < letterArr.length; i++){
      letterArr[i] = new Letter(word.substring(i, i+1));
    }
  }

  public String buildDisplay(){
    String val = "";
    for(Letter obj: letterArr) val += obj.toString();
    return val;
  }

  public void guess(String a){
    for(Letter obj: letterArr) obj.checkLetter(a);
  }
}

class Letter {
  String letter;

  public Letter(String letter){
    this.letter = letter;
  }

  boolean guessed = false;

  public String toString(){
    return this.guessed ? " " + letter + " " : " _ ";
  }

  public void checkLetter(String a){
    if (a.equals(this.letter)){
      this.guessed = true;
    }
  }
}