package com.WalletManagement.WalletApi.ControllerTest;

import com.WalletManagement.WalletApi.controller.WalletController;
import com.WalletManagement.WalletApi.exceptions.NotFoundException;
import com.WalletManagement.WalletApi.model.Wallet;
import com.WalletManagement.WalletApi.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WalletService walletService;

    Wallet wallet1 = new Wallet("00", 0L);
    Wallet wallet2 = new Wallet("000", 0L);

    @Test
    @DisplayName("Get All Wallets success")
    public void getAllWalletsTest() throws Exception {
        List<Wallet> walletList = new ArrayList<>(Arrays.asList(wallet1, wallet2));

        Mockito.when(walletService.getAllWallets()).thenReturn(walletList);

        mockMvc.perform(MockMvcRequestBuilders.get("/wallet"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[1].mobileNumber", Matchers.is(wallet2.getMobileNumber())));
    }

    @Test
    @DisplayName("walletsNotFoundException in Get All Wallets")
    public void walletsNotFoundExceptionTest() throws Exception {
        Mockito.when(walletService.getAllWallets()).thenThrow(new NotFoundException("Wallets List Empty"));

        mockMvc.perform(MockMvcRequestBuilders.get("/wallet"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("Wallets List Empty"));

    }

    @Test
    @DisplayName("Get Wallet by MobileNumber success")
    public void getWalletByIdTest() throws Exception {
        Mockito.when(walletService.getWalletById(wallet1.getMobileNumber())).thenReturn(wallet1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/wallet/" + wallet1.getMobileNumber())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.balance", Matchers.is(0)));
    }

    @Test
    @DisplayName("walletNotFoundException in Get Wallet by MobileNumber")
    public void walletNotFoundExceptionOfGetWalletByIdTest() throws Exception {
        Mockito.when(walletService.getWalletById(wallet1.getMobileNumber())).thenThrow(new NotFoundException("Wallet doesn't Exists"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/wallet/" + wallet1.getMobileNumber())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("Wallet doesn't Exists"));
    }

    @Test
    @DisplayName("Delete Wallet success")
    public void deleteWalletTest() throws Exception {
        Mockito.doNothing().when(walletService).deleteWalletById(wallet1.getMobileNumber());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/wallet/" + wallet1.getMobileNumber())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string("Wallet deleted"));
    }

    @Test
    @DisplayName("walletNotFoundException in Delete Wallet ")
    public void walletNotFoundExceptionOfDeleteWalletTest() throws Exception {
        Mockito.doThrow(new NotFoundException("Wallet doesn't Exists")).when(walletService).deleteWalletById(wallet1.getMobileNumber());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/wallet/" + wallet1.getMobileNumber())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("Wallet doesn't Exists"));
    }

    @Test
    @DisplayName("Create Wallet success")
    public void createWalletTest() throws Exception {
        Mockito.when(walletService.createWallet(wallet1)).thenReturn(wallet1);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(wallet1)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Add Money success")
    public void addMoneyTest() throws Exception {
        Mockito.when(walletService.addMoney(50L, wallet1.getMobileNumber())).thenReturn(new Wallet("00", 50L));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/wallet/50/" + wallet1.getMobileNumber())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}

