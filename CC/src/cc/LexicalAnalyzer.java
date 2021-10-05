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
    public static Factory fact = new Factory();

    public LexicalAnalyzer() throws FileNotFoundException, IOException {

        this.inputFile = new FileInputStream("C:\\Users\\Hazique\\OneDrive\\Documents\\NetBeansProjects\\CC\\test\\test1.txt");
        BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(inputFile));

        while ((str = inputBuffer.readLine()) != null) {
            ArrayList<String> words;

            words = breaker(str);
            words.forEach((word) -> {
                System.err.println(word);
            });
        }
    }

    public static ArrayList<String> breaker(String line) {
        String temp = "";
        boolean IsSignStarted = false;
        boolean IsStringStarted = false;
        boolean IsCharStarted = false;
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<String> Punctuators = Factory.Punctuators;
        ArrayList<String> Operators = Factory.Operators;
        for (int i = 0; i < line.length(); i++) {
            String currentAndNextChar = "";
            char charAt = line.charAt(i);
            if (i != (line.length() - 1)) {
                currentAndNextChar += charAt;
                currentAndNextChar += line.charAt(i + 1);
            } else {
                currentAndNextChar += line.charAt(i);
            }
            if ((Character.isAlphabetic(charAt) || charAt == '_' || charAt == '$')) {
                if (temp.contains("+") || temp.contains("-")) {
                    words.add(temp);
                    temp = "";
                    temp += charAt;
                } else {
                    temp += charAt;
                }
            } else if (i != line.length() - 1 & (charAt == '+' || charAt == '-') || Operators.contains(currentAndNextChar)) {
                if (Character.isDigit(line.charAt(i + 1))) {
                    if (i == 0) {
                        temp += charAt;
                    } else if (Character.isAlphabetic(line.charAt(i - 1))) {
                        words.add(temp);
                        temp = "";
                        temp += charAt;
                    } else if (Character.isDigit(line.charAt(i - 1)) & Character.isDigit(line.charAt(i + 1))) {
                        words.add(temp);
                        temp = "";
                        temp += charAt;
                        words.add(temp);
                        temp="";
                    } else {
                        temp += charAt;
                    }
                    IsSignStarted = true;
                } else if (Operators.contains(currentAndNextChar)) {
                    if (!temp.isEmpty()) {
                        words.add(temp);
                        temp = "";
                        temp += currentAndNextChar;
                        words.add(temp);
                        temp = "";
                        i++;
                    } else if (temp.isEmpty()) {
                        temp += currentAndNextChar;
                        words.add(temp);
                        temp = "";
                        i++;
                    } else {
                        temp += charAt;
                    }

                } else if (temp.isEmpty()) {
                    temp += charAt;
                    words.add(temp);
                    temp = "";
                } else {
                    words.add(temp);
                    temp = "";
                    temp += charAt;
                }

            } else if (IsSignStarted & Character.isDigit(charAt)) {
                temp += charAt;

            } else if (IsSignStarted & !Character.isDigit(charAt)) {
                words.add(temp);
                temp = "";
                temp += charAt;
                IsSignStarted = false;
            } else if (Character.isDigit(charAt)) {
                temp += charAt;
            } else if (charAt == '.') { // check for dot and then check all conditions
                if (Factory.isIdentifier(temp)) {
                    words.add(temp);
                    temp = "";
                } else if (Factory.isFloatingPointNumber(temp)) {
                    words.add(temp);
                    temp = "";
                }
                if (i != line.length() - 1) {
                    if (i == 0 & Character.isDigit(line.charAt(i + 1))) {
                        temp += charAt;
                    } else if (i == 0 & (Character.isAlphabetic(line.charAt(i + 1)) || line.charAt(i + 1) == '_' || line.charAt(i + 1) == '$')) {
                        temp += charAt;
                        words.add(temp);
                        temp = "";
                    } else if ((Character.isAlphabetic(line.charAt(i - 1)) || line.charAt(i - 1) == '_' || line.charAt(i - 1) == '$') & (Character.isAlphabetic(line.charAt(i + 1)) || line.charAt(i + 1) == '_' || line.charAt(i + 1) == '$')) {
                        if (!temp.isEmpty()) {
                            words.add(temp);
                            temp = "";
                        }
                        temp += charAt;
                        words.add(temp);
                        temp = "";
                    } else if (charAt == '.' & !temp.isEmpty() & temp.contains(".")) {
                        words.add(temp);
                        temp = "";
                        temp += charAt;
                    } else if (i == 0 & Character.isDigit(line.charAt(i + 1))) {
                        temp += charAt;
                    } else if (charAt == '.' & Character.isDigit(line.charAt(i - 1)) & (Character.isAlphabetic(line.charAt(i + 1)) || line.charAt(i + 1) == '_' || line.charAt(i + 1) == '$') & temp.isEmpty()) {
                        temp += charAt;
                        words.add(temp);
                        temp = "";
                    } else if (charAt == '.' & Character.isDigit(line.charAt(i - 1)) & (Character.isAlphabetic(line.charAt(i + 1)) || line.charAt(i + 1) == '_' || line.charAt(i + 1) == '$')) {
                        words.add(temp);
                        temp = "";
                        temp += charAt;
                        words.add(temp);
                        temp = "";
                    } else if (charAt == '.' & (Character.isAlphabetic(line.charAt(i + 1)) || line.charAt(i + 1) == '_' || line.charAt(i + 1) == '$')) {
                        temp += charAt;
                        words.add(temp);
                        temp = "";
                    } else if (charAt == '.' & Character.isDigit(line.charAt(i + 1))) {
                        temp += charAt;
                    } else if (Character.isDigit(line.charAt(i - 1)) & Character.isDigit(line.charAt(i + 1))) {
                        temp += charAt;

                    } else if ((Character.isAlphabetic(line.charAt(i + 1)) || line.charAt(i + 1) == '_' || line.charAt(i + 1) == '$') & Character.isDigit(line.charAt(i + 1))) {
                        words.add(temp);
                        temp = "";
                        temp += charAt;
                    }
                }
//            } else if (charAt == '0' & !IsAddressStarted & i != line.length() - 1) {
//                if (!"".equals(temp)) {
//                    words.add(temp);
//                    temp = "";
//                }
//                IsAddressStarted = true;
//                temp += charAt;
//            } else if (IsAddressStarted & charAt == 'x' & line.charAt(i - 1) == '0') {
//                temp += charAt;
//            } else if (temp.contains("0x") & temp.length() <= 42) {
//                temp += charAt;
//                if(Factory.isAddress(temp)){
//                    words.add(temp);
//                    temp = "";
//                }
            } else if (charAt == '"' & !IsStringStarted & !IsCharStarted) {
                if (!"".equals(temp)) {
                    words.add(temp);
                    temp = "";
                }
                IsStringStarted = true;
                temp += charAt;
            } else if (IsStringStarted & charAt == '"' & !IsCharStarted) {
                temp += charAt;
                words.add(temp);
                temp = "";
                IsStringStarted = false;
            } else if (IsStringStarted & charAt != '"' & !IsCharStarted) {
                temp += charAt;

            } else if (charAt == '\'' & !IsStringStarted & !IsCharStarted) {
                if (!"".equals(temp)) {
                    words.add(temp);
                    temp = "";
                }
                IsCharStarted = true;
                temp += charAt;
            } else if (IsCharStarted & charAt == '\'' & !IsStringStarted) {
                temp += charAt;
                words.add(temp);
                temp = "";
                IsCharStarted = false;
            } else if (IsCharStarted & charAt != '\'' & !IsStringStarted) {
                temp += charAt;

            } else if (!IsStringStarted & !IsCharStarted) {
                if (Operators.contains(currentAndNextChar) || Punctuators.contains(currentAndNextChar)) {
                    if (!"".equals(temp)) {
                        words.add(temp);
                    }
                    words.add(currentAndNextChar);

                    temp = "";
                    i++;
                } else if (Punctuators.contains(Character.toString(charAt)) || charAt == ' ' || Operators.contains(Character.toString(charAt))
                        || Operators.contains(currentAndNextChar) || Punctuators.contains(currentAndNextChar)) {
                    if (charAt == ' ' || Punctuators.contains(Character.toString(charAt)) || Operators.contains(Character.toString(charAt))) {

                        if (!"".equals(temp)) {
                            words.add(temp);
                        }
                        if (charAt != ' ') {
                            words.add(Character.toString(charAt));
                        }
                        temp = "";
                    }
                }
            }
        }

        if (!"".equals(temp)) {
            words.add(temp);
            temp = "";
        }
        return words;
    }

    public static boolean IsIdentifier(String word) {

        return true;
    }
}
