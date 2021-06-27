README.md

#Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?

Goal: Provide users with a tool for managing weight.
Purpose: Weight management is a journey, not something achieved overnight. To assist that journey, the weight tracking app will allow users to track their weight daily, set a goal, and select a date in the future for when they want to achieve that goal.

#What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful? 

The application has three main screens; a login, account creation, and homepage(weight tracking page). The login page and account creation page allow for a user to store their data and have it directly attached to them. Within account creation the app ask for their goal weight and a future date for acheiment of that weight. The home page provides a scrollable calendar in which the user selects a date and adds their weight for that day. If mistakes are made they can be edited or deleted. We keep our users in mind through using familiar terms and icons. Such as right and left arrows for navigating to different months in the tracking calendar. 

#How did you approach the process of coding your app? What techniques or strategies did you use? How could those be applied in the future?

When coding the applcaition the frontend and backend were developed seperatly. Before business logic was added to the frontend a user could just click buttons and move forward in the app, but not receive any feedback. The same can be said for the backend API to the databases. Test classes can be created and successfully send and retrieve information to the API without the use of the front-end. Once both were completed, the business logic was added to the applcation connecting the front and back ends of the app. The strategy was to create re-usable code. The localized backend could be moved to a remote server and an updated controller created for the app.

#How did you test to ensure your code was functional? Why is this process important and what did it reveal?
Most testing was done manually and no test classes currently exist. Manually testing was important as it revealed aspects of navigation that were not taken into account. One such thing is forward navigation while the keyboard is overlayed and hiding a submit button.

#Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge? I think one of the innovative approaches I had was using the BottomNavigationView object as a child in a BottomAppBar object. This allowed me to have only 2 menu options center aligned and a FAB object top-centered between them. One of the difficulties is the styling of the two objects and how primary and secondary colors are used between them.

#In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?

The backend in particular is a highlight of the application. The DB, Models, and Controller create a multi-user applicaiton from login to data recording; keeping track of each specifc users goals and tracked daily weights. Adding an API for a remote server connection will allow for a user to be able to access this data across multiple devices. 
