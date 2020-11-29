# SmashPE for Android

An Android application to help SmashPE tournament organisers manage [Challonge](https://challonge.com/) tourneys.  With this app, the user can view their tournaments, their matches and report scores results.

During offline tournaments, TO's constantly have to report match results. The current way of doing so is using the Challonge's mobile website, which shows the matches in a [traditional bracket view](https://image.shutterstock.com/image-vector/16-team-tournament-bracket-templates-260nw-733642078.jpg"). This is not suited for small screens such as mobiles one, therefore, the TO's lose a lot of time looking for the right match to update.

This app list the tournament's matches dividing between "Pending results" and "Previous matches" in a simple list. This way, TO's can easily find active matches to be reported.

## How to use

- Download the latest APK in the [releases page](https://github.com/hcaula/SmashPE/releases);
- Install the APK and run the app;
- Find your API Key [here](https://challonge.com/settings/developer) (Challonge account required);
- Enter the API Key;
- Done! The API Key is saved, so you don't have to re-enter after you close the app.

## Implementations details

### Main Activity

This app uses the [Challonge API](https://api.challonge.com/v1) to manage its tournaments. Because of that, it was necessary to provide a way for the user to register their API Key.

- First, we check to see if the user has an API Key saved in their [Shared Preferences](https://developer.android.com/training/data-storage/shared-preferences).
	- If they have, we open the <b>Menu Activity</b>;
	- Otherwise, render the Main Activity as normal.
- The first screen shows an input that the user can enter their API Key;
- Then, we use that key to fetch the user's tournaments.
	- If it returns OK, we save the key to their shared preferences and open the <b>Menu Activity</b>;
	- Otherwise, an error message appears telling the user to enter their API Key again.

The Shared Preferences API was chosen because it provides a simple way to persist simple data. The API Key is a great example of that.

This activity also has a Browser Intent, linking to where the user can retrieve their API key.

### Menu Activity

In here, the user can navigate between the <b>Tournaments</b> or <b>Settings</b> fragments. This is achieved using [BottomNavigationView](https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView) and [AppBarConfiguration](https://developer.android.com/reference/androidx/navigation/ui/AppBarConfiguration).

#### Tournaments Fragment

In this screen, we fetch and show the user's tournaments, sorting them by most recently created. We show the tournament's state, participants count and creation date.

This fragment uses the [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) pattern to fetch and manage the tournaments, removing this logic from the fragment - which deals with the presentation layer. It also uses [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview)  to render the list, since the list can be mutable and fairly large.

When an item from the RecyclerView is clicked, we enter the <b>MatchesActivity</b>.

#### Settings Fragment

The Settings page simply provides an option to erase the API Key from the Shared Preferences. When doing so, a Dialog popup shows up and, if the user confirm their decision, we clear the `API_KEY` field and finish the activity - since the Main Activity is the previous activity, the user will be quickly prompted to re-enter it. This guarantees that no other activity will be rendered without a saved API Key.

### Matches Activity

This activity uses [ViewPager]((https://developer.android.com/guide/navigation/navigation-swipe-view)) to differentiate between pending and previous matches in a swipe tabs view.

It uses the ViewModel pattern, since it also fetches data similarly to the TournamentsFragment. In fact, it has two ViewModels - the SectionsViewModel for managing the ViewPager - and MatchesViewModel for actually fetching the matches.

Since we have two fragments instances (one for each match state division), we can't instantiate the MatchesViewModel in each fragment, since this would cause a <b>double fetching</b> of the matches. Instead, we instantiate it in the MatchesActivity and pass this instance to the fragments. Now, each fragment still calls for the LiveData attribute, but it will only be initiated once.

Furthermore, since the user should see an updated list of matches after reporting their results, this activity uses the <b>OnRestart</b> lifecycle method to re-fetch the match list.

When the user select an <b>active</b> match, we start the <b>ReportActivity</b>.

<b>OBS.:</b> To avoid API complications, it's not possible to report in a finished match.

### Reports Activity

This activity is fairly simple - two input fields and a report button. The report button, however, is disabled until both players have a result in their input field <b>and its not a draw</b>. Furthermore, this activity counts with a Dialog box to prevent mistakes when reporting.

When the API returns a success message, we finish this activity, which brings us to the <b>MatchesActivity</b>.

### Other classes

#### Retrofit

This app uses [Retrofit](https://square.github.io/retrofit/) to manage API calls. This makes much easier to deal with HTTP calls threads.

The API wrapper is in the `challonge` package. The `RetrofitFacade` provides a singleton class, so we can abstract the initialisation of the `ApiHelper`.

#### HTTP Client

The Challonge API requires the API Key being passed as a query string. In order to avoid having to set this value for every route, we intercept the HTTP request and add it to the path using [OkHttp](https://square.github.io/okhttp/). This also allowed us to log every request being made for debugging proposes - which helped to catch the double refetching in the MatchesActivity.

Furthermore, in this class, we retrieve the API Key store in the Shared Preferences.

## Possible improvements

### Query tournament's matches and participants simultaneously

The [match list route](https://api.challonge.com/v1/documents/matches/index) doesn't provide the players names, only their ID's. Because of that, we also have to fetch the [participants list](https://api.challonge.com/v1/documents/matches/index) for that tournament and pair with the ID's.

Currently, we first fetch the match list - once the request is finished, we fetch the participant list. Ideally, we would fetch them at the same time and synchronize their arrivals, making it faster to load the page.

### Refactor match-participants pairing

Currently, the players and matches pairing are done in the same lamba function from the response. This culminates in cascade programming and bad practice.

Ideally, this logic should be re-written in a util class.

### Implement pull-to-refresh

The only way to refresh activities is restarting them. Ideally, this should be done with a [pull-to-refresh implementation](https://guides.codepath.com/android/implementing-pull-to-refresh-guide).

## Authors

- Henrique Caúla | henrique.caula3@gmail.com