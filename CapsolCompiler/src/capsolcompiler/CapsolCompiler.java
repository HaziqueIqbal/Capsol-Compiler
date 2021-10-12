package capsolcompiler;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Hazique
 */
public class CapsolCompiler {

    public static void main(String[] args) throws FileNotFoundException, IOException{
        new Factory().Initialize();
        new LexicalAnalyzer();
        new Validator();
    }

}
