package playstudios.models.external;

public class StatusResponse {
    private String StatusDescription;

    public StatusResponse(String statusDescription) {
        StatusDescription = statusDescription;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }
}
