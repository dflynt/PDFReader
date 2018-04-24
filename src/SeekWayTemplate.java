import java.util.ArrayList;

public class SeekWayTemplate {
	
	ArrayList<Box> boxes = new ArrayList<Box>();
	String[] file;
	JSONReader parser = new JSONReader();
	
	public SeekWayTemplate(String[] file) {
		this.file = file;
		boxes.add(getInvoiceNumber(file));
		boxes.add(getEmissionDate(file));
		boxes.add(getCarrierCNPJ(file));
		boxes.get(2).useSubstring(19);
		boxes.add(getCarrierCity(file));
		boxes.add(getTakerCNPJ(file));
		boxes.add(getTakerCity(file));
		boxes.get(5).useStringUntilVal(":");
		boxes.add(getServiceProvisioningCity(file));
		boxes.add(getServiceDescription(file));
		//boxes.add(getServiceCode(file));
		boxes.add(getTotalAmount(file));
		boxes.add(getOtherInformation(file));
		boxes.add(getDeductions(file));
		//boxes.add(getUnconditionalDiscount(file));
		boxes.add(getCalculusBasis(file));
		boxes.add(getAliquote(file));
		boxes.add(getISSValue(file));
	}
	
	/* The string arguments found below look incomplete. 
	 * That's because copying and pasting the unicode characters in the file
	 * will not work. I have to type as many characters as possible for
	 * string comparison
	 * 
	 * Ex: Outras Informações will not be found in the file
	 * even though we have special characters in the string.
	 */
	public void printInfo() {
		for(Box b : boxes) {
			System.out.println(b.toString());
		}
	}
	public Box getInvoiceNumber(String[] file) {
		return parser.valueOnNextLine(file, "da Nota");
	}
	public Box getEmissionDate(String[] file) {
		return parser.valueOnNextLine(file, "Data/Hora de");
	}
	public Box getCarrierCNPJ (String[] file) {
		return parser.valueOnSameLine(file, "CNPJ");
	}
	public Box getCarrierCity(String[] file) {
		return parser.valueOnNextLine(file, "Municipio:");
	}
	public Box getTakerCNPJ(String[] file) {
		return parser.valueOnSameLine(file, "CPF/CNPJ/PAS");
	}
	public Box getTakerCity(String[] file) {
		return parser.valueOnNextLine(file, "Municipio:", 20);
		/*
		 * Overloaded valueOnSameLine() method
		 * 10 is an index for the file array
		 * There are two instances of "Municipio"
		 * We want the second one in this case
		 * This specific "Municipio" will always come
		 * after the first
		 */
	}
	public Box getServiceProvisioningCity(String[] file) {
		return parser.valueOnLaterLines(file, "SERVI", 2, 30);
	}
	public Box getServiceDescription(String[] file) {
		return parser.valueOnLaterLines(file, "DESCRI", 4);
	}
	public Box getTotalAmount(String[] file) {
		return parser.valueOnNextLine(file, "| VALOR TOTAL");
	}
	public Box getOtherInformation(String[] file) {
		return parser.valueOnNextLine(file, "OUTRAS INFORMA");
	}
	public Box getDeductions(String[] file) {
		return parser.valueOnNextLine(file, "Dedu");
	}
	public Box getCalculusBasis(String[] file) {
		return parser.valueOnLaterLines(file, "Base C", 6);
	}
	public Box getAliquote(String[] file) {
		return parser.valueOnLaterLines(file, "quota", 6);
	}
	public Box getISSValue(String[] file) {
		return parser.valueOnLaterLines(file, "Valor do ISS", 6);
	}
}
