package org.bongiorno.ws.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author chribong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class LoadContextTest {
    @Test
    public void loadContext() throws Exception {
        // simply load the context and make sure we don't have any unresolvable beans
    }
}
