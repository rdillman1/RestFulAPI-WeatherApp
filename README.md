# MyFinalProject_weatherApp

# MyFinalProject_weatherApp

## Group 3

## Members: Robert Dillman



## 03/26/2021
Started project 
Began with a design template
designed a spash screen

## 03/27/2021
Worked on the next step the log in method
created a java class for the login activity

researched how to do it through android developer. 

got it working!
confirmed I could login and on login was able to launch mainactivity


## 04/01/2021
developed the main screen implemented a spinner class for a main menu. 
Organized main class to prepare for weather API pull over
cleaned up the xml document

I think the main menu / main activity looks so good!

## 04/12/2021
Continued learning how to implement API for metaweather. 
developed an Service to do the leg work for the api response. 
got it working with a command in the menu to pull city ID from city name 

develeloped a location service that picked up locality. (city)
got it to pull during the splach activty then passes it all the way to main activity and displays it for the user
also got the main to display the persons google name. 
Main Activity is looking great!

## 04/14/2021 API integrations
Contineud with api integrations I got the whole consolidated weather report for today pulling through in a whole Toast!!!
This was a huge moment for me! 
After this i setup a few extra menu options
Refresh which is just offering the abiltiy to reload the mainactivity page
Change location > to allow the user to select a city other than the one pulled by location service!
and logout which will log the user out of the google auth throguh firebase and allow the user to pick another account. 

## 05/05/2021 Finsihing touches
Developed another screen to display a weather for a specific date
Unfortunately I could not get this to work due to the fact the Json array is unamed. 
After some time I was able to adjust the code enough to get it to work 
added some cleaning to the login activity and wahlah

# Functionality
## Splash Screen
On launch user is met with splash screen 
in the splash screen the app will request user to allow location services. 
Then app checks if user has logged in before.. if so the mainactivity, if not login activity. 
## Login activity
The user opens a sscreen and is pushed to login with google account (shoudl work with university email)
upon finishing met with three toasts (logging in/checking location/ google auth)
The splash screen will play again as a loading screen (this is to allow time to buffer and push the location data to the web service)
## Main Activity
This is the meat of the app. 
This is what pushes to the web service metaweather.com
In this activity I am on a weatherDataService.java class that handles more of the Json requests so we can keep it simple here. 
We also implemented a weathermodel to pull down requests from the JSON array into the text views you see in this screen
** This was probably the hardest thing I have done to date**
We also implement google firebase auth to hold user data. This is so we can welcome User to the app!

Using weatherDataService and WeatherDataModel we were able to create functions that pull down data for each of our text views and change as the user changes location. ** Pretty Cool**

On luanch the app is provided the current location of the user, prompting with the city name and weather for that city. 
On the top right is a spinner class. 
This bad guy is like a dropdown menu!

Providing an array on options for the user to choose from. 

## Change Location Activity

This activity allows the user to select another city and on confirming will launch the main activty with a new cities weather!
pretty simple I just use intents to push data back and forth. 

## Weather By Data 
This activity is just like main activity all the basic understanding, except the URL the JSON array is being pulled from is incorporating a date!
This is a fairly straightforward change but Doesnt work due to the array being unamed on we web service side. 

## Refresh
This is a neat thing i learned. This is a runnable that refreshes the contents on the app. 
Allowing the user to get updated info from meta weather on the users terms!
pretty cool 

## logout
This allows the user ot logotu of their google account be sent back to splash and have a chance to log in again! 

## Weather capture
This is a fun little add on that allows the user to access the camera and capture the mood outside! 
See if it compares to the weather on their app! 
This uses basic bitmap and permissions requests. 

## Weather Genie
This is another little fun add-on. 
This incororates shake method to allow you to imatate a shake movement to get a response from the genie. 
The responses re very magic eight ball oriented but with some weather based twists in there as well. 
implemented with a sensorEventListener method. 

# Learning outcomes
I learned so many things from this project. 
Learned how to implement a menu and have each item push to a new activity or do something
learned how to use web services and push to text views.
Learned how to utilize camera. 
Learned how to activate action using shake on my genie activity
Learned how to user Firebase to authorize Google logins!
Learned how to bring in Google data to the app (like name)
Learned about singletons!
Learned how to use a dataservice!
Learned how to implement a gif, and create a loading screen
Learned how to refresh a page in the app. 










