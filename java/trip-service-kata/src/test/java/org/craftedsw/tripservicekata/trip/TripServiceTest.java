package org.craftedsw.tripservicekata.trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class TripServiceTest {

    TripService tripService = new TesteableTripService();
    private User loggedUser;

    @Test
    public void should_thrown_UserNotLoggedInException_when_logged_user_is_null() {
        //Arrange
        loggedUser = null;
        User user = new User();

        //Act
        Executable ex = () -> tripService.getTripsByUser(user);

        //Assert
        Assertions.assertThrows(UserNotLoggedInException.class, ex);
    }

    @Test
    public void should_return_empty_trips_list_when_logged_user_is_not_friend() {
        //Arrange
        loggedUser = new User();
        User user = new User();
        //Act
        List<Trip> trips =  tripService.getTripsByUser(user);

        //Assert
        assertEquals(0, trips.size());
    }

    @Test
    public void should_return_trips_when_logged_user_is_friend() {
        //Arrange
        loggedUser = new User();
        User user = new User();
        user.addTrip(new Trip());
        user.addFriend(loggedUser);

        //Act
        List<Trip> trips = tripService.getTripsByUser(user);

        //Assert
        assertFalse(trips.isEmpty());
    }

    @Test
    public void should_return_trips_when_logged_user_not_friend() {
        //Arrange
        loggedUser = new User();
        User user = new User();
        user.addTrip(new Trip());
        user.addFriend(new User());

        //Act
        List<Trip> trips = tripService.getTripsByUser(user);

        //Assert
        assertTrue(trips.isEmpty());
    }

    @Test
    public void should_return_NPE() {
        // Arrange
        User user = null;
        loggedUser = new User();

        // Act
        Executable ex = () -> tripService.getTripsByUser(user);

        // Assert
        Assertions.assertThrows(NullPointerException.class, ex);
    }

    private class TesteableTripService extends TripService {

        @Override
        protected User getLoggedUser() {
            return loggedUser;
        }

        @Override
        protected List<Trip> getTripByUserDAO(User user) {
            return user.trips();
        }
    }

}
