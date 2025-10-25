package praktikum;

import org.junit.Test;
import org.junit.Before;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class BurgerPriceParamTest {

    private Burger burger;
    private Bun bun;

    private final double[] ingredientPrices = { 1.2, 3.4, 5.6 };

    @Before
    public void setUp() {
        burger = new Burger();

        bun = mock(Bun.class);
        when(bun.getName()).thenReturn("Test Bun");
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        for (double p : ingredientPrices) {
            Ingredient ingredient = mock(Ingredient.class);
            when(ingredient.getPrice()).thenReturn((float) p);
            when(ingredient.getType()).thenReturn(IngredientType.FILLING);
            when(ingredient.getName()).thenReturn("X");
            burger.addIngredient(ingredient);
        }
    }

    @Test
    public void priceIsCalculatedFromBunAndIngredients() {
        double expectedTotal = 2 * (double) bun.getPrice();
        for (double p : ingredientPrices) {
            expectedTotal += p;
        }
        assertThat((double) burger.getPrice(), closeTo(expectedTotal, 0.0001));
    }
}
