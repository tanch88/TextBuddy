import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextBuddy {
	
	private static Controller c;
	
    public static void main(String [] args) throws IOException {
    	
    	c = new Controller(args);
    	initialize();
    	runMain();  
    	
    }

	private static void initialize() {
		String welcome = c.getWelcome();
    	System.out.println(welcome);
    	
    	if(!c.hasFile()){
    		System.exit(0);
    	}
	}
	
	private static void runMain() throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
		while(true){
		    String input = bufferedReader.readLine();
		    
			String feedback = c.executeCommand(input);
			System.out.println(feedback);
		}
	}
}