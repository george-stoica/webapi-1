package playstudios.controllers;

import playstudios.models.external.StatusResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/api")
public class StatusController {
    @GetMapping("status")
    public CompletableFuture<StatusResponse> getStatus() {
        return CompletableFuture.completedFuture(new StatusResponse("Ok"));
    }
}
