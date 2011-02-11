package net.homeip.dvdstore.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.security.ui.logout.LogoutHandler;
import org.springframework.security.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class WSLogoutFilter extends SpringSecurityFilter {

    //~ Instance fields ================================================================================================
    private String filterProcessesUrl = "/j_spring_security_logout";
    private String logoutSuccessUrl;
    private LogoutHandler[] handlers;
    private boolean useRelativeContext;

    //~ Constructors ===================================================================================================
    public WSLogoutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
        Assert.notEmpty(handlers, "LogoutHandlers are required");
        this.logoutSuccessUrl = logoutSuccessUrl;
        Assert.isTrue(UrlUtils.isValidRedirectUrl(logoutSuccessUrl), logoutSuccessUrl + " isn't a valid redirect URL");
        this.handlers = handlers;
    }

    //~ Methods ========================================================================================================
    @Override
    public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {

        if (requiresLogout(request, response)) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (logger.isDebugEnabled()) {
                logger.debug("Logging out user '" + auth + "' and redirecting to logout page");
            }

            for (int i = 0; i < handlers.length; i++) {
                handlers[i].logout(request, response, auth);
            }

            String targetUrl = determineTargetUrl(request, response);
            try {
                sendRedirect(request, response, targetUrl);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return;
        }
        try {
            chain.doFilter(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Allow subclasses to modify when a logout should take place.
     *
     * @param request the request
     * @param response the response
     *
     * @return <code>true</code> if logout should occur, <code>false</code> otherwise
     */
    protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            // strip everything from the first semi-colon
            uri = uri.substring(0, pathParamIndex);
        }

        int queryParamIndex = uri.indexOf('?');

        if (queryParamIndex > 0) {
            // strip everything from the first question mark
            uri = uri.substring(0, queryParamIndex);
        }

        if ("".equals(request.getContextPath())) {
            return uri.endsWith(filterProcessesUrl);
        }

        return uri.endsWith(request.getContextPath() + filterProcessesUrl);
    }

    /**
     * Returns the target URL to redirect to after logout.
     * <p>
     * By default it will check for a <tt>logoutSuccessUrl</tt> parameter in
     * the request and use this. If that isn't present it will use the configured <tt>logoutSuccessUrl</tt>. If this
     * hasn't been set it will check the Referer header and use the URL from there.
     *
     */
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        String targetUrl = request.getParameter("logoutSuccessUrl");

        if (!StringUtils.hasLength(targetUrl)) {
            targetUrl = getLogoutSuccessUrl();
        }

        if (!StringUtils.hasLength(targetUrl)) {
            targetUrl = request.getHeader("Referer");
        }

        if (!StringUtils.hasLength(targetUrl)) {
            targetUrl = "/";
        }

        return targetUrl;
    }

    /**
     * Allow subclasses to modify the redirection message.
     *
     * @param request  the request
     * @param response the response
     * @param url      the URL to redirect to
     *
     * @throws IOException in the event of any failure
     */
    protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
            throws IOException {
        try {
            request.getRequestDispatcher(getLogoutSuccessUrl()).forward(request, response);
        } catch (ServletException ex) {
            ex.printStackTrace();
        }
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        Assert.hasText(filterProcessesUrl, "FilterProcessesUrl required");
        Assert.isTrue(UrlUtils.isValidRedirectUrl(filterProcessesUrl), filterProcessesUrl + " isn't a valid redirect URL");
        this.filterProcessesUrl = filterProcessesUrl;
    }

    protected String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }

    protected String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setUseRelativeContext(boolean useRelativeContext) {
        this.useRelativeContext = useRelativeContext;
    }

    public int getOrder() {
        return FilterChainOrder.LOGOUT_FILTER;
    }
}
