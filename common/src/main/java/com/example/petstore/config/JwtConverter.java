package com.example.petstore.config;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

/**
 * A converter that extracts Spring Security {@link GrantedAuthority}s from a JWT token,
 * including both default authorities and Keycloak-specific resource roles.
 */
@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private final JwtConverterProperties properties;

    public JwtConverter(JwtConverterProperties properties) {
        this.properties = properties;
    }

    /**
     * Converts a {@link Jwt} into an {@link AbstractAuthenticationToken}, extracting the principal
     * and combining standard authorities with those from the Keycloak "resource_access" claim.
     *
     * @param jwt the JWT token
     * @return an authentication token containing authorities and principal
     */
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> defaultAuthorities = jwtGrantedAuthoritiesConverter.convert(jwt);
        Collection<GrantedAuthority> resourceAuthorities = extractResourceRoles(jwt);

        Set<GrantedAuthority> combinedAuthorities = new HashSet<>();
        if (defaultAuthorities != null) combinedAuthorities.addAll(defaultAuthorities);
        if (resourceAuthorities != null) combinedAuthorities.addAll(resourceAuthorities);

        String principal = getPrincipalClaimName(jwt);
        return new JwtAuthenticationToken(jwt, combinedAuthorities, principal);
    }

    /**
     * Determines which JWT claim to use as the principal name.
     * Defaults to "sub" unless overridden in {@link JwtConverterProperties}.
     *
     * @param jwt the JWT token
     * @return the value of the principal claim
     */
    private String getPrincipalClaimName(Jwt jwt) {
        String claimName = Optional.ofNullable(properties.getPrincipalAttribute())
                                   .orElse(JwtClaimNames.SUB);
        return jwt.getClaim(claimName);
    }

    /**
     * Extracts custom roles from the JWT's "resource_access" section for a configured client ID.
     *
     * @param jwt the JWT token
     * @return a collection of {@link GrantedAuthority}s representing Keycloak roles
     */
    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Object resourceAccessObj = jwt.getClaim("resource_access");
        if (!(resourceAccessObj instanceof Map<?, ?> resourceAccess)) {
            return Collections.emptySet();
        }

        Object clientResourceObj = resourceAccess.get(properties.getResourceId());
        if (!(clientResourceObj instanceof Map<?, ?> clientResource)) {
            return Collections.emptySet();
        }

        Object rolesObj = clientResource.get("roles");
        if (!(rolesObj instanceof Collection<?> roles)) {
            return Collections.emptySet();
        }

        return roles.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
