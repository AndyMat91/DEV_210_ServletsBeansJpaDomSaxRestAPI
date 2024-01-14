package com.example.dev_200_1_network_client_data_storage_system_jpa.security;

import jakarta.ejb.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class AuthorizationBean {
    private Map<String, String> loginSession = new HashMap<>();

    public void addLoginSession(String login, String sessionId) {
        loginSession.put(login, sessionId);
    }

    public String getSessionId(String login) {
        return loginSession.get(login);
    }

    public void removeLogin(String login) {
        loginSession.remove(login);
    }
}
