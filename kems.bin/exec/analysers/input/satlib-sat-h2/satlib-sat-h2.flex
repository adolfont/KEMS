package SATH2;
import java_cup.runtime.*;

%%

/*

  Adolfo Gustavo Serra Seca Neto, December 2004
  Lexical analyser for classical propositional logic formulas

  What to change (in case you want to change the file format):
	name of the parser and of the symbols class
	  - SATH2Lexer, sym (several references!)
      productions and states (obviously)


*/

/* Name of the parser class to be generated */
%class SATH2Lexer
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
  return symbol(SATH2sym.EOF);
%eofval}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
Comment = "c " {InputCharacter}* {LineTerminator}
WhiteSpace = [ \t\f]
Problem="p " {WhiteSpace}* "sat" {WhiteSpace}*
Number = [1-9][0-9]*
Implies = "->"
Top = "TOP"
Bottom = "BOT"

%state COMMENTS, PROBLEM, FORMULA

%%

/* beginning of the lexical analyser */
<YYINITIAL> {
    {Comment}		{
					yybegin (COMMENTS);
				}

/* if it is a problem line, goes to the state PROBLEM */
    {Problem}           { 
					yybegin(PROBLEM); 
				  	number.setLength(0);
				}

}

/* reads comment lines */
<COMMENTS>{

    {Comment}		{  }

/* if it is a problem line, goes to the state PROBLEM */
    {Problem}                 { 
				  	yybegin(PROBLEM);
				  	number.setLength(0);
				}

}

/* reads a problem line and then goes back to initial */
<PROBLEM> {

  {Number}         { 		number.append( yytext() ); }

  {WhiteSpace}*{LineTerminator} { 
						yybegin(FORMULA); 
					    	return symbol(SATH2sym.PROBLEM, number.toString()); 
					  }
  
}

<FORMULA>{
    {Implies}          { return symbol(SATH2sym.IMPLIES); }
    "-"                { return symbol(SATH2sym.NEG); }
    "*"                { return symbol(SATH2sym.AND); }
    "+"                { return symbol(SATH2sym.OR); }
    "("                { return symbol(SATH2sym.LPAREN); }
    ")"                { return symbol(SATH2sym.RPAREN); }
    {Top}              { return symbol(SATH2sym.TOP); }
    {Bottom}           { return symbol(SATH2sym.BOTTOM); }


    {Number}           { return symbol (SATH2sym.NUMBER, yytext());}

    {LineTerminator}   { /* just skip what was found, do nothing */  }

    {WhiteSpace}       { /* just skip what was found, do nothing */ }   

}

[^]                    { throw new Error("Illegal character <"+yytext()+ "> in line " + yyline + ", column " + yychar ); }

