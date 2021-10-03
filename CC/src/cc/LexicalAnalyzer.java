package cc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

        this.inputFile = new FileInputStream("C:\\Users\\ziaud\\source\\repos\\CC\\Capsol-Compiler\\CC\\test\\test1.txt");
        BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(inputFile));

        while ((str = inputBuffer.readLine()) != null) {     
            ArrayList<String> words;
            words = breaker(str);
            words.forEach((word) -> {
                System.err.println(word);
            });
            
//            Factory.oFactories.forEach((oFactory2) -> {
//                oFactory2.KeyWords.forEach((KeyWord) -> {
//                    if(words.contains(KeyWord)){
//                        System.out.println(true +" "+ oFactory2.ClassName);
//                    }
//                });
//            });
        }
    }

    public static ArrayList<String> breaker(String line) {
        String temp = "";
        boolean IsStringStarted = false;
        boolean IsCharStarted = false;
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<String> Punctuators = Factory.Punctuators;
        ArrayList<String> Operators = Factory.Operators;
        for (int i = 0; i < line.length(); i++) {
            String currentAndNextChar = "";
            char charAt = line.charAt(i);
            if(i != (line.length()-1)){
                currentAndNextChar += charAt;
                currentAndNextChar += line.charAt(i + 1);
            }else{
                currentAndNextChar += line.charAt(i);
            }
            if (Character.isAlphabetic(charAt) || charAt == '_') {
                temp += charAt;
            }
            else if(Character.isDigit(charAt)){
                temp += charAt;
            
            }else if(charAt == '"' & !IsStringStarted & !IsCharStarted){
                if(temp != ""){
                    words.add(temp);
                    temp = "";
                }
                IsStringStarted = true;
                temp += charAt;
            }else if(IsStringStarted & charAt == '"' & !IsCharStarted){
                temp += charAt;
                words.add(temp);
                temp = "";
                IsStringStarted = false;
            }
            else if(IsStringStarted & charAt != '"' & !IsCharStarted){
                temp += charAt;
                
            }else if(charAt == '\'' & !IsStringStarted & !IsCharStarted){
                if(temp != ""){
                    words.add(temp);
                    temp = "";
                }
                IsCharStarted = true;
                temp += charAt;
            }else if(IsCharStarted & charAt == '\'' & !IsStringStarted){
                temp += charAt;
                words.add(temp);
                temp = "";
                IsCharStarted = false;
            }
            else if(IsCharStarted & charAt != '\'' & !IsStringStarted){
                temp += charAt;
                
            }else if(!IsStringStarted & !IsCharStarted ){
                if(Operators.contains(currentAndNextChar) || Punctuators.contains(currentAndNextChar)){
                    if(temp != "")
                        words.add(temp);
                    words.add(currentAndNextChar);
                    
                    temp = "";
                    i++;
                }
                else if(Punctuators.contains(Character.toString(charAt)) || charAt == ' ' || Operators.contains(Character.toString(charAt))
                    || Operators.contains(currentAndNextChar) || Punctuators.contains(currentAndNextChar)){
                    if(charAt == ' ' || Punctuators.contains(Character.toString(charAt)) || Operators.contains(Character.toString(charAt))){
                     
                        
                     
                     if(temp != "")
                         words.add(temp);
                     if(charAt != ' '){
                        words.add(Character.toString(charAt));
                    }
                    temp = "";
                }
                }
                
            }
        }
        if(temp != ""){
            words.add(temp);
            temp = "";
        }
        return words;
    }
    
    public static boolean IsIdentifier(String word){
       
        return true;
    }
}

