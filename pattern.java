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
    File file = new File(string_filename);
    System.out.println("バイト：" + file.length() + " Byte");
    System.out.println("キロバイト：" + (file.length() / 1024.0) + " KByte");
    System.out.println("メガバイト：" + (file.length() / 1024.0 / 1024.0) + " MByte");
  }
}

