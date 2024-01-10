package demo;

import java.io.IOException;
import demo.Lexer;
import demo.Token;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
   
public class main {

    public static void main(String[] args) {
    	
    	    // check command-line argument
    	        if (args.length != 1) {
    	            System.out.println("Error: Please provide a file path as a command-line argument.");
    	            System.exit(1);
    	        } else {
    	            String filepath = args[0];
    	            System.out.println("Filepath: " + filepath); // print filepath

    	            try {
    	                Path myPath = Paths.get(filepath);
    	                String content = new String(Files.readAllBytes(myPath));

    	                System.out.println("File content:");
    	                System.out.println(content);

    	                Lexer lexer = new Lexer(content); // call lex method
    	                List<Token> tokens = lexer.lex();

    	                System.out.println("Tokens:");
    	                int i = 0;
    	                while (i < tokens.size()) {
    	                    Token token = tokens.get(i);
    	                    System.out.println(token);  // print tokens
    	                    i++;
    	                }

    	            } catch (IOException e) {
    	                System.out.println("Error reading the file: " + e.getMessage());
    	            }
    	        }
    	    }
    	}


        



