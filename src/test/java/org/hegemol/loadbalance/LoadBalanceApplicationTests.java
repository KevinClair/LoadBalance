package org.hegemol.loadbalance;

import java.util.ArrayList;
import java.util.List;

import org.hegemol.loadbalance.model.Model;
import org.hegemol.loadbalance.service.LoadBalance;
import org.hegemol.loadbalance.service.impl.RandomLoadBalance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class LoadBalanceApplicationTests {


    @Test
    public void random() {
        List<Model> models = new ArrayList<>();
        models.add(new Model("127.0.0.1"));
        models.add(new Model("127.0.0.2"));
        models.add(new Model("127.0.0.3"));
        for (int i = 0; i < 100; i++) {
            LoadBalance loadBalance = new RandomLoadBalance();
            System.out.println(loadBalance.load(models));
        }
    }

}
