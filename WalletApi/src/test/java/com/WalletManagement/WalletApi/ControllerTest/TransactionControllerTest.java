package com.WalletManagement.WalletApi.ControllerTest;

import com.WalletManagement.WalletApi.Utils.enums.TransactionStatus;
import com.WalletManagement.WalletApi.Utils.enums.WalletStatus;
import com.WalletManagement.WalletApi.controller.TransactionController;
import com.WalletManagement.WalletApi.exceptions.NotFoundException;
import com.WalletManagement.WalletApi.model.Transaction;
import com.WalletManagement.WalletApi.model.User;
import com.WalletManagement.WalletApi.repository.UserRepository;
import com.WalletManagement.WalletApi.service.TransactionService;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private UserRepository userRepository;

    String path = "src/test/JsonFiles/Transaction.json";
    String requestBody= new String(Files.readAllBytes(Paths.get(path)));

    Transaction transaction1=new Transaction(0L,"00","000",100L, TransactionStatus.Successful);
    Transaction transaction2=new Transaction(00L,"000","00",100L,TransactionStatus.Successful);

    User user1=new User(0L,"sou06","sou","00","d@gmail.com", WalletStatus.True);

    public TransactionControllerTest() throws IOException {
    }


    @Test
    @DisplayName("Get All Transactions")
    public void getAllTransactionsTest() throws Exception {
        List<Transaction> transactionList=new ArrayList<>(Arrays.asList(transaction1,transaction2));

        Mockito.when(transactionService.getAllTransactions()).thenReturn(transactionList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[1].status",Matchers.is("Successful")));
    }

    @Test
    @DisplayName("transactionsNotFoundException in Get All Transactions")
    public void transactionsNotFoundExceptionTest() throws Exception{
        Mockito.when(transactionService.getAllTransactions()).thenThrow(new NotFoundException("Transactions List is empty"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/all"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("Transactions List is empty"));

    }

    @Test
    @DisplayName("Get All Transactions of User success")
    public void getAllTransactionByUserIdTest() throws Exception {
        List<Transaction> transactionList=new ArrayList<>(Arrays.asList(transaction1,transaction2));
        Mockito.when(transactionService.getTransactionByUserId(user1.getUserId())).thenReturn(transactionList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/" + user1.getUserId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].txnId", Matchers.is(00)));
    }

    @Test
    @DisplayName("userNotFoundException in Get All Transactions of User")
    public void userNotFoundExceptionTest() throws Exception {
        Mockito.when(transactionService.getTransactionByUserId(user1.getUserId())).thenThrow(new NotFoundException("UserId doesn't exists"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/" + user1.getUserId()))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("UserId doesn't exists"));
    }

    @Test
    @DisplayName("Get Transaction by TxnId success")
    public void getTransactionByTxnIdTest() throws Exception {
        Mockito.when(transactionService.getTransactionByTxnId(transaction1.getTxnId())).thenReturn(transaction1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/").param("txnId","0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.txnAmount", Matchers.is(100)));
    }

    @Test
    @DisplayName("transactionNotFoundException in Get Transaction by TxnId")
    public void transactionNotFoundExceptionTest() throws Exception {
        Mockito.when(transactionService.getTransactionByTxnId(transaction1.getTxnId())).thenThrow(new NotFoundException("Transaction doesn't exist"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/").param("txnId","0"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("Transaction doesn't exist"));
    }

    @Test
    @DisplayName("Create Transaction success")
    public void createTransactionTest() throws Exception{
        Mockito.when(transactionService.createTransaction(transaction1)).thenReturn(transaction1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());
    }

}
