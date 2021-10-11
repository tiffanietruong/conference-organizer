**** CSC207 Phase 2 - Conference System README ****
This README file contains instructions for running the program and default information.

** General Instructions **
Program users are given numbered menus to request actions and follow text prompts that direct further input.
You can input a variety of valid and invalid inputs--the program should handle the invalid ones.
To test the features, go through the menus, entering numbers and then information as directed.

Here is a suggested sequence of how to go about testing:
   (1) Watch the demo video
   (2) Log into an Organizer account.
   (3) Use the view menu (5) and the event menu (2 -> 2) to verify that the default information exists in the program.
   (4) Add / modify information.

In particular, here are some phase 2 updates to test that were not included in the demo:
   (1) The raffle
            Log into 1-2 Attendees, sign up for events, write 1-2 reviews.
            Log into an Admin (“tiffanie”) > (5) Admin action menu > (2) Pull raffle winner. Repeat if desired.
   (2) The request menu
            Log into any account.
            Select (4) Messaging Menu > (5) Go to requests menu to make and view requests.
            Follow the same instructions above but on an Organizer account to address requests
   (3) Other options in the view menu
            Log into an Organizer > (5) View information menu > user and event distribution options.
   (4) Trying to log into a banned account. Unbanning users.
            Log into an Admin > (5) Admin action menu > (2) Ban a user > (1 > 1) Log out
            Attempt to log in to a banned account. Then, log back into an Admin, unban, and repeat.

** Demo Video **
For reference, here are some phase 2 features demonstrated in the video.
    (*) Displaying the event schedule (namely by speaker)
    (*) Enhanced messaging experience (namely, mark as unread)
    (*) Organizers viewing statistics
    (*) Organizer account creation (with regexes and username suggestions)
    (*) Admin users (banning, changing user types)
    (*) VIP users (sign up for VIP events)

** Gateways and Saving Data **
Saved data on users, events, rooms, messages, and requests is loaded from file every time the program begins.
Importantly, data is only saved to file when the user exits the program via the menu options.
Therefore, if you do not want to save the changes you made during program execution
(applicable if you want to use the default information each time), end the program using IntelliJ instead of the menu.

** Default Information **
To load in the default information, ensure that the .ser files containing the default information are in ~/data
where ~ is the root directory of the project.

**** User Accounts ****
username: angela            password: Abcdef1/ 	        userType: Attendee
username: samritha          password: Abcdef1/  	    userType: Attendee
username: rudy              password: Abcdef1/		    userType: Organizer
username: thaneeshan        password: Abcdef1/		    userType: Organizer
username: robin             password: Abcdef1/		    userType: Speaker
username: matthew           password: Abcdef1/		    userType: Speaker
username: brandon           password: Abcdef1/		    userType: VIP
username: tiffanie          password: Abcdef1/		    userType: Admin

**** Rooms ****
roomID: 2       capacity: 2
roomID: 5   	capacity: 5
roomID: 10   	capacity: 10

**** Events ****
title: topology                 title: algebra                  title: manifolds
type: general                   type: vip                       type: general
day: 2021-01-01                 day: 2021-01-01                 day: 2021-01-01
start time: 9:00 / 9am          start time: 9:00 / 9am          start time: 10:00 / 10am
duration: 60 mins               duration: 60 mins               duration: 60 mins
room: 2                         room: 5                         room: 5
capacity: 2 people              capacity: 5 people              capacity: 5 people
type: party (0 speakers)        type: talk (1 speaker)          type: panel (5 speakers)
speakers: n/a                   speakers: robin                 speakers: robin, matthew

**** Messages ****
tiffanie is friends with robin
there is a message from tiffanie to robin
there is a reply from robin to tiffanie

**** Requests ****
There are two requests from tiffanie.
One request is PENDING and one request is ADDRESSED.
