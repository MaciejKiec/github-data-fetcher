package org.kiec.clients;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.kiec.DTO.BranchDTO;
import org.kiec.DTO.GitHubRepositoryDTO;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterProvider(UserNotFoundExceptionProvider.class)
@ApplicationScoped
@RegisterRestClient
@ClientHeaderParam(name = "X-GitHub-Api-Version", value = "2022-11-28")
public interface GitHubClient {

    @GET
    @Path("/users/{user}/repos")
    Uni<List<GitHubRepositoryDTO>> getRepositoriesForUser(@PathParam("user") String user);

    @GET
    @Path("/repos/{user}/{repo}/branches")
    Uni<List<BranchDTO>> getBranchesForUserAndRepo(@PathParam("user") String user, @PathParam("repo") String repo);
}
