package capsolcompiler;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Hazique
 */
public class Factory {

    public String ClassName;
    public ArrayList<String> KeyWords = new ArrayList<>();
    public static ArrayList<String> Punctuators = new ArrayList<>();
    public static ArrayList<String> Operators = new ArrayList<>();
    public static ArrayList<Factory> oFactories = new ArrayList<>();
    public static ArrayList<Factory> oOperators = new ArrayList<>();
    public static ArrayList<Character> EscapeCharacters = new ArrayList<>();
    public static ArrayList<String> OperatorsToken = new ArrayList<>();
    private static Matcher match;
    private static Pattern identifier;
    private static Pattern SignedFloatingPointNumber;
    private static Pattern UnSignedFloatingPointNumber;
    private static Pattern character;
    private static Pattern String;
    private static Pattern unSignedInteger;
    private static Pattern signedInteger;
    private static Pattern address;

    public void Initialize() {
        //KeyWords
        Factory oFactory = new Factory();
        oFactory.ClassName = "Address-Keyword";
        ArrayList<String> KeyWords = new ArrayList<>();
        KeyWords.add("address");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "String-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("string");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "UnsignedInteger-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("uint");
        KeyWords.add("alpha");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "SignedInteger-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("int");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "FloatingPointNumber-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("point");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Enum-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("enum");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);
        
         oFactory = new Factory();
        oFactory.ClassName = "Struct-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("struct");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "AccessModifier-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("public");
        KeyWords.add("private");
        KeyWords.add("internal");
        KeyWords.add("external");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "State-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("view");
        KeyWords.add("pure");
        KeyWords.add("payable");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "thisOrSuper-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("this");
        KeyWords.add("super");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Is-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("is");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Abstract-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("abstract");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Interface-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("interface");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Constructor-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("constructor");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Return-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("return");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Returns-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("returns");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "New-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("new");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Import-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("import");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Using-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("using");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Override-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("override");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "assertOrRequire-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("assert");
        KeyWords.add("require");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);
        
        oFactory = new Factory();
        oFactory.ClassName = "Function-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("function");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Revert-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("revert");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Class-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("contract");
        KeyWords.add("library");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "From-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("from");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Do-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("do");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "While-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("while");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "If-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("if");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Else-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("else");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "For-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("for");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "trueOrFalse-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("true");
        KeyWords.add("false");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Control-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("break");
        KeyWords.add("continue");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Capsol-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("capsol");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Version-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("version");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Mapping-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("mapping");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Constant-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("constant");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Modifier-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("modifier");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Null-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("null");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Store-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("memory");
        KeyWords.add("storage");
        KeyWords.add("calldata");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Event-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("event");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Emit-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("emit");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        oFactory = new Factory();
        oFactory.ClassName = "Indexed-Keyword";
        KeyWords = new ArrayList<>();
        KeyWords.add("indexed");
        oFactory.KeyWords = KeyWords;
        oFactories.add(oFactory);

        //Operators Token
        Factory oFactory1 = new Factory();
        oFactory1.ClassName = "Equal";
        ArrayList<String> _Operators = new ArrayList<>();
        _Operators.add("=");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);

        oFactory1 = new Factory();
        oFactory1.ClassName = "Relational";
        _Operators = new ArrayList<>();
        _Operators.add("<");
        _Operators.add(">");
        _Operators.add("<=");
        _Operators.add(">=");
        _Operators.add("!=");
        _Operators.add("==");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);

        oFactory1 = new Factory();
        oFactory1.ClassName = "Assignment";
        _Operators = new ArrayList<>();
        _Operators.add("+=");
        _Operators.add("-=");
        _Operators.add("/=");
        _Operators.add("*=");
        _Operators.add("%=");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);

        oFactory1 = new Factory();
        oFactory1.ClassName = "Increment/Decrement";
        _Operators = new ArrayList<>();
        _Operators.add("++");
        _Operators.add("--");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);

        oFactory1 = new Factory();
        oFactory1.ClassName = "Logical-1";
        _Operators = new ArrayList<>();
        _Operators.add("&&");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);

        oFactory1 = new Factory();
        oFactory1.ClassName = "Logical-2";
        _Operators = new ArrayList<>();
        _Operators.add("||");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);

        oFactory1 = new Factory();
        oFactory1.ClassName = "Logical-3";
        _Operators = new ArrayList<>();
        _Operators.add("!");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);

        oFactory1 = new Factory();
        oFactory1.ClassName = "Arithmetic-1";
        _Operators = new ArrayList<>();
        _Operators.add("+");
        _Operators.add("-");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);

        oFactory1 = new Factory();
        oFactory1.ClassName = "Arithmetic-2";
        _Operators = new ArrayList<>();
        _Operators.add("**");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);

        oFactory1 = new Factory();
        oFactory1.ClassName = "Arithmetic-3";
        _Operators = new ArrayList<>();
        _Operators.add("*");
        _Operators.add("/");
        _Operators.add("%");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);

        oFactory1 = new Factory();
        oFactory1.ClassName = "Bitwise-1";
        _Operators = new ArrayList<>();
        _Operators.add("~");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);

        oFactory1 = new Factory();
        oFactory1.ClassName = "Bitwise-2";
        _Operators = new ArrayList<>();
        _Operators.add("<<");
        _Operators.add(">>");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);

        oFactory1 = new Factory();
        oFactory1.ClassName = "Bitwise-3";
        _Operators = new ArrayList<>();
        _Operators.add("&");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);
        
        oFactory1 = new Factory();
        oFactory1.ClassName = "Bitwise-4";
        _Operators = new ArrayList<>();
        _Operators.add("^");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);
        
         oFactory1 = new Factory();
        oFactory1.ClassName = "Bitwise-5";
        _Operators = new ArrayList<>();
        _Operators.add("|");
        oFactory1.KeyWords = _Operators;
        oOperators.add(oFactory1);


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
        //Bitwise
        Operators.add("!");
        Operators.add("<<");
        Operators.add("~");
        Operators.add("&");
        Operators.add("^");
        Operators.add("|");
        //Escape Characters
        EscapeCharacters.add('r');
        EscapeCharacters.add('n');
        EscapeCharacters.add('a');
        EscapeCharacters.add('t');
        EscapeCharacters.add('b');

