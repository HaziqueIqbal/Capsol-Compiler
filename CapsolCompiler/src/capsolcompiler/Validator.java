package capsolcompiler;

import java.util.ArrayList;

/**
 *
 * @author Hazique
 */
public class Validator {

    public static ArrayList<Token> token = new ArrayList<>();
    public Token finalToken;

    public Validator() {
        boolean isKeyword;
        LexicalAnalyzer.tempTK.forEach((word) -> {
//            System.out.println("[" + word.valuePart + "," + word.lineNumber + "]");
            if (Factory.Operators.contains(word.valuePart)) {
                finalToken = new Token("Operators", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.Punctuators.contains(word.valuePart)) {
                finalToken = new Token("Puntuators", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isAddress(word.valuePart)) {
                finalToken = new Token("Address", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isCharacter(word.valuePart)) {
                finalToken = new Token("Character", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isSignedFloatingPointNumber(word.valuePart)) {
                finalToken = new Token("SignedPoint", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isUnSignedFloatingPointNumber(word.valuePart)) {
                finalToken = new Token("UnsignedPoint", word.valuePart, word.lineNumber);
                token.add(finalToken);
            } else if (Factory.isIdentifier(word.valuePart)) {
                Factory.oFactories.forEach((store) -> {
                    if (store.equals(word.valuePart)) {
                        finalToken = new Token(store.ClassName, word.valuePart, word.lineNumber);
                        token.add(finalToken);
                    }
                });
            } else {
                finalToken = new Token("Invalid Token", word.valuePart, word.lineNumber);
                token.add(finalToken);
            }
        });

        token.forEach((word) -> {
            System.out.println("[" + word.classPart + ", " + word.valuePart + ", " + word.lineNumber + "]");
        });
    }
}
