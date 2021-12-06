package capsolcompiler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author Hazique
 */
public class LexicalAnalyzer {

    public FileInputStream inputFile;
    public static String str;
    public static int lineNumber = 1;
    public static Factory fact = new Factory();
    static boolean comment = false;
    public static ArrayList<tempToken> tempTK = new ArrayList<>();
    public tempToken t;

    public LexicalAnalyzer() throws FileNotFoundException, IOException {

        String userDirectory = Paths.get("").toAbsolutePath().toString();
        this.inputFile = new FileInputStream(userDirectory + "\\test\\testing.txt");
        BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(inputFile));
        ArrayList<String> words;
        while ((str = inputBuffer.readLine()) != null) {

            words = breaker(str);
            words.forEach((word) -> {
                t = new tempToken(word, lineNumber);
                tempTK.add(t);
//                System.err.println(word + "    --------->" + lineNumber);
            });
            lineNumber++;
        }
        t = new tempToken("<END-MARKER>", lineNumber);
        tempTK.add(t);
    }

    public static ArrayList<String> breaker(String line) {
        String temp = "";
        boolean IsStringStarted = false;
        boolean IsCharStarted = false;
        int IsPointStart = 0;
        boolean IsAddressStarted = false;
        boolean isDigit = false;
        boolean isSignDigit = false;
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
            if (currentAndNextChar.contains("$$")) { //comment
                while (i != line.length() - 1) {
                    i++;
                }
                continue;
            }
            if (charAt == '*' & "*`".equals(currentAndNextChar) & comment) {
                comment = false;
                continue;
            }
            if (comment) {
                continue;
            }
            if (charAt == '`' & "`*".equals(currentAndNextChar)) {
                comment = true;
                continue;
            }
            if ((Character.isAlphabetic(charAt) || charAt == '_' || charAt == '$')) {
                if (currentAndNextChar.contains("0x")) {
                    IsAddressStarted = true;
                } else if (temp.contains(".")) {
                    if (isDigit & (Character.isAlphabetic(charAt) || charAt == '_' || charAt == '$')) {
                        temp += charAt;
                    } else {
                        words.add(temp);
                        temp = "";
                        temp += charAt;
                    }
                } else {
                    temp += charAt;
                }
            } else if (Character.isDigit(charAt)) {
                if (currentAndNextChar.contains("0x")) {
                    IsAddressStarted = true;
                }
                if (temp.contains(".")) {
                    if (Character.isDigit(temp.charAt(0)) || Character.isDigit(charAt) & !IsAddressStarted) {
                        temp += charAt;
                        isDigit = true;
                    } else {
                        words.add(temp);
                        temp = "";
                        IsPointStart = 0;
                        temp += charAt;
                    }
                } else {
                    temp += charAt;
                }
            } else if (charAt == '.' & i != line.length() - 1) {
                isDigit = false;
                if (Factory.isIdentifier(temp)) {
                    words.add(temp);
                    temp = "";
                }
                if (i == 0 & (Character.isAlphabetic(line.charAt(i + 1)))) {
                    temp += charAt;
                    words.add(temp);
                    temp = "";
                    temp += charAt;
                } else if (i == 0 & (Character.isDigit(line.charAt(i + 1)))) {
                    temp += charAt;
                } else if ((i == 0 || line.charAt(i + 1) == '.') & IsPointStart > 1) {
                    temp += charAt;
                    words.add(temp);
                    temp = "";
                } else if ((i == 0 || line.charAt(i + 1) == '.') & IsPointStart == 0) {
                    if (!temp.isEmpty()) {
                        words.add(temp);
                        temp = "";
                        temp += charAt;
                    } else {
                        temp += charAt;
                        words.add(temp);
                        temp = "";
                    }
                } else if (IsAddressStarted) {
                    words.add(temp);
                    temp = "";
                    IsAddressStarted = false;
                    temp += charAt;
                } else if (Character.isAlphabetic(line.charAt(i - 1)) || Character.isAlphabetic(line.charAt(i + 1))) {
                    if (!temp.isEmpty()) {
                        words.add(temp);
                        temp = "";
                        temp += charAt;
                    } else {
                        temp += charAt;
                    }
                } else if (Character.isDigit(line.charAt(i - 1)) || Character.isDigit(line.charAt(i + 1))) {
                    if (IsPointStart > 1) {
                        temp += charAt;
                        words.add(temp);
                        temp = "";

                    } else {
                        if (charAt == '.' & !temp.isEmpty()) {
                            if (Character.isDigit(line.charAt(i + 1)) & (Factory.isUnsignedInteger(temp) || Factory.isSignedInteger(temp))) {
                                temp += charAt;
                            } else {
                                words.add(temp);
                                temp = "";
                                temp += charAt;
                            }

                        } else {
                            temp += charAt;
                            IsPointStart++;
                        }
                    }
                }
            } else if (charAt == '"' & !IsStringStarted & !IsCharStarted) {
                if (!"".equals(temp)) {
                    words.add(temp);
                    temp = "";
                }
                IsStringStarted = true;
                temp += charAt;
            } else if (IsStringStarted & charAt == '\\' & !IsCharStarted) {
                temp += charAt;
                if (i + 1 < line.length()) {
                    char nextChar = line.charAt(i + 1);
                    temp += nextChar;
//                    if (!Factory.EscapeCharacters.contains(nextChar)) {
//                        temp += nextChar;
//                    }

                    i += 1;
                }
            } else if (IsStringStarted & charAt == '"' & !IsCharStarted) {
//                if (line.charAt(i - 1) == '\\' & line.charAt(i - 2) == '\\') {
//                    temp += charAt;
//                    words.add(temp);
//                    temp = "";
//                    IsStringStarted = false;
//
//                } else {
//                    temp += charAt;
//                }
                temp += charAt;
                words.add(temp);
                temp = "";
                IsStringStarted = false;
            } else if (IsStringStarted & charAt != '"' & !IsCharStarted) {
                temp += charAt;
            } else if (charAt == '\'' & !IsCharStarted & !IsStringStarted) {
                if (!temp.equals("")) {
                    words.add(temp);
                    temp = "";
                }
                temp += charAt;
                if (i + 1 < line.length()) {
                    if (line.charAt(i + 1) == '\\') {
                        temp += line.charAt(i + 1);
                        if (i + 2 < line.length()) {
                            temp += line.charAt(i + 2);
                            if (i + 3 < line.length()) {
                                temp += line.charAt(i + 3);
                            }
                        }
                        i = i + 3;
                    } else {
                        temp += line.charAt(i + 1);

                        if (i + 2 < line.length()) {
                            temp += line.charAt(i + 2);
                            i = i + 2;
                        } else {
                            i = i + 1;
                        }
                    }
                    words.add(temp);
                    temp = "";

                }
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

                    if ((charAt == '+' || charAt == '-') & !isSignDigit) {

                        if (i != line.length() - 1) {
                            if (i > 0) {
                                if (Character.isDigit(line.charAt(i - 1)) & Character.isDigit(line.charAt(i + 1))) {
                                    words.add(temp);
                                    temp = "";
                                    temp += charAt;
                                    words.add(temp);
                                    temp = "";

                                } else if (Character.isAlphabetic(line.charAt(i - 1)) & Character.isAlphabetic(line.charAt(i + 1))) {
                                    words.add(temp);
                                    temp = "";
                                    temp += charAt;
                                    words.add(temp);
                                    temp = "";
                                } else if (line.charAt(i - 1) == ' ' & Character.isDigit(line.charAt(i + 1))) {
                                    temp += charAt;
                                } else if (Character.isAlphabetic(line.charAt(i - 1)) & Character.isDigit(line.charAt(i + 1))) {
                                    words.add(temp);
                                    temp = "";
                                    temp += charAt;
                                    words.add(temp);
                                    temp = "";
                                } else if ((line.charAt(i - 1) == '/' || line.charAt(i - 1) == '*' || line.charAt(i - 1) == '+' || line.charAt(i - 1) == '-' || line.charAt(i - 1) == '%') & !Character.isDigit(i + 1)) {
                                    if (temp.isEmpty()) {
                                        temp += charAt;
                                    } else {
                                        words.add(temp);
                                        temp = "";
                                        temp += charAt;
                                    }
                                } else {
                                    if (!temp.isEmpty()) {
                                        words.add(temp);
                                        temp = "";
                                        temp += charAt;

                                    } else {
                                        temp += charAt;
                                        words.add(temp);
                                        temp = "";
                                    }
                                }
                            } else if (Character.isDigit(line.charAt(i + 1))) {
                                isSignDigit = true;
                                temp += charAt;
                            } else {
                                if (!temp.isEmpty()) {
                                    words.add(temp);
                                    temp = "";
                                    temp += charAt;

                                } else {
                                    temp += charAt;
                                    words.add(temp);
                                    temp = "";

                                }
                            }
                        }
//                    } else if (charAt == '+' || charAt == '-') {
//                        if (!temp.isEmpty()) {
//                            words.add(temp);
//                            temp = "";
//                            temp += charAt;
//                        }
                    } else if (charAt == ';') {
                        if (!temp.isEmpty()) {
                            words.add(temp);
                            temp = "";
                            temp += charAt;
                            words.add(temp);
                            temp = "";
                        } else {
                            temp += charAt;
                            words.add(temp);
                            temp = "";
                        }
                    } else if (charAt == ' ' || Punctuators.contains(Character.toString(charAt)) || Operators.contains(Character.toString(charAt))) {
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
}
