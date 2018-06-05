package com.devakt.controller;

import com.devakt.modal.RoomView;
import java.util.ArrayList;

import com.devakt.service.BookingService;
import com.devakt.service.InitDatabaseService;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InitDatabaseService initDatabaseService;

    @MockBean
    private BookingService bookingService;

    @Test
    public void testInit() throws Exception {
        doNothing().when(initDatabaseService).initData();
        mockMvc.perform(get("/init")).andExpect(status().isNoContent());
        verify(initDatabaseService).initData();
    }

    @Test
    public void testGetRooms() throws Exception {
        when(bookingService.getRooms()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/rooms")).andExpect(status().isOk());
        verify(bookingService).getRooms();
    }

    @Test
    public void testGetMatchByGroup() throws Exception {
        when(bookingService.getRoom(15)).thenReturn(new RoomView());
        mockMvc.perform(get("/rooms/15")).andExpect(status().isOk());
        verify(bookingService).getRoom(eq(15));
    }

}
