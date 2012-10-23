package com.geertvanderploeg.facebook;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Login servlet for the Facebook Graph API, using OAuth 2.0
 */
@WebServlet(name = "oauthclient", urlPatterns = {"/", "/callback"})
public class OAuthClientServlet extends HttpServlet {

  private static final String AUTHORIZATION_URL = "https://www.facebook.com/dialog/oauth?" +
    "client_id=%s" +
    "&redirect_uri=%s" +
    "&state=%s" +
    "&scope=user_birthday";

  private static final String ACCESS_TOKEN_URL = "https://graph.facebook.com/oauth/access_token" +
    "?client_id=%s" +
    "&redirect_uri=%s" +
    "&client_secret=%s" +
    "&code=%s";

  private static final String CLIENT_ID = "165680210237078";
  private static final String CLIENT_SECRET = "831f6fc9dee3c2bc2fddd6f841bb651b";
  private static final String REDIRECT_URI = "http://localhost:8080/facebook-oauth-callback";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (req.getRequestURI().endsWith("callback")) {
      callback(req, resp);
    } else {
      initial(req, resp);
    }

  }

  private void initial(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    String state = UUID.randomUUID().toString();
    req.getSession().setAttribute("nonce", state);

    String authorizationUrl = String.format(AUTHORIZATION_URL, CLIENT_ID, REDIRECT_URI, state);
    resp.sendRedirect(authorizationUrl);
  }

  private void callback(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    if (!req.getParameter("state").equals(req.getSession().getAttribute("nonce"))) {
      throw new IllegalStateException("CSRF!");
    }
    String code = req.getParameter("code");
    String url = String.format(ACCESS_TOKEN_URL, CLIENT_ID, REDIRECT_URI, CLIENT_SECRET, code);

    String response = Util.readStringFromUrl(url);
    // Response looks like: "access_token=12398172398&expires=123123"
    String accessToken = response.split("[&=]")[1];
    req.getSession().setAttribute("accessToken", accessToken);

    resp.sendRedirect("/list");
  }
}
