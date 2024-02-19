package Global;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Configuration {
    public static FileInputStream ouvre(String filepath) {
        try {
		    return new FileInputStream(filepath);
		} catch (FileNotFoundException err) {
			err.printStackTrace();
			System.exit(2);
            return null;
		}
    }
}