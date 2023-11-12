package pl.jdacewicz.socialmediaserver.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.jdacewicz.socialmediaserver.tokengenerator.TokenGeneratorFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserNotFoundException;

import java.io.IOException;

@RequiredArgsConstructor
class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final TokenGeneratorFacade tokenGeneratorFacade;
    private final JwtClaimsExtractor jwtClaimsExtractor;
    private final JwtTokenValidator jwtTokenValidator;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            var authenticationHeader = request.getHeader("Authorization");
            checkAuthentication(authenticationHeader, request);
            filterChain.doFilter(request, response);
        } catch (UserNotFoundException exception) {
            var status = HttpStatus.NOT_FOUND;
            response.setStatus(status.value());
            response.getWriter()
                    .write("Provided username or password is invalid");
        }
    }

    private void checkAuthentication(String authenticationHeader, HttpServletRequest request) {
        if (isAuthenticationHeaderValid(authenticationHeader) && !isUserAuthenticated()) {
            prepareUsername(authenticationHeader, request);
        }
    }

    private boolean isAuthenticationHeaderValid(String authenticationHeader) {
        return (authenticationHeader != null && authenticationHeader.startsWith("Bearer "));
    }

    private boolean isUserAuthenticated() {
        return (SecurityContextHolder.getContext().getAuthentication() != null);
    }

    private void prepareUsername(String authenticationHeader, HttpServletRequest request) {
        var jwtToken = authenticationHeader.substring(7);
        var userEmail = jwtClaimsExtractor.extractUsername(jwtToken);
        if (userEmail != null) {
            authenticate(userEmail, jwtToken, request);
        }
    }

    private void authenticate(String userEmail, String jwtToken, HttpServletRequest request) {
        var userDetails = userDetailsService.loadUserByUsername(userEmail);
        if (isTokenValidAndActive(jwtToken, userDetails)) {
            setSecurityContextAuthentication(userDetails, request);
        }
    }

    private boolean isTokenValidAndActive(String jwtToken, UserDetails userDetails) {
        var isTokenActive = tokenGeneratorFacade
                .getTokenByCode(jwtToken)
                .active();
        return ((jwtTokenValidator.isValid(jwtToken, userDetails)) && isTokenActive);
    }

    private void setSecurityContextAuthentication(UserDetails userDetails, HttpServletRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder
                .getContext()
                .setAuthentication(authenticationToken);
    }
}
