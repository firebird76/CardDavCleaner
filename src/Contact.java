import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.rmi.activation.UnknownObjectException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.sun.media.sound.InvalidFormatException;

public class Contact {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd#HHmmss");
	//private String revision;
	//private String productId;
	private TreeSet<Adress> adresses = new TreeSet<Adress>(ObjectComparator.get());
	private Collection<Phone> phones = new TreeSet<Phone>(ObjectComparator.get());
	private TreeSet<Email> mails = new TreeSet<Email>(ObjectComparator.get());
	private Name name;
	private String formattedName; // TODO: eine vcard kann auch mehrere haben!
	private TreeSet<String> titles=new TreeSet<String>(ObjectComparator.get());
	private String role; // TODO: eine vcard kann auch mehrere haben!
	private Birthday birthday;	
	private boolean htmlMail;
	private TreeSet<Url> urls = new TreeSet<Url>(ObjectComparator.get());
	private String uid;
	private TreeSet<String> notes=new TreeSet<String>(ObjectComparator.get());
	private TreeSet<String> photos=new TreeSet<String>(ObjectComparator.get());
	private TreeSet<Organization> orgs=new TreeSet<Organization>(ObjectComparator.get());
	private String vcfName;
	
	public boolean conflictsWith(Contact c2){
		if (name!=null && c2.name!=null && !name.canonical().equals(c2.name.canonical())) return true;
		if (birthday!=null && c2.birthday!=null && !birthday.equals(c2.birthday)) return true;
		if (!titles.isEmpty() && !c2.titles.isEmpty() && !titles.equals(c2.titles)) return true;
		if (role!=null && c2.role!=null && !role.equals(c2.role)) return true;
		if (!phones.isEmpty() && !c2.phones.isEmpty() && !getPhoneNumbers().equals(c2.getPhoneNumbers())) return true;
		if (!mails.isEmpty() && !c2.mails.isEmpty() && !getMailAdresses().equals(c2.getMailAdresses())) return true;
		if (!adresses.isEmpty() && !c2.adresses.isEmpty() && !getAdressData().equals(c2.getAdressData())) return true;
		if (!urls.isEmpty() && !c2.urls.isEmpty() && !urls.equals(c2.urls))	return true;
		
		if (!notes.isEmpty() && !c2.notes.isEmpty() && !notes.equals(c2.notes))return true;
		if (!orgs.isEmpty() && !c2.orgs.isEmpty() && !orgs.equals(c2.orgs)) return true;
		if (!photos.isEmpty() && !c2.photos.isEmpty() && !photos.equals(c2.photos))	return true;		
		return false;
	}

	private TreeSet<String> getMailAdresses() {
		TreeSet<String> result=new TreeSet<String>(ObjectComparator.get());
		for (Email mail:mails) result.add(mail.adress());
		return result;
	}

	private TreeSet<String> getPhoneNumbers() {
		TreeSet<String> result=new TreeSet<String>(ObjectComparator.get());
		for (Phone phone:phones) result.add(phone.number());
		return result;
	}

	private TreeSet<String> getAdressData() {
		TreeSet<String> result=new TreeSet<String>(ObjectComparator.get());
		for (Adress adress:adresses) result.add(adress.canonical());
		return result;
	}
	
	

	public boolean isEmpty() {
		return adresses.isEmpty() &&
					phones.isEmpty() &&
					mails.isEmpty() && 
					titles.isEmpty() &&
					role==null && 
					birthday==null &&
					urls.isEmpty() &&
					notes.isEmpty() &&
					photos.isEmpty() &&
					orgs.isEmpty();
	}
	
