package com.xiaoma.api.transaction;

import org.junit.jupiter.api.Test;

/**
 * ClassName: BankTest
 * Description:
 *
 * @Author Mabuyao
 * @Create 2023/10/12 17:36
 * @Version 1.0
 */
public class BankTest {
    @Test
    public void testBank() throws Exception {
        BankService bankService = new BankService();
        bankService.transfer("ergouzi", "lvdandan",
                500);
    }
}
