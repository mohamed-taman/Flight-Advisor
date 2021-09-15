package org.siriusxi.htec.fa.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Objects;

public record UserView(@JsonProperty String id,
                       @JsonProperty String username,
                       @JsonProperty String firstName,
                       @JsonProperty String lastName,
                       @JsonProperty String[] authorities) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserView userView = (UserView) o;
        return Objects.equals(id, userView.id) &&
                   Objects.equals(username, userView.username) &&
                   Objects.equals(firstName, userView.firstName) &&
                   Objects.equals(lastName, userView.lastName) &&
                   Arrays.equals(authorities, userView.authorities);
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, firstName, lastName);
        result = 31 * result + Arrays.hashCode(authorities);
        return result;
    }
    
    @Override
    public String toString() {
        return "UserView{ id= %s, username=  %s, firstName= %s, lastName= %s , authorities= %s}"
            .formatted(id, username, firstName, lastName, Arrays.toString(authorities));
    }
}
