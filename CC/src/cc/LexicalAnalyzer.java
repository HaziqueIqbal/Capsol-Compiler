package cc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Hazique
 */
public class LexicalAnalyzer {

    public FileInputStream inputFile;
    public String str;
    public static int lineNumber;
    public Factory fact = new Factory();

    public LexicalAnalyzer() throws FileNotFoundException, IOException {

        this.inputFile = new FileInputStream("C:\\Users\\Hazique\\OneDrive\\Documents\\NetBeansProjects\\CC\\test\\test1.txt");
        BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(inputFile));

        while ((str = inputBuffer.readLine()) != null) {     
            String temp;
            temp = breaker(str);

            Factory.oFactories.forEach((oFactory2) -> {
                oFactory2.KeyWords.forEach((KeyWord) -> {
                    if(KeyWord.equals(temp)){
                        System.out.println(true +" "+ oFactory2.ClassName);
                    }
                });
            });
        }
    }

    public static String breaker(String word) {
        String temp = "";
        for (int i = 0; i < word.length(); i++) {
            if (Character.isAlphabetic(word.charAt(i))) {
                temp += word.charAt(i);
            } else if (word.charAt(i) == ' ') {
                return temp;
            }
        }
        return temp;
    }
    
    public static boolean IsIdentifier(String word){
       
        return true;
    }
}

