
package capsolcompiler;

/**
 *
 * @author Hazique
 */
public class Token {
    
    public String classPart;
    public String valuePart;
    public int lineNumber;

    public Token(String classPart, String valuePart, int lineNumber) {
        this.classPart = classPart;
        this.valuePart = valuePart;
        this.lineNumber = lineNumber;
    }

    public String getClassPart() {
        return classPart;
    }

    public String getValuePart() {
        return valuePart;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setClassPart(String classPart) {
        this.classPart = classPart;
    }

    public void setValuePart(String valuePart) {
        this.valuePart = valuePart;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