	public void merge(Contact contact) throws InvalidAssignmentException {
		adresses.addAll(contact.adresses);
		
		/* merging phones by numbers */
		TreeMap<String,Phone> phoneMap=new TreeMap<String, Phone>(ObjectComparator.get());
		for (Phone phone:phones){
			Phone existingPhone = phoneMap.get(phone.number());
			if (existingPhone!=null){
				existingPhone.merge(phone);
			} else phoneMap.put(phone.number(), phone);
		}
		for (Phone phone:contact.phones){
			Phone existingPhone = phoneMap.get(phone.number());
			if (existingPhone!=null){
				existingPhone.merge(phone);
			} else phoneMap.put(phone.number(), phone);
		}		
		phones=phoneMap.values();
		
		TreeMap<String,Email> mailMap=new TreeMap<String,Email>(ObjectComparator.get());
		for (Email mail:mails){
			Email existingMail=mailMap.get(mail.adress());
			if (existingMail!=null){
				existingMail.merge(mail);
			} else mailMap.put(mail.adress(), mail);
		}
		
		if (name!=null){
			if (contact.name!=null && !contact.name.equals(name)){
				name=(Name) selectOneOf("name",name,contact.name,contact);
			}
		} else name=contact.name;
		
		if (formattedName!=null){
			if (contact.formattedName!=null && !contact.formattedName.equals(formattedName)){
				formattedName=(String) selectOneOf("formated name", formattedName, contact.formattedName,contact);
			}
		} else formattedName=contact.formattedName;
		
		titles.addAll(contact.titles);
		
		if (role!=null){
			if (contact.role!=null && !contact.role.equals(role)){
				role=(String)selectOneOf("role", role, contact.role,contact);
			}
		} else role=contact.role;
		
		if (birthday!=null){
			if (contact.birthday!=null && !contact.birthday.equals(birthday)){
				birthday= (Birthday) selectOneOf("birtday", birthday, contact.birthday,contact);
			}
		} else birthday=contact.birthday;
		
		if (contact.htmlMail) htmlMail=true;
		urls.addAll(contact.urls);
		if (uid==null) uid=contact.uid;
		notes.addAll(contact.notes);
		photos.addAll(contact.photos);
		orgs.addAll(contact.orgs);		
	}
	
	private Object selectOneOf(String title, Object o1, Object o2, Contact contact2) {
		VerticalPanel vp=new VerticalPanel();
		vp.add(new JLabel("<html>Merging the following two contacts:<br>&nbsp;"));
		HorizontalPanel hp=new HorizontalPanel();
		hp.add(new JLabel("<html>"+this.toString(true).replace("\n", "&nbsp;<br>")));
		hp.add(new JLabel("<html>"+contact2.toString(true).replace("\n", "<br>")));
		hp.scale();
		vp.add(hp);
		vp.add(new JLabel("<html><br>Which "+title+" shall be used?"));
		vp.scale();
		UIManager.put("OptionPane.yesButtonText", o1.toString());
		UIManager.put("OptionPane.noButtonText", o2.toString());
		int decision = JOptionPane.showConfirmDialog(null, vp, "Please select", JOptionPane.YES_NO_CANCEL_OPTION);
		UIManager.put("OptionPane.yesButtonText","Yes");
		UIManager.put("OptionPane.noButtonText", "No");
		switch (decision){
			case JOptionPane.YES_OPTION:	return o1;
			case JOptionPane.NO_OPTION:	return o2;
			case JOptionPane.CANCEL_OPTION: System.exit(0);
		}
		return null;
	}

	public Contact(String directory,String name) throws UnknownObjectException, IOException, AlreadyBoundException  {
		vcfName=name;
		parse(new URL(directory+name));
	}
	
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("BEGIN:VCARD\n");

		sb.append("VERSION:3.0\n");
		sb.append("PRODID:-//SRSoftwae CalDavCleaner\n");
		if (uid!=null) sb.append("UID:"+uid+"\n");
		sb.append(newRevision()); sb.append("\n");
		
		sb.append("FN:"); if (formattedName!=null) sb.append(formattedName); // required for Version 3
		sb.append("\n");
		
		sb.append(name);// required for Version 3
		sb.append("\n");
		
