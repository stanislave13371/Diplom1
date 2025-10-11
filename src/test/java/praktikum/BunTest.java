package praktikum;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.closeTo;

public class BunTest {
    @Test
    public void bunStoresNameAndPrice() {
        Bun bun = new Bun("Test Bun", 100f);
        assertThat(bun.getName(), is("Test Bun"));
        assertThat((double) bun.getPrice(), closeTo(100.0, 1e-6));
    }
}
