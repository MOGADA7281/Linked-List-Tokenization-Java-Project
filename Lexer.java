package demo;

import java.util.ArrayList;

import java.util.List;
import demo.StringHandler;
import demo.Token.TokenType;
import java.util.HashMap;


public class Lexer {
	private StringHandler strhan;
	public int linumb;
	public int charpos = 0;
	private List<Token> tokens;
	
	//Hashmap for keywords
	private final HashMap<String, TokenType> keywordMap = new HashMap<>();
	//Hashmap for two character symbols
	private final HashMap<String, TokenType>twocharsymMap = new HashMap<>();
	//Hashmap for one character symbols
	private final HashMap<Character, TokenType> onecharsymMap = new HashMap<>();
	
	public Lexer(String docu) {
		 System.out.println("Lexer created with content: " + docu);
		strhan = new StringHandler(docu);
		tokens = new ArrayList<>();
		populateKeyword();
		popuTwoCharsymMap();
		popuOneCharSymMap();
		
	}
	
	private void addKeyW(String kw) {
		keywordMap.put(kw,TokenType.KEYWORD );
		
	}
	
	private void populateKeyword( ) {
		addKeyW("while");
		addKeyW("if");
		addKeyW("do");
		addKeyW("for");
		addKeyW("break");
		addKeyW("continue");
		addKeyW("else");
		addKeyW("return");
		addKeyW("BEGIN");   // mapping keywords 
		addKeyW("END");
		addKeyW("print");
		addKeyW("printf");
		addKeyW("next");
		addKeyW("in");
		addKeyW("delete");
		addKeyW("getline");
		addKeyW("exit");
		addKeyW("nextfile");
		addKeyW("function");
		
	}
	
	private void symbolHelper(String symbol, TokenType toktyp ) {
		twocharsymMap.put(symbol, toktyp);
	}
	
	private void popuTwoCharsymMap() {
		symbolHelper(">=", TokenType.GREATER_THAN_EQUAL);
		symbolHelper("++", TokenType.INCREMENT);
		symbolHelper("--", TokenType.DECREMENT);
		symbolHelper("<=", TokenType.LESS_THAN_EQUAL);
		symbolHelper("==", TokenType.EQUAL_EQUAL);
		symbolHelper("!=", TokenType.NOT_EQUAL);
		symbolHelper("^=", TokenType.CARET_EQUAL);
		symbolHelper("%=", TokenType.PERCENT_EQUAL);
		symbolHelper("*=", TokenType.ASTERISK_EQUAL);
		symbolHelper("/=", TokenType.SLASH_EQUAL);  //mapping two character symbols
		symbolHelper("+=", TokenType.PLUS_EQUAL);
		symbolHelper("-=", TokenType.MINUS_EQUAL);
		symbolHelper("!~", TokenType.NOT_TILDE);
		symbolHelper("&&", TokenType.LOGICAL_AND);
		symbolHelper(">>", TokenType.RIGHT_SHIFT);
		symbolHelper("||", TokenType.LOGICAL_OR);
	}
	
	private void addSymb(char symbol, TokenType toketpe) {
        onecharsymMap.put(symbol, toketpe);
    }
	
	 private void popuOneCharSymMap() {
	        // Populate the symbol map with single-character symbols and their token types
	        addSymb('{', TokenType.LEFT_BRACE);
	        addSymb('}', TokenType.RIGHT_BRACE);
	        addSymb('[', TokenType.LEFT_BRACKET);
	        addSymb(']', TokenType.RIGHT_BRACKET);
	        addSymb('(', TokenType.LEFT_PARENTHESIS);
	        addSymb(')', TokenType.RIGHT_PARENTHESIS);
	        addSymb('$', TokenType.DOLLAR);
	        addSymb('~', TokenType.TILDE);
	        addSymb('=', TokenType.EQUALS);
	        addSymb('<', TokenType.LESS_THAN);
	        addSymb('>', TokenType.GREATER_THAN);
	        addSymb('!', TokenType.EXCLAMATION);
	        addSymb('+', TokenType.PLUS);
	        addSymb('^', TokenType.CARET);
	        addSymb('-', TokenType.MINUS);
	        addSymb('?', TokenType.QUESTION_MARK);
	        addSymb(':', TokenType.COLON);
	        addSymb('*', TokenType.ASTERISK);
	        addSymb('/', TokenType.SLASH);
	        addSymb('%', TokenType.PERCENT);
	        addSymb(';', TokenType.SEPARATOR);
	        addSymb('\n', TokenType.SEPARATOR);
	        addSymb('|', TokenType.PIPE);
	        addSymb(',', TokenType.COMMA);
	    }
	
