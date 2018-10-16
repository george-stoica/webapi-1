package playstudios;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import playstudios.controllers.StatusController;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StatusController.class, secure = false)
public class StatusControllerTest {
    @Inject
    private MockMvc mvc;

    @Test
    public void getStatusTest() throws Exception {
        mvc.perform(get("/api/status")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
//        .andExpect(jsonPath("status_description", is("Ok")));
    }
}