//        -----REGIX-----
//        Regix for Identifier
        identifier = Pattern.compile("([A-Za-z](_|\\$)?|_|\\$)([A-Za-z0-9]+(_|\\$)?)*");
//        Regix for FloatingPointNumber
        SignedFloatingPointNumber = Pattern.compile("[+-]\\d*\\.\\d+");
        // Regix for UnFloatingPointNumber
        UnSignedFloatingPointNumber = Pattern.compile("\\d*\\.\\d+");
//        Regix for Address
        address = Pattern.compile("0x([a-fA-F0-9]{40})");
//        Regix for SignedInteger
        signedInteger = Pattern.compile("[+-][0-9][0-9]*");
//        Regix for UnSignedInteger
        unSignedInteger = Pattern.compile("[0-9][0-9]*");
//        Regix for Character
        character = Pattern.compile("\'(\\\\[fbnrt0\\\\]|'|\"|/|[A-Za-z0-9]|[$&+,:;=?@#|'<>.-^*()%!])\'");
//        Regix for String
        String = Pattern.compile("\"((\\\\[\'\"\\\\])|(\\\\[bfnrt0])|([\\!#-&\\(-/0-9:-@A-Z\\[\\]-`ac-eg-mo-qsu-z\\{-~])|([bnfrt0])|(\\s))*\"");
    }

    public static boolean isIdentifier(String word) {
        match = identifier.matcher(word);
        return match.matches();
    }

    public static boolean isSignedFloatingPointNumber(String word) {
        match = SignedFloatingPointNumber.matcher(word);
        return match.matches();
    }

    public static boolean isUnSignedFloatingPointNumber(String word) {
        match = UnSignedFloatingPointNumber.matcher(word);
        return match.matches();
    }

    public static boolean isAddress(String word) {
        match = address.matcher(word);
        return match.matches();
    }

    public static boolean isSignedInteger(String word) {
        match = signedInteger.matcher(word);
        return match.matches();
    }

    public static boolean isUnsignedInteger(String word) {
        match = unSignedInteger.matcher(word);
        return match.matches();
    }

    public static boolean isCharacter(String word) {
        match = character.matcher(word);
        return match.matches();
    }

    public static boolean isString(String word) {
        match = String.matcher(word);
        return match.matches();
    }
}
