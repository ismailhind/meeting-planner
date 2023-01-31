package com.canalplus.meetingplanner.domain;

import com.canalplus.meetingplanner.MeetingPlannerApplicationTests;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MeetingPlannerApplicationTests.class)
public class MeetingRoomControllerTest {

        MockMvc mockMvc;
        @Mock
        private MeetingRoomService meetingRoomService;
        @InjectMocks
        private MeetingRoomController roomController;
        private final String jsonBody = "{\"meetingType\": \"RS\", " +
                "\"dateBegin\": \"2023-01-30T13:00:00\"," +
                "\"dateEnd\": \"2023-01-30T14:00:00\"," +
                "\"participantsCount\": \"8\"," +
                "}";

        @Before
        public void preTest() {
            MockitoAnnotations.openMocks(this);
            mockMvc = MockMvcBuilders
                    .standaloneSetup(roomController)
                    .build();
        }

        //TODO to complete
        //@Test
        public void requestBodyTest() {
            try {
                mockMvc
                        .perform(post("http://localhost:8080/v1/reservations")
                                .content(jsonBody)
                                .contentType("application/json"))
                        .andDo(print())
                        .andExpect(status().isOk());
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }

}