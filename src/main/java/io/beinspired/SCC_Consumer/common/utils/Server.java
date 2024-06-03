package io.beinspired.SCC_Consumer.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Server {
    LOCAL_HOST("http://localhost:");
    
    private String address;
    
    public static Server serverOfAddress(String address) {
        for (Server server : values()) {
            if (server.address.equals(address)) {
                return server;
            }
        }
        return null;
    }
}
