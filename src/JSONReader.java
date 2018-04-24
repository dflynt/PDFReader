public class JSONReader {
	
	public JSONReader() {}
	
	public Box valueOnNextLine(String[] file, String labelToFind) {
		String label = "";
		String value = "";
		for(int fileArrIndex = 0; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				
				//The | VALOR TOTAL label is an anomaly where
				//it has two values side by side.
				if(labelToFind.equals("| VALOR TOTAL")) {
					label = file[fileArrIndex].substring(17);
					//substring removes the VLR. UNITÁRIO found in the file
					value = file[fileArrIndex + 1].split(" ")[0];
					break;
				}
				
				//The OUTRAS INFORMACOES label is also an anomaly
				//It is its own section with multiple lines as its value
				else if(labelToFind.equals("OUTRAS INFORMA")) {
					label = file[fileArrIndex];
					//for loop to compile all the values to the value variable
					for(int i = fileArrIndex; i < file.length; i++) {
						value += file[i];
					}
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
	
	public Box valueOnLaterLines(String[] file, String labelToFind, int indexOfValue) {
		String label = "";
		String value = "";
		for(int fileArrIndex = 0; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				System.out.println(file[fileArrIndex]);
				label = file[fileArrIndex];
				value = file[fileArrIndex + indexOfValue];
				break;
			}
		}
		return new Box(label, value);
	}
	
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
	
	public Box valueOnSameLine(String[] file, String labelToFind) {
		String[] line = new String[2];
		String label = "";
		String value = "";
		for(int fileArrIndex = 0; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				line = file[fileArrIndex].split(":");
				label = line[0];
				value = line[1];
				break;
			}
		}
		return new Box(label, value);
	}	


	public Box valueOnSameLine(String[] file, 
									  String labelToFind, int startingIndex) {
		String[] line = new String[2];
		String label = "";
		String value = "";
		for(int fileArrIndex = startingIndex; fileArrIndex < file.length; fileArrIndex++) {
			if(file[fileArrIndex].contains(labelToFind)) {
				line = file[fileArrIndex].split(":");
				label = line[0];
				value = line[1];
				break;
			}
		}
		return new Box(label, value);
	}
}
