package org.kiec;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.kiec.DTO.BranchDTO;
import org.kiec.DTO.GitHubRepositoryDTO;
import org.kiec.clients.GitHubClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@QuarkusTest
class GitHubDataResourceTest {

    @InjectMock
    @RestClient
    public GitHubClient gitHubClient;


    private static final GitHubRepositoryDTO forkRepository = new GitHubRepositoryDTO("java-microservices-fork", "thomas", true);
    private static final GitHubRepositoryDTO nonForkRepository = new GitHubRepositoryDTO("java-microservices-not-fork", "thomas", false);
    private static final BranchDTO branch = new BranchDTO("master", "56e05fced214c44a37759efa2dfc25a65d8ae98d");


    @Test
    void getNonForkRepositories() {
        prepareMocks();
        given()
                .pathParam("user", "thomas")
                .when()
                .get("/github/repos/{user}")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].ownerLogin", equalTo(nonForkRepository.ownerLogin()))
                .body("[0].repositoryName", equalTo(nonForkRepository.repositoryName()))
                .body("[0].branches", hasSize(1))
                .body("[0].branches[0].commitSHA", equalTo(branch.commitSHA()))
                .body("[0].branches[0].branchName", equalTo(branch.branchName()));
    }

    private void prepareMocks() {
            when(gitHubClient.getRepositoriesForUser(eq("thomas")))
                    .thenReturn(Uni.createFrom().item(List.of(forkRepository, nonForkRepository)));
            when(gitHubClient.getBranchesForUserAndRepo(eq("thomas"), eq("java-microservices-not-fork")))
                    .thenReturn(Uni.createFrom().item(List.of(branch)));
    }

}