package yatospace.mathematica.numbers.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import yatospace.mathematica.numbers.model.Complex;

/**
 * Complex Basic IO. 
 * @author VM
 * @version 1.0
 */
public final class ComplexBasicIO {
	private ComplexBasicIO() {}
	
	public static String toString(Complex complex) {
		if(complex==null) return null; 
		return complex.toString(); 
	}
	
	public static boolean fromString(String input, Complex complex) {
		if(input==null) return false;
		if(complex==null) return false;
		Complex c = Complex.loadComplex(input);
		if(c==null) return false; 
		complex.apply(c);
		return true;
	}
	
	public static boolean toFile(File file, Complex complex) {
		if(file==null)    return false; 
		if(complex==null) return false; 
		try(FileOutputStream fos = new FileOutputStream(file)){
			fos.write(complex.toString().getBytes("UTF-8")); 
			return true;
		}catch(Exception ex) {
			return false; 
		}
	}
	
	public static boolean fromFile(File file, Complex complex) {
		if(file==null)    return false; 
		if(complex==null) return false; 
		try(FileInputStream fis = new FileInputStream(file)){
			String input = new String(fis.readAllBytes(), "UTF-8");
			Complex c = Complex.loadComplex(input);
			if(c==null) return false; 
			complex.apply(c);
			return true;
		}catch(Exception ex) {
			return false; 
		}
	}
}
