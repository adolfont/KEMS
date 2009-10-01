package SATH;
import java_cup.runtime.*;

%%

/*

  Adolfo Gustavo Serra Seca Neto, October 2003
  Lexical analyser for classical propositional logic formulas


  What to change (in case you want to change the file format):
	name of the parser and of the symbols class
	  - SATHLexer, sym (several references!)
      productions and states (obviously)


*/

/* Name of the parser class to be generated */
%class SATHLexer
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
  return symbol(SATHsym.EOF);
%eofval}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n] 
Comment = "c " {InputCharacter}* {LineTerminator} 
WhiteSpace = [ \t\f] 
Problem="p " {WhiteSpace}* "sat" {WhiteSpace}* 
Number = [1-9][0-9]*
Implies = "->"

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
					    	return symbol(SATHsym.PROBLEM, number.toString()); 
					  }
  
}

<FORMULA>{
    {Implies}          { return symbol(SATHsym.IMPLIES); }
    "-"                { return symbol(SATHsym.NEG); }
    "*"                { return symbol(SATHsym.AND); }
    "+"                { return symbol(SATHsym.OR); }
    "("                { return symbol(SATHsym.LPAREN); }
    ")"                { return symbol(SATHsym.RPAREN); }

    {Number}           { return symbol (SATHsym.NUMBER, yytext());}

    {LineTerminator}   { /* just skip what was found, do nothing */  }

    {WhiteSpace}       { /* just skip what was found, do nothing */ }   

}

[^]                    { throw new Error("Illegal character <"+yytext()+ "> in line " + yyline + ", column " + yychar ); }

