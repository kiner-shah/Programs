import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;
public final class EMICalculator extends MIDlet implements CommandListener {
	private final TextField p = new TextField("Principal amount","",20,TextField.DECIMAL);
	private final TextField r = new TextField("Rate of Interest","",20,TextField.DECIMAL);
	private final TextField n = new TextField("No. of years","",20,TextField.DECIMAL);
	private final TextField tr = new TextField("EMI","",20,TextField.DECIMAL);
	private final Command exit = new Command("Exit",Command.EXIT,2);
	private final Command calc = new Command("Calculate",Command.SCREEN,1);
	private final Alert alert = new Alert("ERROR","",null,AlertType.ERROR);
	private boolean isInitialized = false;
	protected void startApp() {
		if(isInitialized) return;
		Form f = new Form("Compund Interest Calculator");
		f.append(p);
		f.append(r);
		f.append(n);
		f.append(tr);
		f.addCommand(exit);
		f.addCommand(calc);
		f.setCommandListener(this);
		Display.getDisplay(this).setCurrent(f);
		isInitialized = true;
	}
	protected void pauseApp() {}
	protected void destroyApp(boolean u) {}
	public double getNumber(TextField t) throws NumberFormatException {
		String s = t.getString();
		double n;
		if(s.length() == 0) {
			alert.setString("No Argument");
			Display.getDisplay(this).setCurrent(alert);
			throw new NumberFormatException();
		}
		try {
			n = Double.parseDouble(s);
		}
		catch(NumberFormatException e) {
			alert.setString("Argument out of range");
			Display.getDisplay(this).setCurrent(alert);
			throw e;
		}
		return n;
	}
	private double square(double x) { return x * x; }
	private double pow(double b, double e, double p) {
		if(e < 0) return 1 / (pow(b, -e, p));
		if(e >= 10) return square(pow(b,e/2,p/2));
		if(e >= 1) return b * pow(b,e - 1, p);
		if(p >= 1) return Math.sqrt(b);
		return Math.sqrt(pow(b, e*2, p*2));
	}
	public void commandAction(Command cc, Displayable dd) {
		if(cc == exit) {
			destroyApp(false);
			notifyDestroyed();
			return;
		}
		double res, a, b, c;
		try {
			a = getNumber(p);
			b = getNumber(r);
			c = getNumber(n);
			res = (a * b * pow(1.0 + b , c, 0.0001))/(pow(1.0 + b, c, 0.0001) - 1);
		}
		catch(NumberFormatException e) { return; }
		catch(ArithmeticException e) {
			alert.setString("Divide by Zero");
			Display.getDisplay(this).setCurrent(alert);
			return;
		}
		tr.setString(Double.toString(res));
	}
}
