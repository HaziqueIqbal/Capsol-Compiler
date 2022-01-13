package capsolcompiler;

import java.util.ArrayList;

/**
 *
 * @author Hazique
 */
public class Semantic {

    public static ArrayList<MainTable> oMainTableList = new ArrayList<>();
    public static ArrayList<FunctionTable> oFunctionTableList = new ArrayList<>();
    public static int scopeCount = 0;

    public void MainTable_Entry(String name, String Parent, ArrayList<classTable> classTable) throws Exception {
        boolean response = true;
        for (MainTable item : oMainTableList) {
            if (item.getName().equals(name)) {
                response = false;
                break;
            }
        }
        if(!response){
            throw new Exception("Contract Redeclaration Error -> " + name + " contract is already declared!");
        }
        oMainTableList.add(new MainTable(name,Parent, classTable));
        response = true;

        
    }

    public MainTable LookUp_MainTable(String name) {
        MainTable oMainTable = new MainTable();
        for (MainTable item : oMainTableList) {
            if (item.getName().equals(name)) {
                oMainTable = item;
                break;
            }
        }
        return oMainTable;
    }

    public boolean FunctionTable_Entry(String name, String type, int scopeCount) {
        boolean response = false;
        for (FunctionTable item : oFunctionTableList) {
            if (item.getName().equals(name) && item.getScope() == scopeCount) {
                response = false;
                return response;
            }
        }

        oFunctionTableList.add(new FunctionTable(name, type, scopeCount));
        response = true;

        return response;
    }

    public FunctionTable LookUp_FunctionTable(String name) {
        FunctionTable oFunctionTable = new FunctionTable();
        for (FunctionTable item : oFunctionTableList) {
            if (item.getName().equals(name)) {
                oFunctionTable = item;
                break;
            }
        }
        return oFunctionTable;
    }

    public boolean compatibilityCheckForUnaray(String type, String operator) {
        if (operator.equals("++") || operator.equals("--")) {
            if (type.equals("Character") || type.equals("SignedPoint") || type.equals("UnsignedPoint")
                    || type.equals("UnSignedInteger") || type.equals("SignedInteger")) {
                return true;
            }
        }
        return false;
    }
    
    

    public void createScope() {
        scopeCount++;
    }

    public void destoryScope() {
        scopeCount--;
    }
}

class MainTable {

    private String name;
    private String Parent;
    private ArrayList<classTable> classTable;

    public String getName() {
        return name;
    }

    public String getParent() {
        return Parent;
    }

    public ArrayList<classTable> getClassTable() {
        return classTable;
    }

    public MainTable(String name, String Parent, ArrayList<classTable> classTable) {
        this.name = name;
        this.Parent = Parent;
        this.classTable = classTable;
    }

    public MainTable() {

    }

}

class classTable {

    private String name;
    private String type;
    private String Access_Modifier;
    private String Type_Modifier;

    public classTable(String name, String type, String Access_Modifier, String Type_Modifier) {
        this.name = name;
        this.type = type;
        this.Access_Modifier = Access_Modifier;
        this.Type_Modifier = Type_Modifier;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAccess_Modifier() {
        return Access_Modifier;
    }

    public String getType_Modifier() {
        return Type_Modifier;
    }

}

class FunctionTable {

    private String name;
    private String type;
    private int scope;

    public FunctionTable(String name, String type, int scope) {
        this.name = name;
        this.type = type;
        this.scope = scope;
    }

    public FunctionTable() {

    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getScope() {
        return scope;
    }

}
