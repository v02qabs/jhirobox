import java.io.*;
import java.util.*;

class pattern{
	public static void main(String[] args){
		System.out.println("Hello trans parttern.");
		new pattern().init();
	}
	private void init(){
		System.out.println("ファイル名を入力せよ");

		Scanner scan_filename = new Scanner(System.in);
		String string_filename = scan_filename.next();
		
		File scan_files = new File(string_filename);
  		  System.out.println("バイト：" + scan_files.length() + " Byte");
  		  System.out.println("キロバイト：" + (scan_files.length() / 1024.0) + " KByte");
  		  System.out.println("メガバイト：" + (scan_files.length() / 1024.0 / 1024.0) + " MByte");
  		  System.out.println("GB: "+ (scan_files.length() / 1024.0/1024.0/1024.0) + "GB");
  		
	}

}

