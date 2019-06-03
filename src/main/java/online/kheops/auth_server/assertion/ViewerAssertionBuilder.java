package online.kheops.auth_server.assertion;

import online.kheops.auth_server.util.JweAesKey;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.lang.JoseException;

import javax.servlet.ServletContext;

final class ViewerAssertionBuilder implements AssertionBuilder {
    private final ServletContext servletContext;

    ViewerAssertionBuilder(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public Assertion build(String assertionToken) throws BadAssertionException {

        try {
            final JsonWebEncryption jwe = new JsonWebEncryption();
            jwe.setAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST,
                    KeyManagementAlgorithmIdentifiers.A128KW));
            jwe.setContentEncryptionAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST,
                    ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256));
            jwe.setKey(JweAesKey.getInstance().getKey());

            jwe.setCompactSerialization(assertionToken);

            return ViewerAssertion.getBuilder(servletContext).build(jwe.getPayload());
        } catch (JoseException e) {
            throw new BadAssertionException("Unable to decode JWT", e);
        }
    }
}
