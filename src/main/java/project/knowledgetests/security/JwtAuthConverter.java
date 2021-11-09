package project.knowledgetests.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import project.knowledgetests.config.EnvironmentConfiguration;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    private static Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {

        final String APP_ID = EnvironmentConfiguration.get("keycloak.app_id", "");
        final String KEYCLOAK_RESOURCES_CLAIM = EnvironmentConfiguration.get("keycloak.resource_access_claim", "");
        final String ROLES = EnvironmentConfiguration.get("keycloak.resource_access_claim_roles", "");

        Map<String, Object> resourceAccess = jwt.getClaim(KEYCLOAK_RESOURCES_CLAIM);
        Map<String, Object> resource = (Map<String, Object>) resourceAccess.get(APP_ID);
        Collection<String> resourceRoles = (Collection<String>) resource.get(ROLES);

        if (resourceRoles == null) return Collections.emptySet();

        return resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {

        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(source).stream(),
                extractResourceRoles(source).stream()
        ).collect(Collectors.toList());

        return new JwtAuthenticationToken(source, authorities);
    }
}
