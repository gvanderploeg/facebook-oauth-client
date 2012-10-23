package com.geertvanderploeg.facebook;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Util {

  public static String readStringFromUrl(String url) throws IOException {
    InputStream accessTokenIS = new URL(url).openStream();
    return new Scanner(accessTokenIS).useDelimiter("\\A").next();
  }
}
