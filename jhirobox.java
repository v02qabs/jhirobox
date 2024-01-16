import java.io.*;

class jhirobox{
	public jhirobox(String app_name, String paramater){
		app(app_name, paramater);
	}
	private void ls(String para)
	{
			System.out.println("ファイルリストの表示");

			File dir = new File(para);
			String[] list_dir = dir.list();
			for(int i=0; i<list_dir.length; i++){
				System.out.println(list_dir[i]);
			}
	}
	private void cat(String fname){
		System.out.println("open filename ; " + fname);
		String line = null;
		try{
			
			File open_file = new File(fname);
			BufferedReader br = new BufferedReader(new FileReader(open_file));
			while(line != br.readLine()){
				System.out.println(line);
				line = br.readLine();
			}
		}
		catch(Exception error)
		{
			System.out.println("cat error");
			System.out.println(error.toString());
		}
	}
	
	private void app(String app , String para){
		System.out.println("app exec ");
		if(app.equals("ls")){
			ls(para);
		}
		else if(app.equals("cat"))
		{
			System.out.println("cat " + para);
			cat(para);
		}
		else{
			System.out.println("no app.");
		}
	}
	public static void main(String[] args)
	{
		System.out.println("hello jhirobox");
		try{
			System.out.println("args[0] : " + args[0] + " args[1] : " + args[1]);
			String args0 = args[0];
			String args1 = args[1];
			
			new jhirobox(args0, args1);
		}
		catch(Exception error)
		{
			//System.out.println("null pointer");
		}
	}
}