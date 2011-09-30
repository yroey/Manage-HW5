package cs236369.hw5;

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
}
