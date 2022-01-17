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

    public void MainTable_Entry(String name, String type, StringBuilder Parent, ArrayList<ClassTable> classTable) throws Exception {
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

    public void MainTable_Entry_For_Function(String name, String type, String stateMutability, ArrayList<FunctionTable> oFunctionTable, boolean isFunction) throws Exception {
        boolean response = true;
        for (MainTable item : oMainTableList) {
            if (item.getName().equals(name) && item.getType().equals(type)) {
                response = false;
                break;
            }
        }
        if (!response) {
            throw new Exception("Function Redeclaration Error -> " + name + " function is already declared!");
        }
        oMainTableList.add(new MainTable(name, type, stateMutability, isFunction, oFunctionTable));
        System.out.println(name + " " + type + " " + stateMutability);
        response = true;
    }

//    public void MainTable_Entry(String name, String type, String stateMutability, ArrayList<FunctionTable> oFunctionTableList, boolean isFunction) throws Exception {
//        boolean response = true;
//        for (MainTable item : oMainTableList) {
//            if (item.getName().equals(name) && item.getType().equals(type)) {
//                response = false;
//                break;
//            }
//        }
//        if (!response) {
//            throw new Exception("Function Redeclaration Error -> " + name + " function is already declared!");
//        }
//        oMainTableList.add(new MainTable(name, type, stateMutability, isFunction, oFunctionTableList));
//        System.out.println(name + " " + type + " " + stateMutability);
//        response = true;
//    }
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
        System.out.println(name + " " + type + " " + scopeCount);
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
    
//    public ClassTable lookUp_ClassTable(StringBuilder type){
//        ClassTable oClassTableList = new ClassTable();
//          for (ClassTable item : oClassTableList) {
//            if (item.getName().equals(type)) {
//                oFunctionTable = item;
//                break;
//            }
//        }
//        return false;
//    }

    public boolean LookUp_ClassTable(ArrayList<ClassTable> oClassTableList, String name) {
        for (ClassTable oClassTable : oClassTableList) {
            if (oClassTable.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean LookUp_ClassTable_For_Function(ArrayList<ClassTable> oClassTableList, String name, String type) {
        for (ClassTable oClassTable : oClassTableList) {
            if (oClassTable.getName().equals(name) && oClassTable.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public StringBuilder compatibilityCheckForUnaray(StringBuilder type, String operator) {
        StringBuilder typeReturn = new StringBuilder("");
        if (operator.equals("++") || operator.equals("--")) {
            if (type.equals("Character")) {
                typeReturn.append("Character");
                return typeReturn;
            } else if (type.equals("SignedPoint")) {
                typeReturn.append("SignedPoint");
                return typeReturn;
            } else if (type.equals("UnsignedPoint")) {
                typeReturn.append("UnsignedPoint");
                return typeReturn;
            } else if (type.equals("UnSignedInteger")) {
                typeReturn.append("UnSignedInteger");
                return typeReturn;
            } else if (type.equals("SignedInteger")) {
                typeReturn.append("SignedInteger");
                return typeReturn;
            }
        } else if (operator.equals("!")) {
            if (type.equals("true") || type.equals("false")) {
                typeReturn.append("boolean");
                return typeReturn;
            }
        }
        return null;
    }

    public StringBuilder compatibilityCheck(StringBuilder leftType, StringBuilder rightType, String operator) {

        if (operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/") || operator.equals("%")) {
            if (leftType.equals("SignedInteger") && rightType.equals("SignedInteger")) {
                StringBuilder type = new StringBuilder("SignedInteger");
                return type;
            }
        }
        return null;
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
    private StringBuilder Parent;
    private String stateMutability;
    private ArrayList<ClassTable> classTable;
    private ArrayList<FunctionTable> oFunctionTable;

    public ArrayList<FunctionTable> getoFunctionTable() {
        return oFunctionTable;
    }

    public String getStateMutability() {
        return stateMutability;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public StringBuilder getParent() {
        return Parent;
    }

    public ArrayList<ClassTable> getClassTable() {
        return classTable;
    }

    public MainTable() {

    }

    public MainTable(String name, String type, StringBuilder Parent, ArrayList<ClassTable> classTable) {
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

    public MainTable(String name, String type, String stateMutability, boolean isFunction, ArrayList<FunctionTable> oFunctionTable) {
        this.name = name;
        this.type = type;
        this.stateMutability = stateMutability;
        if (isFunction) {
            this.oFunctionTable = oFunctionTable;
        }
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
    private ArrayList<ClassTable> oClassTableList;
    private ArrayList<FunctionTable> oFunctionTableList;
    
    public ClassTable(){}

    public ClassTable(String name, String type, String Access_Modifier, String Type_Modifier) {
        this.name = name;
        this.type = type;
        this.accessModifier = Access_Modifier;
        this.typeModifier = Type_Modifier;
    }

    public ClassTable(String name, String type, String Access_Modifier, String Type_Modifier, ArrayList<ClassTable> oClassTableList) {
        this.name = name;
        this.type = type;
        this.accessModifier = Access_Modifier;
        this.typeModifier = Type_Modifier;
        this.oClassTableList = oClassTableList;
    }

    public ClassTable(String name, String type, String Access_Modifier, String Type_Modifier, ArrayList<FunctionTable> oFunctionTableList, boolean isFunction) {
        this.name = name;
        this.type = type;
        this.accessModifier = Access_Modifier;
        this.typeModifier = Type_Modifier;
        if (isFunction) {
            this.oFunctionTableList = oFunctionTableList;
        }
    }

    public ClassTable(String name, String type, String Access_Modifier, String Type_Modifier, ArrayList<ClassTable> oClassTableList, ArrayList<FunctionTable> oFunctionTableList) {
        this.name = name;
        this.type = type;
        this.accessModifier = Access_Modifier;
        this.typeModifier = Type_Modifier;
        this.oClassTableList = oClassTableList;
        this.oFunctionTableList = oFunctionTableList;
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
