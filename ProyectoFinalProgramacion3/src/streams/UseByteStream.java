package streams;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UseByteStream {

	public static void copyFile(String source,String destination) {
		
		try (
				FileInputStream read = new FileInputStream (source);
				FileOutputStream write= new FileOutputStream(destination)) {
			
			System.out.println("Cargados");
			int readByte;
			
			while((readByte = read.read())!=-1) {
				write.write(readByte);
				//System.out.println((char)readByte);
				
			}
			System.out.println("Se copio el archivo");
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}/*finally {
			try {
				if(read !=null )
					read.close();
				if(write !=null )
					write.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/

	}

}
