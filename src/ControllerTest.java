import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class ControllerTest {
	private static Controller c;
	
	@Test
	public void clearTest() throws IOException{
		initialize();
		
		add();
		clear();
	}
	
	@Test
	public void addTest() throws IOException{
		initialize();
		
		add();
		
		clear();
	}
	
	@Test
	public void deleteTest() throws IOException{
		initialize();
		
		add();
		delete();
		
		clear();
	}
		
	@Test
	public void sortTest() throws IOException{
		initialize();
		
		sort();
		
		clear();
	}
	
	@Test
	public void searchTest() throws IOException{
		initialize();
		
		search();
		
		clear();
	}

	private void search() throws IOException{
		testOneCommand("no search condition", "please indicate word to search", "search");
		
		testOneCommand("list is empty", "mytextfile.txt is empty", "search little");
		
		add();
		
		testOneCommand("search one item", "1. little brown fox", "search little");
		
		testOneCommand("add one item", "added to mytextfile.txt: “this little blue fox”", "add this little blue fox");

		testOneCommand("search two item", "1. little brown fox\n2. this little blue fox", "search little");

		testOneCommand("search not found", "no text matches “dino”", "search dino");
	}

	private void sort() throws FileNotFoundException, IOException, Error {
		testOneCommand("list is empty", "mytextfile.txt is empty", "sort");
		
		add();
		
		testOneCommand("sort item", "1. jumped over the moon\n2. little brown fox", "sort");
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
	
	public static void initialize() throws IOException{
		String[] args = {"mytextfile.txt"};
		c = new Controller(args);
	}
	
	private void testOneCommand(String description, String expected, String command) throws FileNotFoundException, IOException, Error{
		assertEquals(description, expected, c.executeCommand(command)); 
	}

}
