import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {
	
	// message
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_ADD = "added to %1$s: “%2$s”";
	private static final String MESSAGE_DELETE = "deleted from %1$s: “%2$s”";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String MESSAGE_EMPTY = "%1$s is empty";
	private static final String MESSAGE_EMPTY_SEARCH = "no text matches “%1$s”";
	private static final String MESSAGE_INVALID = "command is invalid";
	private static final String MESSAGE_NO_VALUE = "please indicate word to %1$s";
	private static final String MESSAGE_NO_FILE = "file not available";
			
	private Data myList;
	private Storage file;
	

	public Controller(String[] args) throws IOException{
		if(args.length == 1){
			file = new Storage(args[0]);
		}
		else{
			file = new Storage();
		}
		
		myList = new Data(file.getData());
	}
	
	public String getWelcome() {
		if(!file.isEmpty()){
			return String.format(MESSAGE_WELCOME, file);
		}
		else{
			return MESSAGE_NO_FILE;
		}
	}
	
	public boolean hasFile(){
		return file.isEmpty();
	}
	
	public String executeCommand(String input)
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

	private String display() {
		if(myList.isEmpty()){
			return String.format(MESSAGE_EMPTY, file);
		}
		else{
			return myList.toString();
		}
	}
			
	private String add(String input) throws IOException {
        file.append(input); 
        myList.add(input);
        
        return String.format(MESSAGE_ADD, file, input);
	}
		
	private String delete(String message) throws IOException{ 
		int deleteNum = Integer.parseInt(message);
		
		String deleteText = myList.remove(deleteNum);
		file.write(myList.getData());
		
		return String.format(MESSAGE_DELETE, file, deleteText);
	}
		
	public String clear() throws FileNotFoundException{
		myList.clear();
		file.clear();
		
		return String.format(MESSAGE_CLEAR, file);
	}
	
	
	private String sort() {
		if(!myList.isEmpty()){
			myList.sort();
			return myList.toString();
		}
		return String.format(MESSAGE_EMPTY, file);
	}
	

	private String search(String word) {
		if(word.equals("")){
			return String.format(MESSAGE_NO_VALUE, "search");
		}
		else if(myList.isEmpty()){
			return String.format(MESSAGE_EMPTY, file);
		}
		else{
			Data searchResult = myList.search(word);
			
			if(!searchResult.isEmpty()){
				return searchResult.toString();
			}
			else{
				return String.format(MESSAGE_EMPTY_SEARCH, word);
			}
		}
	}
		
}