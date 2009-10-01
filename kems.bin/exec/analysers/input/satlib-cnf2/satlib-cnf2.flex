package satcnf2;
import java_cup.runtime.*;

%%

/*

  Adolfo Gustavo Serra Seca Neto, October 2004
  Lexical analyser for classical propositional logic formulas
  in SATLIB CNF format

  What to change (in case you want to change the file format):
	name of the parser and of the symbols class
	  - satcnf2Lexer, satcnf2sym (several references!)
      productions and states (obviously)

  How to produce the lexer:

  - Run the command:

  	java JFlex.Main satlib-cnf.flex

  	which will create the following file:

	  	satcnf2Lexer.java



*/

/* Name of the parser class to be generated */
%class satcnf2Lexer
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
  return symbol(satcnf2sym.EOF);
%eofval}


LineTerminator = \r|\n|\r\n
Comment = "c " {WhiteSpace}* {InputCharacter}* {LineTerminator}
InputCharacter = [^\r\n]
WhiteSpace = [ \t\f]
Problem="p " {WhiteSpace}* "cnf" {WhiteSpace}*
Number = [1-9][0-9]*
Zero = 0
%state COMMENTS, PROBLEM_VARIABLES, PROBLEM_CLAUSES, FORMULA, FIM

%%

/* beginning of the lexical analyser */
<YYINITIAL> {
    {Comment}		{
					yybegin (COMMENTS);
				}

/* if it is a problem line, goes to the state PROBLEM_VARIABLES */
    {Problem}           {
					yybegin(PROBLEM_VARIABLES);
				  	number.setLength(0);
				}

}

/* reads comment lines */
<COMMENTS>{

    {Comment}		{  }
    "c" {LineTerminator} { }

/* if it is a problem line, goes to the state PROBLEM_VARIABLES*/
    {Problem}                 {
				  	yybegin(PROBLEM_VARIABLES);
				  	number.setLength(0);
				}

}

/* reads a problem line and then goes back to initial */
<PROBLEM_VARIABLES> {

  {Number}         { 		number.append( yytext() ); }
  {Zero}         { 		number.append( yytext() ); }

  {WhiteSpace}* {
						yybegin(PROBLEM_CLAUSES);
					    	return symbol(satcnf2sym.VARIABLES, number.toString());
					  }

}

<PROBLEM_CLAUSES> {

  {Number}         { 		number.append( yytext() ); }
  {Zero}         { 		number.append( yytext() ); }

  {WhiteSpace}*{LineTerminator} {
						yybegin(FORMULA);
					    	return symbol(satcnf2sym.CLAUSES, number.toString());
					  }

}



<FORMULA>{
    "-"                { return symbol(satcnf2sym.NEG); }
    {Number}           { return symbol (satcnf2sym.NUMBER, yytext());}
    {Zero}           { return symbol (satcnf2sym.ENDOFCLAUSE);}

    {LineTerminator}   { /* just skip what was found, do nothing */  }

    {WhiteSpace}       { /* just skip what was found, do nothing */ }

    "%" {WhiteSpace}* {LineTerminator} {
    			yybegin(FIM);
		    	return symbol(satcnf2sym.FINAL);
    		}

}
<FIM>{
   {Number} { return symbol (satcnf2sym.NUMBER, yytext());}
   {Zero} { return symbol (satcnf2sym.NUMBER, yytext());}
   {LineTerminator} {}
   {WhiteSpace} {}
}

[^]                    { throw new Error("Illegal character <"+yytext()+ "> in line " + yyline + ", column " + yychar ); }



