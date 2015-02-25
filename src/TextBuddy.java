import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TextBuddy {
	
	// message
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_ADD = "added to %1$s: “%2$s”";
	private static final String MESSAGE_DELETE = "deleted from %1$s: “%2$s”";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String MESSAGE_EMPTY = "%1$s is empty";
	private static final String MESSAGE_INVALID = "command is invalid";
	private static final String MESSAGE_NO_FILE = "file not available";
	
	// command types
	enum COMMAND_TYPE {
		DISPLAY, ADD, DELETE, CLEAR, EXIT, INVALID
	}
	
	private static final int INPUT_SPLIT_SIZE = 2;
	private static final int INPUT_SPLIT_FIRST = 0;
	private static final int INPUT_SPLIT_SECOND = 1;
	
	public static ArrayList<String> internalFile;
	public static String file;
	
    public static void main(String [] args) throws IOException {
    	
    	initialize(args);
		runMain();   
		
    }

	public static void initialize(String[] args) throws IOException {
		getFile(args);
        filetoInternalFile();
	}

	public static void runMain() throws IOException, FileNotFoundException,
			Error {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)); 
        while(true){
	        String input = bufferedReader.readLine();
	        
			String feedback = executeCommand(input);
			System.out.println(feedback);
        }
	}

	public static String executeCommand(String input)
			throws IOException, FileNotFoundException, Error {
		
        String command = toFirstWord(input);
        String message = removeFirstWord(input);
        
		COMMAND_TYPE commandType = determineCommandType(command);
		
		switch(commandType){
		case DISPLAY :
			return display();
		case ADD:
			return add(message);
		case DELETE :
			return delete(message);
		case CLEAR :
			return clear();
		case INVALID :
			return String.format(MESSAGE_INVALID);
		case EXIT :
			System.exit(0);
		default :
			throw new Error("Unrecognized command type");
		}
	}

	private static void getFile(String[] args) {
		if(args.length == 1){
			file = args[0];
			System.out.println(String.format(MESSAGE_WELCOME, file));
		}
		else{
			System.out.println(MESSAGE_NO_FILE);
			System.exit(0);
		}
	}
    
	private static void filetoInternalFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        internalFile = new ArrayList<String>();
		
        String line = null;

        while((line = bufferedReader.readLine()) != null) {
            internalFile.add(line);
        }    

        bufferedReader.close();   
	}
	
	private static COMMAND_TYPE determineCommandType(String commandTypeString) {
		if (commandTypeString == null)
			throw new Error("command type string cannot be null!");

		if (commandTypeString.equalsIgnoreCase("display")) {
			return COMMAND_TYPE.DISPLAY;
		} else if (commandTypeString.equalsIgnoreCase("add")) {
			return COMMAND_TYPE.ADD;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
			return COMMAND_TYPE.DELETE;
		} else if (commandTypeString.equalsIgnoreCase("clear")) {
			return COMMAND_TYPE.CLEAR;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
		 	return COMMAND_TYPE.EXIT;
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}

	private static String display() {
		if(isInternalFileEmpty()){
			return String.format(MESSAGE_EMPTY, file);
		}
		else{
			return printInternalFile();
		}
	}

	private static boolean isInternalFileEmpty(){
		return(internalFile.size() <= 0);
	}
		
	private static String printInternalFile() {
		String output = "";
		
		int numbering = 0;
		
		for(int i = 0; i < internalFile.size(); i++){
			numbering = i + 1;
			output += numbering + ". " + internalFile.get(i);
			
			int lastIndex = internalFile.size() - 1;
			if(i != lastIndex){
				output += "\n";
			}
		}
		
		return output;
	}
	
	private static String add(String input) throws IOException {
        appendToFile(input); 
        addToInternalFile(input);
        
        return String.format(MESSAGE_ADD, file, input);
	}

	private static void internalFileToFile() throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        
		for(int i = 0; i < internalFile.size(); i++){
			bufferedWriter.write(internalFile.get(i));
        	bufferedWriter.newLine();
		}

        bufferedWriter.close();
	}
	
	public static void appendToFile(String input) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        
        bufferedWriter.write(input);
        bufferedWriter.newLine();

        bufferedWriter.close();
	}
	
	public static void addToInternalFile(String input){
		internalFile.add(input);
	}
	
	private static String delete(String message) throws IOException{ 
		int deleteNum = Integer.parseInt(message);
		//numeric index to array index
		int deleteIndex = deleteNum - 1;
		
		String deleteText = deleteItemFromInternalFile(deleteIndex);
		internalFileToFile();
		
		return String.format(MESSAGE_DELETE, file, deleteText);
	}
	
	private static String deleteItemFromInternalFile(int deleteIndex){
		return internalFile.remove(deleteIndex);
	}
	
	public static String clear() throws FileNotFoundException{
		clearInternalFile();
		clearFile();
		
		return String.format(MESSAGE_CLEAR, file);
	}
	
	private static void clearInternalFile(){
		internalFile.clear();
	}
	
	private static void clearFile() throws FileNotFoundException{
		PrintWriter printWriter = new PrintWriter(file);
		printWriter.close();
	}
	
	private static String[] splitFirstWord(String input){
		String[] splitString = input.split(" ", INPUT_SPLIT_SIZE);
		
		return splitString;
	}
	
	private static String toFirstWord(String input){
		String[] splitString = splitFirstWord(input);
		String firstWord = splitString[INPUT_SPLIT_FIRST];
		
		return firstWord;
	}
	
	private static String removeFirstWord(String input){
		String newWord = "";
		
		String[] splitString = splitFirstWord(input);
		
		if(splitString.length == INPUT_SPLIT_SIZE){
			newWord = splitString[INPUT_SPLIT_SECOND];
		}
		
		return newWord;
	}
		
}