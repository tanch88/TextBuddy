import java.util.ArrayList;
import java.util.Collections;

public class Data {

	ArrayList<String> data;
	
	public Data(ArrayList<String> data){
		this.data = data;
	}
	
	public ArrayList<String> getData(){
		return data;
	}
	
	public void add(String input){
		data.add(input);
	}
	
	public String remove(int deleteNum){
		int deleteIndex = deleteNum - 1;
		
		return data.remove(deleteIndex);
	}
	
	public void clear(){
		data.clear();
	}
	
	public boolean isEmpty(){
		return data.size() <= 0;
	}
	
	public void sort(){
		Collections.sort(data);
	}
	
	public String toString() {
		String output = "";
		
		int numbering = 0;
		
		for(int i = 0; i < data.size(); i++){
			numbering = i + 1;
			output += numbering + ". " + data.get(i);
			
			int lastIndex = data.size() - 1;
			if(i != lastIndex){
				output += "\n";
			}
		}
		
		return output;
	}
}
