package satlfiinconsdef;
import java_cup.runtime.*;

%%

/*

  Adolfo Gustavo Serra Seca Neto, April 2006
  Lexical analyser for classical propositional logic formulas
  SATLIB format with signs, implication, biimplication 
  and without headers

  This is sats for the new formula and signed formula classes.

  What to change (in case you want to change the file format):
	name of the parser and of the symbols class
	  - satlfiinconsdefLexer, satlfiinconsdefsym (several references!)
      productions and states (obviously)

  How to produce the lexer:

  - Run the command:

  	java JFlex.Main satlib-sat-lfi-inconsdef.flex

  	which will create the following file:

	  	satlfiinconsdefLexer.java



*/

/* Name of the parser class to be generated */
%class satlfiinconsdefLexer
%public
%line
%column

%cup

%{

    StringBuffer number = new StringBuffer();

    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}


%eofval{
  return symbol(satlfiinconsdefsym.EOF);
%eofval}

LineTerminator = \r|\n|\r\n
WhiteSpace = [ \t\f]
String = [a-z_A-Z_1-9][a-z_A-Z_0-9_,]*
Biimplies = "<=>"
Xor = "%"
Implies = "->"
Sign = "T"|"F"
Top = "TOP"
Bottom = "BOT"
Consistency = "@"
Inconsistency = "#"

%state FORMULA

%%

/* beginning of the lexical analyser */
<YYINITIAL>{

	{Sign}  			{
	  						yybegin(FORMULA);
						   	return symbol(satlfiinconsdefsym.SIGN, yytext());
						}

}
<FORMULA> {

    {LineTerminator}      {
    						yybegin(YYINITIAL);
    						return symbol(satlfiinconsdefsym.EOL);
	    				   }

    {Biimplies}        { return symbol(satlfiinconsdefsym.BIIMPLIES); }
    {Implies}          { return symbol(satlfiinconsdefsym.IMPLIES); }
    {Xor}              { return symbol(satlfiinconsdefsym.XOR); }
    "-"                { return symbol(satlfiinconsdefsym.NEG); }
    "*"                { return symbol(satlfiinconsdefsym.AND); }
    "+"                { return symbol(satlfiinconsdefsym.OR); }
    "("                { return symbol(satlfiinconsdefsym.LPAREN); }
    ")"                { return symbol(satlfiinconsdefsym.RPAREN); }
    {Top}              { return symbol(satlfiinconsdefsym.TOP); }
    {Bottom}           { return symbol(satlfiinconsdefsym.BOTTOM); }
    {Consistency}      { return symbol(satlfiinconsdefsym.CONSISTENCY); }
    {Inconsistency}      { return symbol(satlfiinconsdefsym.INCONSISTENCY); }

    {String}           { return symbol (satlfiinconsdefsym.STRING, yytext());}

    {WhiteSpace}       { /* just skip what was found, do nothing */ }

}

[^]                    { throw new Error("Illegal character <"+yytext()+ "> at line " + yyline + ", column " + yychar ); }


