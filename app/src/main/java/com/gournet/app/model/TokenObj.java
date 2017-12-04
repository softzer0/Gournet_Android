package com.gournet.app.model;

import java.io.Serializable;

public class TokenObj implements Serializable
{

    private String token;
      private long exp;

    public String getToken() {
        return token;
    }

    public long getExp() {
        return exp;
    }
}
