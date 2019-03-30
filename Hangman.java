import java.util.Random;

public class Hangman {

  String[] choices = {"washington", "adams", "jefferson", "madison", "monroe", "jackson", "harrison", "tyler", "polk", "taylor", "fillmore", "pierce", "buchanan", "lincoln", "johnson", "grant", "hayes", "garfield", "arthur", "cleveland", "mckinley", "roosevelt", "taft", "wilson", "harding", "coolidge", "hoover", "truman", "eisenhower", "kennedy", "nixon", "ford", "carter", "reagan", "bush", "clinton", "obama", "trump"};

  int guesses = 6;

  String word = "";
  String display = "";

  public void chooseWord(String[] choices){
    Random rand = new Random();
    int randomIndex = rand.nextInt(38);
    word = choices[randomIndex];
  }
  public static void main(String[] args){
    var game = new Hangman();
    game.chooseWord(game.choices);
    Word wordObj = new Word(game.word);
    wordObj.populateArr();
    String display = wordObj.buildDisplay();
    System.out.println("Your word is: " + display);
    System.out.println(game.word);
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