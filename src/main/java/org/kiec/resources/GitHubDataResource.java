package org.kiec.resources;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.kiec.DTO.GitHubRepositoryEnrichedWithBranchesDTO;
import org.kiec.clients.GitHubClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/github")
@ApplicationScoped
public class GitHubDataResource {

    @Inject
    @RestClient
    GitHubClient gitHubClient;

    @GET
    @Path("/repos/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<GitHubRepositoryEnrichedWithBranchesDTO> getUserRepos(@PathParam("user") String user) {

        return gitHubClient.getRepositoriesForUser(user)
                .onItem().transformToMulti(list -> Multi.createFrom().iterable(list))
                .filter(repository -> !repository.isFork())
                .onItem().transformToUniAndMerge(repository ->
                        gitHubClient
                                .getBranchesForUserAndRepo(repository.ownerLogin(), repository.repositoryName())
                                .onItem()
                                .transform((branches -> new GitHubRepositoryEnrichedWithBranchesDTO(repository.repositoryName(), repository.ownerLogin(), branches))));
    }
}
