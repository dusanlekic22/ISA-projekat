package isaproject.util;

import javax.servlet.http.HttpServletRequest;

public final class ProjectUtil {

	public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    } 
}
