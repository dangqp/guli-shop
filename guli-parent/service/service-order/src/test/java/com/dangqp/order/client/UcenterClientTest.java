package com.dangqp.order.client;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Title:com.dangqp.order.client
 * Copyright: Copyright (c) 2021
 *
 * @author dangqp
 * @version 1.0
 * @created 2021/01/20  17:49
 */
public class UcenterClientTest {

    @Autowired
    UcenterClient ucenterClient;
    @Test
    public void getUserInfoOrder() {
        ucenterClient.getUserInfoOrder( "" );
    }
}