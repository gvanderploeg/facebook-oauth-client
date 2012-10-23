package com.geertvanderploeg.facebook;

import org.junit.Test;

public class OAuthClientServletTest {

  @Test
  public void testMatch() {
//    Matcher matcher = OAuthClientServlet.p.matcher("access_token=12398172398&expires=123123");
//    assertTrue(matcher.matches());
//    assertNotNull(matcher.group(1));
//    System.out.println(matcher.group(1));

    String r = "access_token=12398172398&expires=123123";
    String ac = r.split("[&=]")[1];
    System.out.println(ac);
  }
}
