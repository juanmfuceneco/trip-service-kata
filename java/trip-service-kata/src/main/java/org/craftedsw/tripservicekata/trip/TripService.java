package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		User loggedUser = Optional.ofNullable(getLoggedUser())
			.orElseThrow(UserNotLoggedInException::new);

		List<Trip> trips = new ArrayList<>();

		Optional<User> userFriend = user.getFriends().stream()
			.filter(loggedUser::equals)
			.findAny();

		if(userFriend.isPresent()){
			trips = findTripsByUser(user);
		}

		return trips;
	}

	protected List<Trip> findTripsByUser(User user) {
		return TripDAO.findTripsByUser(user);
	}

	protected User getLoggedUser() {
		return UserSession.getInstance().getLoggedUser();
	}



}
