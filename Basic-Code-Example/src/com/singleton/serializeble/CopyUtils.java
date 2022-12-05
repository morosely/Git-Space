package com.singleton.serializeble;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CopyUtils {
	
	public static Object deepCopy(Serializable src) throws IOException, ClassNotFoundException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(src);
		oos.flush();
		oos.close();
		baos.close();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object copy = ois.readObject();
		ois.close();
		bais.close();
		return copy;
	}

}
