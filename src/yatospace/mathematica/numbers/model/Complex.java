package yatospace.mathematica.numbers.model;

import java.io.Serializable;

import yatospace.mathematica.numbers.util.Quadrant;

/**
 * Complex nubmer representation. 
 * @author VM
 * @version 1.0
 */
public class Complex implements Serializable, Cloneable{
	private static final long serialVersionUID = 9011940168215785034L;
	private double real = 0.0d; 
	private double imag = 0.0d;
	
	public Complex() {}
	
	public Complex(double real) {
		this.real = real; 
	}
	
	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}
	
	public double getReal() {
		return real;
	}
	
	public void setReal(double real) {
		this.real = real;
	}
	
	public double getImag() {
		return imag;
	}
	
	public void setImag(double imag) {
		this.imag = imag;
	}
	
	public void plus(Complex other) {
		if(other==null) other = new Complex();
		real += other.real; 
		imag += other.imag; 
	}
	
	public void minus(Complex other) {
		if(other==null) other = new Complex();
		real -= other.real; 
		imag -= other.imag; 
	}
	
	public void multipy(Complex other) {
		if(other==null) other = new Complex();
		double realRes = real * other.real - imag * other.imag;
		double imagRes = imag = real * other.imag + imag * other.real; 
		real = realRes; 
		imag = imagRes; 
	}
	
	public void divide(Complex other) {
		if(other==null) other = new Complex();
		double resReal = real * other.real + imag * other.imag;
		double resImag = imag * other.real - real * other.imag;
		resReal /= other.real * other.real + other.imag * other.imag; 
		resImag /= other.real * other.real + other.imag * other.imag;
		real = resReal;
		imag = resImag; 
	}
	
	public double module() {
		return Math.sqrt(real*real+imag*imag); 
	}
	
	public double argument() {
		if(real==0.0d) throw new NullPointerException("Argument of the Real zero."); 
		double effImag = Math.abs(imag); 
		double effReal = Math.abs(real);
		return Math.atan(effImag/effReal)*(180d/Math.PI);
	}
	
	public Quadrant quadrant() {
		if(real>0 && imag>0) return Quadrant.I; 
		if(real<0 && imag>0) return Quadrant.II; 
		if(real<0 && imag<0) return Quadrant.III; 
		if(real>0 && imag<0) return Quadrant.IV; 
		return Quadrant.O;
	}
	
	public double phase() {
		if(real==0 && imag==0) throw new NullPointerException("Phase for Zero.");
		if (imag == 0 && real > 0) return 0; 
		if (imag == 0 && real < 0) return 180;
		if (imag > 0 && real == 0) return 90;
		if (imag < 0 && real == 0) return 270;
		if (quadrant() == Quadrant.I)  return argument(); 
		if (quadrant() == Quadrant.II) return 180 - argument();
		if (quadrant() == Quadrant.III) return 180 + argument(); 
		if (quadrant() == Quadrant.IV)  return 360 - argument();
		throw new RuntimeException();
	}
	
	@Override 
	public Complex clone() {
		Complex result = new Complex();
		result.real = real; 
		result.imag = imag; 
		return result; 
	}
	
	@Override 
	public boolean equals(Object object) {
		if(object instanceof Complex) {
			Complex complex = (Complex) object; 
			if(real!=complex.real) return false; 
			if(imag!=complex.imag) return false; 
			return true; 
		}
		return false; 
	}
	
	@Override 
	public String toString() {
		if (real == 0.0 && imag == 0.0) return "0";
		else if (imag == 0.0) return ""+real;
		else if (real == 0.0) {
			if (imag < 0.0) return "-i" + (-imag);
			else return "i" + imag;
		}
		else if (imag < 0) return "" +real + "-i" + (-imag);
		else  return "" + real + "+i" + imag;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode(); 
	}
	
	
	public void fromString(String text) {
		Complex c = loadComplex(text);
		if(c==null) throw new NullPointerException();
		real = c.real; 
		imag = c.imag; 
	}
	
	public void apply(Complex complex) {
		if(complex==null) throw new NullPointerException();
		real = complex.real;
		imag = complex.imag;
	}
	
	public static Complex loadComplex(String text) {
		if(text==null) return null; 
		if(text.length()==0) return null; 
		if(text.endsWith("+")) return null; 
		if(text.endsWith("-")) return null; 
		if(text.endsWith("i")) return null;
		int countI = 0; 
		int countPlus = 0; 
		int countMinus = 0;
		int countDots = 0; 
		
		for(int i=0; i<text.length(); i++) {
			if(text.charAt(i)=='+') countPlus++; 
			else if(text.charAt(i)=='-') countMinus++;
			else if(text.charAt(i)=='.') countDots++;
			else if(text.charAt(i)=='i') countI++;
			else if(!Character.isDigit(text.charAt(i)))
				return null; 
		}
		
		if(countI>1) return null; 
		if(countPlus+countMinus>2) return null; 
		if(countDots>2) return null; 
		
		if(text.contains("++")) return null; 
		if(text.contains("--")) return null; 
		if(text.contains("+-")) return null; 
		if(text.contains("-+")) return null; 
		if(text.contains("i+")) return null; 
		if(text.contains("i-")) return null; 
		
		if(countPlus+countMinus==2) {
			if(!text.startsWith("+") && !text.startsWith("-")) return null; 
			if(!text.contains("+i") && !text.contains("-i")) return null;
			if(text.startsWith("+i")) return null; 
			if(text.startsWith("-i")) return null;
			String signReal = text.substring(0,1); 
			String signImag = ""; 
			text = text.substring(1); 
			if(text.contains("+")) signImag = "+";
			if(text.contains("-")) signImag = "-";
			String[] array = text.split("["+signImag+"]");
			if(array.length!=2) return null; 
			if(!array[1].startsWith("i")) return null; 
			array[1] = array[1].substring(1);
			double sr = 1.0; 
			double si = 1.0; 
			if(signReal.contentEquals("+")) sr = 1.0; 
			if(signImag.contentEquals("+")) si = 1.0; 
			if(signReal.contentEquals("-")) sr = -1.0; 
			if(signImag.contentEquals("-")) si = -1.0; 
			return new Complex(sr*Double.parseDouble(array[0]), si*Double.parseDouble(array[1]));
		}else if(countPlus==1) {
			if(text.startsWith("+")) {
				if(text.startsWith("+i")) {
					text = text.substring(2); 
					return new Complex(0.0, Double.parseDouble(text));
				}else {
					text = text.substring(1); 
					return new Complex(Double.parseDouble(text));
				}
			}else {
				if(!text.contains("+i")) return null; 
				String[] array = text.split("[+]"); 
				if(array.length!=2) return null;
				if(!array[1].startsWith("i")) return null; 
				array[1] = array[1].substring(1);
				return new Complex(Double.parseDouble(array[0]), Double.parseDouble(array[1]));
			}
		}else if(countMinus==1) {
			if(text.startsWith("-")) {
				if(text.startsWith("-i")) {
					text = text.substring(2); 
					return new Complex(0.0, -Double.parseDouble(text));
				}else {
					text = text.substring(1); 
					return new Complex(-Double.parseDouble(text));
				}
			}else {
				if(!text.contains("-i")) return null; 
				String[] array = text.split("[-]"); 
				if(array.length!=2) return null;
				if(!array[1].startsWith("i")) return null; 
				array[1] = array[1].substring(1);
				return new Complex(Double.parseDouble(array[0]), -Double.parseDouble(array[1]));
			}
		}else if(countI==1) {
			if(!text.startsWith("i")) return null;
			text = text.substring(1); 
			return new Complex(0.0d, Double.parseDouble(text));
		}else {
			return new Complex(Double.parseDouble(text));
		}
	}
}
