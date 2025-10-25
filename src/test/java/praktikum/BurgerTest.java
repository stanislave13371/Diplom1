package praktikum;

import org.junit.Test;
import org.junit.Before;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class BurgerTest {

    private Burger burger;
    private Bun bun;

    @Before
    public void setUp() {
        burger = new Burger();
        bun = mock(Bun.class);
        when(bun.getName()).thenReturn("Test Bun");
        when(bun.getPrice()).thenReturn(50f);
        burger.setBuns(bun);
    }

    @Test
    public void addIngredientAppendsToList() {
        Ingredient ing = mock(Ingredient.class);
        burger.addIngredient(ing);
        assertThat(burger.ingredients, hasSize(1));
    }

    @Test
    public void removeIngredientDecreasesListSize() {
        Ingredient a = mock(Ingredient.class);
        Ingredient b = mock(Ingredient.class);
        burger.addIngredient(a);
        burger.addIngredient(b);

        burger.removeIngredient(0);

        assertThat(burger.ingredients, hasSize(1));
    }

    @Test
    public void moveIngredientChangesOrder() {
        Ingredient first = mock(Ingredient.class);
        Ingredient second = mock(Ingredient.class);
        Ingredient third = mock(Ingredient.class);

        burger.addIngredient(first);
        burger.addIngredient(second);
        burger.addIngredient(third);

        burger.moveIngredient(0, 2);

        assertThat(burger.ingredients.get(2), sameInstance(first));
    }

    @Test
    public void getPriceUsesBunAndIngredients() {
        Ingredient ing = mock(Ingredient.class);
        when(ing.getPrice()).thenReturn(25f);
        burger.addIngredient(ing);

        double expected = 2 * 50d + 25d;
        assertThat((double) burger.getPrice(), closeTo(expected, 0.0001));
    }

    @Test
    public void receiptEndsWithTotalPriceLine() {
        Ingredient ing = mock(Ingredient.class);
        when(ing.getType()).thenReturn(IngredientType.SAUCE);
        when(ing.getName()).thenReturn("Ketchup");
        when(ing.getPrice()).thenReturn(1f);
        burger.addIngredient(ing);

        double expectedPrice = 2 * (double) bun.getPrice()
                + burger.ingredients.stream()
                .mapToDouble(i -> (double) i.getPrice())
                .sum();

        StringBuilder expected = new StringBuilder();
        expected.append(String.format("(==== %s ====)%n", bun.getName()));
        for (Ingredient i : burger.ingredients) {
            expected.append(String.format("= %s %s =%n",
                    i.getType().toString().toLowerCase(),
                    i.getName()));
        }
        expected.append(String.format("(==== %s ====)%n", bun.getName()));
        expected.append(String.format("%nPrice: %f%n", expectedPrice));

        assertThat(burger.getReceipt(), is(expected.toString()));
    }
}
