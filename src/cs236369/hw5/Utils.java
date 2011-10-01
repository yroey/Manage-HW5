package cs236369.hw5;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Utils {
  public static void setSessionMessage(HttpSession session, String msg) {
    session.setAttribute("msg", msg);
  }

  public static String getSessionMessage(HttpSession session) {
    Object msg = (String)session.getAttribute("msg");
    session.removeAttribute("msg");
    return msg == null ? "" : (String)msg;
  }

  public static void setCookie(HttpServletResponse response, String name, String value) {
	  Cookie cookie = new Cookie(name, value);
	  response.addCookie(cookie);
  }
  public static String getCookie(HttpServletRequest request, String name) {
	 Cookie[] cookies = request.getCookies();
	 for (Cookie cookie : cookies) {
		 if (cookie.getName().equals(name)) {
			 return cookie.getValue();
		 }
	 }
	 return "";
  }
}
