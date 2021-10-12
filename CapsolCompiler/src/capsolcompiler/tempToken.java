package capsolcompiler;

/**
 *
 * @author Hazique
 */
public class tempToken {

    public String valuePart;
    public int lineNumber;

    public tempToken( String valuePart, int lineNumber) {
        this.valuePart = valuePart;
        this.lineNumber = lineNumber;
    }

    public String getValuePart() {
        return valuePart;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setValuePart(String valuePart) {
        this.valuePart = valuePart;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
