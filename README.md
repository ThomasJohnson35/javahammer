

# Javahammer

This companion app for the popular tabletop game Warhammer 40k, assists players in learning/referencing the rules of the game, constructing their army rosters which are necessary for play, and quickly setting up games where their army rosters are automatically imported. The function of this app is based off of the currently existing Warhammer 40k companion app developed by Games Workshop, however this version is written from scratch by myself and attempts to redesign many features in orer make the process of preparing/playing the game of Warhammer 40k both more streamlined and more enjoyable.


## Original Features

All of the items included in this section are features that are present in Games Workshop's version of the Warhammer 40k app, who's function has also been replicated in my app. All videos shown represent screen recordings of my own app's implementation of the features.


### Core Rules / Faction Reference

Includes a list of all 24 factions available for play in the game of Warhammer 40k, as well as their relevant faction rules, detachments, enhancements, and stratagems.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExZGUzMGZoanB0ZWs2NXBvY2hxOHptMzhjdnp5amNqZXVkeHcwb3VociZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/A0k1yWVZYsXY5dyIdd/giphy.gif)




### Roster Building Tool

Users can create and store an indefinte number of rosters that represent a choice in faction, battle size, detachment, and a list of units. Based on the user's choice of faction and detachment, the UI is populated with relevant images of the faction and the units from their selected faction are then able to be added via the corresponding unit header. The cumulative points cost of all the units currently added to their roster is disaplyed alongside the points limit of the battle size they have selected.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExNmxjaDFzNzhobzJwY2cyeWY4YXE4bXVjcHl0MTM0NjJldmdscmgwaiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/ykoqfwfNowZT0v6PqS/giphy.gif)

### Roster Exporting Tool

If a user wants to view their roster into a more readable plaintext format, they need simply click the corresponding export icon when viewing their roster. Another icon on this page will copy the text elements of this page into the user's clipboard to make for easy use outside of the app.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExZTM3Y2piNTViZmVteTBjanQ0ZWt5dmtuZDVqbHZpYXBmYW1wOWtuNiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/BbKa6IF7qVU5yymwqG/giphy.gif)


### Persistent Data 

Whenever the Main Activity that wraps the various fragments of the application onPaused() method is called (i.e. whenever a user exits or closes the app), the list of rosters is serialized into a JSON data format that I designed. Upon relaunching the application the JSON data is parsed and the list of rosters is recreated. This method of saving ensures that no explicit saving must ever be performed by the user, so there is very little risk of their work building a list ever being lost.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExazU3dHM3eHF0M2d5NjdkMHYwdnNqODcyZWp1NjRmZXBhZmgxN2E4aSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/Wu5yLgV5rVIspxYV9h/giphy.gif)

### Light-weight design

Utilizing a central activity with a BottomNavigationBar, a fragment manager, and a fragment tagging system, the UI has been settup to allow users to navigate freely back and forth between the three various sections of the app (Rules Reference/Roster Editor/Damage Calculator) without losing your place.

As demonstrated, you could be in the middle of configuring your roster, tab over to the Rules Reference to check something, tab over again to the Damage Calculator to check some math, and then return the exact place you left your roster.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExeDUzbWhrcXBiMzVlZTYyZ2g1MXpvbXVsMHVxNDAxZjF0eTk4NTM2bCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/FWnDhE50q8gMn38w0Q/giphy.gif)





## Redesigned Features


### Forced Roster Validation

Rules for army construction in Warhammer 40k can be very complicated at times, resulting in a lot of frustration and tedium while trying to build a roster to use. Specifically trying to figure out a legal loadout for a given unit can be incredibly difficult. The Games Workshop Warhammer 40k app does not make this process much easier, as it merely describes the many parameters a unit may have to be valid, and then throws validation warnings at the user if the configuration does not comply. In the development of my version of the app I ensured that Unit validation errors would never occur, as the app would pre-emptively determine what would and would not be legal configuration of a Unit, block said configuration if it would cause a validation error, and provide explaination as to what validation error would be caused.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExbGc3M25nZzcxaDM5bXowMjh2dnI4NmVtemN5aW0zdWVyYjA0dmIxMiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/tJ142vpaQ65IeO54dt/giphy.gif) 

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExOGwxaG10aXlmdzBrMmJydDNyYWVwMzZheWhrcWtndnJ6eXM5ZXh5NCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/GbCX49RxnkCgF58tBQ/giphy.gif)


### Damage Calculator

Warhammer 40k is a dice game 

As opposed to requiring users to copy weapon data by hand whenever they would like to check some math, users can quickly import any weapon profile they are viewing into the list of profiles on display in the damage calculator tab, by simply clicking the corresponding calculator icon next to weaopon profiles wherever they are on display in the app. 

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExa3N0N3N3aWFvbG91OGxvaXoyYXpmZm8xeGRtaHM2MjVpc3hwZTJlMyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/2DgGU7EkVOPhfJzdHX/giphy.gif)
