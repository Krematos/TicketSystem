package krematos;

import com.fasterxml.jackson.databind.ObjectMapper;
import krematos.dto.TicketResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TicketSystemIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void completeQueueLifecycle_shouldBehaveCorrectly() throws Exception {
        // --- GIVEN ---
        TicketResponse first = createTicket();
        TicketResponse second = createTicket();
        TicketResponse third = createTicket();

        // --- THEN: creation ---
        assertEquals(0, first.positionInQueue(), "První ticket má být na pozici 0");
        assertEquals(1, second.positionInQueue(), "Druhý ticket má být na pozici 1");
        assertEquals(2, third.positionInQueue(), "Třetí ticket má být na pozici 2");
        assertTrue(first.id() < second.id());
        assertTrue(second.id() < third.id());
        assertNotNull(first.createdAt());
        assertNotNull(second.createdAt());

        // --- WHEN: get current ---
        TicketResponse current = getCurrentTicket();

        // --- THEN: current ---
        assertEquals(first.id(), current.id(), "Aktuální ticket musí být první ve frontě");
        assertEquals(0, current.positionInQueue());

        // --- WHEN: delete current ---
        deleteCurrentTicket();

        // --- THEN: new current ---
        TicketResponse newCurrent = getCurrentTicket();
        assertEquals(second.id(), newCurrent.id(),
                "Po smazání musí být aktuální původně druhý ticket");
        assertEquals(0, newCurrent.positionInQueue(),
                "Nový aktuální ticket musí mít pozici 0");
    }

    // -------------------------------------------------
    // Helper metody
    // -------------------------------------------------

    private TicketResponse createTicket() throws Exception {
        MvcResult result = mockMvc.perform(post("/tickets"))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TicketResponse.class
        );
    }

    private TicketResponse getCurrentTicket() throws Exception {
        MvcResult result = mockMvc.perform(get("/tickets/current"))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TicketResponse.class
        );
    }

    private void deleteCurrentTicket() throws Exception {
        mockMvc.perform(delete("/tickets/current"))
                .andExpect(status().isNoContent());
    }
}
