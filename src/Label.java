import java.util.TreeSet;

public class Label extends Mergable<Label> implements Comparable<Label>{
	TreeSet<String> types=new TreeSet<String>();
	String encoding=null;
	String label;
	
	public Label(String line) throws InvalidFormatException {
		String data=null;
		if (line.startsWith("LABEL")) {
			data=line.substring(5);
		} else throw new InvalidFormatException(line);
		String[] parts=null;
		if (data.contains("ENCODING=")) {
			parts = data.split("ENCODING=");
			for (String type:parts[0].split(";")){
				if (type.length()>0) types.add(type);
			}
			parts=parts[1].split(":", 0);
			encoding=parts[0];
			label=parts[1];
		} else {
			parts = data.split(":",0);
			for (String type:parts[0].split(";")){
				if (type.length()>0) types.add(type);
			}
			label=parts[1];			
		}
		if (!this.toString().equals(line)) throw new InvalidFormatException("original: "+line+"\ncoded: "+this);
	}
	
	@Override
	public String toString() {
		String result="LABEL;";
		for (String type:types){
			result+=type+";";
		}
		if (encoding!=null){
			result+="ENCODING="+encoding;	
		}
		result+=":";
		return result.replace(";:", ":")+label;
	}

	public int compareTo(Label o) {
		return this.toString().compareTo(o.toString());
	}

	@Override
  public boolean isCompatibleWith(Label other) {
		if (different(label,other.label)) return false;
	  return true;
  }

	@Override
  public boolean mergeWith(Label other) {
		label=merge(label,other.label);
	  return true;
  }

	public boolean isEmpty() {
	  return label==null || label.trim().isEmpty();
  }
}