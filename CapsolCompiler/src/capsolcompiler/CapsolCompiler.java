package capsolcompiler;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Hazique
 */
public class CapsolCompiler {

    public static void main(String[] args) throws FileNotFoundException, IOException{
         
//        String abc = "abc+=\\+abc-xabx+5\"dd";
//        System.out.println(abc);
        new Factory().Initialize();
        new LexicalAnalyzer();
        new Validator();
    }

}
