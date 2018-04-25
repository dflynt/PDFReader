import java.util.ArrayList;
import java.util.Arrays;
public class JSONReader {
	
	public JSONReader() {}
	
	/*
	 * INPUT: Stringified json file, label we want to find
	 * valueOnNextLine() searches for the specific label in
	 * the JSON file and once found will retrieve the value, 
	 * corresponding to the label, on the next line.
	 * OUTPUT: new Box object with the new label/value pair
	 */
	public Box valueOnNextLine(String[] file, String labelToFind) {
		String label = "";
		String value = "";
		for(int fileArrIndex = 0; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				
				//The | VALOR TOTAL label is an anomaly in the CaboDeSanto Template
				//it has two values side by side.
				if(labelToFind.equals("| VALOR TOTAL")) {
					label = file[fileArrIndex].substring(17);
					//substring removes the VLR. UNITÁRIO found in the file
					value = file[fileArrIndex + 1].split(" ")[0];
					break;
				}
				
				
				//The OUTRAS INFORMACOES label is also an anomaly in the CaboDeSanto Template
				//It is its own section with multiple lines as its value
				else if(labelToFind.equals("OUTRAS INFOR")) {
					label = file[fileArrIndex];
					//for loop to compile all the values to the value variable
					for(int i = fileArrIndex + 1; i < file.length; i++) {
						value += file[i] + " - ";
					}
					break;
				}
				
				//No anomaly, value is on the next line as usual
				else {
					label = file[fileArrIndex];
					value = file[fileArrIndex + 1];
					break;
				}
			}
		}
		return new Box(label, value);
	}
	/*
	 * Similar to valueOnNextLine() above
	 * INPUT: Stringified json file, label we want to find,
	 * 			the index we want to start on to bypass duplicates
	 * 
	 * valueOnNextLine() searches for the specific label in
	 * the JSON file and once found will retrieve the value, 
	 * corresponding to the label, on the next line.
	 * 
	 * The difference is that we can start farther into the file with
	 * startingIndex. This allows us to bypass duplicate labels
	 * EX: In many of the files there are > 1 instances of CNPJ/CPF
	 * With starting index, we can skip the first one (string comparison 
	 * will pick it up) and look for the specific instance of the label we want
	 * OUTPUT: new Box object with the new label/value pair
	 */
	public Box valueOnNextLine(String[] file, String labelToFind, int startingIndex) {
		String label = "";
		String value = "";
		for(int fileArrIndex = startingIndex; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				label = file[fileArrIndex];
				value = file[fileArrIndex + 1];
				break;
			}
		}
		return new Box(label, value);
	}
	
	/*
	 * INPUT: Stringified json file, label we want to find, 
	 * 		  the array index of the value we want
	 * valueOnLaterLines() searches for the specific label in
	 * the JSON file and once found will retrieve the value, 
	 * corresponding to the label, on however many indices passed the value
	 * is in relation to the label in the array.
	 * 
	 * OUTPUT: new Box object with the new label/value pair
	 */
	public Box valueOnLaterLines(String[] file, String labelToFind, int indexOfValue) {
		String label = "";
		String value = "";
		for(int fileArrIndex = 0; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				label = file[fileArrIndex];
				value = file[fileArrIndex + indexOfValue];
				break;
			}
		}
		return new Box(label, value);
	}
	/*
	 * INPUT: Stringified json file, label we want to find, 
	 * 		  the array index of the value we want,
	 * 			the index we want to start on to bypass duplicates
	 * 
	 * valueOnLaterLines() searches for the specific label in
	 * the JSON file and once found will retrieve the value, 
	 * corresponding to the label, on however many indices passed the value
	 * is in relation to the label in the array.
	 * 
	 * Just like the overloaded valueOnNextLine(), we can bypass duplicates 
	 * and go to the one we want
	 * OUTPUT: new Box object with the new label/value pair
	 */
	
	public Box valueOnLaterLines(String[] file, String labelToFind, 
								int indexOfValue, int startingIndex) {
		String label = "";
		String value = "";
		for(int fileArrIndex = startingIndex; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				label = file[fileArrIndex];
				value = file[fileArrIndex + indexOfValue];
				break;
			}
		}
		return new Box(label, value);
	}
	
	/*
	 * INPUT: Stringified json file, label we want to find, 
	 * 			string we want to split the line on
	 * valueOnSameLine_MultipleWords() searches for the specific label in
	 * the JSON file and once found will retrieve the index that holds
	 * BOTH the label and value. They're in the same index due
	 * to how google vision's API parses the image
	 * 
	 * Once we have the label found, the value is separated with a ':' or
	 * a ' ' so we pass an argument to split on the specific character.\
	 * EX: Valor Total do ISS: $123.45\n
	 * Split on ' ' will yield
	 * [Valor][Total][do][ISS:][$123.45] 
	 * OUTPUT: new Box object with the new label/value pair
	 */
	public Box valueOnSameLine_MultipleWords(String[] file, String labelToFind, String splitOnVal) {
		String[] lineArr = new String[10];
		String label = "";
		String value = "";
		for(int fileArrIndex = 0; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				lineArr = file[fileArrIndex].split(splitOnVal);
				
				//convert array to array list and removes spaces
				ArrayList<String> lineArrList = new ArrayList<>(Arrays.asList(lineArr));
				label = lineArrList.get(0) + " " + lineArrList.get(1) + " " + 
						lineArrList.get(2) + " " + lineArrList.get(3);
						//this format is always the case so hardcoding is fine
				for(int i = 4; i < lineArrList.size(); i++) {
					value += lineArrList.get(i) + " ";
				}
				break;
			}
		}
		return new Box(label, value);
	}
	
	/*
	 * INPUT: Stringified json file, label we want to find, 
	 * 			string we want to split the line on
	 * valueOnSameLine() searches for the specific label in
	 * the JSON file and once found will retrieve the index that holds
	 * BOTH the label and value. They're in the same index due
	 * to how google vision's API parses the image
	 * 
	 * Once we have the label found, the value is separated with a ':' or
	 * a ' ' so we pass an argument to split on the specific character.\
	 * EX: Valor Total do ISS: $123.45\n
	 * Split on ' ' will yield
	 * [Valor][Total][do][ISS:][$123.45] 
	 * OUTPUT: new Box object with the new label/value pair
	 */
	public Box valueOnSameLine(String[] file, String labelToFind, String splitOnVal) {
		String[] line = new String[2];
		String label = "";
		String value = "";
		for(int fileArrIndex = 0; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				line = file[fileArrIndex].split(splitOnVal);
				label = line[0];
				value = line[1];
				break;
			}
		}
		return new Box(label, value);
	}	
	
	/*
	 * INPUT: Stringified json file, label we want to find, 
	 * 			string we want to split the line on,
	 * 			the index we want to start on to bypass duplicates
	 * 
	 * Similar to the above valueOnSameLine() but this will allow us to
	 * skip over duplicate instances of the label as seen in other methods
	 * 
	 * OUTPUT: new Box object with the new label/value pair
	 */
	public Box valueOnSameLine(String[] file, 
								String labelToFind, String splitOnVal, int startingIndex) {
		String[] line = new String[2];
		String label = "";
		String value = "";
		for(int fileArrIndex = startingIndex; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				line = file[fileArrIndex].split(splitOnVal);
				label = line[0];
				value = line[1];
			break;
			}
		}
		return new Box(label, value);
	}
	
	/*
	 * INPUT: Stringified json file, label we want to find, 
	 * 			the amount of proceeding indexes we want to have as our value
	 * valueUpToSpecificLine() will find the given label and once found,
	 * will take each proceeding index and keep adding to its value.
	 * The method will stop constructing the value once the indexLimit is met
	 * 
	 * NOTE: If indexLimit == -1, this means we want to keep appending to the value
	 * until the end of the file.
	 * 
	 * OUTPUT: new Box object with the new label/value pair
	 */
	public Box valueUpToSpecificLine(String[] file, String labelToFind, int indexLimit) {
		String label = "";
		String value = "";

		for(int fileArrIndex = 0; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				label = file[fileArrIndex];
				int indexOfLabel = fileArrIndex;
				int maxIndex;
				if(indexLimit == -1) {
					//removes bottom link and page number
					maxIndex = file.length - 2;
				}
				else {
					maxIndex = indexOfLabel + indexLimit;
				}
				for(int i = fileArrIndex + 1; i < maxIndex; i++) {
					value += file[i] + " - ";
				}
				break;
			}
		}
		return new Box(label, value);
	}
	
	/*
	 * INPUT: Stringified json file, label we want to find, 
	 * 			the string we want to halt the value's construction on
	 * 
	 * Similar to valueUpToSpecificLine(), we keep making the value until 
	 * the specific String is found
	 * 
	 * OUTPUT: new Box object with the new label/value pair
	 */
	public Box valueUpToSpecificWord(String[] file, String labelToFind, String stopAtWord) {
		String label = "";
		String value = "";

		for(int fileArrIndex = 0; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				label = file[fileArrIndex];
				for(int i = fileArrIndex + 1; i < file.length; i++) {
					if(!file[i].equals(stopAtWord)) {
						value += file[i] + " - ";
					}
					else {
						break;
					}
				}
				break;
			}
		}
		return new Box(label, value);
	}
	
	/*
	 * INPUT: Stringified json file, label we want to find, 
	 * 			the amount of proceeding indexes we want to have as our value
	 * valueUpToSpecificWord_startAtSpecificLine() is similar to the above
	 * method but will start at a certain index to pass over unwanted indices
	 * that shouldn't be appended to the value
	 * 
	 * OUTPUT: new Box object with the new label/value pair
	 */
	public Box valueUpToSpecificWord_startAtSpecificLine(String[] file, String labelToFind, 
														String stopAtWord, int startingLine) {
		String label = "";
		String value = "";

		for(int fileArrIndex = 0; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				label = file[fileArrIndex];
				for(int i = fileArrIndex  + startingLine; i < file.length; i++) {
					if(!file[i].equals(stopAtWord)) {
						value += file[i] + " - ";
					}
					else {
						break;
					}
				}
				break;
			}
		}
		return new Box(label, value);
	}

	
}
