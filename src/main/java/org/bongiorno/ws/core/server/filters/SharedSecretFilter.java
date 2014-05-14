package org.bongiorno.ws.core.server.filters;

import org.bongiorno.misc.utils.SecurityUtils;
import org.bongiorno.ws.core.client.headers.DefaultAuthHeader;
import org.bongiorno.ws.core.exceptions.UnauthorizedException;
import org.bongiorno.ws.core.server.WebserviceFilter;
import org.apache.commons.lang.StringUtils;
import org.bongiorno.misc.utils.SecurityUtils;
import org.bongiorno.ws.core.exceptions.UnauthorizedException;
import org.bongiorno.ws.core.server.WebserviceFilter;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements authentication with a static shared secret.
 */
public class SharedSecretFilter extends WebserviceFilter {

    private Logger logger = LoggerFactory.getLogger(SharedSecretFilter.class);

    private String tokenSeed;
    private String strategy;

    public SharedSecretFilter(String tokenSeed) {
        this.tokenSeed = tokenSeed;
        this.strategy = DefaultAuthHeader.DEFAULT_STRATEGY;
    }

    public SharedSecretFilter(String tokenSeed, String strategy) {
        this.tokenSeed = tokenSeed;
        this.strategy = strategy;
    }

    @Override
    protected void doFilterWebserviceInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String client = request.getHeader(DefaultAuthHeader.USER_NAME_HEADER);
        String clientToken = request.getHeader(DefaultAuthHeader.TOKEN_HEADER);
        String timeHeader = request.getHeader(DefaultAuthHeader.TIME_HEADER);

        if(StringUtils.isEmpty(timeHeader))
            throw new UnauthorizedException("No time header provided");

        DateTime timeStamp = new DateTime(new Long(timeHeader));
        if(timeStamp.isBefore(DateTime.now().minusMinutes(1)))
            throw new UnauthorizedException("Token too old");

        if(StringUtils.isEmpty(client)) {
            logger.warn(String.format("No client name supplied"));
            throw new UnauthorizedException("Forbidden client");
        }

        String expectedToken = SecurityUtils.hashTo(tokenSeed + timeHeader, strategy);

        if(!expectedToken.equals(clientToken) ) {
            logger.warn(String.format("client '%s' failed token check",client));
            throw new UnauthorizedException("Bad token");
        }

        filterChain.doFilter(request, response);

    }


}
