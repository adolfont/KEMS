package satlfi;
import java_cup.runtime.*;

%%

/*

  Adolfo Gustavo Serra Seca Neto, December 2004
  Lexical analyser for classical propositional logic formulas
  SATLIB format with signs, implication, biimplication 
  and without headers

  This is sats for the new formula and signed formula classes.

  What to change (in case you want to change the file format):
	name of the parser and of the symbols class
	  - satlfiLexer, satlfisym (several references!)
      productions and states (obviously)

  How to produce the lexer:

  - Run the command:

  	java JFlex.Main satlib-sat-s5.flex

  	which will create the following file:

	  	satlfiLexer.java



*/

/* Name of the parser class to be generated */
%class satlfiLexer
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
  return symbol(satlfisym.EOF);
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

%state FORMULA

%%

/* beginning of the lexical analyser */
<YYINITIAL>{

	{Sign}  			{
	  						yybegin(FORMULA);
						   	return symbol(satlfisym.SIGN, yytext());
						}

}
<FORMULA> {

    {LineTerminator}      {
    						yybegin(YYINITIAL);
    						return symbol(satlfisym.EOL);
	    				   }

    {Biimplies}        { return symbol(satlfisym.BIIMPLIES); }
    {Implies}          { return symbol(satlfisym.IMPLIES); }
    {Xor}              { return symbol(satlfisym.XOR); }
    "-"                { return symbol(satlfisym.NEG); }
    "*"                { return symbol(satlfisym.AND); }
    "+"                { return symbol(satlfisym.OR); }
    "("                { return symbol(satlfisym.LPAREN); }
    ")"                { return symbol(satlfisym.RPAREN); }
    {Top}              { return symbol(satlfisym.TOP); }
    {Bottom}           { return symbol(satlfisym.BOTTOM); }
    {Consistency}      { return symbol(satlfisym.CONSISTENCY); }

    {String}           { return symbol (satlfisym.STRING, yytext());}

    {WhiteSpace}       { /* just skip what was found, do nothing */ }

}

[^]                    { throw new Error("Illegal character <"+yytext()+ "> at line " + yyline + ", column " + yychar ); }


