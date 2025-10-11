package praktikum;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.closeTo;

public class IngredientTest {
    @Test
    public void ingredientStoresTypeNamePrice() {
        Ingredient ing = new Ingredient(IngredientType.FILLING, "Bacon", 33.3f);
        assertThat(ing.getType(), is(IngredientType.FILLING));
        assertThat(ing.getName(), is("Bacon"));
        assertThat((double) ing.getPrice(), closeTo(33.3, 1e-6));
    }
}
