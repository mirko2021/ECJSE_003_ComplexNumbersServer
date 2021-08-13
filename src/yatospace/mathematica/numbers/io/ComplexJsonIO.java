package yatospace.mathematica.numbers.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.google.gson.Gson;

import yatospace.mathematica.numbers.model.Complex;

/**
 * Complex json IO. 
 * @author VM
 * @version 1.0
 */
public final class ComplexJsonIO {
	private ComplexJsonIO() {}
	private static Gson gson = new Gson();
	
	public static String toString(Complex complex) {
		try {
			if(complex==null) return null; 
			return gson.toJson(complex, Complex.class); 
		}catch(Exception ex) {
			return null; 
		}
	}
	
	public static boolean fromString(String input, Complex complex) {
		try {
			if(input==null) return false;
			if(complex==null) return false;
			Complex c = gson.fromJson(input, Complex.class);
			if(c==null) return false; 
			complex.apply(c);
			return true;
		}catch(Exception ex) {
			return false;
		}
	}
	
	public static boolean toFile(File file, Complex complex) {
		if(file==null)    return false; 
		if(complex==null) return false; 
		try(FileOutputStream fos = new FileOutputStream(file)){
			fos.write(gson.toJson(complex, Complex.class).getBytes("UTF-8"));
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
			Complex c = gson.fromJson(input, Complex.class);
			if(c==null) return false; 
			complex.apply(c);
			return true;
		}catch(Exception ex) {
			return false; 
		}
	}
}
