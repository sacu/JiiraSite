package org.jiira.we.url;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.jiira.utils.CommandCollection;

public class SAURLStream {
	private SAURLStream() {}
	private static SAURLStream instance;
	
	public static SAURLStream getInstance() {
		if(null == instance) {
			instance = new SAURLStream();
		}
		return instance;
	}
	
	//资源下载转byte[]
    public byte[] download(String url) {
        try {
        	URL con = new URL(url);
            DataInputStream dataInputStream = new DataInputStream(con.openStream());
            ByteArrayOutputStream output = new ByteArrayOutputStream();
 
            byte[] buffer = new byte[1024];
            int length;
 
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            dataInputStream.close();
            System.out.println(output.size());
            byte[] data = output.toByteArray();
            output.close();
            return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void save(String type, String name, byte[] data) {
		try {
			FileOutputStream os = new FileOutputStream(CommandCollection.GetLocalPath(type) + name);
			os.write(data);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
