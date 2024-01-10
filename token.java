package demo;

public class Token {

	
	// Token Types 
    public enum TokenType {
        WORD, NUMBER, SEPARATOR, KEYWORD, STRINGLITERAL, PATTERN, GREATER_THAN_EQUAL,INCREMENT,          // ++
        DECREMENT, LESS_THAN_EQUAL, EQUAL_EQUAL, NOT_EQUAL, CARET_EQUAL, PERCENT_EQUAL,
        ASTERISK_EQUAL, SLASH_EQUAL, PLUS_EQUAL, MINUS_EQUAL, NOT_TILDE, LOGICAL_AND,RIGHT_SHIFT, 
        LOGICAL_OR, LEFT_BRACE, RIGHT_BRACE, LEFT_BRACKET, RIGHT_BRACKET,
        LEFT_PARENTHESIS, RIGHT_PARENTHESIS, DOLLAR, TILDE,
        EQUALS, LESS_THAN, GREATER_THAN, EXCLAMATION,
        PLUS, CARET, MINUS, QUESTION_MARK,
        COLON, ASTERISK, SLASH, PERCENT, 
         PIPE, COMMA, UNKNOWN      
               
        
    }

    private TokenType token;
    private String value;    
    private int lineNumb;
    private int charpos;

    public Token(TokenType tk, int ln, int cp) {
        token = tk;                            // token constructor
        lineNumb = ln;                          //Initialize line number and chracter position
        charpos = cp;                           
    }

    public Token(TokenType tk, String vl, int ln, int cp) {
        token = tk;
        value = vl;      //  Initialize string value
        
    }

    public String toString() {
        if (value == null) {
            return "type: " + token;
        } else {
            return "type: " + token + "( " + value + " ) " ;  // return value with tokentype
        }
    }

	public TokenType getType() {
		// TODO Auto-generated method stub
		return token;
	}
}
