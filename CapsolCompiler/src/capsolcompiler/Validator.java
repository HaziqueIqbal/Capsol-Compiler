package capsolcompiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hazique
 */
public class Validator {

    public static ArrayList<Token> token = new ArrayList<>();
    public Token finalToken;
    static boolean isKeyword = false;
    static boolean isOperatore = false;
    char c = '+';

    public Validator() throws FileNotFoundException, IOException {

        LexicalAnalyzer.tempTK.forEach((word) -> {
//            System.out.println("[" + word.valuePart + "," + word.lineNumber + "]");
            if (Factory.Operators.contains(word.valuePart)) {
                isOperatore = false;
                Factory.oOperators.forEach((op) -> {
                    if (op.KeyWords.contains(word.valuePart)) {
                        finalToken = new Token(op.ClassName, word.valuePart, word.lineNumber);
                        token.add(finalToken);
                        isOperatore = true;
                    }
                });
            } else if (Factory.Punctuators.contains(word.valuePart)) {
                finalToken = new Token("Puntuators", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isAddress(word.valuePart)) {
                finalToken = new Token("Address", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isCharacter(word.valuePart)) {
                word.valuePart = word.valuePart.substring(1, word.valuePart.length() - 1);
                finalToken = new Token("Character", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isSignedFloatingPointNumber(word.valuePart)) {
                finalToken = new Token("SignedPoint", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isUnSignedFloatingPointNumber(word.valuePart)) {
                finalToken = new Token("UnsignedPoint", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isUnsignedInteger(word.valuePart)) {
                finalToken = new Token("UnSignedInteger", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isSignedInteger(word.valuePart)) {
                finalToken = new Token("SignedInteger", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isString(word.valuePart)) {
                word.valuePart = word.valuePart.substring(1, word.valuePart.length() - 1);
                finalToken = new Token("String", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isIdentifier(word.valuePart)) {
                isKeyword = false;
                Factory.oFactories.forEach((store) -> {
//                    System.out.println(store.KeyWords);
                    if (store.KeyWords.contains(word.valuePart)) {
//                        System.out.println("true");
                        finalToken = new Token(store.ClassName, word.valuePart, word.lineNumber);
                        token.add(finalToken);
                        isKeyword = true;

                    }

                });
//                System.out.println("Keyword: " + isKeyword);
                if (!isKeyword) {
                    finalToken = new Token("Identifier", word.valuePart, word.lineNumber);
                    token.add(finalToken);
                }
            } else {
                finalToken = new Token("Invalid Token", word.valuePart, word.lineNumber);
                token.add(finalToken);
            }
        });

        File fout = new File("C:\\Users\\Hazique\\OneDrive\\Documents\\NetBeansProjects\\CapsolCompiler\\Output\\output.txt");
        FileOutputStream fos = new FileOutputStream(fout);

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            bw.write("[Class-Part, Value-Part, Line-Number]");
            bw.newLine();
            bw.write("<===================================>");
            bw.newLine();
            token.forEach((word) -> {
                try {
                    System.out.println("[" + word.classPart + ", " + word.valuePart + ", " + word.lineNumber + "]");
                    bw.write("[" + word.classPart + ", " + word.valuePart + ", " + word.lineNumber + "]");
                    bw.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        }

    }
}
