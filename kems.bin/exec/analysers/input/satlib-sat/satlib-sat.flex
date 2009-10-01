package parsers;
import java_cup.runtime.*;

%%

/*

  Adolfo Gustavo Serra Seca Neto, October 2003
  Lexical analyser for classical propositional logic formulas


  What to change (in case you want to change the file format):
	name of the parser and of the symbols class
	  - SATLexer, SATsym (several references!)
      productions and states (obviously)


*/

/* Name of the parser class to be generated */
%class SATLexer
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
  return symbol(SATsym.EOF);
%eofval}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n] 
WhiteSpace = [ \t\f] 
String = [a-z_A-Z_1-9][a-z_A-Z_0-9_,]*
Implies = "->"

%%

/* beginning of the lexical analyser */
<YYINITIAL> {

    {Implies}          { return symbol(SATsym.IMPLIES); }
    "-"                { return symbol(SATsym.NEG); }
    "*"                { return symbol(SATsym.AND); }
    "+"                { return symbol(SATsym.OR); }
    "("                { return symbol(SATsym.LPAREN); }
    ")"                { return symbol(SATsym.RPAREN); }

    {String}           { return symbol (SATsym.STRING, yytext());}

    {LineTerminator}   { /* just skip what was found, do nothing */  }

    {WhiteSpace}       { /* just skip what was found, do nothing */ }   

}

[^]                    { throw new Error("Illegal character <"+yytext()+ "> at line " + yyline + ", column " + yychar ); }


