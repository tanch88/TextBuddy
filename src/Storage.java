import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Storage {

	String file;
	
	public Storage(){
		this.file = "";
	}
	
	public Storage(String file){
		this.file = file;
	}
	
	public boolean isEmpty(){
		return file == "";
	}
	
	public ArrayList<String> getData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        ArrayList<String> data = new ArrayList<String>();
		
        String line = null;

        while((line = bufferedReader.readLine()) != null) {
        	data.add(line);
        }    

        bufferedReader.close(); 
        
        return data;
	}
	
	public void write(ArrayList<String> data) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        
		for(int i = 0; i < data.size(); i++){
			bufferedWriter.write(data.get(i));
        	bufferedWriter.newLine();
		}

        bufferedWriter.close();
	}
	
	public void append(String input) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        
        bufferedWriter.write(input);
        bufferedWriter.newLine();

        bufferedWriter.close();
	}
	
	public void clear() throws FileNotFoundException{
		PrintWriter printWriter = new PrintWriter(file);
		printWriter.close();
	}
	
	public String toString(){
		return file;
	}
	
}
