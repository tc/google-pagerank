package google.pagerank;

import java.net.URLConnection;
import java.net.URL;
import java.io.InputStream;
import java.util.Random;

/**
 * <b>PageRank provides simple API to Google PageRank Technology</b>
 * Original source: http://www.temesoft.com/google-pagerank-api.jsp
 * <br>
 * PageRank queries google toolbar webservice and returns a
 * google page rank. 
*/
public class PageRank {

    /**
     * List of available google datacenter IPs and addresses
     */
    static final public String [] GOOGLE_PR_DATACENTER_IPS = new String[]{
                "64.233.183.91",
                "64.233.189.44",
                "66.249.89.83",
                "toolbarqueries.google.com",
                };
    /**
     * Must receive a domain in form of: "http://www.domain.com"
     * @param domain - (String)
     * @return PR rating (int) or -1 if unavailable or internal error happened.
     */
    public static int get(String domain) {
        int dataCenterIdx = new Random().nextInt(GOOGLE_PR_DATACENTER_IPS.length) + 1;
        int result = -1;
        JenkinsHash jHash = new JenkinsHash();

        String googlePrResult = "";

        long hash = jHash.hash(("info:" + domain).getBytes());

        String url = "http://"+GOOGLE_PR_DATACENTER_IPS[dataCenterIdx]+"/search?client=navclient-auto&hl=en&"+
                "ch=6"+hash+"&ie=UTF-8&oe=UTF-8&features=Rank&q=info:" + domain;

        try {
            URLConnection con = new URL(url).openConnection();
            InputStream is = con.getInputStream();
            byte [] buff = new byte[1024];
            int read = is.read(buff);
            while (read > 0) {
                googlePrResult = new String(buff, 0, read);
                read = is.read(buff);
            }
            googlePrResult = googlePrResult.split(":")[2].trim();
            result = new Long(googlePrResult).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
