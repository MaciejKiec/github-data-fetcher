package org.kiec.DTO;


import jakarta.json.JsonObject;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public record BranchDTO(String branchName, String commitSHA) {
@JsonbCreator
    public BranchDTO(
        @JsonbProperty("name") String branchName,
        @JsonbProperty("commit") JsonObject commit
){
    this(branchName, commit.getString("sha", null));
}
}
