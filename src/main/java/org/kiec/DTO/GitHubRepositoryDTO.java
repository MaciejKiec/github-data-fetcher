package org.kiec.DTO;


import jakarta.json.JsonObject;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public record GitHubRepositoryDTO(String repositoryName, String ownerLogin, boolean isFork) {
    @JsonbCreator
    public GitHubRepositoryDTO(
            @JsonbProperty("name") String repositoryName,
            @JsonbProperty("owner") JsonObject owner,
            @JsonbProperty("fork") boolean isFork) {
        this(repositoryName, owner.getString("login", null), isFork);
    }
}
