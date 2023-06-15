# Capsol-Compiler
A simple Compiler inspired by Solidity and Java programming language.

# Features in Capsol Language
## OBJECT ORIENTED RESERVED KEYWORDS
| Keyword   |          |
|-----------|----------|
| is        | constructor |
| function  | public   |
| private   | internal |
| external  | payable  |
| interface | new      |
| pure      | return   |
| returns   | view     |
| using     | throw    |
| abstract  | this     |
| super     | override |
| assert    | require  |
| revert    | contract |
| from      | library  |
| import    |          |

## RESERVED KEYWORDS
| Keyword   |          |
|-----------|----------|
| break     | continue |
| do        | while    |
| false     | storage  |
| calldata  | version  |
| capsol    | modifier |
| memory    | mapping  |
| constant  | true     |
| indexed   | if       |
| event     | else     |
| for       | emit     |
| Null      |          |

## DATATYPE RESERVED KEYWORDS
uint, int, alpha,	string,	address, struct,	point,	enum		

## PUNCTUATORS
| Symbol |       |
|--------|-------|
| {      | }     |
| (      | )     |
| [      | ]     |
| :      | ;     |
| .      | ?     |
| =>     |       |

## OPERATORS
-	Equal: =
-	Relational: <, >, <=, >=, !=, ==
-	Assignment: +=, -=, /=, *=, %=
-	Increment/Decrement: ++, --
-	Logical-X: &&
-	Logical-X: ||
-	Logical-X: !
-	Arithmetic-1: +, -
-	Arithmetic-2: **
-	Arithmetic-3: *, /, %, 
-	Bitwise-1: (~(negation)) -> (Right-to-Left)
-	Bitwise-2: (<<(Left-Shift), >>(Right-Shift)) -> (Left-to-Right)
-	Bitwise-3: (&(AND)) -> (Left-to-Right)
-	Bitwise-4: (^(XOR)) -> (Left-to-Right)
-	Bitwise-5: (|(OR)) -> (Left-to-Right)

## COMMENTS
-	Single-Line: $$---------------------------------------
-	Multi-Line: `* ----------------------------------------------------------------------------------------------------------------------------------------------------------------*`

## REGIX
-	Identifier: "([A-Za-z](_|\\$)?|_|\\$)([A-Za-z0-9]+(_|\\$)?)*"
-	Signed Floating Point Number: "[+-]\\d*\\.\\d+"
-	UnSigned Floating Point Number: "\\d*\\.\\d+"
-	Signed Integer: "[+-][0-9][0-9]*" 
-	Unsigned Integer: "[0-9][0-9]*"
-	Alpha: "\'(\\\\[fbnrt0\\\\]|'|\"|/|[A-Za-z0-9]|[$&+,:;=?@#|'<>.-^*()%!])\'"
-	String: "\"((\\\\[\'\"\\\\])|(\\\\[bfnrt0])|([\\!#-&\\(-/0-9:-@A-Z\\[\\]-`ac-eg-mo-qsu-z\\{-~])|([bnfrt0])|(\\s))*\""
-	address: "0x([a-fA-F0-9]{40})"

## CLASSES OF DATATYPES
-	Address -> address
-	String -> string
-	Unsigned Integer -> uint/alpha
-	Signed Integer -> int
-	Point -> point
-	Enum -> enum
-	Struct -> struct

## CLASSES OF OBJECT ORIENTED KEYWORDS
-	AccessModifier -> public/private/internal/external
-	State -> view/pure/payable
-	thisOrSuper -> this/super
-	Is -> is
-	Abstract -> abstract
-	Interface -> interface
-	Constructor -> constructor
-	Return -> return
-	Returns -> returns
-	New -> new
-	Import -> import
-	Using -> using
-	Override -> override
-	assertOrRequire -> assert/require
-	Revert -> revert
-	Class -> contract/library
-	From -> from
-	Using -> using
-	Import -> import
-	Function -> function

## CLASSES OF RESERVED KEYWORDS
-	DO -> do
-	While -> while
-	If -> if
-	Else -> else
-	For -> for
-	trueOrFalse -> true/false
-	Control -> break/continue
-	Capsol -> capsol
-	Version -> version
-	Mapping -> mapping
-	Constant -> constant
-	Modifier -> modifier
-	Null -> Null
-	Store -> memory/storage/calldata
-	Event -> event
-	Emit -> emit
-	Indexed -> indexed

