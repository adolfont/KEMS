package sats3;
import java_cup.runtime.*;

%%

/*

  Adolfo Gustavo Serra Seca Neto, October 2004
  Lexical analyser for classical propositional logic formulas
  SATLIB format with signs, implication and without headers

  This is sats for the new formula and signed formula classes.


  What to change (in case you want to change the file format):
	name of the parser and of the symbols class
	  - sats3Lexer, sats3sym (several references!)
      productions and states (obviously)

  How to produce the lexer:

  - Run the command:

  	java JFlex.Main satlib-sat-s2.flex

  	which will create the following file:

	  	sats3Lexer.java



*/

/* Name of the parser class to be generated */
%class sats3Lexer
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
  return symbol(sats3sym.EOF);
%eofval}

LineTerminator = \r|\n|\r\n
WhiteSpace = [ \t\f]
String = [a-z_A-Z_1-9][a-z_A-Z_0-9_,]*
Implies = "->"
Sign = "T"|"F"
Top = "TOP"
Bottom = "BOT"

%state FORMULA

%%

/* beginning of the lexical analyser */
<YYINITIAL>{

	{Sign}  			{
	  						yybegin(FORMULA);
						   	return symbol(sats3sym.SIGN, yytext());
						}

}
<FORMULA> {

    {LineTerminator}      {
    						yybegin(YYINITIAL);
    						return symbol(sats3sym.EOL);
	    				   }

    {Implies}          { return symbol(sats3sym.IMPLIES); }
    "-"                { return symbol(sats3sym.NEG); }
    "*"                { return symbol(sats3sym.AND); }
    "+"                { return symbol(sats3sym.OR); }
    "("                { return symbol(sats3sym.LPAREN); }
    ")"                { return symbol(sats3sym.RPAREN); }
    {Top}              { return symbol(sats3sym.TOP); }
    {Bottom}           { return symbol(sats3sym.BOTTOM); }

    {String}           { return symbol (sats3sym.STRING, yytext());}

    {WhiteSpace}       { /* just skip what was found, do nothing */ }

}

[^]                    { throw new Error("Illegal character <"+yytext()+ "> at line " + yyline + ", column " + yychar ); }


