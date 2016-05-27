import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;
public final class CalculatorWithButtons extends MIDlet implements CommandListener, ItemCommandListener {
	private final TextField one = new TextField("First Number","",20,TextField.DECIMAL);
	private final TextField two = new TextField("Second Number","",20,TextField.DECIMAL);
	private final TextField res = new TextField("Second Number","",20,TextField.UNEDITABLE);
	private final Command add = new Command("Add",Command.ITEM,1);
	private final Command sub = new Command("Subtract",Command.ITEM,1);
	private final Command mul = new Command("Multiply",Command.ITEM,1);
	private final Command div = new Command("Division",Command.ITEM,1);
	private final Command exit = new Command("Exit",Command.EXIT,2);
	private final Alert alert = new Alert("ERROR","",null,AlertType.ERROR);
	private boolean isInitialized = false;
	protected void startApp() {
		if(isInitialized) return;
		Form f = new Form("Calculator");
		f.append(one);
		f.append(two);
		StringItem ia = new StringItem("    +    ","",Item.BUTTON);
		StringItem is = new StringItem("    -    ","",Item.BUTTON);
		StringItem im = new StringItem("    *    ","",Item.BUTTON);
		StringItem id = new StringItem("    /    ","",Item.BUTTON);
		ia.setDefaultCommand(add);
		ia.setItemCommandListener(this);
		f.append(ia);
		is.setDefaultCommand(sub);
		is.setItemCommandListener(this);
		f.append(is);
		im.setDefaultCommand(mul);
		im.setItemCommandListener(this);
		f.append(im);
		id.setDefaultCommand(div);
		id.setItemCommandListener(this);
		f.append(id);
		f.append(res);
		f.addCommand(exit);
		f.setCommandListener(this);
		Display.getDisplay(this).setCurrent(f);
		alert.addCommand(new Command("Back",Command.SCREEN,1));
		isInitialized = true;
	}
	protected void pauseApp() {}
	protected void destroyApp(boolean u) {}
	public void commandAction(Command cc, Displayable dd) {
		destroyApp(false);
		notifyDestroyed();
		return;
	}
	public void commandAction(Command cc, Item i) {
		double a, b, c = 0.0;
		try {
			a = Double.parseDouble(one.getString());
			b = Double.parseDouble(two.getString());
			if(cc == add) {
				c = a + b;
			}
			if(cc == sub) {
				c = a - b;
			}
			if(cc == mul) {
				c = a * b;
			}
			if(cc == div) {
				c = a / b;
			}

		}
		catch(NumberFormatException e) {
			return;
		}
		catch(ArithmeticException e) {
			alert.setString("Divide By Zero");
			Display.getDisplay(this).setCurrent(alert);
			return;
		}
		res.setString(Double.toString(c));
	}
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
}