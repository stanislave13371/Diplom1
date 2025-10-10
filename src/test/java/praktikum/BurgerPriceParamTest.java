package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class BurgerPriceParamTest {

    @Parameterized.Parameters(name = "{index}: bun={0}, ingredientsSum={1}, expectedTotal={2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {100f, 0f, 200f},
                {50f, 10f, 110f},
                {100f, 25.5f + 77f, 302.5f}
        });
    }

    @Parameterized.Parameter(0)
    public float bunPrice;

    @Parameterized.Parameter(1)
    public float ingredientsSum;

    @Parameterized.Parameter(2)
    public float expectedTotal;

    @Test
    public void shouldCalculatePriceForDifferentSets() {
        Burger burger = new Burger();
        burger.setBuns(new Bun("R2-D3", bunPrice));

        float part1 = ingredientsSum / 3f;
        float part2 = ingredientsSum - part1;

        burger.addIngredient(new Ingredient(IngredientType.SAUCE, "Alpha", part1));
        burger.addIngredient(new Ingredient(IngredientType.FILLING, "Beta", part2));

        float actual = burger.getPrice();
        assertEquals(expectedTotal, actual, 0.0001f);
    }
}
