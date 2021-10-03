package cc;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author Hazique
 */
public class Factory {

    public String ClassName;
    public static ArrayList<String> KeyWords = new ArrayList<>();
    public static ArrayList<String> Punctuators = new ArrayList<>();
    public static ArrayList<String> Operators = new ArrayList<>();
    public static ArrayList<Factory> oFactories = new ArrayList<>();
    private Matcher match;
    private Pattern identifier;
    private Pattern FloatingPointNumber;
    private Pattern character;
    private Pattern String;
    private Pattern unSignedInteger;
    private Pattern signedInteger; 
    private Pattern hexNumber;
    private Pattern address;

    public void Initialize() {
        Factory oFactory = new Factory();
        oFactory.ClassName = "Address";
        ArrayList<String> KeyWords = new ArrayList<>();
        KeyWords.add("address");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "String";
        KeyWords = new ArrayList<>();
        KeyWords.add("string");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "UnsignedInteger";
        KeyWords = new ArrayList<>();
        KeyWords.add("uint");
        KeyWords.add("alpha");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "SignedInteger";
        KeyWords = new ArrayList<>();
        KeyWords.add("int");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "FloatingPointNumber";
        KeyWords = new ArrayList<>();
        KeyWords.add("point");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Enum";
        KeyWords = new ArrayList<>();
        KeyWords.add("enum");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "AccessModifier";
        KeyWords = new ArrayList<>();
        KeyWords.add("public");
        KeyWords.add("private");
        KeyWords.add("internal");
        KeyWords.add("external");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "State";
        KeyWords = new ArrayList<>();
        KeyWords.add("view");
        KeyWords.add("pure");
        KeyWords.add("payable");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "thisOrSuper";
        KeyWords = new ArrayList<>();
        KeyWords.add("this");
        KeyWords.add("super");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Is";
        KeyWords = new ArrayList<>();
        KeyWords.add("is");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Abstract";
        KeyWords = new ArrayList<>();
        KeyWords.add("abstract");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Interface";
        KeyWords = new ArrayList<>();
        KeyWords.add("interface");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Constructor";
        KeyWords = new ArrayList<>();
        KeyWords.add("constructor");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Return";
        KeyWords = new ArrayList<>();
        KeyWords.add("return");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Returns";
        KeyWords = new ArrayList<>();
        KeyWords.add("returns");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "New";
        KeyWords = new ArrayList<>();
        KeyWords.add("new");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Import";
        KeyWords = new ArrayList<>();
        KeyWords.add("import");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Using";
        KeyWords = new ArrayList<>();
        KeyWords.add("using");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Override";
        KeyWords = new ArrayList<>();
        KeyWords.add("override");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "assertOrRequire";
        KeyWords = new ArrayList<>();
        KeyWords.add("assert");
        KeyWords.add("require");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Revert";
        KeyWords = new ArrayList<>();
        KeyWords.add("revert");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Class";
        KeyWords = new ArrayList<>();
        KeyWords.add("contract");
        KeyWords.add("library");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "From";
        KeyWords = new ArrayList<>();
        KeyWords.add("from");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Do";
        KeyWords = new ArrayList<>();
        KeyWords.add("do");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "While";
        KeyWords = new ArrayList<>();
        KeyWords.add("while");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "If";
        KeyWords = new ArrayList<>();
        KeyWords.add("if");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Else";
        KeyWords = new ArrayList<>();
        KeyWords.add("else");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "For";
        KeyWords = new ArrayList<>();
        KeyWords.add("for");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "trueOrFalse";
        KeyWords = new ArrayList<>();
        KeyWords.add("true");
        KeyWords.add("false");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Control";
        KeyWords = new ArrayList<>();
        KeyWords.add("break");
        KeyWords.add("continue");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Capsol";
        KeyWords = new ArrayList<>();
        KeyWords.add("capsol");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Version";
        KeyWords = new ArrayList<>();
        KeyWords.add("version");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Mapping";
        KeyWords = new ArrayList<>();
        KeyWords.add("mapping");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Constant";
        KeyWords = new ArrayList<>();
        KeyWords.add("constant");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Modifier";
        KeyWords = new ArrayList<>();
        KeyWords.add("modifier");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Null";
        KeyWords = new ArrayList<>();
        KeyWords.add("null");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Store";
        KeyWords = new ArrayList<>();
        KeyWords.add("memory");
        KeyWords.add("storage");
        KeyWords.add("calldata");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Event";
        KeyWords = new ArrayList<>();
        KeyWords.add("event");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Emit";
        KeyWords = new ArrayList<>();
        KeyWords.add("emit");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Indexed";
        KeyWords = new ArrayList<>();
        KeyWords.add("indexed");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

//        Punctuators
        Punctuators.add("{");
        Punctuators.add("}");
        Punctuators.add("(");
        Punctuators.add(")");
        Punctuators.add("[");
        Punctuators.add("]");
        Punctuators.add(":");
        Punctuators.add(";");
        Punctuators.add(",");
        Punctuators.add(".");
        Punctuators.add("=>");
        Punctuators.add("?");
        

//        Operators

        Operators.add("+");
        Operators.add("-");
        Operators.add("*");
        Operators.add("/");
        Operators.add("%");
        Operators.add("<");
        Operators.add(">");
        Operators.add("<=");
        Operators.add(">=");
        Operators.add("!=");
        Operators.add("==");
        Operators.add("=");
        Operators.add("+=");
        Operators.add("-=");
        Operators.add("*=");
        Operators.add("/=");
        Operators.add("%=");
        Operators.add("--");
        Operators.add("++");
        Operators.add("**");
        Operators.add("&&");
        Operators.add("||");
        Operators.add("!");
        
        
//        int i = 1;
//        for (Factory oFactory2 : oFactories) {
//            System.out.println(oFactory2.ClassName);
//
//            oFactory2.KeyWords.forEach((KeyWord) -> {
//                System.out.println(KeyWord);
//            });
//            System.out.println("===============================" + i);
//            i++;
//        }
//        -----REGIX-----
//        Regix for Identifier
        identifier = Pattern.compile("([A-Za-z](_|\\$)?|_|\\$)([A-Za-z0-9]+(_|\\$)?)*");
//        Regix for FloatingPointNumber
        FloatingPointNumber = Pattern.compile("[+-]?[0-9]*.[0-9][0-9]*([Ee]([+-]?[0-9]*))?");
//        Regix for Address
        address = Pattern.compile("0x([a-fA-F0-9]{40})");
//        Regix for HexNumber
        hexNumber = Pattern.compile("([a-fA-F0-9]{40})");
//        Regix for SignedInteger
        signedInteger = Pattern.compile("[+-][0-9][0-9]*");
//        Regix for UnSignedInteger
        unSignedInteger = Pattern.compile("[0-9][0-9]*");
//        Regix for Character
        character = Pattern.compile("\'(\\\\[fbnrt0\\\\]|'|\"|/|[A-Za-z0-9])?\'");
//        Regix for String
        String = Pattern.compile("\"((\\\\[\'\"\\\\])|(\\\\[bfnrt0])|([\\!#-&\\(-/0-9:-@A-Z\\[\\]-`ac-eg-mo-qsu-z\\{-~])|([bnfrt0])|(\\s))*\"");

    }

    public boolean isIdentifier(String word) {
        match = identifier.matcher(word);
        return match.matches();
    }
    public boolean isFloatingPointNumber(String word) {
        match = FloatingPointNumber.matcher(word);
        return match.matches();
    }
    public boolean isAddress(String word) {
        match = address.matcher(word);
        return match.matches();
    }
    public boolean isHexNumber(String word) {
        match = hexNumber.matcher(word);
        return match.matches();
    }
    public boolean isSignedInteger(String word) {
        match = signedInteger.matcher(word);
        return match.matches();
    }
    public boolean isUnsignedInteger(String word) {
        match = unSignedInteger.matcher(word);
        return match.matches();
    }
    public boolean isCharacter(String word) {
        match = character.matcher(word);
        return match.matches();
    }
    public boolean isString(String word) {
        match = String.matcher(word);
        return match.matches();
    }
}
