package proclipsing.processingprovider;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import proclipsing.os.OSHelperManager;
import proclipsing.util.LogHelper;

public class ProcessingLibrary {
    
    private String identifier;
    private String processing_path;
    
    public ProcessingLibrary(String processingPath, String identifier) {
    	this.identifier = identifier;
    	this.processing_path = processingPath;
    }
    
    public String getIdentifier() {
        return identifier;
    }
    
    /**
     * Gets urls to all the library files (.jar, .zip, .dll, .so)
     * 
     * @return
     */
    public URL[] getUrls() {

        File realPath = new File(getResourcePath());
        if (!realPath.exists()) return null;

        URL[] urls = getUrls(realPath.listFiles());        
        return urls;
    }

    /**
     * given a set of files in the filesystem, 
     * return an array with urls for all the files
     * 
     * @param files
     * @return
     */
    private URL[] getUrls(File[] files) {
        ArrayList<URL> urls = new ArrayList<URL>();
        for(File file : files) {
            if (file.isDirectory()) continue;
            try {
                urls.add(file.toURI().toURL());
            } catch (MalformedURLException e) {
                LogHelper.LogError(e);
            }
        }
        return urls.toArray(new URL[urls.size()]);
    }
    
    /**
     * Gets the 
     * 
     * @return
     *  the path to the current library.
     */
    private String getResourcePath() {
        if (isCore())
            return processing_path + OSHelperManager.getHelper().getCorePath();
        else 
            return processing_path + OSHelperManager.getHelper().getPathToLibrary(identifier);
    }
    
    /**
     * Are we the processing core?
     * 
     * @return
     */
    private boolean isCore() {
        return identifier == ProcessingProvider.CORE;
    }
    
}
