import java.io.*;
import java.util.*;
import org.apache.commons.io.FileUtils;

public class touch{

	private String string_fname_new;

	public static void main(String[] args){
		System.out.print("Hello touch file.");
		System.out.println("File type, please");

			new touch().init();
		
	}
	public touch(){
	}
	private void init(){
		System.out.println("File touch please typing file name and full path");
		Scanner scan_fname = new Scanner(System.in);
		string_fname_new = scan_fname.next();
	try{
		FileUtils.touch(new File(string_fname_new));
	}catch(Exception error){
		System.out.println("write error");
	}

	}
}
	
