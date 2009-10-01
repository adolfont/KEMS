/*
 * Created on 24/10/2003, 13:55:23 
 *
 */
package parsers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

//import JSX.ObjectWriter;

/**
 * A class for using different parsers constructed with java_cup.
 * 
 * @author Adolfo Neto
 */

public class ParserUser {

    ClassLoader _classLoader;

    /**
     * creates a parser user object using the default class loader
     *  
     */
    public ParserUser() {
        _classLoader = this.getClass().getClassLoader();
    }

    /**
     * creates a parser user using the a class loader that uses jar files
     * 
     * @param logicJarFiles
     */
    public ParserUser(String[] jarFiles) {

        try {

            URL[] jarURLs = new URL[jarFiles.length];

            for (int i = 0; i < jarFiles.length; i++) {

                // verifies existence of file
                // removes "file:"
                String name = jarFiles[i].substring(5);
                try {
                    new FileReader(name);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                jarURLs[i] = new URL(jarFiles[i]);
            }

            _classLoader = new URLClassLoader(jarURLs);

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

    public Object parseString(String lexerClassName, String parserClassName,
            String str) {

        Object lexer = null, parser = null;
        try {
            lexer = createLexerForString(lexerClassName, str);
        } catch (Exception e) {
            throw new Error("ParserUser.parseString: didn't find class "
                    + lexerClassName);
        }
        try {
            parser = createParser(parserClassName, lexer);
        } catch (Exception e) {
            throw new Error("ParserUser.parseString: didn't find class "
                    + parserClassName);
        }

        Object result = null;
        try {
            result = ((java_cup.runtime.lr_parser) parser).parse().value;
        } catch (Exception e1) {
            throw new Error(e1.getClass().getName() + " in line \""+str+"\"");
        }
        return result;
    }

    public Object parseFile(String lexerClassName, String parserClassName,
            String filename) {

        // filename = System.getProperty("user.dir") + File.separator +
        // filename;

        try {
            Object lexer = createLexerForFile(lexerClassName, filename);
            Object parser = createParser(parserClassName, lexer);

            Object result = ((java_cup.runtime.lr_parser) parser).parse().value;

            return result;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * creates a lexer object from the name of the class lexer
     * 
     * @param lexerClassName
     * @param str
     * @return the lexer object or null
     */
	public Object createLexerForString(String lexerClassName, String str) {

        try {
            Class<?> lexerClass = Class
                    .forName(lexerClassName, true, _classLoader);
            // System.out.println(lexerClass);

            Class<?>[] lexerArgsClasses = new Class[] { java.io.Reader.class };
            Object[] lexerArgs = new Object[] { new StringReader(str) };

            Constructor<?> lexerConstructor;
            // showConstructors(lexerClass);

            try {

                lexerConstructor = lexerClass.getConstructor(lexerArgsClasses);

                try {
                    Object object = null;
                    object = lexerConstructor.newInstance(lexerArgs);
                    return object;

                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
              } catch (IllegalAccessException e) {
                  throw new RuntimeException(e);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }

            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        return null;

    }

    /**
     * creates a lexer object from the name of the class lexer
     * 
     * @param lexerClassName
     * @param filename
     * @return the lexer object or null
     */
	public Object createLexerForFile(String lexerClassName, String filename) {

        try {
            Class<?> lexerClass = Class
                    .forName(lexerClassName, true, _classLoader);

            Class<?>[] lexerArgsClasses = new Class[] { java.io.Reader.class };
            Object[] lexerArgs = new Object[] { new FileReader(filename) };
            Constructor<?> lexerConstructor;

            try {

                lexerConstructor = lexerClass.getConstructor(lexerArgsClasses);

                try {
                    Object object = null;
                    object = lexerConstructor.newInstance(lexerArgs);
                    return object;

                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }

            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * creates a parse object from the name of the class parser
     * 
     * @param parserClassName
     * @param str
     * @return the parser object or null
     */
	public Object createParser(String parserClassName, Object lexer) {

        try {
            Class<?> parserClass = Class.forName(parserClassName, true,
                    _classLoader);

            Class<?>[] parserArgsClasses = new Class[] { java_cup.runtime.Scanner.class };
            Object[] parserArgs = new Object[] { lexer };
            Constructor<?> parserConstructor;

            try {

                parserConstructor = parserClass
                        .getConstructor(parserArgsClasses);

                try {
                    Object object = null;
                    object = parserConstructor.newInstance(parserArgs);

                    return object;

                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }

            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	static void showConstructors(Class<?> c) {

        System.out.println("SHOW CONSTRUCTORS FOR " + c.toString());

        Constructor<?>[] theConstructors = c.getConstructors();

        System.out.println("number: " + theConstructors.length);
        for (int i = 0; i < theConstructors.length; i++) {
            System.out.print("( ");
            Class<?>[] parameterTypes = theConstructors[i].getParameterTypes();
            for (int k = 0; k < parameterTypes.length; k++) {
                String parameterString = parameterTypes[k].getName();
                System.out.print(parameterString + " ");
            }
            System.out.println(")");
        }
    }



}