    public List<Token> lex(){
   	 System.out.println("Lexing started...");
   	
   	while(!strhan.IsDone()) {
   		char currChar = strhan.Peek(0);
   		
   		if (currChar == '\0') {
   	        // Skip null character
   	        strhan.GetChar();
   	        charpos++;
   	        continue;
   	    }

   		
   		if(currChar == ' ' || currChar == '\t' || currChar == '\0') {
   			
   			strhan.GetChar();
   			charpos++;
   		}
   		else if (currChar == '\n' ) {
   			tokens.add(new Token(TokenType.SEPARATOR, " ", linumb, charpos));
   			linumb++;
   			charpos = 0;
   			
   		}
   		else if (currChar ==  '\r') {
   			strhan.GetChar();
   		}else if (currChar == '#') {
            // Skip the rest of the line (comment)
            while (!strhan.IsDone()) {
            	if(strhan.Peek(0) != '\n') 
                strhan.GetChar();
            }
   		}
   		else if (currChar == '"') {
            // Handle string literal
            HandleStringLiteral();
            break;
        }
   	// Inside your lex() method
   	// ...
   	else if (currChar == '`') {
   	    // Handle pattern enclosed in backticks
   	    HandlePattern();
   	}
   	
   		
   	else if (currChar == '>' || currChar == '+' || currChar == '-' || currChar == '<' || currChar == '=' || currChar == '!' || currChar == '^' || currChar == '%' || currChar == '*' || currChar == '/' ||  currChar == '!' || currChar == '~' || currChar == '&' || currChar == '>' || currChar == '|') {
   	    String symbol = processSymbol();
   	    TokenType tokenType = twocharsymMap.get(symbol);
   	 if (tokenType == null) {
         tokenType = onecharsymMap.get(String.valueOf(currChar));
     }
     tokens.add(new Token(tokenType, symbol, linumb, charpos));
 }
   		
   		


   		else if (currChar == '\0'){
   			charpos++;
   		}
   		else if ((currChar >= 'A' && currChar <= 'Z') || (currChar >= 'a' && currChar <= 'z')) {
               
               String word = processWord();

               tokens.add(new Token(TokenType.WORD, word, linumb, charpos));
   	}
   		else if (currChar > '0' && currChar < '9') {
   		    String digit = processDigit();

   		    tokens.add(new Token(TokenType.NUMBER, digit, linumb, charpos));
   		}

   		else {
   			System.out.println("Unexpected character (Unicode code point): " + (int) currChar);

   		    throw new IllegalArgumentException(); 
   		}

   	
   }   
   	return tokens;
   }
    
   
    private void HandleStringLiteral() {
        StringBuilder lit = new StringBuilder();
        strhan.GetChar(); // Consume the opening double-quote

        while (!strhan.IsDone()) {
            char currChar = strhan.GetChar();

            if (currChar == '\\') {
                // Check for escaped characters within the string
                char currPeak = strhan.Peek(0);
                while (!strhan.IsDone()) {
                	if(strhan.IsDone()) {
                		break;
                	}
                    if (currPeak == '"') {
                        // Escaped double-quote: \" within the string
                        lit.append(currPeak); // Append escaped double-quote
                        strhan.GetChar(); // Consume the escaped double-quote
                    } else if (currPeak == '\\') {
                        // Escaped backslash: \\ within the string
                        lit.append(currPeak); // Append escaped backslash
                        strhan.GetChar(); // Consume the escaped backslash
                    } else {
                        // Regular backslash within the string
                        lit.append(currChar);
                    }
                }
            } else if (currChar == '"') {
                // End of string literal
                break;
            } else {
                // Regular character within the string
                lit.append(currChar);
            }
        }

        tokens.add(new Token(TokenType.STRINGLITERAL, lit.toString(), linumb, charpos));
    }


    
    
    private void HandlePattern() {
        StringBuilder pattern = new StringBuilder();
        strhan.GetChar(); // Consume the opening backtick

        while (strhan.IsDone() == false) {
            char currChar = strhan.GetChar();

            if (currChar == '\\') {
                // Check for escaped character
                char nextChar = strhan.Peek(0);
                if (nextChar == '`') {
                    // It's an escaped backtick `\` within the pattern
                    pattern.append(nextChar); // Append escaped backtick
                    strhan.GetChar(); // Consume the escaped backtick
                } else {
                    // Not an escaped backtick, treat `\` as a regular character
                    pattern.append(currChar);
                }
            } else if (currChar == '`') {
                // End of pattern
                break;
            } else {
                pattern.append(currChar); // Append the character to the pattern
            }
        }

        tokens.add(new Token(TokenType.PATTERN, pattern.toString(), linumb, charpos));
    }


    
    
    private String processWord() {
        StringBuilder wb = new StringBuilder();

        char nextChar;
        while (!strhan.IsDone() && (nextChar = strhan.Peek(0)) != '\0' &&
               ((nextChar >= 'a' && nextChar <= 'z') || (nextChar >= 'A' && nextChar <= 'Z') || nextChar == '_')) {
            wb.append(strhan.GetChar());
        }

        String wd = wb.toString();
        
        TokenType toktyp = keywordMap.get(wd);
        
        if (toktyp == null) {
            toktyp = TokenType.WORD;
        }

        return new Token(toktyp, wd, linumb, charpos).toString();
    }

    private String processDigit() {
        StringBuilder nB = new StringBuilder();

        char ntChar;
        while (!strhan.IsDone() && (ntChar = strhan.Peek(0)) != '\0' &&
               ((ntChar >= '0' && ntChar <= '9') || ntChar == '.')) {
            char curChar = strhan.GetChar();
            nB.append(curChar);
        }

        return nB.toString();
    }

   public String processSymbol() {
	    String twoCharSym = strhan.PeekString(charpos, 2);

	    // Check the two-character hash map
	    if (twocharsymMap.get(twoCharSym) != null) {
	        return twoCharSym;
	    } else {

	        String oneCharSym = strhan.PeekString(charpos, 1);

	        // Check the one-character hash map
	        if (onecharsymMap.get(oneCharSym) != null) {
	            return oneCharSym;
	        } else {
	            // No symbol found, return null
	            return null;
	        }
	    }
	}


	
	




}

