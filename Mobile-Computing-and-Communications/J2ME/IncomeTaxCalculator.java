import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;
public final class IncomeTaxCalculator extends MIDlet implements CommandListener {
	private final Command exit = new Command("Exit",Command.EXIT,2);
	private final Command calc = new Command("Calculate",Command.SCREEN,1);
	private final Alert alert = new Alert("ERROR","",null,AlertType.ERROR);
	private final TextField t1 = new TextField("Name","",20,TextField.ANY);
	private final TextField t2 = new TextField("Age","",20,TextField.DECIMAL);
	private final ChoiceGroup t3 = new ChoiceGroup("",ChoiceGroup.POPUP,new String[] {"male","female"},null);
	private final TextField t4 = new TextField("Monthly Income","",20,TextField.DECIMAL);
	private final TextField t5 = new TextField("Income from other source","",20,TextField.DECIMAL);
	private final TextField t6 = new TextField("Investment under 80C","",20,TextField.DECIMAL);
	private final TextField t7 = new TextField("Rent/Interest on home loan","",20,TextField.DECIMAL);
	private final TextField t8 = new TextField("Mediclaim","",20,TextField.DECIMAL);
	private final TextField t9 = new TextField("Donations","",20,TextField.DECIMAL);
	private final TextField t10 = new TextField("Taxable Income","",20,TextField.UNEDITABLE);
	private final TextField t11 = new TextField("Gross Income","",20,TextField.UNEDITABLE);
	private final TextField t12 = new TextField("Standard Deduction","",20,TextField.UNEDITABLE);
	private final TextField tr = new TextField("Total tax for CFY","",20,TextField.UNEDITABLE);
	private boolean isInitialized = false;
	protected void startApp() {
		if(isInitialized) return;
		Form f = new Form("Income Tax Calculator");
		f.append(t1);
		f.append(t2);
		f.append(t3);
		f.append(t4);
		f.append(t5);
		f.append(t6);
		f.append(t7);
		f.append(t8);
		f.append(t9);
		f.append(t10);
		f.append(t11);
		f.append(t12);
		f.append(tr);
		f.addCommand(exit);
		f.addCommand(calc);
		f.setCommandListener(this);
		Display.getDisplay(this).setCurrent(f);
		alert.addCommand(new Command("Back",Command.SCREEN,1));
		isInitialized = true;
	}
	protected void pauseApp() {}
	protected void destroyApp(boolean u) {}
	public void commandAction(Command cc, Displayable dd) {
		if(cc == exit) {
			destroyApp(false);
			notifyDestroyed();
			return;
		}
		int age;
		String gender;
		double standard_deduction,gross_income,taxable_income,a,b,c,d,e,f,res;
		try {
			a = getNumber(t4);
			b = getNumber(t5);
			c = getNumber(t6);
			d = getNumber(t7);
			e = getNumber(t8);
			f = getNumber(t9);
			gross_income = (a * 12) + b;
			if(c > 150000) c = 150000;
			if(d > 200000) d = 200000;
			if(e > 15000) e = 15000;
			if(f > 10000) f = 10000;
			standard_deduction = c + d + e + f;
			taxable_income = gross_income - standard_deduction;
			t11.setString(Double.toString(gross_income));
			t12.setString(Double.toString(standard_deduction));
			t10.setString(Double.toString(taxable_income));
			age = Integer.parseInt(t2.getString());
			gender = (t3.getSelectedIndex() == 0 ? "male" : "female");
			res = findIncomeTax(taxable_income, age, gender);
			tr.setString(Double.toString(res));
		}
		catch(NumberFormatException ee) {
			return;
		} 		
	}
	private double getNumber(TextField t) throws NumberFormatException {
		String s = t.getString();
		double n;
		try {
			n = Double.parseDouble(s);
		}
		catch(NumberFormatException e) {
			alert.setString("Bad value entered");
			Display.getDisplay(this).setCurrent(alert);
			throw e;
		}
		return n;
	}
	private double findIncomeTax(double income, int age, String gender) {
		double tax = 0.0;
		if(age < 60) {
			if(income <= 250000) tax = 0;
			else if(income >= 250001 && income <= 500000) tax = 0.1 * income;
			else if(income >= 500001 && income <= 1000000) tax = 0.2 * income;
			else if(income >= 1000001) tax = 0.3 * income; 
		}
		else if(age >= 60 && age < 80) {
			if(income <= 300000) tax = 0;
			else if(income >= 300001 && income <= 500000) tax = 0.1 * income;
			else if(income >= 500001 && income <= 1000000) tax = 0.2 * income;
			else if(income >= 1000001) tax = 0.3 * income; 
		}
		if(age >= 80) {
			if(income <= 500000) tax = 0;
			else if(income >= 500001 && income <= 1000000) tax = 0.2 * income;
			else if(income >= 1000001) tax = 0.3 * income; 
		}
		return tax;
	}
}