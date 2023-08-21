package hgcha.CodeAgora.oauth;

import java.util.Map;

public interface OAuth2UserInfo {
    String getUsername();

    String getEmail();

    Map<String, Object> getAttributes();
}
