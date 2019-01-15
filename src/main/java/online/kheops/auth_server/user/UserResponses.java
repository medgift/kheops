package online.kheops.auth_server.user;

import online.kheops.auth_server.entity.User;

import javax.xml.bind.annotation.XmlElement;


public class UserResponses {

    private UserResponses() {
        throw new IllegalStateException("Utility class");
    }

    public static class UserResponse {
        @XmlElement(name = "email")
        public String email;
        @XmlElement(name = "sub")
        public String sub;

    }



    public static UserResponse userToUserResponse(User user) {
        final UserResponse userResponse = new UserResponse();

        userResponse.email = user.getEmail();
        userResponse.sub = user.getKeycloakId();

        return userResponse;
    }
    public static UserResponse newUserResponse(String email, String id) {
        final UserResponse userResponse = new UserResponse();

        userResponse.email = email;
        userResponse.sub = id;

        return userResponse;
    }

}
