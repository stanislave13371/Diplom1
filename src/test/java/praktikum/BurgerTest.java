package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient sauce;

    @Mock
    private Ingredient filling;

    @Before
    public void setUp() {
        burger = new Burger();

        when(bun.getName()).thenReturn("Флюоресцентная булка R2-D3");
        when(bun.getPrice()).thenReturn(100.0f);

        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("Space Sauce");
        when(sauce.getPrice()).thenReturn(25.5f);

        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("Protostomia");
        when(filling.getPrice()).thenReturn(77.0f);
    }

    @Test
    public void setBuns_shouldAssignBun() {
        burger.setBuns(bun);
        assertThat("Должен сохраниться объект булки", burger.bun, is(bun));
    }

    @Test
    public void addIngredient_shouldAppendToList() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        assertEquals(2, burger.ingredients.size());
        assertThat(burger.ingredients.get(0), is(sauce));
        assertThat(burger.ingredients.get(1), is(filling));
    }

    @Test
    public void removeIngredient_shouldRemoveByIndex() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        burger.removeIngredient(0);

        assertEquals(1, burger.ingredients.size());
        assertThat(burger.ingredients.get(0), is(filling));
    }

    @Test
    public void moveIngredient_shouldReorder() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        burger.moveIngredient(1, 0);

        assertThat(burger.ingredients.get(0), is(filling));
        assertThat(burger.ingredients.get(1), is(sauce));
    }

    @Test
    public void getPrice_shouldUseDoubleBunPriceAndIngredients() {
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        float price = burger.getPrice();
        assertEquals(100.0f * 2 + 25.5f + 77.0f, price, 0.0001f);

        verify(bun, atLeastOnce()).getPrice();
    }

    @Test
    public void getReceipt_shouldContainInTopAndBottomBunsIngredientsAndPrice() {
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        assertThat(receipt, containsString("(==== " + bun.getName() + " ====)"));

        assertThat(receipt, containsString(String.format("= %s %s =",
                sauce.getType().toString().toLowerCase(), sauce.getName())));
        assertThat(receipt, containsString(String.format("= %s %s =",
                filling.getType().toString().toLowerCase(), filling.getName())));

        assertThat(receipt, containsString("(==== " + bun.getName() + " ====)"));

        Pattern p = Pattern.compile("Price:\\s*([\\d.,]+)");
        Matcher m = p.matcher(receipt);
        assertThat("В чеке должна быть строка с ценой", m.find(), is(true));
        float parsedFromReceipt = Float.parseFloat(m.group(1).replace(',', '.'));

        assertEquals("Чек должен содержать корректную цену",
                burger.getPrice(), parsedFromReceipt, 0.0001f);
    }
}