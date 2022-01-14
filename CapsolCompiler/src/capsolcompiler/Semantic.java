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

    public void MainTable_Entry(String name, String type, String Parent, ArrayList<ClassTable> classTable) throws Exception {
        boolean response = true;
        for (MainTable item : oMainTableList) {
            if (item.getName().equals(name)) {
                response = false;
                break;
            }
        }
        if (!response) {
            throw new Exception("Contract Redeclaration Error -> " + name + " contract is already declared!");
        }
        oMainTableList.add(new MainTable(name, type, Parent, classTable));
        System.out.println(name + " " + type + " " + Parent);
        response = true;

    }

    public void MainTable_Entry(String name, String type, ArrayList<ClassTable> classTable) throws Exception {
        boolean response = true;
        for (MainTable item : oMainTableList) {
            if (item.getName().equals(name)) {
                response = false;
                break;
            }
        }
        if (!response) {
            throw new Exception("Redeclaration Error -> " + name + " is already declared!");
        }
        oMainTableList.add(new MainTable(name, type, classTable));
        System.out.println(name + " " + type);
        response = true;

    }

    public void MainTable_Entry(String name, String type, String stateMutability) throws Exception {
        boolean response = true;
        for (MainTable item : oMainTableList) {
            if (item.getName().equals(name) & item.getType().equals(type)) {
                response = false;
                break;
            }
        }
        if (!response) {
            throw new Exception("Function Redeclaration Error -> " + name + " function is already declared!");
        }
        oMainTableList.add(new MainTable(name, type, stateMutability));
        System.out.println(name + " " + type + " " + stateMutability);
        response = true;
    }

    public void MainTable_Entry(String name, String type) throws Exception {
        boolean response = true;
        for (MainTable item : oMainTableList) {
            if (item.getName().equals(name)) {
                response = false;
                break;
            }
        }
        if (!response) {
            throw new Exception("Redeclaration Error -> " + name + " is already declared!");
        }
        oMainTableList.add(new MainTable(name, type));
        System.out.println(name + " " + type);
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

    public boolean LookUp_ClassTable(ArrayList<ClassTable> oClassTableList, String name){
        for (ClassTable oClassTable : oClassTableList) {
            if(oClassTable.getName().equals(name))
                return true;
        }
        return false;
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
    private String type;
    private String Parent;
    private String stateMutability;
    private ArrayList<ClassTable> classTable;

    public String getStateMutability() {
        return stateMutability;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getParent() {
        return Parent;
    }

    public ArrayList<ClassTable> getClassTable() {
        return classTable;
    }

    public MainTable() {

    }

    public MainTable(String name, String type, String Parent, ArrayList<ClassTable> classTable) {
        this.name = name;
        this.type = type;
        this.Parent = Parent;
        this.classTable = classTable;
    }

    public MainTable(String name, String type, ArrayList<ClassTable> classTable) {
        this.name = name;
        this.type = type;
        this.classTable = classTable;
    }

    public MainTable(String name, String type, String stateMutability) {
        this.name = name;
        this.type = type;
        this.stateMutability = stateMutability;
    }

    public MainTable(String name, String type) {
        this.name = name;
        this.type = type;
    }
}

class ClassTable {

    private String name;
    private String type;
    private String accessModifier;
    private String typeModifier;

    public ClassTable(String name, String type, String Access_Modifier, String Type_Modifier) {
        this.name = name;
        this.type = type;
        this.accessModifier = Access_Modifier;
        this.typeModifier = Type_Modifier;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAccess_Modifier() {
        return accessModifier;
    }

    public String getType_Modifier() {
        return typeModifier;
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
