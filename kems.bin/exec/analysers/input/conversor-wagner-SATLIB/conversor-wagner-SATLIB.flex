/*
Adolfo Gustavo Serra Seca Neto, November 2003


Converts Wagner Dias's format formulas into SATLIB
format with signs and implication.

java JFlex.Main conversor-wagner-SATLIB.flex 
or
java -jar JFlex.jar conversor-wagner-SATLIB.flex 
  	
generates ConversorWagnerSATLIBLexer.java
and needs generation of ConversorWagnerSATLIBsym.java
by a parser.

06-08-2005: Added consistency



*/
package ConversorWagnerSATLIB;
import java_cup.runtime.*;

%%

%class ConversorWagnerSATLIBLexer
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
  return symbol(ConversorWagnerSATLIBsym.EOF);
%eofval}


LineTerminator = \r|\n|\r\n|\u2028|\u2029|\u000B|\u000C|\u0085

WhiteSpace     = [ \t\f]

Sign = "T"|"F"

dec_int_id = [A-Za-z_][A-Za-z_0-9_,]*


InputCharacter = [^\r\n]
Comment = "#" {WhiteSpace}* {InputCharacter}* {LineTerminator} 
EndComment = "#" {WhiteSpace}* {InputCharacter}*



%state FORMULA

%%



<YYINITIAL>{

	{Comment}			{ /* just skip what was found, do nothing */ }

	{WhiteSpace}       { /* just skip what was found, do nothing */ }

	{Sign}  			{
	  						yybegin(FORMULA);
						   	return symbol(ConversorWagnerSATLIBsym.SIGN, yytext());
						}

	{EndComment}			{ /* just skip what was found, do nothing */ }
						
}



<FORMULA> {
   

    "TOP"                {
                         return symbol(ConversorWagnerSATLIBsym.TRUE); }
    "BOTTOM"                {
                         return symbol(ConversorWagnerSATLIBsym.FALSE); }
    "!"                { //System.out.print("!");
                         return symbol(ConversorWagnerSATLIBsym.NEG); }
    "@"                { //System.out.print("!");
                         return symbol(ConversorWagnerSATLIBsym.CONSISTENCY); }
    "*"                { //System.out.print("!");
                         return symbol(ConversorWagnerSATLIBsym.INCONSISTENCY); }
    "&"                { //System.out.print("&");
                         return symbol(ConversorWagnerSATLIBsym.AND); }
    "|"                { //System.out.print("|");
                         return symbol(ConversorWagnerSATLIBsym.OR); }
    "+"                { //System.out.print("@");
                          return symbol(ConversorWagnerSATLIBsym.XOR); }
    "<=>"                { //System.out.print("<->");
                          return symbol(ConversorWagnerSATLIBsym.BIIMPLIES); }
    "->"                { //System.out.print("->");
                          return symbol(ConversorWagnerSATLIBsym.IMPLIES); }
    "("                { //System.out.print("(");
                         return symbol(ConversorWagnerSATLIBsym.LPAREN); }
    ")"                { //System.out.print(")");
                         return symbol(ConversorWagnerSATLIBsym.RPAREN); }

    {dec_int_id}       { //System.out.print(yytext());
                         return symbol (ConversorWagnerSATLIBsym.ID, yytext());}

    {LineTerminator}      {  	yybegin(YYINITIAL);
    							return symbol(ConversorWagnerSATLIBsym.EOL); 
	    		  		  }

    {WhiteSpace}       { /* just skip what was found, do nothing */ }

    {EndComment}       {  return symbol(ConversorWagnerSATLIBsym.EOL); }
    
}

[^]                    { throw new Error("Illegal character <"+yytext()+ "> at line " + yyline + ", column " + yychar ); }

