package SATS;
import java_cup.runtime.*;

%%

/*

  Adolfo Gustavo Serra Seca Neto, November 2003
  Lexical analyser for classical propositional logic formulas
  SATLIB format with signs, implication and without headers


  What to change (in case you want to change the file format):
	name of the parser and of the symbols class
	  - SATSLexer, SATSsym (several references!)
      productions and states (obviously)

  How to produce the lexer:
  	
  - Run the command:
  
  	java JFlex.Main satlib-sat-S.flex
  	
  	which will create the following file:
  	
	  	SATSLexer.java
  	
  		

*/

/* Name of the parser class to be generated */
%class SATSLexer
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
  return symbol(SATSsym.EOF);
%eofval}

LineTerminator = \r|\n|\r\n
WhiteSpace = [ \t\f] 
String = [a-z_A-Z_1-9][a-z_A-Z_0-9_,]*
Implies = "->"
Sign = "T"|"F"

%state FORMULA

%%

/* beginning of the lexical analyser */
<YYINITIAL>{

	{Sign}  			{
	  						yybegin(FORMULA);
						   	return symbol(SATSsym.SIGN, yytext());
						}
	
}
<FORMULA> {

    {LineTerminator}      { 
    						yybegin(YYINITIAL);
    						return symbol(SATSsym.EOL); 
	    				   }

    {Implies}          { return symbol(SATSsym.IMPLIES); }
    "-"                { return symbol(SATSsym.NEG); }
    "*"                { return symbol(SATSsym.AND); }
    "+"                { return symbol(SATSsym.OR); }
    "("                { return symbol(SATSsym.LPAREN); }
    ")"                { return symbol(SATSsym.RPAREN); }

    {String}           { return symbol (SATSsym.STRING, yytext());}

    {WhiteSpace}       { /* just skip what was found, do nothing */ }   

}

[^]                    { throw new Error("Illegal character <"+yytext()+ "> at line " + yyline + ", column " + yychar ); }


