import java.util.ArrayList;

public class BelfordRoxoTemplate {
	ArrayList<Box> boxes = new ArrayList<Box>();
	String[] file;
	JSONReader parser = new JSONReader();
	
	public BelfordRoxoTemplate(String[] file) {
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

		boxes.add(getServiceProvisioningCity(file));
		boxes.get(6).setLabel("Municipio de prestação do serviço");

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

		boxes.add(getISSValue(file));
		boxes.get(13).setLabel("Valor do ISS");

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
		return parser.valueOnNextLine(file, "Data e Hora");
	}
	public Box getCarrierCNPJ (String[] file) {
		return parser.valueOnNextLine(file, "CPF/CNPJ");
	}
	public Box getCarrierCity(String[] file) {
		return new Box("MUNICIPIO PRESTADOR", "BELFORD ROXO");
	}
	public Box getTakerCNPJ(String[] file) {
		return parser.valueOnNextLine(file, "CPF/CNPJ", 25 );
	}
	public Box getTakerCity(String[] file) {
		return parser.valueOnNextLine(file, "Endere");
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
		return parser.valueOnLaterLines(file, "LOCAL. PRESTA", 2);
	}
	public Box getServiceDescription(String[] file) {
		return parser.valueUpToSpecificLine(file, "DISCRIMINA", 4);
	}
	public Box getTotalAmount(String[] file) {
		return parser.valueOnSameLine(file, "VALOR TOTAL DA NOTA", ":");
	}
	public Box getOtherInformation(String[] file) {
		return parser.valueOnNextLine(file, "OUTRAS INFORMA");
	}
	public Box getDeductions(String[] file) {
		return parser.valueOnNextLine(file, "DEDU");
	}
	public Box getCalculusBasis(String[] file) {
		return parser.valueOnNextLine(file, "LCULO");
	}
	public Box getAliquote(String[] file) {
		return parser.valueOnNextLine(file, "QUOTA");
	}
	public Box getISSValue(String[] file) {
		return parser.valueOnNextLine(file, "VALOR ISS");
	}
}
