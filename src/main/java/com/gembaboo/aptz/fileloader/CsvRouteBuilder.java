package com.gembaboo.aptz.fileloader;

import com.gembaboo.aptz.domain.AirportFileRecord;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;

import java.io.File;

/**
 * Camel route builder to poll a directory, load the files placed into it, process each line and
 * parse it using Camel Bindy then forward it to {@link #DIRECT_PROCESS_OUTPUT}
 * See {@link #configure()}
 */
public abstract class CsvRouteBuilder extends RouteBuilder {

    protected static final String DIRECT_PROCESS_OUTPUT = "direct:processOutput";
    /**
     * Location if the file being processed. It is relative to {@link #file}
     */
    private static final String PRE_MOVE = ".inprogress";
    /**
     * Failed files are moved here. It is relative to {@link #file}
     */
    private static final String FAILED = ".failed";
    /**
     * Processed files are moved here. It is relative to {@link #PRE_MOVE}
     */
    private static final String PROCESSED = "../.processed";
    /**
     * The directory or file subject for processing by Camel
     */
    private File file = new File(System.getProperty("user.dir"));

    @Override
    public void configure() throws Exception {
        String fileUri = getFileUri();
        from(fileUri).routeId("parse-file")
                //Split line by line
                .split()
                .tokenize("\n").streaming().unmarshal().string("UTF-8")
                //Ignore the first line of the file
                .filter().simple("${property.CamelSplitIndex} > 0")
                //remove double " characters
                .transform(body().regexReplaceAll("\"\"", ""))
                //Parse the line using camel bindy
                .unmarshal().bindy(BindyType.Csv, AirportFileRecord.class.getPackage().getName())
                .to(DIRECT_PROCESS_OUTPUT);
    }


    /**
     * If the {@link #file} is a directory (default behavior), the directory is polled for new files.
     * The processed file is moved to the {@link #PRE_MOVE} folder for processing. Once the processing completed it is moved to the {@link #PROCESSED} folder.
     * If the processing fails it is moved to the {@link #FAILED} directory. All three folders are under the {@link #file} directory.
     * <p/>
     * In case only one file should be loaded (i.e. {@link #file} referst to a plain file), it is left on its original location.
     *
     * @return The URI of the file component (For the uri syntax refer to <a href="http://camel.apache.org/file2.html">http://camel.apache.org/file2.html</a>)
     */
    private String getFileUri() {
        String url = "file:";
        if (file.isDirectory()) {
            url += file.getAbsolutePath() + "?move=" + PROCESSED + "&preMove=" + PRE_MOVE + "&moveFailed=" + FAILED + "&charset=utf-8";
        } else {
            url += file.getParent() + "?noop=true&charset=utf-8&fileName=" + file.getName();
        }
        return url;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
