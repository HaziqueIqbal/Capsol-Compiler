
package cc;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Hazique
 */
public class CC {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        new Factory().Initialize();
        new LexicalAnalyzer();
        
//        ArrayList<String> test = new ArrayList<String>();
//        test.add("=");
//        test.add(";");
//        System.out.print(test.contains('='));
    }
    
}
