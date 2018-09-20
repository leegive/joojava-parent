package com.joojava.web.filter;


import com.joojava.config.FrameworkProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author leegive
 *
 * 该过滤器用于生产，将HTTP高速缓存报头放置时间长（4年
 */
public class CachingHttpHeadersFilter implements Filter {

    public static final int DEFAULT_DAYS_TO_LIVE = 1461;
    public static final long DEFAULT_SECONDS_TO_LIVE = TimeUnit.DAYS.toMillis(DEFAULT_DAYS_TO_LIVE);

    public final static long LAST_MODIFIED = System.currentTimeMillis();

    private long cacheTimeToLive = DEFAULT_SECONDS_TO_LIVE;

    private FrameworkProperties frameworkProperties;

    public CachingHttpHeadersFilter(FrameworkProperties frameworkProperties) {
        this.frameworkProperties = frameworkProperties;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        cacheTimeToLive = TimeUnit.DAYS.toMillis(frameworkProperties.getHttp().getCache().getTimeToLiveInDays());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.setHeader("Cache-Control", "max-age=" + cacheTimeToLive + ", public");
        httpServletResponse.setHeader("Pragma", "cache");

        httpServletResponse.setDateHeader("Expires", cacheTimeToLive + System.currentTimeMillis());

        httpServletResponse.setDateHeader("Last-Modified", LAST_MODIFIED);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
