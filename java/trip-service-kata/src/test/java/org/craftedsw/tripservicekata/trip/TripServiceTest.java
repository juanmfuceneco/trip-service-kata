package org.craftedsw.tripservicekata.trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class TripServiceTest {

    private TripService tripService = new TesteableTripService();
    private User loggedUser;

    @Test
    public void when_logged_user_is_null_throws_UserNotLoggedInException() {
        // Arrange
        User user = new User();
        loggedUser = null;

        // Act
        Executable ex = () -> tripService.getTripsByUser(user);

        // Assert
        assertThrows(UserNotLoggedInException.class, ex);
    }

    @Test
    public void when_logged_user_is_not_friend_then_returns_empty_list() {
        // Arrange
        User user = new User();
        loggedUser = new User();

        // Act
        List<Trip> trips = tripService.getTripsByUser(user);

        // Assert
        assertEquals(0, trips.size());
    }

    @Test
    public void when_logged_user_is_friend_then_return_list_with_trips() {
        // Arrange
        User user = new User();
        loggedUser = new User();
        user.addFriend(loggedUser);
        user.addTrip(new Trip());

        // Act
        List<Trip> trips = tripService.getTripsByUser(user);

        // Assert
        assertEquals(1, trips.size());

    }

    private class TesteableTripService extends TripService {

        @Override
        protected User getLoggedUser() {
            return loggedUser;
        }

        @Override
        protected List<Trip> findTripsByUser(User user) {
            return user.trips();
        }
    }
}
