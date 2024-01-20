## Flow of Default Basic Authentication

    When User set username(principal) and password(secret) with request it will be converted into basicAuthentication token 
    like Basic c3VwZXIuaWwuY29tOjEyMzQ1

````
Step 1. doFilterInternal method of BasicAuthenticationFilter.class is being called

public class BasicAuthenticationFilter extends OncePerRequestFilter {
    ....
    ....
    
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    }  
    ....
    ....
        
}
````

````
Step 2. From request create object of UsernamePasswordAuthenticationToken.class which is child 
class of Authentication

protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    boolean debug = this.logger.isDebugEnabled();

    try {
        UsernamePasswordAuthenticationToken authRequest = this.authenticationConverter.convert(request);
        if (authRequest == null) {
            chain.doFilter(request, response);
            return;
        }
        
    .....
    .....
}

public class UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private Object credentials;
    .....
    .....
}  

````

````
Step 3. authenticate method of ProviderManager.class is called which is implementation class 
of interface AuthenticationManager

protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    .....
    .....
    
    Authentication authResult = this.authenticationManager.authenticate(authRequest);
    
    .....
    .....
}  
    

public class ProviderManager implements AuthenticationManager, MessageSourceAware, InitializingBean {

    .....
    .....
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            .....
            .....
        }
    .....
    .....
}
````   

````
Step 4. authenticate will call authenticate method of valid AuthenticationProvider

public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    .....
    .....
    while(var8.hasNext()) {
        AuthenticationProvider provider = (AuthenticationProvider)var8.next();
        if (provider.supports(toTest)) {
            if (debug) {
                logger.debug("Authentication attempt using " + provider.getClass().getName());
            }

            try {
                result = provider.authenticate(authentication);
                if (result != null) {
                    this.copyDetails(authentication, result);
                    break;
                }
            } catch (InternalAuthenticationServiceException | AccountStatusException var13) {
                this.prepareException(var13, authentication);
                throw var13;
            } catch (AuthenticationException var14) {
                lastException = var14;
            }
        }
    }
    .....
    .....
}

````

````
Let's suppose valid AuthenticationProvider is AbstractUserDetailsAuthenticationProvider.class
public abstract class AbstractUserDetailsAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {
    .....
    .....
    
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        .....
        .....
    }
    .....
    .....
}
````

````
Step 5. Fetch UserDetail object from authentication object converted in doFilterInternal method of BasicAuthenticationFilter.class
public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    .....
    .....
    UserDetails user = this.retrieveUser(username, (UsernamePasswordAuthenticationToken)authentication);
    .....
    .....
}

protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    .....
    .....
    UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
    .....
    .....
}
````

````
Step6. Validate credential by calling additionalAuthenticationChecks method of AuthenticationProvider which implemention 
is done in DaoAuthenticationProvider.class

public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    .....
    .....
    this.additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken)authentication);
    .....
    .....
}

DaoAuthenticationProvider{
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            String presentedPassword = authentication.getCredentials().toString();
            if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
                this.logger.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }
}

````

````
Step 7. Once everything went perfect new Authentication object is being created using userDetails and returned to filter

protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
    UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal, authentication.getCredentials(), this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
    result.setDetails(authentication.getDetails());
    return result;
}
````

````
Step 8. SecurityContextHolder set in filter

SecurityContextHolder.getContext().setAuthentication(authResult);
this.rememberMeServices.loginSuccess(request, response, authResult);
this.onSuccessfulAuthentication(request, response, authResult);
````