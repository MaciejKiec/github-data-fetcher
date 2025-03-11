package org.kiec.DTO;

import java.util.List;

public record GitHubRepositoryEnrichedWithBranchesDTO(String repositoryName, String ownerLogin, List<BranchDTO> branches) {
}
