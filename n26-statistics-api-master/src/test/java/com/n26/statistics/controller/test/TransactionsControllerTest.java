/**
 * Copyright 2018 N26 Bank coding challenge. All rights reserved.
 */

package com.n26.statistics.controller.test;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Chetankumar Hiremath
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionsControllerTest extends RestCommonControllerHelper {
    // ***************************************************************************************************************
    // ******************************************** Public Fields ****************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ****************************************** Non Public Fields **************************************************
    // ***************************************************************************************************************

    private static final String PAYLOAD_WITH_INVALID_TIMESTAMP = "{\"amount\": \"22.9\", \"timestamp\": \"1515087936000\"}";

    // ***************************************************************************************************************
    // *********************************************** Constructors **************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ******************************************** Public Methods ***************************************************
    // ***************************************************************************************************************

    @Test
    public void testRegisterNewTrasactionForSuccessResponse() throws Exception {
        final String paylodData = createTestTransactionPaylod();
        registerTransaction(paylodData).andExpect(status().isCreated());
    }

    @Test
    public void testInvalidTimeStampTrasaction() throws Exception {
        registerTransaction(PAYLOAD_WITH_INVALID_TIMESTAMP).andExpect(status().isNoContent());
    }

    // ***************************************************************************************************************
    // ****************************************** Non Public Methods *************************************************
    // ***************************************************************************************************************

    private ResultActions registerTransaction(String testPayload) throws Exception {
        return postMethod("/transactions", testPayload);
    }

    private String createTestTransactionPaylod() throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", "17.7");
        jsonObject.put("timestamp", System.currentTimeMillis() - 10000);
        return jsonObject.toString();
    }
}
