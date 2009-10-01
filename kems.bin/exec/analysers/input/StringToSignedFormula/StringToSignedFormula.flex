/*
Adolfo Gustavo Serra Seca Neto, November 2003


Converts strings into formulas

java JFlex.Main StringToSignedFormula.flex
or
java -jar JFlex.jar StringToSignedFormula.flex

generates StringToSignedFormulaLexer.java
and needs generation of StringToSignedFormulasym.java
by a parser.

*/
package StringToSignedFormula;
import java_cup.runtime.*;

%%

%class StringToSignedFormulaLexer
%public

%line
%column

%cup

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

%eofval{
  return symbol(StringToSignedFormulasym.EOF);
%eofval}


LineTerminator = \r|\n|\r\n

WhiteSpace     = [ \t\f]

Sign = "T"|"F"

dec_int_id = [A-Za-z_][A-Za-z_0-9_,]*


%state FORMULA

%%

<YYINITIAL>{

	{Sign}  			{
	  						yybegin(FORMULA);
						   	return symbol(StringToSignedFormulasym.SIGN, yytext());
						}

}

<FORMULA> {

    {LineTerminator}      {
    						yybegin(YYINITIAL);
    						return symbol(StringToSignedFormulasym.EOL);
	    				   }

    "!"                { //System.out.print("!");
                         return symbol(StringToSignedFormulasym.NEG); }
    "&"                { //System.out.print("&");
                         return symbol(StringToSignedFormulasym.AND); }
    "|"                { //System.out.print("|");
                         return symbol(StringToSignedFormulasym.OR); }
    "->"                { //System.out.print("->");
                          return symbol(StringToSignedFormulasym.IMPLIES); }
    "<>"                { //System.out.print("<->");
                          return symbol(StringToSignedFormulasym.BIIMPLIES); }
    "("                { //System.out.print("(");
                         return symbol(StringToSignedFormulasym.LPAREN); }
    ")"                { //System.out.print(")");
                         return symbol(StringToSignedFormulasym.RPAREN); }

    {dec_int_id}       { //System.out.print(yytext());
                         return symbol (StringToSignedFormulasym.ID, yytext());}

    {WhiteSpace}       { /* just skip what was found, do nothing */ }
}

[^]                    { throw new Error("Illegal character <"+yytext()+ "> at line " + yyline + ", column " + yychar ); }
