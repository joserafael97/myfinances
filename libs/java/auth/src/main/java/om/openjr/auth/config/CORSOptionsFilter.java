package om.openjr.auth.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import om.openjr.auth.util.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filter to manage OPTIONS from CORS.
 *
 * @author Virtus
 */
public class CORSOptionsFilter extends GenericFilterBean {

    /**
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     *      javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response = SecurityUtils.fillAccessControlHeader(response);

        /*
         * If it is a OPTIONS request (usually used for check, return OK because there is no header
         */
        if (RequestMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(request, response);
    }
}
