package com.geertvanderploeg.facebook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * List a few details from the Facebook Graph API
 */
@WebServlet(urlPatterns = "/list")
public class ListServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String accessToken = (String) req.getSession().getAttribute("accessToken");

    resp.setHeader("Content-Type", "text/html");

    String basicInfoUrl = String.format("https://graph.facebook.com/me?access_token=%s", accessToken);
    String basicInfo = Util.readStringFromUrl(basicInfoUrl);
    resp.getWriter().write("<h2>Basic info:</h2><pre>" + basicInfo + "</pre>");

    String friendsListUrl = String.format("https://graph.facebook.com/me/friends?access_token=%s", accessToken);
    String friendsList = Util.readStringFromUrl(friendsListUrl);
    resp.getWriter().write("<h2>List of friends:</h2><pre>" + friendsList + "</pre>");
  }


}
