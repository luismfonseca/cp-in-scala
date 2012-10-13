package pt.up.fe.luisfonseca.cp.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;

public class CP {
    private static final String CPwebserver = "http://paginas.fe.up.pt/~ei10139/cp/";

    public static final String URLstations = CPwebserver + "getStations.php";
    public static final String URLnews = CPwebserver + "getNews.php";
    public static final String URLwarnings = CPwebserver + "getWarnings.php";
    public static final String URLlogin = CPwebserver + "doLogin.php?email=%s&password=%s";
    public static final String URLsearchTimetable = CPwebserver + "searchTimetable.php?%s";
    public static final String URLgetTimetableDetalil = CPwebserver + "getDetailTimetable.php?queryID=%s&selectedSolution=%s";
    public static final String URLnewUser = CPwebserver + "newUser.php?%s";
    public static final String URLrecoverpw = CPwebserver + "recoverPassword.php?email=%s";

    public static HttpURLConnection get(String url) {
        try {
            final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("connection", "close");
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Student query Reply from web service
     * 
     * @param url
     * @return page
     * @throws IOException
     * @throws AuthenticationException
     */
    static String getReply(String strUrl, String cookie)
    		throws IOException, AuthenticationException
    {
        String page = null;
        do {
            final HttpURLConnection connection = get(strUrl);
            connection.setRequestProperty("Cookie", cookie);
            final InputStream pageContent = connection.getInputStream();
            String charset = HTTP.DEFAULT_CONTENT_CHARSET;
            if (connection.getResponseCode() == HttpURLConnection.HTTP_FORBIDDEN)
                throw new AuthenticationException();
            
            page = getPage(pageContent, charset);
            pageContent.close();
            
            InputStream errStream = connection.getErrorStream();
            if (errStream != null)
                errStream.close();
            
            connection.disconnect();
            if (page == null)
                throw new IOException("Null page");
            
        } while (page.isEmpty());
        
        return page;
    }
    
    public static String getPage(InputStream in) throws IOException {
        return getPage(in, HTTP.DEFAULT_CONTENT_CHARSET);
    }
    
    /**
     * Fetch data
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static String getPage(InputStream in, String encoding)
            throws IOException
    {
        BufferedInputStream bis = new BufferedInputStream(in);
        ByteArrayBuffer baf = new ByteArrayBuffer(512);
        int read = 0;
        byte[] buffer = new byte[512];
        while (true) {
            read = bis.read(buffer);
            if (read == -1) {
                break;
            }
            baf.append(buffer, 0, read);
        }
        bis.close();
        in.close();
        return new String(baf.toByteArray(), encoding);
    }
    
    
}
