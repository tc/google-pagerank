package google.pagerank;

import static org.junit.Assert.*;
import org.junit.Test;

public class PageRankTest{

  @Test(timeout=10000)
  public void testPageRank(){
     String domain = "http://www.gmail.com";
     System.out.println("Checking " + domain);
     int pageRank = PageRank.get(domain);
     assertEquals(9, pageRank);
  }
}
