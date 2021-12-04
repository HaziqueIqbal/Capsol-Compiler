package capsolcompiler;

/**
 *
 * @author Hazique
 */
public class Syntax {

    int index = 0;

    public Syntax() {
        if (Start()) {
            if (Validator.token.get(index).classPart.equals("End-Marker")) {
                System.out.println("Valid Syntax!");
            } else {
                System.out.println("Invalid Syntax!");
            }
        } else {
            System.out.println("Invalid Syntax!");
        }
    }

    boolean Start() {
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
        if (Validator.token.get(index).classPart.toLowerCase().equals("interface-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("class-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("abstract-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("library-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("import-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("struct-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("enum-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("function-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || dataTypes()) {

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
                if (Library()) {
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

            if (dataTypes() || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
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
                if (Function()) {
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
                index++;
                if (inheritance()) {
                    if (Validator.token.get(index).valuePart.equals("{")) {
                        index++;
                        if (CBody()) {
                            if (Validator.token.get(index).valuePart.equals("}")) {
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

    boolean inheritance() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("is-keyword")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (I1()) {
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

    boolean I1() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (I1()) {
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

    boolean CBody() {
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
            if (Constructor()) {
                if (CBody1()) {
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

    boolean Constructor() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("constructor-keyword")) {
            if (Constructor_Def()) {
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

    boolean Constructor_Def() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("constructor-keyword")) {
            index++;
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (PL_()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                        index++;
                        if (Con_1()) {
                            if (Con_modifier()) {
                                if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                                    index++;
                                    if (MST()) {
                                        if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
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

    boolean PL() {
        if (dataTypes() || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            index++;
            if (PL_1_()) {
                return true;
            }
        }
        return false;
    }

    boolean PL_1_() {
        if (Pl_S()) {
            if (DataLocation()) {
                if (PL_2_()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean PL_2_() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            index++;
            if (PL()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean Con_modifier() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("public")
                || Validator.token.get(index).valuePart.toLowerCase().equals("internal")) {
            index++;
            return true;
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                return true;
            }
        }
        return true;
    }

    boolean Con_1() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("payable")) {
            index++;
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

    boolean MST() {
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
                || Validator.token.get(index).valuePart.equals("return")
                || Validator.token.get(index).valuePart.equals("continue")
                || Validator.token.get(index).valuePart.equals("break")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || dataTypes()) {
            if (SST()) {
                if (MST()) {
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

    boolean DT_() {
        if (dataTypes()) {
            index++;
            if (DT__()) {
                return true;
            }
        }
        return false;
    }

    boolean DT__() {
        if (Validator.token.get(index).valuePart.equals("memory")
                || Validator.token.get(index).valuePart.equals("storage")
                || Validator.token.get(index).valuePart.equals("calldate")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || Validator.token.get(index).valuePart.equals("[")) {
            if (DataLocation()) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (VDS_1()) {
                        if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                            index++;
                            return true;
                        }
                    }
                }
            }
            if (Validator.token.get(index).valuePart.equals("[")) {
                if (Array_SST()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean SST() {
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
                || Validator.token.get(index).valuePart.equals("return")
                || Validator.token.get(index).valuePart.equals("continue")
                || Validator.token.get(index).valuePart.equals("break")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || dataTypes()) {
            if (dataTypes()) {
                if (DT_()) {
                    return true;
                }
            }
            if (Validator.token.get(index).valuePart.equals("(")) {
                if (Tuple()) {
                    return true;
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("mapping-keyword")) {
                if (Mapping()) {

                    return true;

                } else {
                    return false;
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                index++;
                if (ThisOrSuper()) {
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
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")) {
                if (ThisOrSuper()) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        index++;
                        if (X_sst()) {
                            return true;
                        } else {
                            return false;
                        }

                    }
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Y()) {

                    return true;

                } else {
                    return false;
                }
            }
            if (Validator.token.get(index).valuePart.equals("if")) {
                if (If()) {
                    return true;
                } else {
                    return false;
                }
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("for")) {
                if (For()) {

                    return true;

                } else {
                    return false;
                }

            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("while")) {
                if (WhileStatement()) {

                    return true;

                } else {
                    return false;
                }
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("do")) {
                if (DoWhileStatement()) {
                    return true;
                } else {
                    return false;
                }
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("continue")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                    index++;
                    return true;
                }
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("break")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                    index++;
                    return true;
                }
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("return")) {
                if (ReturnStatement()) {
                    return true;
                } else {
                    return false;
                }
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("revert")
                    || Validator.token.get(index).valuePart.toLowerCase().equals("assert")) {
                if (Er_Statement()) {
                    return true;
                } else {
                    return false;
                }
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
                if (OE()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("]")) {
                        index++;
                        if (A_Opt_1()) {
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("equal")
                    || Validator.token.get(index).classPart.toLowerCase().equals("assignment")) {
                if (Operator()) {
                    if (OE()) {
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

    boolean X_sst() {
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
                    if (X_sst()) {
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
                if (OE()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("]")) {
                        index++;
                        if (A_Opt()) {
                            return true;
                        }
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("equal")
                    || Validator.token.get(index).classPart.toLowerCase().equals("assignment")) {
                if (Operator()) {
                    if (OE()) {
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
                if (Operator()) {
                    if (OE()) {
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
                    if (X_sst()) {
                        return true;
                    }
                }
            } else if (Validator.token.get(index).classPart.toLowerCase().equals("equal")
                    || Validator.token.get(index).classPart.toLowerCase().equals("assignment")) {
                if (Operator()) {
                    if (OE()) {
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
                    if (X_sst()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean Y() {
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
                    if (X_sst()) {
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
                if (DataLocation()) {
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
            }
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
        return false;
    }

    boolean CBody1() {
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
                if (Function()) {
                    if (CBody1()) {
                        return true;
                    }
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("modifier-keyword")) {
                if (Modifier_Def()) {
                    if (CBody1()) {
                        return true;
                    }
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("struct-keyword")) {
                if (Struct()) {
                    if (CBody1()) {
                        return true;
                    }
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("enum-keyword")) {
                if (Enum()) {
                    if (CBody1()) {
                        return true;
                    }
                }
                return false;
            }
            if (dataTypes()) {
                index++;
                if (Validator.token.get(index).valuePart.toLowerCase().equals("[")
                        || Validator.token.get(index).valuePart.toLowerCase().equals("public")
                        || Validator.token.get(index).valuePart.toLowerCase().equals("private")
                        || Validator.token.get(index).valuePart.toLowerCase().equals("internal")
                        || Validator.token.get(index).valuePart.toLowerCase().equals("constant")
                        || Validator.token.get(index).valuePart.toLowerCase().equals("override")) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                        if (Array_()) {
                            if (CBody1()) {
                                return true;
                            }
                        }
                    }
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("public")
                            || Validator.token.get(index).valuePart.toLowerCase().equals("private")
                            || Validator.token.get(index).valuePart.toLowerCase().equals("internal")
                            || Validator.token.get(index).valuePart.toLowerCase().equals("constant")
                            || Validator.token.get(index).valuePart.toLowerCase().equals("override")) {
                        if (state_Variable()) {
                            if (CBody1()) {
                                return true;
                            }
                        }

                    }

                }

            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("event-keyword")) {
                if (Event_Def()) {
                    if (CBody1()) {
                        return true;
                    }
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("mapping-keyword")) {
                if (Mapping_()) {
                    if (CBody1()) {
                        return true;
                    }
                }
                return false;
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                        || Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        if (Object_Dec()) {
                            if (CBody1()) {
                                return true;
                            }
                        }
                    }
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                        if (Array_()) {
                            if (CBody1()) {
                                return true;
                            }
                        }
                    }
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("using-keyword")) {
                if (Using()) {
                    if (CBody1()) {
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
        if (Validator.token.get(index).classPart.equals("interface-keyword")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (inheritance()) {
                    if (Validator.token.get(index).valuePart.equals("{")) {
                        index++;
                        if (IBody()) {
                            if (Validator.token.get(index).valuePart.equals("}")) {
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

    boolean IBody() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("modifier-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || Validator.token.get(index).classPart.toLowerCase().equals("struct-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("enum-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("function-keyword")
                || dataTypes()
                || Validator.token.get(index).classPart.toLowerCase().equals("event-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("using-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("mapping-keyword")) {
            if (CBody1()) {
                return true;
            }
        }
        return false;
    }

    boolean Abstract() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("abstract-keyword")) {
            index++;
            if (CB()) {
                return true;
            }
        }
        return false;
    }

    boolean Library() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("library-keyword")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                    index++;
                    if (CBody1()) {
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
        }
        if (Path()) {
            if (X1()) {
                return true;
            }
        }
        if (SymbolAlises()) {
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
            index++;
            if (Visibility()) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (Validator.token.get(index).valuePart.equals("{")) {
                        index++;
                        if (StrBody()) {
                            if (Validator.token.get(index).valuePart.equals("}")) {
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

    boolean Visibility() {
        if (Validator.token.get(index).valuePart.equals("public")
                || Validator.token.get(index).valuePart.equals("private")
                || Validator.token.get(index).valuePart.equals("external")
                || Validator.token.get(index).valuePart.equals("internal")) {
            index++;
            return true;
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                    || Validator.token.get(index).classPart.toLowerCase().equals("state-keyword")) {
                return true;
            }
        }
        return false;
    }

    boolean StrBody() {
        if (dataTypes()
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            index++;
            if (Str_()) {
                return true;
            }
        }
        return false;
    }

    boolean Str_() {
        if (Pl_S()) {
            if (DataLocation()) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (Str__()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean Str__() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
            index++;
            if (Str__1()) {
                return true;
            }
        }
        return false;
    }

    boolean Str__1() {
        if (dataTypes()
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            index++;
            if (Str_()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                return true;
            }
        }
        return false;
    }

    boolean Pl_S() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
            index++;
            if (Size()) {
                if (Validator.token.get(index).valuePart.toLowerCase().equals("]")) {
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

    boolean Size() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("unsignedinteger")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
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

    boolean DataLocation() {
        if (Validator.token.get(index).valuePart.equals("memory")
                || Validator.token.get(index).valuePart.equals("storage")
                || Validator.token.get(index).valuePart.equals("calldate")) {
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
        if (Validator.token.get(index).classPart.toLowerCase().equals("address-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("string-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("unsignedinteger-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("signedinteger-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("character-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("floatingpointnumber-keyword")) {
            return true;
        }
        return false;
    }

    boolean constants() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("address")
                || Validator.token.get(index).classPart.toLowerCase().equals("character")
                || Validator.token.get(index).classPart.toLowerCase().equals("signedpoint")
                || Validator.token.get(index).classPart.toLowerCase().equals("unsignedpoint")
                || Validator.token.get(index).classPart.toLowerCase().equals("signedinteger")
                || Validator.token.get(index).classPart.toLowerCase().equals("unsignedinteger")
                || Validator.token.get(index).classPart.toLowerCase().equals("string")) {
            return true;
        }
        return false;
    }

    boolean Function() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("function-keyword")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                    index++;
                    if (PL_()) {
                        if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                            index++;
                            if (Visibility()) {
                                if (State_Mutability()) {
                                    if (Modifier_Invocation()) {
                                        if (Virtual()) {
                                            if (Override_Specifier()) {
                                                if (Fn_1()) {
                                                    if (Fn_2()) {
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
        }
        return false;
    }

    boolean PL_() {
        if (dataTypes() || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            index++;
            if (PL_1()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean PL_1() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("[")
                || Validator.token.get(index).classPart.toLowerCase().equals("store-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            if (Pl_S()) {
                if (DataLocation()) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        index++;
                        if (PL_2()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean PL_2() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            index++;
            if (PL_3()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                return true;
            }
        }
        return false;
    }

    boolean PL_3() {
        if (dataTypes() || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            index++;
            if (PL_1()) {
                return true;
            }
        }
        return false;
    }

    boolean State_Mutability() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("state-keyword")) {
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
            if (PL_()) {
                if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                    index++;
                    return true;
                }
            }
        }
        return false;
    }

    boolean Virtual() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("virtual")) {
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

    boolean Override_Specifier() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("override-keyword")) {
            index++;
            if (OV_1()) {
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

    boolean OV_1() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (OV_2()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                        index++;
                        return true;
                    }
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                return true;
            }
        }
        return false;
    }

    boolean OV_2() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            index++;
            if (OV_3()) {
                return true;
            }
        }
        return false;
    }

    boolean OV_3() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (OV_3()) {
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

    boolean Fn_1() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("returns-keyword")) {
            index++;
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (PL()) {
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

    boolean Fn_2() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")
                || Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                index++;
                return true;
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                index++;
                if (MST()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                        index++;
                        return true;
                    }
                }
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
                    if (Virtual()) {
                        if (Override_Specifier()) {
                            if (Fn_2()) {
                                return true;
                            }
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
                    || Override_Specifier()) {
                return true;
            }
        }
        return false;
    }

    boolean Mod_2() {
        if (PL_()) {
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
            index++;
            if (Visibility()) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                        index++;
                        if (Enum_1()) {
                            if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
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

    boolean Enum_1() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            index++;
            if (Enum_2()) {
                return true;
            }
        }
        return false;
    }

    boolean Enum_2() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Enum_2()) {
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
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("constant-keyword")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (Validator.token.get(index).classPart.toLowerCase().equals("equal")) {
                        index++;
                        if (OE()) {
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

    boolean state_Variable() {
        if (SV_1()) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (SV_2()) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                        index++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean SV_1() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("public")
                || Validator.token.get(index).valuePart.toLowerCase().equals("private")
                || Validator.token.get(index).valuePart.toLowerCase().equals("constant")
                || Validator.token.get(index).valuePart.toLowerCase().equals("internal")
                || Override_Specifier()) {
            index++;
            return true;
        }
        return false;
    }

    boolean SV_2() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("equal")) {
            index++;
            if (OE()) {
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

    boolean OE() {
        if (Validator.token.get(index).valuePart.equals("!")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || constants()) {
            if (AE()) {
                if (OE_()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean MDM() {
        if (Validator.token.get(index).valuePart.equals("*")
                || Validator.token.get(index).valuePart.equals("/")) {
            return true;
        }
        return false;
    }

    boolean PM() {
        if (Validator.token.get(index).valuePart.toLowerCase().equals("+")
                || Validator.token.get(index).valuePart.toLowerCase().equals("-")) {
            return true;
        }
        return false;
    }

    boolean RO() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("relational")) {
            return true;
        }
        return false;
    }

    boolean AE() {
        if (Validator.token.get(index).valuePart.equals("!")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || constants()) {
            if (RE()) {
                if (AE_()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean OE_() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("logical-2")) {
            index++;
            if (AE()) {
                if (OE_()) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.equals(",")
                    || Validator.token.get(index).valuePart.equals("]")
                    || Validator.token.get(index).valuePart.equals(")")
                    || Validator.token.get(index).valuePart.equals(";")) {
                return true;
            }
        }
        return false;
    }

    boolean RE() {

        if (Validator.token.get(index).valuePart.equals("!")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || constants()) {
            if (E()) {
                if (RE_()) {

                    return true;
                }
            }
        }
        return false;
    }

    boolean AE_() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("logical-1")) {
            index++;
            if (RE()) {
                if (AE_()) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.equals(",")
                    || Validator.token.get(index).valuePart.equals("]")
                    || Validator.token.get(index).valuePart.equals(")")
                    || Validator.token.get(index).valuePart.equals(";")
                    || Validator.token.get(index).classPart.toLowerCase().equals("logical-2")) {
                return true;
            }
        }
        return false;
    }

    boolean E() {

        if (Validator.token.get(index).valuePart.equals("!")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || constants()) {
            if (T()) {
                if (E_()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean RE_() {
        if (RO()) {
            index++;
            if (E()) {
                if (RE_()) {
                    return true;
                }
            }
        } else {
            if (Validator.token.get(index).valuePart.equals(",")
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

    boolean T() {

        if (Validator.token.get(index).valuePart.equals("!")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || constants()) {
            if (F()) {
                if (T_()) {
                    return true;
                }
            }

        }
        return false;
    }

    boolean E_() {
        if (PM()) {
            index++;
            if (T()) {
                if (E_()) {
                    return true;
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
                return true;
            }
        }
        return false;
    }

    boolean F() {
        if (Validator.token.get(index).valuePart.equals("!")
                || Validator.token.get(index).valuePart.equals("(")
                || Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")
                || Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).classPart.toLowerCase().equals("identifier")
                || constants()) {
            if (Validator.token.get(index).valuePart.equals("!")) {
                index++;
                if (F()) {
                    return true;
                }
            }
            if (constants()) {
                index++;
                return true;
            }
            if (Validator.token.get(index).valuePart.equals("(")) {
                index++;
                if (OE()) {
                    if (Validator.token.get(index).valuePart.equals(")")) {
                        index++;
                        return true;
                    }
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                index++;
                if (ThisOrSuper()) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        index++;
                        return true;
                    }
                }
            }
            if (ThisOrSuper()) {
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (F_()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean F_() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")
                || Validator.token.get(index).valuePart.toLowerCase().equals("(")
                || Validator.token.get(index).valuePart.toLowerCase().equals("[")
                || Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (ArgList()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
                        index++;
                        if (F_Opt()) {
                            return true;
                        }
                    }
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                index++;
                return true;
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                index++;
                if (OE()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("]")) {
                        index++;
                        if (Arr_Opt()) {
                            return true;
                        }
                    }
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
                index++;
                if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                    index++;
                    if (F_()) {
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

    boolean T_() {
        if (MDM()) {
            index++;
            if (F()) {
                if (T_()) {
                    return true;
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
                return true;
            }
        }
        return false;
    }

    boolean F_Opt() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (F_()) {
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

    boolean Arr_Opt() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (F_()) {
                    return true;
                }
            }
        }
        if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
            index++;
            return true;
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
            if (Size()) {
                if (Validator.token.get(index).valuePart.equals("]")) {
                    index++;
                    if (DataLocation()) {
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
            if (Size()) {
                if (Validator.token.get(index).valuePart.equals("]")) {
                    index++;
                    if (Visibility()) {
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
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
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
            if (OE()) {
                if (itemlist1()) {
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

    boolean itemlist1() {
        if (Validator.token.get(index).valuePart.equals(",")) {
            index++;
            if (OE()) {
                if (itemlist1()) {
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
                if (Size()) {
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
            if (OE()) {
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
            if (OE()) {
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
                                if (DataLocation()) {
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
                                if (Visibility()) {
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
                if (OE()) {
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
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("if-keyword")) {
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
            if (MST()) {
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
            if (OE()) {
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
                if (ThisOrSuper()) {
                    if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                        index++;
                        if (FOR_X()) {
                            return true;
                        }
                    }
                }
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("increment/decrement")) {
                index++;
                if (ThisOrSuper()) {
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
        if (ThisOrSuper()) {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
                index++;
                if (Ref()) {
                    if (Operator()) {
                        if (OE()) {
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

    boolean Operator() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("equal")) {
            index++;
            return true;
        }
        if (Validator.token.get(index).classPart.toLowerCase().equals("assignment")) {
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
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
                index++;
                if (OE()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("]")) {
                        index++;
                        if (Ref_()) {
                            return true;
                        }
                    }
                }
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
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
        }
        if (Validator.token.get(index).valuePart.toLowerCase().equals("[")) {
            index++;
            if (OE()) {
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

    boolean ThisOrSuper() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("thisorsuper-keyword")) {
            index++;
            if (Validator.token.get(index).classPart.toLowerCase().equals("dot")) {
                index++;
                return true;
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
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
                    if (OE()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean VDS_1() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("equal")) {
            index++;
            if (OE()) {
                return true;
            }
        } else {
            if (Validator.token.get(index).classPart.toLowerCase().equals("semi-colon")) {
                return true;
            }
        }
        return false;
    }

    boolean WhileStatement() {
        if (Validator.token.get(index).classPart.toLowerCase().equals("while-keyword")) {
            index++;
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (OE()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals(")")) {
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
                if (MST()) {
                    if (Validator.token.get(index).valuePart.toLowerCase().equals("}")) {
                        index++;
                        if (Validator.token.get(index).classPart.toLowerCase().equals("while-keyword")) {
                            index++;
                            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                                index++;
                                if (OE()) {
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
                if (OE()) {
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
            }
            if (OE()) {
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
            if (OE()) {
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
        if (Validator.token.get(index).classPart.toLowerCase().equals("identifier")) {
            index++;
            if (Validator.token.get(index).valuePart.toLowerCase().equals("(")) {
                index++;
                if (ArgList()) {
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
                        if (OE()) {
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
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
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
            }
            if (Validator.token.get(index).classPart.toLowerCase().equals("comma")) {
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
            }
            if (Validator.token.get(index).valuePart.toLowerCase().equals("{")) {
                index++;
                if (MST()) {
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
