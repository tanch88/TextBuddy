import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextBuddy {
	
	// message
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_ADD = "added to %1$s: “%2$s”";
	private static final String MESSAGE_DELETE = "deleted from %1$s: “%2$s”";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String MESSAGE_EMPTY = "%1$s is empty";
	private static final String MESSAGE_INVALID = "command is invalid";
	private static final String MESSAGE_NO_VALUE = "please indicate word to %1$s";
	private static final String MESSAGE_NO_FILE = "file not available";
			
	private static Data myList;
	private static Storage file;
	
    public static void main(String [] args) throws IOException {
    	initialize(args);
		runMain();   
    }

	public static void initialize(String[] args) throws IOException{
		getFile(args);
		myList = new Data(file.getData());
	}
	
	private static void getFile(String[] args) {
		if(args.length == 1){
			file = new Storage(args[0]);
			System.out.println(String.format(MESSAGE_WELCOME, file));
		}
		else{
			System.out.println(MESSAGE_NO_FILE);
			System.exit(0);
		}
	}
	
	public static void runMain() throws IOException, FileNotFoundException, Error {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)); 
        while(true){
	        String input = bufferedReader.readLine();
	        
			String feedback = executeCommand(input);
			System.out.println(feedback);
        }
	}

	public static String executeCommand(String input)
			throws IOException, FileNotFoundException, Error {
		
        Parser ps = new Parser(input);
        
		COMMAND_TYPE commandType = ps.getCommandType();
		
		switch(commandType){
		case DISPLAY :
			return display();
		case ADD:
			return add(ps.getMessage());
		case DELETE :
			return delete(ps.getMessage());
		case CLEAR :
			return clear();
		case SORT:
			return sort();
		case SEARCH:
			return search(ps.getMessage());
		case EXIT :
			System.exit(0);
		default :
			throw new Error(MESSAGE_INVALID);
		}
	}

	private static String display() {
		if(myList.isEmpty()){
			return String.format(MESSAGE_EMPTY, file);
		}
		else{
			return myList.toString();
		}
	}
			
	private static String add(String input) throws IOException {
        file.append(input); 
        myList.add(input);
        
        return String.format(MESSAGE_ADD, file, input);
	}
		
	private static String delete(String message) throws IOException{ 
		int deleteNum = Integer.parseInt(message);
		
		String deleteText = myList.remove(deleteNum);
		file.write(myList.getData());
		
		return String.format(MESSAGE_DELETE, file, deleteText);
	}
		
	public static String clear() throws FileNotFoundException{
		myList.clear();
		file.clear();
		
		return String.format(MESSAGE_CLEAR, file);
	}
	
	
	private static String sort() {
		if(!myList.isEmpty()){
			myList.sort();
			return myList.toString();
		}
		return String.format(MESSAGE_EMPTY, file);
	}
	

	private static String search(String message) {
		return String.format(MESSAGE_NO_VALUE, "search");
	}
		
}