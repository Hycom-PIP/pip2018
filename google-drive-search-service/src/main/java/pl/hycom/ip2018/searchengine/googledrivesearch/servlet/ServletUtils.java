package pl.hycom.ip2018.searchengine.googledrivesearch.servlet;

import com.google.api.client.util.store.FileDataStoreFactory;

public class ServletUtils {

    public static final String CLIENT_ID = "178672516058-jqge1qe3n2hef1oe6kv0f3c91h8f8eae.apps.googleusercontent.com";

    public static final String CLIENT_SECRET = "m7aLELtPivozEJZlNlCSG-Qz";

    /** Directory to store user credentials for this application. */
    public static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/drive-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    public static FileDataStoreFactory DATA_STORE_FACTORY;

    static {
        try {
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
}
