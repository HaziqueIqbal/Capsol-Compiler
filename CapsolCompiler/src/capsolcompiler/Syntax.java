package capsolcompiler;

import java.util.ArrayList;

/**
 *
 * @author Hazique
 */
public class Syntax {

    int index = 0;
    MainTable main;
    FunctionTable func;
    ArrayList<ClassTable> classTab;
    String name = "";
    String type = "";
    String Acc_Mod = "";
    MainTable parentChecking;
    static boolean flagSuper;

    static boolean check_for_function = false;

    Semantic sm = new Semantic();

    public Syntax() {

        if (Start()) {
            if (Validator.token.get(index).classPart.equals("End-Marker")) {
                System.out.println("Valid Syntax!");
                System.out.println("Compiled Successfully!");
            } else {
                System.out.println("Error at line no" + Validator.token.get(index).lineNumber);
                System.out.println("Invalid Syntax!");
            }
        } else {
            System.out.println("Error at line no" + Validator.token.get(index).lineNumber);
            System.out.println("Invalid Syntax!");
        }
    }

    final boolean Start() {
        if (Header()) {
            if (definitions()) {
                return true;
            }
        }
        return false;
    }

    boolean Header() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("capsol-keyword")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("version-keyword")) {
                index++;
                if (Validator.token.get(index).valuePart.equals("0.1")) {
                    index++;
                    if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                        index++;
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    boolean definitions() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("interface-keyword") // important -> done entry
                || Validator.token.get(index).classPart.toLowerCase().equals("class-keyword") // important -> done entry
                || Validator.token.get(index).classPart.toLowerCase().equals("abstract-keyword") // important -> done entry
                || Validator.token.get(index).classPart.toLowerCase().equals("library-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("import-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("struct-keyword") // important - if we have time left
                || Validator.token.get(index).classPart.toLowerCase().equals("enum-keyword") // important - if we have time left
                || Validator.token.get(index).classPart.toLowerCase().equals("function-keyword") // important
                || dataTypes()) // Important -> done entry but work on OE left
        {

            if (Validator.token.get(index).classPart.toLowerCase().equals("class-keyword")) {
                if (CB()) {
                    if (definitions()) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("interface-keyword")) {
                if (Interface()) {
                    if (definitions()) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("abstract-keyword")) {
                if (Abstract()) {
                    if (definitions()) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("library-keyword")) {
                if (Library(new ArrayList<ClassTable>(), "", null)) {
                    if (definitions()) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("import-keyword")) {
                if (Import()) {
                    if (definitions()) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("struct-keyword")) {
                if (Struct()) {
                    if (definitions()) {
                        return true;
                    }
                }
                return false;
            }

            if (dataTypes()) {
                if (Const_Var()) {
                    if (definitions()) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("enum-keyword")) {
                if (Enum()) {
                    if (definitions()) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("function-keyword")) {
                if (Function(1, null, "", null)) {
                    if (definitions()) {
                        return true;
                    }
                }
                return false;
            }

        }
        return true;
    }

    boolean CB() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("class-keyword")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                name = Validator.token.get(index).valuePart.toLowerCase();
                index++;
                StringBuilder Parent = new StringBuilder("");
                if (inheritance(Parent)) {
                    if (Validator.token.get(index).valuePart.equals("{")) {
                        classTab = new ArrayList<>();
                        index++;
                        sm.createScope();
                        if (CBody(classTab, name, Parent)) {
                            if (Validator.token.get(index).valuePart.equals("}")) {
                                try {
                                    if ("".equals(type)) {
                                        type = "class";
                                    }
                                    if (Parent.toString().equals("-")) {
                                        sm.MainTable_Entry(name, type, Parent, classTab);
                                    } else {
                                        StringBuilder errorIf = new StringBuilder("");
                                        StringBuilder doubleEntry = new StringBuilder("");
                                        String[] parms = Parent.toString().split(",");
                                        int count = parms.length;
                                        boolean flag = false;
                                        int checkFlag = 0;
                                        boolean undeclared = false;
                                        boolean flagForDoubleEntry = false;
                                        ArrayList<String> strageForDoubleEntry = new ArrayList<>();
                                        for (int i = 0; i < parms.length; i++) {
                                            parentChecking = sm.LookUp_MainTable(parms[i]);
                                            if (parentChecking.getName() != null) {
                                                if (strageForDoubleEntry.contains(parms[i])) {
                                                    flagForDoubleEntry = true;
                                                    doubleEntry.append(parms[i] + " ");
                                                }
                                            } else {
                                                undeclared = true;
                                                errorIf.append(parms[i] + " ");
                                            }
                                            checkFlag++;
                                            if (checkFlag == count) {
                                                flag = true;
                                            }
                                            strageForDoubleEntry.add(parms[i]);
                                        }
                                        if (flag & !flagForDoubleEntry & !undeclared) {
                                            sm.MainTable_Entry(name, type, Parent, classTab);
                                        } else {
                                            System.out.println("===============Error!===============");
                                            if (flagForDoubleEntry) {
                                                System.out.println("- " + doubleEntry + "is already inherited.");
                                            }
                                            if (undeclared) {
                                                System.out.println("- " + errorIf + "is not declared.");
                                            }
                                            System.out.println("====================================");
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                index++;
                                sm.destoryScope();
                                name = "";
                                type = "";
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean inheritance(StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("is-keyword")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                Parent.append(Validator.token.get(index).valuePart.toLowerCase());
                index++;
                if (I1(Parent)) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                Parent.append("-");
                return true;
            }
        }
        return false;
    }

    boolean I1(StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                Parent.append("," + Validator.token.get(index).valuePart.toLowerCase());
                index++;
                if (I1(Parent)) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                return true;
            }
        }
        return false;
    }

    boolean CBody(ArrayList<ClassTable> oClassTable, String itemName, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("modifier-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || Validator.token.get(index).classPart.toLowerCase().equals("struct-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("enum-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("function-keyword")
                || dataTypes()
                || Validator.token.get(index).classPart.toLowerCase().equals("event-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("using-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("mapping-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("constructor-keyword")) {
            if (Constructor(oClassTable, itemName, Parent)) {
                if (CBody1(oClassTable, itemName, Parent)) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                return true;
            }
        }
        return false;
    }

    boolean Constructor(ArrayList<ClassTable> oClassTable, String itemName, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("constructor-keyword")) {
            if (Constructor_Def(oClassTable, itemName, Parent)) {
                return true;
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("struct-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("enum-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("function-keyword")
                    || dataTypes()
                    || Validator.token.get(index).classPart.toLowerCase().equals("event-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("modifier-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                    || Validator.token.get(index).classPart.toLowerCase().equals("using-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("mapping-keyword")) {

                return true;
            }
        }
        return false;
    }

    boolean Constructor_Def(ArrayList<ClassTable> oClassTable, String itemName, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("constructor-keyword")) {
            String itemType = Validator.token.get(index).classPart;
            index++;
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                sm.createScope();
                StringBuilder pType = new StringBuilder("");
                StringBuilder pName = new StringBuilder("");
                if (PL_(pType, pName)) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                        index++;
                        StringBuilder accModifier = new StringBuilder("");
                        if (Con_1(accModifier)) {
                            if (Con_modifier(accModifier)) {
                                if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                                    index++;
                                    ArrayList<FunctionTable> oFunctionTable = new ArrayList<>();
                                    if (MST(oClassTable, oFunctionTable, itemName, Parent)) {
                                        if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                                            String[] params = pType.toString().split(",");
                                            String[] paramNames = pName.toString().split(",");
                                            for (int counter = 0; counter < params.length; counter++) {
                                                oFunctionTable.add(new FunctionTable(paramNames[counter], params[counter], Semantic.scopeCount));
                                            }
                                            oClassTable.add(new ClassTable(itemName, itemType + "->" + pType, accModifier.toString(), "-", oFunctionTable, true));
                                            sm.destoryScope();
                                            index++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        return false;
    }

    boolean PL(StringBuilder returnsCollection) {
        if (dataTypes() || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            if (dataTypes()) {
                returnsCollection.append(Validator.token.get(index).classPart.toLowerCase());
            } else {
                returnsCollection.append(Validator.token.get(index).valuePart);
            }
            index++;
            if (PL_1_(returnsCollection)) {
                return true;
            }
        }
        return false;
    }

    boolean PL_1_(StringBuilder returnsCollection) {
        if (Pl_S(returnsCollection)) {
            if (DataLocation(returnsCollection)) {
                if (PL_2_(returnsCollection)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean PL_2_(StringBuilder returnsCollection) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            returnsCollection.append(",");
            index++;
            if (PL(returnsCollection)) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean Con_modifier(StringBuilder accModifier) {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("public")
                || Validator.token.get(index).valuePart.toLowerCase().equals("internal")) {
            accModifier.append("," + Validator.token.get(index).valuePart);
            index++;
            return true;
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                return true;
            }
        }
        return true;
    }

    boolean Con_1(StringBuilder accModifier) {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("payable")) {
            index++;
            accModifier.append(Validator.token.get(index).valuePart);
            return true;
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("public")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("internal")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                return true;
            }
        }
        return false;
    }

    boolean MST(ArrayList<ClassTable> oClassTable, ArrayList<FunctionTable> oFunctionTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).valuePart.equals("if")
                || Validator.token.get(index).valuePart.equals("for")
                || Validator.token.get(index).valuePart.equals("while")
                || Validator.token.get(index).valuePart.equals("do")
                || Validator.token.get(index).valuePart.equals("mapping")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).valuePart.equals("++")
                || Validator.token.get(index).valuePart.equals("--")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).valuePart.equals("emit")
                || Validator.token.get(index).valuePart.equals("revert")
                || Validator.token.get(index).valuePart.equals("assert")
                || Validator.token.get(index).valuePart.equals("return")
                || Validator.token.get(index).valuePart.equals("continue")
                || Validator.token.get(index).valuePart.equals("break")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || dataTypes()) {
            if (SST(oClassTable, oFunctionTable, className, Parent)) {
                if (MST(oClassTable, oFunctionTable, className, Parent)) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.equals("}")) {
                return true;
            }
        }
        return false;
    }

    boolean DT_(ArrayList<ClassTable> oClassTable, ArrayList<FunctionTable> oFunctionTable, String className, StringBuilder Parent) {
        if (dataTypes()) {
            StringBuilder datatype = new StringBuilder(Validator.token.get(index).classPart);
            index++;
            if (DT__(oClassTable, oFunctionTable, className, Parent, datatype)) {
                return true;
            }
        }
        return false;
    }

    boolean DT__(ArrayList<ClassTable> oClassTable, ArrayList<FunctionTable> oFunctionTable, String className, StringBuilder Parent, StringBuilder getTypeTop) {
        if (Validator.token.get(index).valuePart.equals("memory")
                || Validator.token.get(index).valuePart.equals("storage")
                || Validator.token.get(index).valuePart.equals("calldate")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || Validator.token.get(index).valuePart.equals("[")) {
            if (DataLocation(new StringBuilder(""))) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    String name1 = Validator.token.get(index).valuePart;
                    index++;
                    StringBuilder getType = new StringBuilder("");
                    StringBuilder getOperator = new StringBuilder("");
                    if (VDS_1(oClassTable, className, Parent, getType, getOperator)) {
                        if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                            if ("=".equals(getOperator.toString())) {
                                StringBuilder check = sm.compatibilityCheck(getTypeTop, getType, getOperator.toString());
                                if (check != null) {
                                    FunctionTable f = new Semantic().LookUp_FunctionTable(oFunctionTable, name1);
                                    if (f == null) {
                                        oFunctionTable.add(new FunctionTable(name1, check.toString(), Semantic.scopeCount));
                                    } else {
                                        System.out.println("Redeclaration Error!");
                                    }
                                } else {
                                    System.out.println("Type Mismatch Error!");
                                    System.exit(0);
                                }
                            }
                            index++;
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).valuePart.equals("[")) {
                if (Array_SST()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean SST(ArrayList<ClassTable> oClassTable, ArrayList<FunctionTable> oFunctionTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).valuePart.equals("if")
                || Validator.token.get(index).valuePart.equals("for")
                || Validator.token.get(index).valuePart.equals("while")
                || Validator.token.get(index).valuePart.equals("do")
                || Validator.token.get(index).valuePart.equals("mapping")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).valuePart.equals("++")
                || Validator.token.get(index).valuePart.equals("--")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).valuePart.equals("emit")
                || Validator.token.get(index).valuePart.equals("revert")
                || Validator.token.get(index).valuePart.equals("assert")
                || Validator.token.get(index).valuePart.equals("return")
                || Validator.token.get(index).valuePart.equals("continue")
                || Validator.token.get(index).valuePart.equals("break")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || dataTypes()) {
            if (dataTypes()) {
                return DT_(oClassTable, oFunctionTable, className, Parent);
            }
            if (Validator.token.get(index).valuePart.equals("(")) {
                return Tuple();
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("mapping-keyword")) {
                return Mapping();
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                index++;
                if (ThisOrSuper(new StringBuilder(""))) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        index++;
                        if (Ref()) {
                            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                                index++;
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")) {
                if (ThisOrSuper(new StringBuilder(""))) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        index++;
                        if (X_sst("", null, null)) {
                            return true;
                        }
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                String setName = Validator.token.get(index).valuePart;
                index++;
                return Y(setName, oClassTable, oFunctionTable);
            }
            if (Validator.token.get(index).valuePart.equals("if")) {
                return If();
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("for")) {
                return For();
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("while")) {
                return WhileStatement(oClassTable, oFunctionTable, className, Parent);
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("do")) {
                return DoWhileStatement();
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("continue")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                    index++;
                    return true;
                }
                return false;
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("break")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                    index++;
                    return true;
                }
                return false;
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("return")) {
                return ReturnStatement();
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("revert")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("assert")) {
                index++;
                return Er_Statement();
            }
        }
        return false;
    }

    boolean FOR_X() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("dot")
                || Validator.token.get(index).valuePart.toLowerCase().equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("equal")
                || Validator.token.get(index).classPart.toLowerCase().equals("assignment")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (FOR_X()) {
                        return true;
                    }
                }
            } else if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (ArgList()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                        index++;
                        if (F_Opt2_1()) {
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                index++;
                if (OE(new StringBuilder(""), null, "", null)) { /////////////////////////////
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("]")) {
                        index++;
                        if (A_Opt_1()) {
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("equal")
                    || Validator.token.get(index).classPart.toLowerCase().equals("assignment")) {
                if (Operator(new StringBuilder(""))) {
                    if (OE(new StringBuilder(""), null, "", null)) { /////////////////////////////
                        return true;
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                index++;
                return true;
            }
        }
        return false;
    }

    boolean X_sst(String name, ArrayList<ClassTable> oClassTable, ArrayList<FunctionTable> oFunctionTable) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("dot")
                || Validator.token.get(index).valuePart.toLowerCase().equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("equal")
                || Validator.token.get(index).classPart.toLowerCase().equals("assignment")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
            FunctionTable lookUp = sm.LookUp_FunctionTable(oFunctionTable, name);
            if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (X_sst("", null, null)) {
                        return true;
                    }
                }
            } else if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (ArgList()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                        index++;
                        if (F_Opt2()) {
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                index++;
                if (OE(new StringBuilder(""), null, "", null)) {/////////////////////
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("]")) {
                        index++;
                        if (A_Opt()) {
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("equal")
                    || Validator.token.get(index).classPart.toLowerCase().equals("assignment")) {
                StringBuilder getOperator = new StringBuilder("");
                if (Operator(getOperator)) {
                    StringBuilder getType = new StringBuilder("");
                    if (OE(getType, oClassTable, "", null)) {
                        if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                            if (lookUp != null) {
                                String[] temp = lookUp.getType().split("\\(");
                                StringBuilder store = new StringBuilder(temp[0]);
                                StringBuilder getFinalType = sm.compatibilityCheck(store, getType, getOperator.toString());
                                if (getFinalType == null) {
                                    System.out.println("Type Mismatch Error!");
                                    System.exit(0);
                                }
                            } else {
                                System.out.println(name + " is not defined!");
                                System.exit(0);
                            }
                            index++;
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                    index++;
                    return true;
                }
            }
        }
        return false;
    }

    boolean A_Opt_1() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("dot")
                || Validator.token.get(index).classPart.toLowerCase().equals("equal")
                || Validator.token.get(index).classPart.toLowerCase().equals("assignment")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (FOR_X()) {
                        return true;
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("equal")
                    || Validator.token.get(index).classPart.toLowerCase().equals("assignment")) {
                if (Operator(new StringBuilder(""))) {
                    if (OE(new StringBuilder(""), null, "", null)) {///////////////
                        return true;
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                index++;
                return true;
            }
        }
        return false;
    }

    boolean A_Opt() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("dot")
                || Validator.token.get(index).classPart.toLowerCase().equals("equal")
                || Validator.token.get(index).classPart.toLowerCase().equals("assignment")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (X_sst("", null, null)) {
                        return true;
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("equal")
                    || Validator.token.get(index).classPart.toLowerCase().equals("assignment")) {
                if (Operator(new StringBuilder(""))) {
                    if (OE(new StringBuilder(""), null, "", null)) {////////////////////
                        if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                            index++;
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                    index++;
                    return true;
                }
            }
        }
        return false;
    }

    boolean F_Opt2_1() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (FOR_X()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean F_Opt2() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")
                || Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                index++;
                return true;
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (X_sst("", null, null)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean Y(String name, ArrayList<ClassTable> oClassTable, ArrayList<FunctionTable> oFunctionTable) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("dot")
                || Validator.token.get(index).valuePart.toLowerCase().equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("equal")
                || Validator.token.get(index).classPart.toLowerCase().equals("assignment")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || Validator.token.get(index).valuePart.toLowerCase().equals("[")
                || Validator.token.get(index).valuePart.equals("memory")
                || Validator.token.get(index).valuePart.equals("storage")
                || Validator.token.get(index).valuePart.equals("calldate")) {

            if (Validator.token.get(index).classPart.toLowerCase().equals("dot")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("(")
                    || Validator.token.get(index).classPart.toLowerCase().equals("equal")
                    || Validator.token.get(index).classPart.toLowerCase().equals("assignment")
                    || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("dot")
                        || Validator.token.get(index).valuePart.toLowerCase().equals("(")
                        || Validator.token.get(index).classPart.toLowerCase().equals("equal")
                        || Validator.token.get(index).classPart.toLowerCase().equals("assignment")
                        || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                    if (X_sst(name, oClassTable, oFunctionTable)) {
                        return true;
                    }
                } else if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                    if (Array_SST()) {
                        return true;
                    }
                }
            } else if (Validator.token.get(index).valuePart.equals("memory")
                    || Validator.token.get(index).valuePart.equals("storage")
                    || Validator.token.get(index).valuePart.equals("calldate")
                    || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                if (DataLocation(new StringBuilder(""))) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        index++;
                        if (Validator.token.get(index).valuePart.equals("=")) {
                            index++;
                            if (Validator.token.get(index).valuePart.equals("new")) {
                                index++;
                                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                                    index++;
                                    if (Validator.token.get(index).valuePart.equals("(")) {
                                        index++;
                                        if (ArgList()) {
                                            if (Validator.token.get(index).valuePart.equals(")")) {
                                                index++;
                                                if (Validator.token.get(index).valuePart.equals(";")) {
                                                    index++;
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        return false;
    }

    boolean Object_Dec() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            index++;
            if (Object()) {
                return true;
            }
        }
        return false;
    }

    boolean Object() {
        if (Validator.token.get(index).valuePart.equals(";") || Validator.token.get(index).valuePart.equals("=")) {
            if (Validator.token.get(index).valuePart.equals(";")) {
                index++;
                return true;
            } else if (Validator.token.get(index).valuePart.equals("=")) {
                index++;
                if (Validator.token.get(index).valuePart.equals("new")) {
                    index++;
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        index++;
                        if (Validator.token.get(index).valuePart.equals("(")) {
                            index++;
                            if (ArgList()) {
                                if (Validator.token.get(index).valuePart.equals(")")) {
                                    index++;
                                    if (Validator.token.get(index).valuePart.equals(";")) {
                                        index++;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean Cap_1(ArrayList<ClassTable> oTable, String itemName, String dataType) {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("[")
                || Validator.token.get(index).valuePart.toLowerCase().equals("public")
                || Validator.token.get(index).valuePart.toLowerCase().equals("private")
                || Validator.token.get(index).valuePart.toLowerCase().equals("internal")
                || Validator.token.get(index).valuePart.toLowerCase().equals("constant")
                || Validator.token.get(index).valuePart.toLowerCase().equals("override")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                if (Array_()) {
                    return true;
                }
            } else if (Validator.token.get(index).valuePart.toLowerCase().equals("public")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("private")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("internal")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("constant")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("override")
                    || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                if (state_Variable(oTable, itemName, dataType)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean Cap_2() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                if (Object_Dec()) {
                    return true;
                }
            } else if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                if (Array_()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean CBody1(ArrayList<ClassTable> oTable, String itemName, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("struct-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("enum-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("function-keyword")
                || dataTypes()
                || Validator.token.get(index).classPart.toLowerCase().equals("event-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("modifier-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || Validator.token.get(index).classPart.toLowerCase().equals("using-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("mapping-keyword")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("function-keyword")) {
                if (Function(2, oTable, itemName, Parent)) {
                    if (CBody1(oTable, itemName, Parent)) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("modifier-keyword")) {
                if (Modifier_Def()) {
                    if (CBody1(oTable, itemName, Parent)) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("struct-keyword")) {
                if (Struct()) {
                    if (CBody1(oTable, itemName, Parent)) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("enum-keyword")) {
                if (Enum()) {
                    if (CBody1(oTable, itemName, Parent)) {
                        return true;
                    }
                }
                return false;
            }
            if (dataTypes()) {
                String dataType = Validator.token.get(index).classPart;
                index++;
                if (Cap_1(oTable, itemName, dataType)) {
                    if (CBody1(oTable, itemName, Parent)) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("event-keyword")) {
                if (Event_Def()) {
                    if (CBody1(oTable, itemName, Parent)) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("mapping-keyword")) {
                if (Mapping_()) {
                    if (CBody1(oTable, itemName, Parent)) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Cap_2()) {
                    if (CBody1(oTable, itemName, Parent)) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("using-keyword")) {
                if (Using()) {
                    if (CBody1(oTable, itemName, Parent)) {
                        return true;
                    }
                }
                return false;
            }

        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                return true;
            }
        }
        return false;
    }

    boolean Interface() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("interface-keyword")) {
            type = "Interface";
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                name = Validator.token.get(index).valuePart.toLowerCase();
                index++;
                StringBuilder Parent = new StringBuilder("");
                if (inheritance(Parent)) {
                    if (Validator.token.get(index).valuePart.equals("{")) {
                        classTab = new ArrayList<>();
                        index++;
                        sm.createScope();
                        if (IBody(classTab, name, Parent)) {
                            if (Validator.token.get(index).valuePart.equals("}")) {
                                try {
                                    if (Parent.toString().equals("-")) {
                                        sm.MainTable_Entry(name, type, Parent, classTab);
                                    } else {
                                        StringBuilder errorIf = new StringBuilder("");
                                        StringBuilder doubleEntry = new StringBuilder("");
                                        StringBuilder errorTypeIf = new StringBuilder("");
                                        String[] parms = Parent.toString().split(",");
                                        int count = parms.length;
                                        boolean flag = false;
                                        int checkFlag = 0;
                                        boolean undeclared = false;
                                        boolean typeCheck = false;
                                        boolean flagForDoubleEntry = false;
                                        ArrayList<String> strageForDoubleEntry = new ArrayList<>();
                                        for (int i = 0; i < parms.length; i++) {
                                            parentChecking = sm.LookUp_MainTable(parms[i]);
                                            if (parentChecking.getName() != null && "Interface".equals(parentChecking.getType())) {
                                                if (strageForDoubleEntry.contains(parms[i])) {
                                                    flagForDoubleEntry = true;
                                                    doubleEntry.append(parms[i] + " ");
                                                }
                                            } else {
                                                if ("class".equals(parentChecking.getType()) || "Abstract".equals(parentChecking.getType())) {
                                                    errorTypeIf.append(parms[i] + " ");
                                                    typeCheck = true;
                                                } else {
                                                    undeclared = true;
                                                    errorIf.append(parms[i] + " ");
                                                }
                                            }
                                            checkFlag++;
                                            if (checkFlag == count) {
                                                flag = true;
                                            }
                                            strageForDoubleEntry.add(parms[i]);
                                        }
                                        if (flag & !flagForDoubleEntry & !undeclared & !typeCheck) {
                                            sm.MainTable_Entry(name, type, Parent, classTab);
                                        } else {
                                            System.out.println("===============Error!===============");
                                            if (flagForDoubleEntry) {
                                                System.out.println("- " + doubleEntry + "is already inherited.");
                                            }
                                            if (undeclared) {
                                                System.out.println("- " + errorIf + "is not declared.");
                                            }
                                            if (typeCheck) {
                                                System.out.println("- " + errorTypeIf + "can't be inherited from contract. Required Interface");
                                            }
                                            System.out.println("====================================");
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                index++;
                                sm.destoryScope();
                                name = "";
                                type = "";
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean IBody(ArrayList<ClassTable> oTable, String itemName, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("function-keyword")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("function-keyword")) {
                if (Function(3, oTable, itemName, Parent)) {
                    if (CBody1(oTable, itemName, Parent)) {
                        return true;
                    }
                }
            }
        }
        return true;
    }

    boolean Abstract() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("abstract-keyword")) {
            type = "Abstract";
            index++;
            if (CB()) {
                return true;
            }
        }
        return false;
    }

    boolean Library(ArrayList<ClassTable> oTable, String itemName, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("library-keyword")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                    index++;
                    if (CBody1(oTable, itemName, Parent)) {
                        if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean Import() {
        if (Validator.token.get(index).valuePart.equals("import")) {
            index++;
            if (X()) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                    index++;
                    return true;
                }

            }
        }
        return false;
    }

    boolean X() {
        if (Validator.token.get(index).valuePart.equals("*")) {
            index++;
            if (Validator.token.get(index).valuePart.equals("as")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (Validator.token.get(index).valuePart.equals("from")) {
                        index++;
                        if (Path()) {
                            return true;
                        }
                    }
                }
            }
        } else if (Path()) {
            if (X1()) {
                return true;
            }
        } else if (SymbolAlises()) {
            if (Validator.token.get(index).valuePart.equals("from")) {
                index++;
                if (Path()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean X1() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("as")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                return true;
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                return true;
            }
        }
        return false;
    }

    boolean Path() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("string")) {
            index++;
            return true;
        }
        return false;
    }

    boolean SymbolAlises() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
            index++;
            if (SL()) {
                if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                    index++;
                    return true;
                }
            }
        }
        return false;
    }

    boolean SL() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            index++;
            if (SD()) {
                return true;
            }
        }
        return false;
    }

    boolean SD() {
        if (X1()) {
            if (X2()) {
                return true;
            }
        }
        return false;
    }

    boolean X2() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            if (SL()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                return true;
            }
        }
        return false;
    }

    boolean Struct() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("struct-keyword")) {
            type = Validator.token.get(index).classPart;
            index++;
            if (Visibility(new StringBuilder(""))) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    name = Validator.token.get(index).valuePart;
                    index++;
                    if (Validator.token.get(index).valuePart.equals("{")) {
                        classTab = new ArrayList<>();

                        index++;
                        //as we know arrays and list are pass by refrence de default
                        if (StrBody(classTab)) {

                            if (Validator.token.get(index).valuePart.equals("}")) {
                                //we are inserting decalration into maintable after } because of speed optimization as we
                                //will not iterate over and over again on every struct member
                                try {
                                    new Semantic().MainTable_Entry(name, type, classTab);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                name = "";
                                type = "";
                                index++;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean Visibility(StringBuilder visibilityCheck) {
        if (Validator.token.get(index).valuePart.equals("public")
                || Validator.token.get(index).valuePart.equals("private")
                || Validator.token.get(index).valuePart.equals("external")
                || Validator.token.get(index).valuePart.equals("internal")) {
            visibilityCheck.append(Validator.token.get(index).valuePart);
            index++;
            return true;
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                    || Validator.token.get(index).classPart.toLowerCase().equals("state-keyword")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("{")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("virtual")
                    || Validator.token.get(index).classPart.toLowerCase().equals("override-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("returns-keyword")) {
                return true;
            }
        }
        return false;
    }

    boolean StrBody(ArrayList<ClassTable> oClassTable) {
        //we are using stringbuilder instead of string because we need pass by refrence functionality that is not
        //available in default strings as they are immutable
        StringBuilder itemType = new StringBuilder("");
        if (dataTypes()
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            if (dataTypes()) {
                itemType.append(Validator.token.get(index).classPart);
            } else {
                MainTable oMainTable = new Semantic().LookUp_MainTable(Validator.token.get(index).valuePart);
                if (oMainTable.getName() != null) {
                    if (!oMainTable.getName().equals("")) {
                        itemType.append(Validator.token.get(index).valuePart);
                    } else {
                        System.out.println("No Identifier found with the name: " + Validator.token.get(index).valuePart);
                    }
                } else {
                    System.out.println("No Identifier found with the name: " + Validator.token.get(index).valuePart);
                }

            }
            index++;
            if (Str_(oClassTable, itemType)) {
                return true;
            }
        }
        return false;
    }

    boolean Str_(ArrayList<ClassTable> oClassTable, StringBuilder itemType) {
        if (Pl_S(itemType)) {
            if (DataLocation(itemType)) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    String itemName = Validator.token.get(index).valuePart;
                    index++;
                    if (Str__(itemName, itemType, oClassTable)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean Str__(String itemName, StringBuilder itemType, ArrayList<ClassTable> oClassTable) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
            if (!new Semantic().LookUp_ClassTable(oClassTable, itemName)) {
                oClassTable.add(new ClassTable(itemName, itemType.toString(), "-", "-"));
            } else {
                System.out.println("Re-declaration error. Item with the same name as: " + itemName + " already declared.");
            }

            index++;
            if (Str__1(oClassTable)) {
                return true;
            }
        }
        return false;
    }

    boolean Str__1(ArrayList<ClassTable> oClassTable) {
        StringBuilder itemType = new StringBuilder("");
        if (dataTypes()
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            if (dataTypes()) {
                itemType.append(Validator.token.get(index).classPart);
            } else {
                MainTable oMainTable = new Semantic().LookUp_MainTable(Validator.token.get(index).valuePart);
                if (oMainTable.getName() != null) {
                    if (!oMainTable.getName().equals("")) {
                        itemType.append(Validator.token.get(index).valuePart);
                    } else {
                        System.out.println("No Identifier found with the name: " + Validator.token.get(index).valuePart);
                    }
                } else {
                    System.out.println("No Identifier found with the name: " + Validator.token.get(index).valuePart);
                }

            }
            index++;
            if (Str_(oClassTable, itemType)) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                return true;
            }
        }
        return false;
    }

    boolean Pl_S(StringBuilder itemType) {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
            itemType.append("[");
            index++;
            if (Size(itemType)) {
                if (Validator.token.get(index).valuePart.toLowerCase().equals("]")) {
                    itemType.append("]");
                    index++;
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("store-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                    || Validator.token.get(index).classPart.toLowerCase().equals("comma")
                    || Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean Size(StringBuilder itemType) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("unsignedinteger")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            itemType.append(Validator.token.get(index).valuePart.toLowerCase());
            index++;
            return true;
        } else {
            if (Validator.token.get(index).valuePart.equals("]")
                    || Validator.token.get(index).valuePart.equals(")")) {
                return true;
            }
        }
        return true;
    }

    boolean DataLocation(StringBuilder itemType) {
        if (Validator.token.get(index).valuePart.equals("memory")
                || Validator.token.get(index).valuePart.equals("storage")
                || Validator.token.get(index).valuePart.equals("calldata")) {
            itemType.append("(" + Validator.token.get(index).valuePart.toLowerCase() + ")");
            index++;
            return true;
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                    || Validator.token.get(index).classPart.toLowerCase().equals("comma")
                    || Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean dataTypes() {
        return Validator.token.get(index).classPart.toLowerCase().equals("address-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("string-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("unsignedinteger-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("signedinteger-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("character-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("floatingpointnumber-keyword");
    }

    boolean constants() {
        return Validator.token.get(index).classPart.toLowerCase().equals("address")
                || Validator.token.get(index).classPart.toLowerCase().equals("character")
                || Validator.token.get(index).classPart.toLowerCase().equals("signedpoint")
                || Validator.token.get(index).classPart.toLowerCase().equals("unsignedpoint")
                || Validator.token.get(index).classPart.toLowerCase().equals("signedinteger")
                || Validator.token.get(index).classPart.toLowerCase().equals("unsignedinteger")
                || Validator.token.get(index).classPart.toLowerCase().equals("string");
    }

    boolean Function(int check, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("function-keyword")) {
            String fType = Validator.token.get(index).classPart;
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                String fName = Validator.token.get(index).valuePart.toLowerCase();
                index++;
                if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                    index++;
                    sm.createScope();
                    StringBuilder pType = new StringBuilder("");
                    StringBuilder pName = new StringBuilder("");
                    if (PL_(pType, pName)) {
                        if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                            index++;
                            StringBuilder visibility = new StringBuilder("");
                            if (Visibility(visibility)) {
                                StringBuilder typeMod = new StringBuilder("");
                                if (State_Mutability(typeMod)) {
                                    //   if (Modifier_Invocation()) {  // implement in future
                                    if (Virtual(typeMod)) {
                                        if (Override_Specifier(typeMod)) {
                                            StringBuilder returnCollection = new StringBuilder("");
                                            if (Fn_1(returnCollection)) {
                                                if (Fn_2(fName, fType, pType, returnCollection, typeMod, visibility, oTable, check, pName, className, Parent)) {
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                    //  }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean PL_(StringBuilder pType, StringBuilder pName) {
        if (dataTypes() || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            if (dataTypes()) {
                pType.append(Validator.token.get(index).classPart);
            } else {
                pType.append(Validator.token.get(index).valuePart);
            }
            index++;
            if (PL_1(pType, pName)) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean PL_1(StringBuilder pType, StringBuilder pName) {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("[")
                || Validator.token.get(index).classPart.toLowerCase().equals("store-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            if (Pl_S(pType)) {
                if (DataLocation(pType)) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        pName.append(Validator.token.get(index).valuePart);
                        index++;
                        if (PL_2(pType, pName)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean PL_2(StringBuilder pType, StringBuilder pName) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            pType.append(",");
            pName.append(",");
            index++;
            if (PL_3(pType, pName)) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean PL_3(StringBuilder pType, StringBuilder pName) {
        if (dataTypes() || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            if (dataTypes()) {
                pType.append(Validator.token.get(index).classPart);
            } else {
                pType.append(Validator.token.get(index).valuePart);
            }
            index++;
            if (PL_1(pType, pName)) {
                return true;
            }
        }
        return false;
    }

    boolean State_Mutability(StringBuilder stateMutability) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("state-keyword")) {
            stateMutability.append(Validator.token.get(index).valuePart.toLowerCase());
            index++;
            return true;
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                    || Validator.token.get(index).classPart.toLowerCase().equals("override-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("returns-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("{")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("virtual")) {
                return true;
            }
        }
        return false;
    }

    boolean Modifier_Invocation() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            index++;
            if (Mod_Inv_1()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("override-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("returns-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("{")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("virtual")) {
                return true;
            }
        }
        return false;
    }

    boolean Mod_Inv_1() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
            index++;
            StringBuilder pType = new StringBuilder("");
            StringBuilder pName = new StringBuilder("");
            if (PL_(pType, pName)) {
                if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                    index++;
                    return true;
                }
            }
        }
        return false;
    }

    boolean Virtual(StringBuilder virtualCheck) {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("virtual")) {
            virtualCheck.append("," + Validator.token.get(index).valuePart.toLowerCase());
            index++;
            return true;
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("override-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("returns-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                return true;
            }
        }
        return false;
    }

    boolean Override_Specifier(StringBuilder overrideCheck) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("override-keyword")) {
            overrideCheck.append("- " + Validator.token.get(index).classPart.toLowerCase());
            index++;
            if (OV_1(overrideCheck)) {
                return true;
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("returns-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                return true;
            }
        }
        return false;
    }

    boolean OV_1(StringBuilder overrideCheck) {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                overrideCheck.append(" " + Validator.token.get(index).valuePart);
                index++;
                if (OV_2(overrideCheck)) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                        overrideCheck.append(Validator.token.get(index).valuePart);
                        index++;
                        return true;
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                overrideCheck.append(" " + Validator.token.get(index).valuePart);
                index++;
                return true;
            }
        }
        return false;
    }

    boolean OV_2(StringBuilder overrideCheck) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            overrideCheck.append(Validator.token.get(index).valuePart);
            index++;
            if (OV_3(overrideCheck)) {
                return true;
            }
        }
        return false;
    }

    boolean OV_3(StringBuilder overrideCheck) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            overrideCheck.append(Validator.token.get(index).valuePart);
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                overrideCheck.append(Validator.token.get(index).valuePart);
                index++;
                if (OV_3(overrideCheck)) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean Fn_1(StringBuilder returnsCollection) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("returns-keyword")) {
            index++;
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (PL(returnsCollection)) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                        index++;
                        return true;
                    }
                }
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                return true;
            }
        }
        return false;
    }

    boolean Fn_2(String fName, String fType, StringBuilder pType, StringBuilder returnsCollection, StringBuilder typeMod, StringBuilder visibility, ArrayList<ClassTable> oTable, int check, StringBuilder pName, String className, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")
                || Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon") && check == 3) {
                index++;
                return true;
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("{") && check != 3) {
                String[] params = pType.toString().split(",");
                String[] paramNames = pName.toString().split(",");
                ArrayList<FunctionTable> oFunctionTable = new ArrayList<>();
                for (int counter = 0; counter < params.length; counter++) {
                    oFunctionTable.add(new FunctionTable(paramNames[counter], params[counter], Semantic.scopeCount));
                }
                index++;
                if (MST(oTable, oFunctionTable, className, Parent)) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                        if (check == 1) {
                            try {
                                if ("".equals(returnsCollection.toString())) {
                                    sm.MainTable_Entry_For_Function(fName.toString(), pType.toString() + "", typeMod.toString(), oFunctionTable, true);
                                } else {
                                    sm.MainTable_Entry_For_Function(fName, pType + "->" + returnsCollection, typeMod.toString(), oFunctionTable, true);
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } else if (check == 2) {
                            if (!new Semantic().LookUp_ClassTable_For_Function(oTable, fName, pType.toString() + "->" + returnsCollection)) {
                                oTable.add(new ClassTable(fName, pType + "->" + returnsCollection, visibility.toString(), typeMod.toString(), oFunctionTable, true));
                            } else {
                                System.out.println("Function Redeclaration Error -> [:fnName] function is already declared!".replace("[:fnName]", fName));
                            }
                        }
                        index++;
                        sm.destoryScope();
                        return true;
                    }
                }
            } else {
                System.out.println("Interfaces can't contain body!");
            }

        }
        return false;
    }

    boolean Modifier_Def() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("modifier-keyword")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Mod_1()) {
                    if (Virtual(new StringBuilder(""))) {
                        if (Override_Specifier(new StringBuilder(""))) {
//                            if (Fn_2()) {
//                                return true; // left
//                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean Mod_1() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
            index++;
            if (Mod_2()) {
                if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                    index++;
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("virtual")
                    || Override_Specifier(new StringBuilder(""))) {
                return true;
            }
        }
        return false;
    }

    boolean Mod_2() {
        if (PL_(new StringBuilder(""), new StringBuilder(""))) {
            return true;
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean Enum() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("enum-keyword")) {
            type = Validator.token.get(index).classPart;
            index++;
            if (Visibility(new StringBuilder(""))) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    name = Validator.token.get(index).valuePart;
                    index++;
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                        classTab = new ArrayList<>();

                        index++;
                        if (Enum_1(classTab)) {

                            if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                                try {
                                    new Semantic().MainTable_Entry(name, type, classTab);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                name = "";
                                type = "";
                                index++;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean Enum_1(ArrayList<ClassTable> oClassTable) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            oClassTable.add(new ClassTable(Validator.token.get(index).valuePart, "-", "-", "-"));
            index++;
            if (Enum_2(oClassTable)) {
                return true;
            }
        }
        return false;
    }

    boolean Enum_2(ArrayList<ClassTable> oClassTable) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                oClassTable.add(new ClassTable(Validator.token.get(index).valuePart, "-", "-", "-"));
                index++;
                if (Enum_2(oClassTable)) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                return true;
            }
        }
        return false;
    }

    boolean Const_Var() {
        if (dataTypes()) {
            type = Validator.token.get(index).classPart.toLowerCase();
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("constant-keyword")) {
                index++;
                type += "-" + "constant";
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    name = Validator.token.get(index).valuePart.toLowerCase();
                    index++;
                    if (Validator.token.get(index).classPart.toLowerCase().equals("equal")) {
                        try {
                            sm.MainTable_Entry(name, type);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        index++;
                        //pending for OE
                        if (OE(new StringBuilder(""), null, "", null)) {///////////////////
                            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                                index++;
                                name = "";
                                type = "";
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean state_Variable(ArrayList<ClassTable> oTable, String itemName, String dataType) {
        StringBuilder data = new StringBuilder(dataType + "");
        StringBuilder modifier = new StringBuilder("");
        if (SV_1(modifier)) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                String dataTypeName = Validator.token.get(index).valuePart;
                index++;
                StringBuilder dataTypeX = new StringBuilder("");
                StringBuilder flag = new StringBuilder("");
                if (SV_2(dataTypeX, flag, oTable, itemName)) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                        if (!flag.toString().equals(null)) {
//                            System.out.println(flag+" " + " " +dataType + " " + dataTypeX);
                            StringBuilder comp = sm.compatibilityCheck(data, dataTypeX, flag.toString());
                            if (comp == null & !dataTypeX.toString().isEmpty()) {
                                System.out.println("Type Mismatch Error!");
                                System.exit(0);
                            } else {
                                if (!new Semantic().LookUp_ClassTable(classTab, dataTypeName)) {
                                    oTable.add(new ClassTable(dataTypeName, dataType, modifier.toString(), "-"));
                                } else {
                                    System.out.println(dataTypeName + " is already decleared!");
                                }
                            }
                        } else {
                            if (!new Semantic().LookUp_ClassTable(classTab, dataTypeName)) {
                                oTable.add(new ClassTable(dataTypeName, dataType, modifier.toString(), "-"));
                            } else {
                                System.out.println(dataTypeName + " is already decleared!");
                            }
                        }
                        index++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean SV_1(StringBuilder modifier) {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("public")
                || Validator.token.get(index).valuePart.toLowerCase().equals("private")
                || Validator.token.get(index).valuePart.toLowerCase().equals("constant")
                || Validator.token.get(index).valuePart.toLowerCase().equals("internal")
                || Override_Specifier(new StringBuilder(""))) {
            modifier.append(Validator.token.get(index).valuePart);
            index++;
            return true;
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                modifier.append("private");
                return true;
            }
        }
        return false;
    }

    boolean SV_2(StringBuilder dataType, StringBuilder flag, ArrayList<ClassTable> oTable, String className) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("equal")) {
            flag.append(Validator.token.get(index).valuePart.toLowerCase());
            index++;
            if (OE(dataType, oTable, className, null)) {
                return true;
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                return true;
            }
        }
        return false;
    }

    boolean Event_Def() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("event-keyword")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                    index++;
                    if (Event_1()) {
                        if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                            index++;
                            if (Anonymous()) {
                                if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                                    index++;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean Event_1() {
        if (dataTypes()) {
            if (Event_2()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean Event_2() {
        if (dataTypes()) {
            if (Event_Parameter()) {
                if (Event_3()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean Event_3() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            index++;
            if (Event_Parameter()) {
                if (Event_3()) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean Event_Parameter() {
        if (dataTypes()) {
            index++;
            if (Indexed()) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    return true;
                }
            }
        }
        return false;
    }

    boolean Indexed() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("indexed-keyword")) {
            index++;
            return true;
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                return true;
            }
        }
        return false;
    }

    boolean Anonymous() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("anonymous")) {
            index++;
            return true;
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                return true;
            }
        }
        return false;
    }

    boolean OE(StringBuilder type, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).valuePart.equals("!")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || constants()) {
            if (AE(type, oTable, className, Parent)) {
                StringBuilder typeX = new StringBuilder("");
                if (OE_(typeX, type, oTable, className, Parent)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean MDM() {
        return Validator.token.get(index).valuePart.equals("*")
                || Validator.token.get(index).valuePart.equals("/");
    }

    boolean PM() {
        return Validator.token.get(index).valuePart.toLowerCase().equals("+")
                || Validator.token.get(index).valuePart.toLowerCase().equals("-");
    }

    boolean RO() {
        return Validator.token.get(index).classPart.toLowerCase().equals("relational");
    }

    boolean AE(StringBuilder type, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).valuePart.equals("!")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || constants()) {
            StringBuilder typeX = new StringBuilder("");
            if (RE(typeX, oTable, className, Parent)) {
                if (AE_(typeX, type, oTable, className, Parent)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean OE_(StringBuilder typeX, StringBuilder typeY, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("logical-2")) {
            String operator = Validator.token.get(index).valuePart;
            index++;
            StringBuilder typeX_ = new StringBuilder("");

            if (AE(typeX_, oTable, className, Parent)) {
                StringBuilder typeX__ = sm.compatibilityCheck(typeX, typeX_, operator);
                if (typeX__ == null) {
                    System.out.println("Type Mismatch Error!");
                    System.exit(0);
                } else {
                    if (OE_(typeX__, typeY, oTable, className, Parent)) {
                        return true;
                    }
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.equals(",")
                    || Validator.token.get(index).valuePart.equals("]")
                    || Validator.token.get(index).valuePart.equals(")")
                    || Validator.token.get(index).valuePart.equals(";")) {
                typeY.append(typeX);
                return true;
            }
        }
        return false;
    }

    boolean RE(StringBuilder type, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).valuePart.equals("!")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || constants()) {
            StringBuilder typeX = new StringBuilder("");
            if (E(typeX, oTable, className, Parent)) {
                if (RE_(typeX, type, oTable, className, Parent)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean AE_(StringBuilder typeX, StringBuilder typeY, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("logical-1")) {
            String operator = Validator.token.get(index).valuePart;
            index++;
            StringBuilder typeX_ = new StringBuilder("");
            if (RE(typeX_, oTable, className, Parent)) {
                StringBuilder typeX__ = sm.compatibilityCheck(typeX, typeX_, operator);
                if (typeX__ == null) {
                    System.out.println("Type Mismatch Error!");
                    System.exit(0);
                } else {
                    if (AE_(typeX__, typeY, oTable, className, Parent)) {
                        return true;
                    }
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.equals(",")
                    || Validator.token.get(index).valuePart.equals("]")
                    || Validator.token.get(index).valuePart.equals(")")
                    || Validator.token.get(index).valuePart.equals(";")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-2")) {
                typeY.append(typeX);
                return true;
            }
        }
        return false;
    }

    boolean E(StringBuilder type, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).valuePart.equals("!")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || constants()) {
            StringBuilder typeX = new StringBuilder("");
            if (T(typeX, oTable, className, Parent)) {
                if (E_(typeX, type, oTable, className, Parent)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean RE_(StringBuilder typeX, StringBuilder typeY, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (RO()) {
            String Operator = Validator.token.get(index).valuePart;
            index++;
            StringBuilder typeX_ = new StringBuilder("");
            if (E(typeX_, oTable, className, Parent)) {
                StringBuilder typeX__ = sm.compatibilityCheck(typeX, typeX_, Operator);
                if (typeX__ == null) {
                    System.out.println("Type Mismatch Error!");
                    System.exit(0);
                } else {
                    if (RE_(typeX__, typeY, oTable, className, Parent)) {
                        return true;
                    }
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.equals(",")
                    || Validator.token.get(index).valuePart.equals("]")
                    || Validator.token.get(index).valuePart.equals(")")
                    || Validator.token.get(index).valuePart.equals(";")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-1")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-2")) {
                typeY.append(typeX);
                return true;
            }
        }
        return false;
    }

    boolean T(StringBuilder type, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {

        if (Validator.token.get(index).valuePart.equals("!")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || constants()) {
            StringBuilder typeX = new StringBuilder("");
            if (F(typeX, oTable, className, Parent)) {
                if (T_(typeX, type, oTable, className, Parent)) {
                    return true;
                }
            }

        }
        return false;
    }

    boolean E_(StringBuilder typeX, StringBuilder typeY, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (PM()) {
            String operator = Validator.token.get(index).valuePart;
            index++;
            StringBuilder typeX_ = new StringBuilder("");
            if (T(typeX_, oTable, className, Parent)) {
                StringBuilder typeX__ = sm.compatibilityCheck(typeX, typeX_, operator);
                if (typeX__ == null) {
                    System.out.println("Type Mismatch Error!");
                    System.exit(0);
                } else {
                    if (E_(typeX__, typeY, oTable, className, Parent)) {
                        return true;
                    }
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.equals(",")
                    || Validator.token.get(index).valuePart.equals("]")
                    || Validator.token.get(index).valuePart.equals(")")
                    || Validator.token.get(index).valuePart.equals(";")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-1")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-2")
                    || RO()) {
                typeY.append(typeX);
                return true;
            }
        }
        return false;
    }

    boolean F(StringBuilder type, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).valuePart.equals("!")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || constants()) {

            StringBuilder thisOrSuperType = new StringBuilder("");
            if (Validator.token.get(index).valuePart.equals("!")) {
                String operator = Validator.token.get(index).valuePart;
                StringBuilder typeX = sm.compatibilityCheckForUnaray(type, operator);
                index++;
                if (typeX == null) {
                    System.out.println("Type Mismatch Error!");
                    System.exit(0);
                } else {
                    if (F(typeX, oTable, className, Parent)) {
                        return true;
                    }
                }
            } else if (constants()) {
                type.append(Validator.token.get(index).classPart);
                index++;
                return true;
            } else if (Validator.token.get(index).valuePart.equals("(")) {
                index++;
                if (OE(type, oTable, className, Parent)) {
                    if (Validator.token.get(index).valuePart.equals(")")) {
                        index++;
                        return true;
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                String operator = Validator.token.get(index).valuePart;
                index++;

                if (ThisOrSuper(thisOrSuperType)) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        if (thisOrSuperType.toString().toLowerCase().equals("this")) {
                            ClassTable oClassTable = new Semantic().Get_ClassTable(oTable, Validator.token.get(index).valuePart);
                            if (oClassTable == null) {
                                System.out.println("Undeclared error");
                                System.exit(0);
                            } else {
                                type.append(oClassTable.getType());

                            }
                        }
                        index++;
                        return true;
                    }
                }
            } else if (ThisOrSuper(thisOrSuperType)) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        if (thisOrSuperType.toString().toLowerCase().equals("this") || thisOrSuperType.toString().toLowerCase().equals("identifier")) {
                            if (oTable != null) {
                                ClassTable oClassTable = new Semantic().Get_ClassTable(oTable, Validator.token.get(index).valuePart);
                                if (oClassTable == null) {
                                    System.out.println(Validator.token.get(index).valuePart + " is not defined!");
                                    System.exit(0);
                                } else {
                                    type.append(oClassTable.getType());
                                }
                            } else {
                                FunctionTable check = sm.LookUp_FunctionTable(Validator.token.get(index).valuePart);
                                if (check.getType() == null) {
                                    System.out.println(Validator.token.get(index).valuePart + " is not defined!");
                                    System.exit(0);
                                } else {
                                    type.append(check.getType());
                                }
                            }
                        } else if (thisOrSuperType.toString().toLowerCase().equals("super")) {
                            flagSuper = false;
                            if (!"-".equals(Parent.toString())) {
                                String[] parms = Parent.toString().split(",");
                                for (int i = 0; i < parms.length; i++) {
                                    MainTable oMainTable = new Semantic().LookUp_MainTable(parms[i]);
                                    if (oMainTable != null) {
                                        if (oMainTable.getName() != null) {
                                            oMainTable.getClassTable().forEach((cn) -> {
//                                        System.out.println("Inside " + Validator.token.get(index).valuePart + " " + cn.getName());
                                                if (Validator.token.get(index).valuePart.equals(cn.getName()) && !flagSuper) {
                                                    if (cn.getAccess_Modifier().equals("public")) {
                                                        type.append(cn.getType());
                                                        flagSuper = true;
                                                    } else {
                                                        System.out.println("Identifier may be private!");
                                                        System.exit(0);
                                                    }
                                                }
                                            });
                                        } else {
                                            System.out.println(parms[i] +" contract is not declared!");
                                            System.exit(0);
                                        }
                                    } else {
                                        System.out.println(Validator.token.get(index).valuePart + " not found!");
                                        System.exit(0);
                                    }
                                }
                                if (!flagSuper) {
                                    System.out.println(Validator.token.get(index).valuePart + " not found in super class!");
                                    System.exit(0);
                                }
                            }
                        }
                        index++;
                        if (F_(type, oTable, className, Parent)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean F_(StringBuilder type, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).valuePart.toLowerCase().equals("(")
                || Validator.token.get(index).valuePart.toLowerCase().equals("[")
                || Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (ArgList()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                        index++;
                        if (F_Opt(type, oTable, className, Parent)) {
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                index++;
                return true;
            } else if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                index++;
                if (OE(type, oTable, className, Parent)) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("]")) {
                        index++;
                        if (Arr_Opt(type, oTable, className, Parent)) {
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (F_(type, oTable, className, Parent)) {
                        return true;
                    }
                }
            }

        } else {
            if (MDM() || RO() || PM() || Validator.token.get(index).valuePart.equals(",")
                    || Validator.token.get(index).valuePart.equals("]")
                    || Validator.token.get(index).valuePart.equals(")")
                    || Validator.token.get(index).valuePart.equals(";")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-1")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-2")) {
                return true;
            }
        }
        return false;
    }

    boolean T_(StringBuilder typeX, StringBuilder typeY, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (MDM()) {
            String operator = Validator.token.get(index).valuePart;
            index++;
            StringBuilder typeX_ = new StringBuilder("");
            if (F(typeX_, oTable, className, Parent)) {
                StringBuilder typeX__ = sm.compatibilityCheck(typeX, typeX_, operator);
                if (typeX__ == null) {
                    System.out.println("Type Mismatch Error!");
                    System.exit(0);
                } else {
                    if (T_(typeX__, typeY, oTable, className, Parent)) {
                        return true;
                    }
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.equals(",")
                    || Validator.token.get(index).valuePart.equals("]")
                    || Validator.token.get(index).valuePart.equals(")")
                    || Validator.token.get(index).valuePart.equals(";")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-1")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-2")
                    || RO() || PM()) {
                typeY.append(typeX);
                return true;
            }
        }
        return false;
    }

    boolean F_Opt(StringBuilder type, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (F_(type, oTable, className, Parent)) {
                    return true;
                }
            }
        } else {
            if (MDM() || RO() || PM() || Validator.token.get(index).valuePart.equals(",")
                    || Validator.token.get(index).valuePart.equals("]")
                    || Validator.token.get(index).valuePart.equals(")")
                    || Validator.token.get(index).valuePart.equals(";")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-1")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-2")) {
                return true;
            }
        }
        return false;
    }

    boolean Arr_Opt(StringBuilder type, ArrayList<ClassTable> oTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement") || Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (F_(type, oTable, className, Parent)) {
                        return true;
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                index++;
                return true;
            }
        } else {
            if (MDM() || RO() || PM() || Validator.token.get(index).valuePart.equals(",")
                    || Validator.token.get(index).valuePart.equals("]")
                    || Validator.token.get(index).valuePart.equals(")")
                    || Validator.token.get(index).valuePart.equals(";")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-1")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-2")) {
                return true;
            }
        }
        return false;
    }

    boolean Using() {
        if (Validator.token.get(index).valuePart.equals("using")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Validator.token.get(index).valuePart.equals("for")) {
                    index++;
                    if (US_1()) {
                        if (Validator.token.get(index).valuePart.equals(";")) {
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean US_1() {
        if (Validator.token.get(index).valuePart.equals("*") || dataTypes()) {
            index++;
            return true;
        }
        return false;
    }

    boolean Array_SST() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
            index++;
            if (Size(new StringBuilder(""))) {
                if (Validator.token.get(index).valuePart.equals("]")) {
                    index++;
                    if (DataLocation(new StringBuilder(""))) {
                        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                            index++;
                            if (check()) {
                                return true;

                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean Array_() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
            index++;
            if (Size(new StringBuilder(""))) {
                if (Validator.token.get(index).valuePart.equals("]")) {
                    index++;
                    if (Visibility(new StringBuilder(""))) {
                        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                            index++;
                            if (check()) {
                                return true;

                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean check() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("equal")
                || Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("equal")) {
                index++;
                if (Array1()) {
                    return true;
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                index++;
                return true;
            }
        }
        return false;
    }

    boolean Array1() {
        if (Validator.token.get(index).valuePart.equals("[") || Validator.token.get(index).valuePart.equals("new")) {
            if (list()) {
                if (Validator.token.get(index).valuePart.equals(";")) {
                    index++;
                    return true;
                }
            }
            if (Array__()) {
                if (Validator.token.get(index).valuePart.equals(";")) {
                    index++;
                    return true;
                }
            }
        }
        return false;
    }

    boolean list() {
        if (Validator.token.get(index).valuePart.equals("[")) {
            index++;
            if (itemlist()) {
                if (Validator.token.get(index).valuePart.equals("]")) {
                    index++;
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                return true;
            }
        }
        return false;
    }

    boolean itemlist() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier") || constants()
                || Validator.token.get(index).valuePart.toLowerCase().equals("!")
                || Validator.token.get(index).valuePart.toLowerCase().equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")) {
            StringBuilder type = new StringBuilder("");
            if (OE(type, null, "", null)) {
                if (itemlist1(type)) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("]")) {
                return true;
            }
        }
        return false;
    }

    boolean itemlist1(StringBuilder type) {
        if (Validator.token.get(index).valuePart.equals(",")) {
            index++;
            if (OE(type, null, "", null)) {
                if (itemlist1(type)) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.equals("]")) {
                return true;
            }
        }
        return false;
    }

    boolean Array__() {
        if (Validator.token.get(index).valuePart.equals("new")) {
            index++;
            if (Array1__()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.equals(";")) {
                index++;
                return true;
            }
        }
        return false;
    }

    boolean Array1__() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier") || dataTypes()) {
            index++;
            if (Validator.token.get(index).valuePart.equals("(")) {
                index++;
                if (Size(new StringBuilder(""))) {
                    if (Validator.token.get(index).valuePart.equals(")")) {
                        index++;
                        return true;

                    }
                }
            }
        }
        return false;
    }

    boolean ArgList() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier") || constants()
                || Validator.token.get(index).valuePart.toLowerCase().equals("!")
                || Validator.token.get(index).valuePart.toLowerCase().equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")) {
            if (OE(new StringBuilder(""), null, "", null)) {
                if (ArgList1()) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean ArgList1() {
        if (Validator.token.get(index).valuePart.equals(",")) {
            index++;
            if (ArgList__()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean ArgList__() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier") || constants()
                || Validator.token.get(index).valuePart.toLowerCase().equals("!")
                || Validator.token.get(index).valuePart.toLowerCase().equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")) {
            if (OE(new StringBuilder(""), null, "", null)) {
                if (ArgList1()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean Mapping() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("mapping-keyword")) {
            index++;
            if (Validator.token.get(index).valuePart.equals("(")) {
                index++;
                if (MappingKey()) {
                    if (Validator.token.get(index).valuePart.equals("=>")) {
                        index++;
                        if (MappingKey()) {
                            if (Validator.token.get(index).valuePart.equals(")")) {
                                index++;
                                if (DataLocation(new StringBuilder(""))) {
                                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                                        index++;
                                        if (Validator.token.get(index).valuePart.equals(";")) {
                                            index++;
                                            return true;
                                        }
                                    }
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean Mapping_() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("mapping-keyword")) {
            index++;
            if (Validator.token.get(index).valuePart.equals("(")) {
                index++;
                if (MappingKey()) {
                    if (Validator.token.get(index).valuePart.equals("=>")) {
                        index++;
                        if (MappingKey()) {
                            if (Validator.token.get(index).valuePart.equals(")")) {
                                index++;
                                if (Visibility(new StringBuilder(""))) {
                                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                                        index++;
                                        if (Validator.token.get(index).valuePart.equals(";")) {
                                            index++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean MappingKey() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier") || dataTypes()) {
            index++;
            return true;
        }
        return false;
    }

    boolean If() {
        if (Validator.token.get(index).valuePart.equals("if")) {
            index++;
            if (Validator.token.get(index).valuePart.equals("(")) {
                index++;
                // can if condition area be null? in this case i can be null because follow set of OE is )
                if (OE(new StringBuilder(""), null, "", null)) {
                    if (Validator.token.get(index).valuePart.equals(")")) {
                        index++;
                        if (Body_Else()) {
                            if (Else()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    boolean Else() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("else-keyword")) {
            index++;
            if (Else_()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("}")
                    || (Validator.token.get(index).valuePart.equals("if")
                    || Validator.token.get(index).valuePart.equals("for")
                    || Validator.token.get(index).valuePart.equals("while")
                    || Validator.token.get(index).valuePart.equals("do")
                    || Validator.token.get(index).valuePart.equals("mapping")
                    || Validator.token.get(index).valuePart.equals("(")
                    || Validator.token.get(index).valuePart.equals("++")
                    || Validator.token.get(index).valuePart.equals("--")
                    || Validator.token.get(index).valuePart.equals("this")
                    || Validator.token.get(index).valuePart.equals("super")
                    || Validator.token.get(index).valuePart.equals("emit")
                    || Validator.token.get(index).valuePart.equals("revert")
                    || Validator.token.get(index).valuePart.equals("return")
                    || Validator.token.get(index).valuePart.equals("continue")
                    || Validator.token.get(index).valuePart.equals("break")
                    || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                    || dataTypes())) {
                return true;
            }
        }
        return false;
    }

    boolean Else_() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("{")
                || Validator.token.get(index).classPart.toLowerCase().equals("if-keyword")) {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                if (Body_Else()) {
                    return true;
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("if-keyword")) {
                if (If()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean Body_Else() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
            index++;
            if (MST(null, null, "", null)) {
                if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                    index++;
                    return true;
                }
            }
        }

        return false;
    }

    boolean For() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("for")) {
            index++;
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (C1()) {
                    if (C2()) {
                        if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                            index++;
                            if (C3()) {
                                if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                                    index++;
                                    if (Body()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean C1() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || dataTypes() || Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
            if (dataTypes()) {
                if (Declaration_()) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                        index++;
                        return true;
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                    || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")) {
                if (AssignmentStatement()) {
                    return true;
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                index++;
                return true;
            }
        }
        return false;
    }

    boolean C2() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier") || constants()
                || Validator.token.get(index).valuePart.toLowerCase().equals("!")
                || Validator.token.get(index).valuePart.toLowerCase().equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")) {
            if (OE(new StringBuilder(""), null, "", null)) {
                return true;
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                return true;
            }
        }
        return false;
    }

    boolean C3() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                    || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                if (ThisOrSuper(new StringBuilder(""))) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        index++;
                        if (FOR_X()) {
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                index++;
                if (ThisOrSuper(new StringBuilder(""))) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        index++;
                        if (Ref()) {
                            return true;
                        }
                    }
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean AssignmentStatement() {
        if (ThisOrSuper(new StringBuilder(""))) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Ref()) {
                    if (Operator(new StringBuilder(""))) {
                        if (OE(new StringBuilder(""), null, "", null)) {
                            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                                index++;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean Operator(StringBuilder getOperator) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("equal")) {
            getOperator.append(Validator.token.get(index).valuePart);
            index++;
            return true;
        } else if (Validator.token.get(index).classPart.toLowerCase().equals("assignment")) {
            getOperator.append(Validator.token.get(index).valuePart);
            index++;
            return true;
        }
        return false;
    }

    boolean Ref() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("dot")
                || Validator.token.get(index).valuePart.toLowerCase().equals("[")
                || Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (Ref()) {
                        return true;
                    }
                }
            } else if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                index++;
                if (OE(new StringBuilder(""), null, "", null)) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("]")) {
                        index++;
                        if (Ref_()) {
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (ArgList()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                        index++;
                        if (Ref__()) {
                            return true;
                        }
                    }
                }
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("equal")
                    || Validator.token.get(index).classPart.toLowerCase().equals("assignment")
                    || Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean Ref_() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
            index++;
            if (Ref()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("equal")
                    || Validator.token.get(index).classPart.toLowerCase().equals("assignment")
                    || Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean Ref__() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Ref()) {
                    return true;
                }
            }
        } else if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
            index++;
            if (OE(new StringBuilder(""), null, "", null)) {
                if (Validator.token.get(index).valuePart.toLowerCase().equals("]")) {
                    index++;
                    if (Ref_()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean ThisOrSuper(StringBuilder type) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")) {
            type.append(Validator.token.get(index).valuePart);
            index++;

            if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
                index++;

                return true;
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                type.append("identifier");
                return true;
            }
        }
        return false;
    }

    boolean Declaration_() {
        if (dataTypes()) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("equal")) {
                    index++;
                    if (OE(new StringBuilder(""), null, "", null)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean VDS_1(ArrayList<ClassTable> oClassTable, String className, StringBuilder Parent, StringBuilder getType, StringBuilder getOperator) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("equal")) {
            getOperator.append(Validator.token.get(index).valuePart);
            index++;
            if (OE(getType, oClassTable, className, Parent)) {
                return true;
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                getOperator.append("");
                return true;
            }
        }
        return false;
    }

    boolean WhileStatement(ArrayList<ClassTable> oClassTable, ArrayList<FunctionTable> oFunctionTable, String className, StringBuilder Parent) {
        if (Validator.token.get(index).classPart.toLowerCase().equals("while-keyword")) {
            index++;
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                StringBuilder types = new StringBuilder("");
                if (OE(types, oClassTable, className, Parent)) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                        if (!"boolean".equals(types.toString())) {
                            System.out.println("Must be Boolean!");
                            System.exit(0);
                        }
                        index++;
                        if (Body()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean DoWhileStatement() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("do-keyword")) {
            index++;
            if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                index++;
                if (MST(null, null, "", null)) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                        index++;
                        if (Validator.token.get(index).classPart.toLowerCase().equals("while-keyword")) {
                            index++;
                            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                                index++;
                                if (OE(new StringBuilder(""), null, "", null)) {
                                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                                        index++;
                                        if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                                            index++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean ReturnStatement() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier") || constants()
                || Validator.token.get(index).valuePart.toLowerCase().equals("!")
                || Validator.token.get(index).valuePart.toLowerCase().equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")) {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (OE(new StringBuilder(""), null, "", null)) {
                    if (OE_1()) {
                        if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                            index++;
                            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                                index++;
                                return true;
                            }
                        }
                    }
                }
            } else if (OE(new StringBuilder(""), null, "", null)) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                    index++;
                    return true;
                }
            }
        }
        return false;
    }

    boolean OE_1() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            index++;
            if (OE(new StringBuilder(""), null, "", null)) {
                if (OE_1()) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean Er_Statement() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
            index++;
            if (OE(new StringBuilder(""), null, "", null)) {
                if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                    index++;
                    if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                        index++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean Tuple() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
            index++;
            if (Tuple_1()) {
                if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                    index++;
                    if (Validator.token.get(index).classPart.toLowerCase().equals("equal")) {
                        index++;
                        if (OE(new StringBuilder(""), null, "", null)) {
                            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                                index++;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean Tuple_1() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Tuple_2()) {
                    return true;
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
                index++;
                if (Tuple_3()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean Tuple_2() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            index++;
            if (Tuple_3()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean Tuple_3() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Tuple_2()) {
                    return true;
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
                if (Tuple_2()) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean Body() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("{")
                || Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                index++;
                return true;
            } else if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                index++;
                if (MST(null, null, "", null)) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                        index++;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
