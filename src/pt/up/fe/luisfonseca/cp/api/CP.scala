package pt.up.fe.luisfonseca.cp.api

import java.net.HttpURLConnection
import java.io.IOException
import java.io.InputStream
import org.apache.http.auth.AuthenticationException
import java.net.URL
import org.apache.http.protocol.HTTP
import org.apache.http.util.ByteArrayBuffer
import java.io.BufferedInputStream
import scala.io.Source

object CP {
	private val CPwebserver = "http://paginas.fe.up.pt/~ei10139/cp/"
    val URLstations = CPwebserver + "getStations.php";
    val URLnews = CPwebserver + "getNews.php";
    val URLwarnings = CPwebserver + "getWarnings.php";
    val URLlogin = CPwebserver + "doLogin.php?email=%s&password=%s";
    val URLsearchTimetable = CPwebserver + "searchTimetable.php?%s";
    val URLgetTimetableDetalil = CPwebserver + "getDetailTimetable.php?queryID=%s&selectedSolution=%s";
    val URLnewUser = CPwebserver + "newUser.php?%s";
    val URLrecoverpw = CPwebserver + "recoverPassword.php?email=%s";

    def get(url: String): HttpURLConnection = {
        try {
            val connection = new URL(url).openConnection()
            connection.setRequestProperty("connection", "close")
            return connection.asInstanceOf[HttpURLConnection]
        } catch {
          case e: IOException => {
        	  e.printStackTrace()
        	  return null;
          }
        }
    }
    
    /**
     * Student query Reply from web service
     * 
     * @param url
     * @return page
     * @throws IOException
     * @throws AuthenticationException
     */
    def getReply(strUrl: String, cookie: String) : String = {
        val connection = get(strUrl);
        connection.setRequestProperty("Cookie", cookie);
        val pageContent = connection.getInputStream();
        val charset = HTTP.DEFAULT_CONTENT_CHARSET;
        if (connection.getResponseCode() == HttpURLConnection.HTTP_FORBIDDEN)
            throw new AuthenticationException();
        
        val page = getPage(pageContent, charset);
        pageContent.close();
        
        val errStream = connection.getErrorStream();
        if (errStream != null)
            errStream.close();
        
        connection.disconnect();
        if (page == null)
            throw new IOException("Null page");
        
        return page;
    }
    
    def getPage(in: InputStream): String =
      getPage(in, HTTP.DEFAULT_CONTENT_CHARSET)
    
    /**
     * Fetch data
     * 
     * @param in
     * @return
     * @throws IOException
     */
    def getPage(in: InputStream, encoding: String): String =
    {
        val result = Source.fromInputStream(in).getLines().mkString("\n")
        in.close();
        return result
    }
    
    
}