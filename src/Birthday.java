import java.awt.Color;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Birthday extends Mergable<Birthday> implements ChangeListener, Comparable<Birthday> {

	public static void test() {
		try {
			System.out.print(_("Birthday creation test (null)..."));
			String testCase = null;
			try {
				Birthday nb = new Birthday(testCase);
				System.err.println(_("failed: #", nb));
				System.exit(-1);
			} catch (InvalidFormatException ife) {
				System.out.println(_("ok"));
			}

			System.out.print(_("Birthday creation test (empty)..."));
			testCase = "BDAY:";
			Birthday eb = new Birthday(testCase);
			if (eb.toString().equals(testCase)) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", eb));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (valid year)..."));
			testCase = "BDAY:1234";
			Birthday vyb = new Birthday(testCase);
			if (vyb.toString().equals(testCase) && !vyb.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", vyb));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (invalid year)..."));
			testCase = "BDAY:XXXX";
			Birthday iyb = new Birthday(testCase);
			if (iyb.toString().equals(testCase) && iyb.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", iyb));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (valid month)..."));
			testCase = "BDAY:--01";
			Birthday vmb = new Birthday(testCase);
			if (vmb.toString().equals(testCase) && !vmb.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", vmb));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (invalid month 1)..."));
			testCase = "BDAY:--13";
			Birthday imb1 = new Birthday(testCase);
			if (imb1.toString().equals(testCase) && imb1.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", imb1));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (invalid month 2)..."));
			testCase = "BDAY:--XX";
			Birthday imb2 = new Birthday(testCase);
			if (imb2.toString().equals(testCase) && imb2.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", imb2));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (valid day)..."));
			testCase = "BDAY:---01";
			Birthday vdb = new Birthday(testCase);
			if (vdb.toString().equals(testCase) && !vdb.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", vdb));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (invalid day 1)..."));
			testCase = "BDAY:---32";
			Birthday idb1 = new Birthday(testCase);
			if (idb1.toString().equals(testCase) && idb1.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", idb1));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (invalid day 2)..."));
			testCase = "BDAY:---XX";
			Birthday idb2 = new Birthday(testCase);
			if (idb2.toString().equals(testCase) && idb2.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", idb2));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (full date)..."));
			testCase = "BDAY:19831026";
			Birthday fdb = new Birthday(testCase);
			if (fdb.toString().equals(testCase) && !fdb.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", fdb));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (valid hour)..."));
			testCase = "BDAY:T01";
			Birthday vhb = new Birthday(testCase);
			if (vhb.toString().equals(testCase.replace(":", ";VALUE=DATE-TIME:")) && !vhb.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", vhb));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (invalid hour 1)..."));
			testCase = "BDAY:T24";
			Birthday ihb1 = new Birthday(testCase);
			if (ihb1.toString().equals(testCase.replace(":", ";VALUE=DATE-TIME:")) && ihb1.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", ihb1));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (invalid hour 2)..."));
			testCase = "BDAY:TXX";
			Birthday ihb2 = new Birthday(testCase);
			if (ihb2.toString().equals(testCase.replace(":", ";VALUE=DATE-TIME:")) && ihb2.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", ihb2));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (valid minute)..."));
			testCase = "BDAY:T-01";
			Birthday vminb = new Birthday(testCase);
			if (vminb.toString().equals(testCase.replace(":", ";VALUE=DATE-TIME:")) && !vminb.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", vminb));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (invalid minute 1)..."));
			testCase = "BDAY:T-60";
			Birthday iminb1 = new Birthday(testCase);
			if (iminb1.toString().equals(testCase.replace(":", ";VALUE=DATE-TIME:")) && iminb1.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", iminb1));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (invalid minute 2)..."));
			testCase = "BDAY:T-XX";
			Birthday iminb2 = new Birthday(testCase);
			if (iminb2.toString().equals(testCase.replace(":", ";VALUE=DATE-TIME:")) && iminb2.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", iminb2));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (valid second)..."));
			testCase = "BDAY:T--01";
			Birthday vsb = new Birthday(testCase);
			if (vsb.toString().equals(testCase.replace(":", ";VALUE=DATE-TIME:")) && !vsb.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", vsb));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (invalid second 1)..."));
			testCase = "BDAY:T--60";
			Birthday isb1 = new Birthday(testCase);
			if (isb1.toString().equals(testCase.replace(":", ";VALUE=DATE-TIME:")) && isb1.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", isb1));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (invalid second 2)..."));
			testCase = "BDAY:T--XX";
			Birthday isb2 = new Birthday(testCase);
			if (isb2.toString().equals(testCase.replace(":", ";VALUE=DATE-TIME:")) && isb2.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", isb2));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (full time)..."));
			testCase = "BDAY:T235959";
			Birthday ftb = new Birthday(testCase);
			if (ftb.toString().equals(testCase.replace(":", ";VALUE=DATE-TIME:")) && !ftb.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", ftb));
				System.exit(-1);
			}

			System.out.print(_("Birthday creation test (full)..."));
			testCase = "BDAY:19991231T235959";
			Birthday fb = new Birthday(testCase);
			if (fb.toString().equals(testCase.replace(":", ";VALUE=DATE-TIME:")) && !fb.isInvalid()) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("failed: #", fb));
				System.exit(-1);
			}

			Birthday[] bdays = { eb, vyb, iyb, vmb, imb1, imb2, vdb, idb1, idb2, fdb, vhb, ihb1, ihb2, vminb, iminb1, iminb2, vsb, isb1, isb2 };

			System.out.print(_("Birthday isEmpty test..."));
			int comp = 0;
			int num = 0;
			for (Birthday b : bdays) {
				comp++;
				if (!b.isEmpty()) {
					num++;
				} 
				if (b == eb) {
					comp--;
				}
			}
			if (num == comp) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("#/# => failed",new Object[]{num,comp}));
				System.exit(-1);
			}

			System.out.print(_("Birthday compare test..."));
			comp = 0;
			num = 0;
			for (Birthday b : bdays) {
				num++;
				if (b.compareTo(fb) != 0 && b.compareTo(fb) == -fb.compareTo(b)) {
					comp++;
				}
			}
			if (comp == num) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("#/# => failed",new Object[]{num,comp}));
				System.exit(-1);
			}

			System.out.print(_("Birthday compatibility test..."));
			comp = 0;
			num = 0;
			for (Birthday a : bdays) {
				for (Birthday b : bdays) {
					num++;
					if (a.isCompatibleWith(b)) {
						comp++;
					} else {
						String concat = (a + "" + b).replace("BDAY", "").replace(";VALUE=DATE-TIME", "").replaceFirst(":", "");
						if (concat.equals("1234:XXXX") || concat.equals("1234:19831026") || concat.equals("XXXX:1234") || concat.equals("XXXX:19831026") || concat.equals("--01:--13") || concat.equals("--01:--XX") || concat.equals("--01:19831026") || concat.equals("--13:--01") || concat.equals("--13:--XX") || concat.equals("--13:19831026") || concat.equals("--XX:--01") || concat.equals("--XX:--13") || concat.equals("--XX:19831026") || concat.equals("---01:---32") || concat.equals("---01:---XX") || concat.equals("---01:19831026") || concat.equals("---32:---01") || concat.equals("---32:---XX") || concat.equals("---32:19831026") || concat.equals("---XX:---01") || concat.equals("---XX:---32") || concat.equals("---XX:19831026") || concat.equals("19831026:1234") || concat.equals("19831026:XXXX") || concat.equals("19831026:--01") || concat.equals("19831026:--13") || concat.equals("19831026:--XX") || concat.equals("19831026:---01") || concat.equals("19831026:---32") || concat.equals("19831026:---XX") || concat.equals("T01:T24") || concat.equals("T01:TXX") || concat.equals("T24:T01") || concat.equals("T24:TXX") || concat.equals("TXX:T01") || concat.equals("TXX:T24") || concat.equals("T-01:T-60") || concat.equals("T-01:T-XX") || concat.equals("T-60:T-01") || concat.equals("T-60:T-XX") || concat.equals("T-XX:T-01") || concat.equals("T-XX:T-60") || concat.equals("T--01:T--60") || concat.equals("T--01:T--XX") || concat.equals("T--60:T--01") || concat.equals("T--60:T--XX") || concat.equals("T--XX:T--01") || concat.equals("T--XX:T--60")) {
							comp++;
						} else {
							System.err.println(a + " <=> " + b);
						}
					}
				}
			}
			if (comp == num) {
				System.out.println(_("ok"));
			} else {
				System.err.println(_("#/# => failed",new Object[]{num,comp}));
				System.exit(-1);
			}
			
			System.out.print(_("Birthday clone test..."));
			comp=0;
			num=0;
			for (Birthday b:bdays){
				comp++;
				try {
					if (b.toString().equals(b.clone().toString())){
						num++;
					}
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
			if (comp==num){
				System.out.println(_("ok"));
			} else {				
				System.err.println(_("#/# => failed",new Object[]{num,comp}));
				System.exit(-1);
			}
			
			System.out.print(_("Birthday merge test..."));
			comp=0;
			num=0;
			for (Birthday b:bdays){
				try {
					comp+=2;
					Birthday clone1=(Birthday) b.clone();
					Birthday clone2=(Birthday) fb.clone();
					
					if (clone1.mergeWith(fb) && clone1.toString().equals(fb.toString())) num++;
					if (clone2.mergeWith(b) && clone2.toString().equals(fb.toString())) num++;
					if (comp>num){
						if ((fb.year!=null && !fb.year.isEmpty()) && (b.year!=null && !b.year.isEmpty()) && !fb.year.equals(b.year)) {
							num+=2;
						} else if ((fb.month!=null && !fb.month.isEmpty()) && (b.month!=null && !b.month.isEmpty()) && !fb.month.equals(b.month)) {
							num+=2;
						} else if ((fb.day!=null && !fb.day.isEmpty()) && (b.day!=null && !b.day.isEmpty()) && !fb.day.equals(b.day)) {
							num+=2;
						} else if ((fb.hour!=null && !fb.hour.isEmpty()) && (b.hour!=null && !b.hour.isEmpty()) && !fb.hour.equals(b.hour)) {
							num+=2;
						} else if ((fb.minute!=null && !fb.minute.isEmpty()) && (b.minute!=null && !b.minute.isEmpty()) && !fb.minute.equals(b.minute)) {
							num+=2;
						} else if ((fb.second!=null && !fb.second.isEmpty()) && (b.second!=null && !b.second.isEmpty()) && !fb.second.equals(b.second)) {
							num+=2;
						} 
					}
					if (comp>num){
						System.out.println();
						System.out.println("fb: "+fb);
						System.out.println(" b: "+b);
						System.out.println(_("merged:"));
						System.out.println("fb: "+clone2);
						System.out.println(" b: "+clone1);
					}
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}				
			}
			if (comp==num){
				System.out.println(_("ok"));
			} else {				
				System.err.println(_("#/# => failed",new Object[]{num,comp}));
				System.exit(-1);
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}

	}
	private static String _(String text) { 
		return Translations.get(text);
	}
	private static String _(String key, Object insert) {
		return Translations.get(key, insert);
	}
	
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private String second;
	private boolean invalid = false;
	private InputField yearField, monthField, dayField, hourField, minuteField, secondField;

	public Birthday(String birthday) throws InvalidFormatException {
		String bday = birthday;
		if (bday == null || !bday.startsWith("BDAY")) {
			throw new InvalidFormatException(_("Birthday entry does not start with BDAY: \"#\"",birthday));
		}
		bday = bday.substring(4);
		if (bday.startsWith(";VALUE=DATE-TIME")) {
			bday = bday.substring(16);
		}
		if (!bday.startsWith(":")) {
			throw new InvalidFormatException(_("BDAY format invalid: #",birthday));
		}
		bday = bday.substring(1); // remove ':'
		if (bday.isEmpty()) return;
		if (!bday.startsWith("T")) {
			// date given
			if (!bday.startsWith("--")) {
				// year given
				year = bday.substring(0, 4);
				bday = bday.substring(4);

				if (bday.startsWith("-")) {
					bday = bday.substring(1);
				}
			} else { // bday starts with --
				bday = bday.substring(2);
			}
			if (!bday.isEmpty()) {
				if (!bday.startsWith("-")) {
					month = bday.substring(0, 2);
					bday = bday.substring(2);
					if (bday.startsWith("-")) {
						bday = bday.substring(1);
					}
				} else {
					bday = bday.substring(1);
				}
			}
			if (!bday.isEmpty()) {
				day = bday.substring(0, 2);
				bday = bday.substring(2);
			}
		}
		if (!bday.isEmpty()) {
			if (!bday.startsWith("T")) {
				throw new InvalidFormatException(_("BDAY format invalid: #",birthday));
			} else {
				bday = bday.substring(1);
				if (!bday.startsWith("-")) {
					hour = bday.substring(0, 2);
					bday = bday.substring(2);
				} else {
					bday = bday.substring(1);
				}
				if (!bday.isEmpty()) {
					if (!bday.startsWith("-")) {
						minute = bday.substring(0, 2);
						bday = bday.substring(2);
					} else {
						bday = bday.substring(1);
					}
				}
				if (!bday.isEmpty()) {
					second = bday;
				}
			}
		}
		checkInvalidity();
	}

	public void checkInvalidity() {
		invalid = false;
		if (year != null) {
			if (yearField != null) yearField.setBackground(Color.orange);
			try {
				int y = Integer.parseInt(year);
				if (y <= Calendar.getInstance().get(Calendar.YEAR)) {
					if (y < 100) year = "19" + y;
					if (y < 10) year = "190" + y;
					if (yearField != null) yearField.setBackground(UIManager.getColor ( "Panel.background" ));
				} else {
					invalid = true;
				}
			} catch (NumberFormatException nfe) {
				invalid = true;
			}
		}
		if (yearField != null && (year == null || year.isEmpty())) yearField.setBackground(Color.yellow);

		if (month != null) {
			if (monthField != null) monthField.setBackground(Color.orange);
			try {
				int m = Integer.parseInt(month);
				if (m < 13) {
					if (m < 10) month = "0" + m;
					if (monthField != null) monthField.setBackground(UIManager.getColor ( "Panel.background" ));
				} else {
					invalid = true;
				}
			} catch (NumberFormatException nfe) {
				invalid = true;
			}
		}
		if (monthField != null && (month == null || month.isEmpty())) monthField.setBackground(Color.yellow);

		if (day != null) {
			if (dayField != null) dayField.setBackground(Color.orange);
			try {
				int d = Integer.parseInt(day);
				if (d < 32) {
					if (d < 10) day = "0" + d;
					if (dayField != null) dayField.setBackground(UIManager.getColor ( "Panel.background" ));
				} else {
					invalid = true;
				}
			} catch (NumberFormatException nfe) {
				invalid = true;
			}
		}
		if (dayField != null && (day == null || day.isEmpty())) dayField.setBackground(Color.yellow);

		if (hour != null) {
			if (hourField != null) hourField.setBackground(Color.orange);
			try {
				int h = Integer.parseInt(hour);
				if (h < 24) {
					if (h < 10) hour = "0" + h;
					if (hourField != null) hourField.setBackground(UIManager.getColor ( "Panel.background" ));
				} else {
					invalid = true;
				}
			} catch (NumberFormatException nfe) {
				invalid = true;
			}
		}
		if (hourField != null && (hour == null || hour.isEmpty())) hourField.setBackground(Color.yellow);

		if (minute != null) {
			if (minuteField != null) minuteField.setBackground(Color.orange);
			try {
				int m = Integer.parseInt(minute);
				if (m < 60) {
					if (m < 10) minute = "0" + m;
					if (minuteField != null) minuteField.setBackground(UIManager.getColor ( "Panel.background" ));
				} else {
					invalid = true;
				}
			} catch (NumberFormatException nfe) {
				invalid = true;
			}
		}
		if (minuteField != null && (minute == null || minute.isEmpty())) minuteField.setBackground(Color.yellow);

		if (second != null) {
			if (secondField != null) secondField.setBackground(Color.orange);
			try {
				int m = Integer.parseInt(second);
				if (m < 60) {
					if (m < 10) second = "0" + m;
					if (secondField != null) secondField.setBackground(UIManager.getColor ( "Panel.background" ));
				} else {
					invalid = true;
				}
			} catch (NumberFormatException nfe) {
				invalid = true;
			}
		}
		if (secondField != null && (second == null || second.isEmpty())) secondField.setBackground(Color.yellow);
	}

	public int compareTo(Birthday otherBday) {
		return this.toString().compareTo(otherBday.toString());
	}

	public JPanel editForm() {
		VerticalPanel form = new VerticalPanel("Birthday");
		form.add(dateForm());
		form.add(timeForm());
		form.scale();
		return form;
	}

	public boolean equals(Birthday b2) {
		if (b2 == null) return false;
		return (year.equals(b2.year) && month.equals(b2.month) && day.equals(b2.day));
	}

	@Override
	public boolean isCompatibleWith(Birthday other) {
		if (other==null) return true;
		if (different(year, other.year)) return false;
		if (different(month, other.month)) return false;
		if (different(day, other.day)) return false;
		if (different(hour, other.hour)) return false;
		if (different(minute, other.minute)) return false;
		if (different(second, other.second)) return false;
		return true;
	}

	@Override
	public boolean isEmpty() {
		if (year != null) return false;
		if (month != null) return false;
		if (day != null) return false;
		if (hour != null) return false;
		if (minute != null) return false;
		if (second != null) return false;
		return true;
	}

	public boolean isInvalid() {
		return invalid;
	}

	@Override
	public boolean mergeWith(Birthday other) {
		if (!isCompatibleWith(other)) return false;
		year = merge(year, other.year);
		month = merge(month, other.month);
		day = merge(day, other.day);
		hour = merge(hour, other.hour);
		minute = merge(minute, other.minute);
		second = merge(second, other.second);
		if (other.invalid) invalid = true;
		return true;
	}

	public void stateChanged(ChangeEvent evt) {
		Object source = evt.getSource();
		if (source == yearField) {
			year = yearField.getText().trim();
			if (year.isEmpty()) year = null;
		}
		if (source == monthField) {
			month = monthField.getText().trim();
			if (month.isEmpty()) month = null;
		}
		if (source == dayField) {
			day = dayField.getText().trim();
			if (day.isEmpty()) day = null;
		}
		if (source == hourField) {
			hour = hourField.getText().trim();
			if (hour.isEmpty()) hour = null;
		}
		if (source == minuteField) {
			minute = minuteField.getText().trim();
			if (minute.isEmpty()) minute = null;
		}
		if (source == secondField) {
			second = secondField.getText().trim();
			if (second.isEmpty()) second = null;
		}
		checkInvalidity();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("BDAY");
		if ((hour == null || hour.isEmpty()) && (minute == null || minute.isEmpty()) && (second == null || second.isEmpty())) {
			// no time given
		} else {
			// time given
			sb.append(";VALUE=DATE-TIME");
		}
		sb.append(':');
		if (year == null || year.isEmpty()) {
			// no year given
			if (month == null || month.isEmpty()) {
				if (day != null && !day.isEmpty()) {
					sb.append("---");
				}
			} else {
				sb.append("--" + month);
			}

			if (day != null && !day.isEmpty()) {
				sb.append(day);
			}
		} else {
			// year given
			sb.append(year);
			if (month != null && !month.isEmpty()) {
				if (day == null || day.isEmpty()) {
					sb.append("-");
					sb.append(month);
				} else {
					sb.append(month);
					sb.append(day);
				}
			}
		}
		if ((hour == null || hour.isEmpty()) && (minute == null || minute.isEmpty()) && (second == null || second.isEmpty())) {
			// no time given
		} else {
			sb.append("T");
			if (hour == null || hour.isEmpty()) {
				sb.append("-");
			} else {
				sb.append(hour);
			}
			if (minute == null || minute.isEmpty()) {
				if (second != null && !second.isEmpty()) {
					sb.append("-");
				}
			} else {
				sb.append(minute);
			}
			if (second != null && !second.isEmpty()) {
				sb.append(second);
			}
		}
		return sb.toString();
	}

	private JComponent dateForm() {
		HorizontalPanel dateForm = new HorizontalPanel();
		if (invalid) dateForm.setBackground(Color.orange);
		dateForm.add(yearField = new InputField(_("Year of birth"), year));
		yearField.addEditListener(this);
		dateForm.add(monthField = new InputField(_("Month of birth"), month));
		monthField.addEditListener(this);
		dateForm.add(dayField = new InputField(_("Day of birth"), day));
		dayField.addEditListener(this);
		dateForm.scale();
		return dateForm;
	}

	private JComponent timeForm() {
		HorizontalPanel form = new HorizontalPanel();
		form.add(hourField = new InputField(_("Hour"), hour));
		hourField.addEditListener(this);
		form.add(minuteField = new InputField(_("Minute"), minute));
		minuteField.addEditListener(this);
		form.add(secondField = new InputField(_("Second"), second));
		secondField.addEditListener(this);
		form.scale();
		return form;
	}

	protected Birthday clone() throws CloneNotSupportedException {		
		try {
			return new Birthday(this.toString());
		} catch (Exception e) {
			throw new CloneNotSupportedException(e.getMessage());
		}
	}
	public String format(String format) {
	  return format.replace("Y", year).replace("m", month).replace("d", day);
  }

}
