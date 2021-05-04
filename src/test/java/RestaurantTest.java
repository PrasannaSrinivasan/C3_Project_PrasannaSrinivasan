import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest{
    Restaurant restaurant;

    public Restaurant createRestaurant(String restaurantName, String restaurantLocation, LocalTime openingTime,LocalTime closingTime){
        Restaurant newRestaurant = new Restaurant(restaurantName,restaurantLocation,openingTime,closingTime);
        newRestaurant.addToMenu("Sweet corn soup",119);
        newRestaurant.addToMenu("Vegetable lasagne", 269);
        return newRestaurant;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime openingTime = LocalTime.parse("18:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Long minutesBetweenOpeningAndClosingHours = ChronoUnit.MINUTES.between(openingTime,closingTime);
        openingTime = LocalTime.now().minusMinutes(minutesBetweenOpeningAndClosingHours / 2);
        closingTime = LocalTime.now().plusMinutes(minutesBetweenOpeningAndClosingHours / 2);
        restaurant = createRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        assertTrue(restaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime openingTime = LocalTime.parse("18:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Long minutesBetweenOpeningAndClosingHours = ChronoUnit.MINUTES.between(openingTime,closingTime);
        openingTime = LocalTime.now().plusMinutes(minutesBetweenOpeningAndClosingHours / 2);
        closingTime = LocalTime.now().minusMinutes(minutesBetweenOpeningAndClosingHours / 2);
        restaurant = createRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        assertFalse(restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant = createRestaurant("Amelie's cafe","Chennai",LocalTime.parse("10:30:00"),LocalTime.parse("22:00:00"));
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant = createRestaurant("Amelie's cafe","Chennai",LocalTime.parse("10:30:00"),LocalTime.parse("22:00:00"));
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant = createRestaurant("Amelie's cafe","Chennai",LocalTime.parse("10:30:00"),LocalTime.parse("22:00:00"));
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}