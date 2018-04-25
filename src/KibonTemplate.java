import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
public class KibonTemplate {

	ArrayList<Box> boxes = new ArrayList<Box>();
	String[] file;
	JSONReader parser = new JSONReader();
	public KibonTemplate(String[] file) throws ParseException {
		this.file = file;
		boxes.add(getNFE_Number(file));
		boxes.get(0).setLabel("NF-E");
		boxes.add(getSerieNumber(file));
		boxes.get(1).setLabel("SERIE");
		boxes.add(getDataEmissao(file));
		boxes.get(2).setLabel("Data de Emissão");
		boxes.add(makeDataDeEntrega(boxes.get(2).getValue()));
	}
	
	public Box getNFE_Number(String[] file) {
		return parser.valueOnNextLine(file, "NF-E", 30);
	}
	
	public Box getSerieNumber(String[] file) {
		return parser.valueOnSameLine(file, "SERIE", " ");
	}
	
	public Box getDataEmissao(String[] file) {
		return parser.valueOnNextLine(file, "Data Emiss");
	}
	
	public void printInfo() {
		for(Box b : boxes) {
			System.out.println(b.toString());
		}
	}
	
	private Box makeDataDeEntrega(String date) throws ParseException {
		//https://stackoverflow.com/questions/428918/how-can-i-increment-a-date-by-one-day-in-java
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(date));
		c.add(Calendar.DATE, 1);  // number of days to add
		date = sdf.format(c.getTime());
		
		String newDate = date.toString();
		return new Box("Data de Entrega", newDate);
	}
}
