# WeightTrackerApp
![image](https://github.com/JeremyBaldner/WeightTrackerApp/assets/127251923/7cd87256-1c76-4b43-b21a-6da3aec0ba94)


Android app that trackers the user's weight over time.

- **Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?** <br>
The app I developed is for users who would like to track their weight. This required a database, multiple screens, user permissions requests, and the ability to modify the data.<br>
- **What screens and features were necessary to support user needs and produce a user-centered UI for the app?** <br>
The app required a login, dashboard, historical data, and modification screen. The dashboard screen allows the users to record their weight. The real-time graph updates immediately after entry. The graph allows users to view a timeline of 7, 30, or 90 days and provides a visual of their goal. The historical screen uses a recycler view to create a list of the weights that have been entered. Clicking on a list item takes the user to the modification screen to either update or delete that data entry.<br>
- **How did your UI designs keep users in mind? Why were your designs successful?** <br>
All UI designs were created with user engagement in mind. Using the Android best practices, wireframes were created to visualize screens prior to implementation. After implementation, user testing was done to check engagement and ease of use. Modifications were made and the UI was improved. My designs were successful since they fit with current best practices and received confirmation from positive feedback after user testing. <br>
- **How did you approach the process of coding your app?** <br>
I took an iterative approach to process the app code. By breaking down the project into smaller sections, I was able to perform miniature sprints and reviews with my peers and instructor to create a user friendly and effective app.<br>
- **What techniques or strategies did you use?** <br>
I used the agile methodology.<br>
- **How could those be applied in the future?** <br>
I will use the knowledge I gained from this project and apply its successful process in my future projects.<br>
- **How did you test to ensure your code was functional?** <br>
I performed white and black box testing. I performed white box testing to ensure that the internal structures of the application performed properly. I had users perform black box testing to ensure the app behaved appropriately. <br>
- **Why is this process important and what did it reveal?** <br>
Testing is important for the functionality of the app. White box testing made sure that functions accepted and returned proper data. This revealed issues with the database when it would not modify data when expected. The issue was able to be corrected after minor trouble shooting. Black box testing shows unexpected bugs even when the app runs fine. This revealed an issue with old data populating when using the devices back button. The issue was resolved by utilizing the finish function appropriately and explicitly setting the back button functionality.<br>
- **Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge?** <br>
I had to innovate when working with the dashboard screen. We were tasked with providing the user with a grid view, but it was not visually appealing for the user. I researched and learned the graph view library to implement this feature on my app. <br>
- **In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?** <br>
I am most proud of the dashboard screen. This screen has a lot of features for the user such as a dynamic real-time graph, available filters, and a section to add data. Being able to provide all this functionality on a visually appealing screen provides some insight into my knowledge, skills, and experience at this time. <br>
