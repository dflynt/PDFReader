import java.util.ArrayList;

public class Campinas_LIRANTemplate {
	ArrayList<Box> boxes = new ArrayList<Box>();
	String[] file;
	JSONReader parser = new JSONReader();
	
	public Campinas_LIRANTemplate(String[] file) {
		this.file = file;
		boxes.add(getInvoiceNumber(file));
		boxes.get(0).setLabel("Número da Nota");

		boxes.add(getEmissionDate(file));
		boxes.get(1).setLabel("Data de Emissão");

		boxes.add(getCarrierCNPJ(file));
		boxes.get(2).setLabel("CNPJ Prestador");

		boxes.add(getCarrierCity(file));
		boxes.get(3).setLabel("Municipio Prestador");

		boxes.add(getTakerCNPJ(file));
		boxes.get(4).setLabel("CNPJ Tomador");

		boxes.add(getTakerCity(file));
		boxes.get(5).setLabel("Municipio Tomador");

		//boxes.add(getServiceProvisioningCity(file));
		boxes.add(getServiceDescription(file));
		boxes.get(7).setLabel("Descrição do serviço");

		//boxes.add(getServiceCode(file));
		boxes.add(getTotalAmount(file));
		boxes.get(8).setLabel("Valor Total");

		boxes.add(getOtherInformation(file));
		boxes.get(9).setLabel("Outras Informações");

		boxes.add(getDeductions(file));
		boxes.get(10).setLabel("Deduções");

		//boxes.add(getUnconditionalDiscount(file));
		boxes.add(getCalculusBasis(file));
		boxes.get(11).setLabel("Base de Calculo");

		boxes.add(getAliquote(file));
		boxes.get(12).setLabel("Aliquota");

		//boxes.add(getISSValue(file));
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
		return parser.valueOnLaterLines(file, "mero da Nota", 2);
	}
	public Box getEmissionDate(String[] file) {
		return parser.valueOnLaterLines(file, "Data e Hora de Emiss", 2);
	}
	public Box getCarrierCNPJ (String[] file) {
		return parser.valueOnSameLine(file, "CPF/CNPJ", ":");
	}
	public Box getCarrierCity(String[] file) {
		//ending of municipio
		//No other label has this ending
		return parser.valueOnSameLine(file, "pio:", ":", 15);
	}
	public Box getTakerCNPJ(String[] file) {
		return parser.valueOnSameLine(file, "CPF/CNPJ", ":", 20);
	}
	public Box getTakerCity(String[] file) {
		return parser.valueOnSameLine(file, "pio", ":", 25);
	}
		/*
		 * Overloaded valueOnSameLine() method
		 * 10 is an index for the file array
		 * There are two instances of "Municipio"
		 * We want the second one in this case
		 * This specific "Municipio" will always come
		 * after the first
		 */
	/*
	 * Label not found in template
	 
	public Box getServiceProvisioningCity(String[] file) {
		return parser.valueOnSameLine_MultipleWords(file, "MUNICIPIO DE", " ");
	}
	*/
	public Box getServiceDescription(String[] file) {
		return parser.valueUpToSpecificWord(file, "DISCRIMINA", "DUSUUTISET");
	}
	public Box getTotalAmount(String[] file) {
		return parser.valueOnSameLine(file, "VALOR TOTAL DA NOTA", " = ");
	}
	public Box getOtherInformation(String[] file) {
		return parser.valueUpToSpecificLine(file, "OUTRAS INFORMA", -1 );
	}
	public Box getDeductions(String[] file) {
		return parser.valueOnLaterLines(file, "es do ISSQN", 4);
	}
	public Box getCalculusBasis(String[] file) {
		return parser.valueOnLaterLines(file, "Base de C", 4);
	}
	public Box getAliquote(String[] file) {
		return parser.valueOnLaterLines(file, "quota", 4);
	}
	/*
	 * Label not found in this template
	public Box getISSValue(String[] file) {
		return parser.valueOnLaterLines(file, "Valor do ISS", 6);
	}
	*/
}
