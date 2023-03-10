package com.bevelop.devbevelop.domain.project.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Website {
    private String host;
    private String domain;

    public Website (String host, String domain) {
        this.host = host;
        this.domain = domain;
    }

    public String getWebsite() {
        return host+"@"+domain;
    }
}
