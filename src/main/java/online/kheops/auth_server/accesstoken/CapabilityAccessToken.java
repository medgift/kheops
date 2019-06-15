package online.kheops.auth_server.accesstoken;

import online.kheops.auth_server.EntityManagerListener;
import online.kheops.auth_server.capability.*;
import online.kheops.auth_server.entity.Capability;
import online.kheops.auth_server.entity.User;
import online.kheops.auth_server.principal.CapabilityPrincipal;
import online.kheops.auth_server.principal.KheopsPrincipalInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContext;
import java.util.Objects;
import java.util.Optional;

final class CapabilityAccessToken implements AccessToken {
    private final String sub;
    private Capability capability;

    static final class CapabilityAccessTokenBuilder implements AccessTokenBuilder {
        @Override
        public AccessToken build(String capabilityToken) throws AccessTokenVerificationException {
            if (!CapabilityToken.isValidFormat(capabilityToken)) {
                throw new AccessTokenVerificationException("Bad capability token format");
            }

            final EntityManager em = EntityManagerListener.createEntityManager();
            final EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();

                final Capability capability = Capabilities.getCapability(capabilityToken, em);

                capability.isValid();

                final String sub = capability.getUser().getKeycloakId();

                capability.setLastUsed();

                tx.commit();

                return new CapabilityAccessToken(capability, sub);
            } catch (CapabilityNotFoundException e) {
                throw new AccessTokenVerificationException("Unknown capability token");
            } catch (CapabilityNotValidException e) {
                throw new AccessTokenVerificationException(e.getMessage());
            } finally {
                if (tx.isActive()) {
                    tx.rollback();
                }
                em.close();
            }
        }
    }

    private CapabilityAccessToken(Capability capability, String sub) {
        this.capability = Objects.requireNonNull(capability);
        this.sub = Objects.requireNonNull(sub);
    }

    @Override
    public String getSub() {
        return sub;
    }

    @Override
    public Optional<String> getScope() {
        String scope;
        if (capability.getScopeType().equalsIgnoreCase(ScopeType.ALBUM.name())) {
            scope = (capability.hasWritePermission()?"write ":"") +
                    (capability.hasReadPermission()?"read ":"") +
                    (capability.hasDownloadButtonPermission()?"downloadbutton ":"") +
                    (capability.hasAppropriatePermission()?"appropriate ":"");
            if (scope.length() > 0) {
                scope = scope.substring(0, scope.length() - 1);
            }
        } else {
            scope = "read write";
        }

        return Optional.of(scope);
    }

    @Override
    public TokenType getTokenType() { return TokenType.CAPABILITY_TOKEN; }

    @Override
    public KheopsPrincipalInterface newPrincipal(ServletContext servletContext, User user) {
        return new CapabilityPrincipal(capability, user);
    }

}