		for (String title:titles){
			sb.append("TITLE:"+title+"\n");
		}
		for (Organization org:orgs){
			sb.append(org);
			sb.append("\n");
		}
		if (role!=null) sb.append("ROLE:"+role+"\n");
		if (birthday!=null) {
			sb.append(birthday);
			sb.append("\n");
		}
		
		for (Adress adress:adresses){
			sb.append(adress);
			sb.append("\n");
		}
		for(Phone phone:phones){
			sb.append(phone);
			sb.append("\n");			
		}
		for(Email mail:mails){
			sb.append(mail);
			sb.append("\n");
		}
		if (htmlMail) sb.append("X-MOZILLA-HTML:TRUE\n");
		
		for (Url url:urls){
			sb.append(url);
			sb.append("\n");
		}
		for (String note:notes){
			sb.append("NOTE:"+note+"\n");	
		}
		
		for (String photo:photos) sb.append(photo+"\n");
		sb.append("END:VCARD\n");
		return sb.toString();
	}

	public String toString(boolean shorter) {
		StringBuffer sb=new StringBuffer();
		sb.append("BEGIN:VCARD\n");

		if (uid!=null) sb.append("UID:"+uid+"\n");
		
		sb.append("FN:"); if (formattedName!=null) sb.append(formattedName); // required for Version 3
		sb.append("\n");
		
		sb.append(name);// required for Version 3
		sb.append("\n");
		
		for (String title:titles){
			sb.append("TITLE:"+title+"\n");
		}
		for (Organization org:orgs){
			sb.append(org);
			sb.append("\n");
		}
		if (role!=null) sb.append("ROLE:"+role+"\n");
		if (birthday!=null) sb.append(birthday);
		
		for (Adress adress:adresses){
			sb.append(adress);
			sb.append("\n");
		}
		for(Phone phone:phones){
			sb.append(phone);
			sb.append("\n");			
		}
		for(Email mail:mails){
			sb.append(mail);
			sb.append("\n");
		}
		if (htmlMail) sb.append("X-MOZILLA-HTML:TRUE\n");
		
		for (Url url:urls){
			sb.append(url);
			sb.append("\n");
		}
		for (String note:notes){
			sb.append("NOTE:"+((note.length()>30)?(note.substring(0,28)+"..."):note)+"\n");	
		}
		for (String photo:photos) {
			sb.append(photo.substring(0,30)+"...\n");
		}

		sb.append("END:VCARD\n");
		return sb.toString();
	}
	private String newRevision() {
		String date=formatter.format(new Date()).replace('#','T');
		return "REV:"+date;
	}

	private void parse(URL url) throws IOException, UnknownObjectException, AlreadyBoundException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		InputStream content = (InputStream) connection.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(content));
		Vector<String> lines=new Vector<String>();
		String line;
		while ((line = in.readLine()) != null) {
			lines.add(line);
		}
		in.close();
		content.close();
		connection.disconnect();
		for (int index = 0; index < lines.size(); index++) {
			line = lines.elementAt(index);
			while (index + 1 < lines.size() && (lines.elementAt(index + 1).startsWith(" ") ||lines.elementAt(index + 1).startsWith("\\n"))) {
				index++;
				line += lines.elementAt(index).trim();
			}
			boolean known = false;
			if (line.equals("BEGIN:VCARD")) known = true;
			if (line.equals("END:VCARD")) known = true;
			if (line.startsWith("VERSION:")) known = true;
			if (line.startsWith("ADR;") && (known = true)) readAdress(line);
			if (line.startsWith("UID:") && (known = true)) readUID(line.substring(4));
			if (line.startsWith("TEL;") && (known = true)) readPhone(line);
			if (line.startsWith("EMAIL;") && (known = true)) readMail(line);
			if (line.startsWith("REV:")) known = true;// readRevision(line.substring(4));
			if (line.startsWith("NOTE:") && (known = true)) readNote(line.substring(5));
			if (line.startsWith("BDAY") && (known = true)) readBirthday(line.substring(4));
			if (line.startsWith("ROLE:") && (known = true)) readRole(line.substring(5));
			if (line.startsWith("URL;") && (known = true)) readUrl(line);
			if (line.startsWith("PRODID:")) known = true; // readProductId(line.substring(7));
			if (line.startsWith("N:") && (known = true)) readName(line);
			if (line.startsWith("FN:") && (known = true)) readFormattedName(line.substring(3));
			if (line.startsWith("ORG:") && (known = true)) readOrg(line);
			if (line.startsWith("TITLE:") && (known = true)) readTitle(line.substring(6));
			if (line.startsWith("PHOTO;") && (known = true)) readPhoto(line);
			if (line.startsWith("X-MOZILLA-HTML:") && (known = true)) readMailFormat(line.substring(15));
			if (line.startsWith(" \\n") && line.trim().equals("\\n")) known = true;

			if (!known) {
				throw new UnknownObjectException("unknown entry/instruction found in vcard "+vcfName+": '" + line+"'");
			}
		}
	}

	private void readBirthday(String bday) {
		birthday=new Birthday(bday);
	}

	private void readPhoto(String line) {
		photos.add(line);
	}

	private void readMailFormat(String line) {
		htmlMail=line.toUpperCase().equals("TRUE");
	}

	private void readTitle(String line) throws AlreadyBoundException {
		if (line.isEmpty()) return;
		titles.add(line);
	}

	private void readOrg(String line) throws InvalidFormatException, UnknownObjectException, AlreadyBoundException {
		Organization org = new Organization(line);		
		if (!org.isEmpty()) orgs.add(org);
	}

	private void readUID(String uid) {
		if (uid.isEmpty()) return;
		this.uid=uid;
	}

	private void readFormattedName(String fn) {
		if (fn.isEmpty()) return;
		formattedName=fn;
	}

	private void readName(String line) throws InvalidFormatException, UnknownObjectException {
		Name n = new Name(line);
		if (!n.isEmpty())	name=n;

	}

	/*private void readProductId(String line) {
		if (line.isEmpty()) return;
		productId = line;
	}*/

	private void readUrl(String line) throws InvalidFormatException, UnknownObjectException {
		Url url=new Url(line);
		if (!url.isEmpty()) urls.add(url);
	}

	private void readNote(String line) throws AlreadyBoundException {
		if (line.isEmpty()) return;
		notes.add(line);
	}
	
	private void readRole(String line) throws AlreadyBoundException {
		if (role!=null) throw new AlreadyBoundException("Trying to assign role, although it is already assigned");

		if (line.isEmpty()) return;
		role = line.replace("\\n", "\n");
	}

/*	private void readRevision(String line) {
		if (line.isEmpty()) return;
		revision = line;
	}*/

	private void readPhone(String line) throws InvalidFormatException, UnknownObjectException {
		Phone phone = new Phone(line);
		if (!phone.isEmpty()) phones.add(phone);
	}

	private void readAdress(String line) throws UnknownObjectException, InvalidFormatException {
		Adress adress = new Adress(line);
		if (!adress.isEmpty()) adresses.add(adress);
	}

	private void readMail(String line) throws UnknownObjectException, InvalidFormatException {
		Email mail = new Email(line);
		if (!mail.isEmpty()) mails.add(new Email(line));
	}

	public Name name() {
		return name;
	}

	public TreeSet<String> phoneNumbers() {
		TreeSet<String> numbers=new TreeSet<String>(ObjectComparator.get());
		for (Phone p:phones){
			numbers.add(p.number());
		}
		return numbers;
	}

	public TreeSet<String> mailAdresses() {
		TreeSet<String> mails=new TreeSet<String>(ObjectComparator.get());
		for (Email e:this.mails){
			mails.add(e.adress());
		}
		return mails;
	}
	
	public String vcfName(){
		return vcfName;
	}

	public byte[] getBytes() {
		return toString().getBytes();
	}
}
