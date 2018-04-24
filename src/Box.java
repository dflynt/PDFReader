
public class Box {
	
	String label;
	String value;
	
	public Box(String label, String value) {
		this.label = label;
		this.value = value;
	}
	public String getLabel() {
		return this.label;
	}
	public String getValue() {
		return this.value;
	}
	public void setLabel(String newLabel) {
		this.label = newLabel;
	}
	public void setValue(String newValue) {
		this.value = newValue;
	}
	public String toString() {
		return this.label + ": " + this.value;
	}
	public void useSubstring(int removeAfterIndex) {
		this.value = this.value.substring(0, removeAfterIndex);
	}
	public void useStringUntilVal(String valueToRemove) {
		int index = this.value.indexOf(valueToRemove);
		this.value = this.value.substring(0, index - 3);
	}
}
