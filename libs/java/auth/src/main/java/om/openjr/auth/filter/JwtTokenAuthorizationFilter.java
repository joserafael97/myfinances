package om.openjr.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import om.openjr.auth.token.converter.TokenConverter;
import om.openjr.auth.util.SecurityContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.SignedJWT;

import om.openjr.auth.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {

    protected final JwtConfig jwtConfiguration;
    protected final TokenConverter tokenConverter;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader(this.jwtConfiguration.getHeader().getName());

        if (header == null || !header.startsWith(this.jwtConfiguration.getHeader().getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.replace(this.jwtConfiguration.getHeader().getPrefix(), "").trim();

        SecurityContextUtil.setSecurityContext(this.parseToken(token));

        chain.doFilter(request, response);
    }

    @SneakyThrows
    private SignedJWT parseToken(String token) {
        final SignedJWT signedToken = SignedJWT.parse(token);
        //this.tokenConverter.validateTokenSignature(signedToken);
        return signedToken;
    }

}
