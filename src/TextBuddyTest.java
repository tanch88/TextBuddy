import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class TextBuddyTest {

	@Test
	public void addTest() throws IOException{
		String[] args = {"mytextfile.txt"};
		TextBuddy.initialize(args);
		
		add();
		
		TextBuddy.clear();
	}
	
	@Test
	public void deleteTest() throws IOException{
		String[] args = {"mytextfile.txt"};
		TextBuddy.initialize(args);
		
		add();
		delete();
		
		TextBuddy.clear();
	}
	
	@Test
	public void clearTest() throws IOException{
		String[] args = {"mytextfile.txt"};
		TextBuddy.initialize(args);
		
		add();
		clear();
		
		TextBuddy.clear();
	}
	
	private void add() throws IOException {			
		testOneCommand("first add", "added to mytextfile.txt: “little brown fox”", "add little brown fox");	
		
		testOneCommand("display one item", "1. little brown fox", "display");	

		testOneCommand("second add", "added to mytextfile.txt: “jumped over the moon”", "add jumped over the moon");
		
		testOneCommand("display two item", "1. little brown fox\n2. jumped over the moon", "display");
	}
	
	private void delete() throws IOException {	
		
		testOneCommand("delete an item", "deleted from mytextfile.txt: “jumped over the moon”", "delete 2");
		
		testOneCommand("display", "1. little brown fox", "display");
	
	}
	
	private void clear() throws IOException {
	
		testOneCommand("clear", "all content deleted from mytextfile.txt", "clear");

		testOneCommand("display empty", "mytextfile.txt is empty", "display");

	}
	
	private void testOneCommand(String description, String expected, String command) throws FileNotFoundException, IOException, Error{
		assertEquals(description, expected, TextBuddy.executeCommand(command)); 
	}

}
