

# Javahammer

This companion app for the popular tabletop game Warhammer 40k, assists players in learning/referencing the rules of the game, constructing their army rosters which are necessary for play, and enabling them to make the best decision's while playing with it's built in damage projection features. The conept of this app is based off of the currently existing Warhammer 40k companion app developed by Games Workshop, however this version is written from scratch by myself and attempts to redesign many features in order make the process of preparing/playing the game of Warhammer 40k both more streamlined and more enjoyable. All media shown represent screenshots and recordings of my version of the companion app.

## Screenshots

<Roster Library>
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/1b1e211d-5f2c-47bf-b3ea-8ad07cad9e17" width="250" height="500" />
<Roster Settings>
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/55f486eb-8638-42fe-8222-571bdea1d213" width="250" height="500" />
<Roster Builder>
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/5051779e-b064-4c1a-8115-3561d1069c9a" width="250" height="500" />
<Datasheet Blowup>
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/e420345f-e919-4d70-a290-ef4edeaa4e61" width="250" height="500" />
<Unit Management>
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/3ef5d316-434c-48ff-a721-63be5004c13d" width="250" height="500" />
<Importing Unit>
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/3fbaefbf-b330-4832-996a-7a6f696b47e2" width="250" height="500" />
<Damage Calculator>
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/f6dd8c2c-27e0-44be-9f3b-bd7af7fdafd9" width="250" height="500" />
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/001888d9-5422-4c27-a9df-e4248cca947f" width="250" height="500" />
<Faction>
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/f932f9a3-8785-458c-ad13-7e8462960f1a" width="250" height="500" />
<Detachment>
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/38f5a1e9-c255-4d57-8247-b9f367b9a372" width="250" height="500" />

## Original Features

The features included in this section are features that are present in both my version and the Games Workshop's version of the Warhammer 40k app, who's function has also been replicated in my app via my own implementation. 


### Core Rules / Faction Reference

Includes a list of all 24 factions available for play in the game of Warhammer 40k, as well as their relevant faction rules, detachments, enhancements, and stratagems.

<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/9a043bf7-7a0c-4c95-ac20-0ecbcf45fb19" width="250" height="500" />
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/54fd527c-6a08-46fb-9bc1-61176395e705" width="250" height="500" />
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/a361127a-3884-4c43-91e3-c9da185c94ed" width="250" height="500" />

Relevant information about each unit in a faction can also be accessed in a compact and light-weight format.

<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/5126a04c-57b5-456d-b4ac-f47cb029ddd5" width="250" height="500" />

### Roster Building Tool

Users can create and store an indefinte number of rosters that represent a choice in faction, battle size, detachment, and a list of units that they are choosing to take. Based on the user's choice of faction and detachment, the UI is populated with relevant images of the faction and the units from their selected faction are then able to be added via the corresponding unit header. The cumulative points cost of all the units currently added to their roster is disaplyed alongside the points limit of the battle size they have selected.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExNmxjaDFzNzhobzJwY2cyeWY4YXE4bXVjcHl0MTM0NjJldmdscmgwaiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/ykoqfwfNowZT0v6PqS/giphy.gif)


### Roster Exporting Tool

If a user wants to view their roster into a more readable plaintext format, they need simply click the corresponding export icon when viewing their roster. Another icon on this page will copy the text elements of this page into the user's clipboard to make for easy use outside of the app.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExZTM3Y2piNTViZmVteTBjanQ0ZWt5dmtuZDVqbHZpYXBmYW1wOWtuNiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/BbKa6IF7qVU5yymwqG/giphy.gif)


### Persistent Data 

Whenever the Main Activity that wraps the various fragments of the application onPaused() method is called (i.e. whenever a user exits or closes the app), the list of rosters is serialized into a JSON data format that I designed. Upon relaunching the application the JSON data is parsed and the list of rosters is recreated. This method of saving ensures that no explicit saving must ever be performed by the user, so there is very little risk of their work building a list ever being lost.

<img src="https://github.com/ThomasJohnson35/javahammer/blob/main/Real%20Data%20Persistence.gif" width="250" height="500" />

### Light-weight design

Utilizing a central activity with a BottomNavigationBar, a fragment manager, and a fragment tagging system, the UI has been settup to allow users to navigate freely back and forth between the three various sections of the app (Rules Reference/Roster Editor/Damage Calculator) without losing your place.

As demonstrated, you could be in the middle of configuring your roster, tab over to the Rules Reference to check something, tab over again to the Damage Calculator to check some math, and then return the exact place you left your roster.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExeDUzbWhrcXBiMzVlZTYyZ2g1MXpvbXVsMHVxNDAxZjF0eTk4NTM2bCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/FWnDhE50q8gMn38w0Q/giphy.gif)


## Redesigned Features

 The features covered in this section represent features present in my version of the app that do not exist in the Games Workshop version. 


### Damage Calculator

Warhammer 40k is - at its core - a strategic tabletop game revolving chiefly around high volumes of dice rolling in order to simulate events happening on the battlefield.
Simply firing a weapon to damage an opposing player's unit could require rolls in order to determine the number of attacks that:

* Are fired from the weapon
* Hit the opponent
* Wound the opponent
* Are saved by the opponent's armor
* Are ignored by the opponent's special rules

Add in the myraid of special rules that weapons can have (rolls of a 6 generate 3 hits, re-roll all misses against enemies of a certain type, ignore save rolls on a wound roll of 4+, etc.) and it quickly becomes incredibly hard for players to mentally work out the damage outputs of various weapon's against different types of units that they'll be facing. This difficulty in determining averages makes building rosters that player's feel confident in and making decisions while playing more tedious and time consuming than they need to be.

One of my goals in building this version of the app was to mitigate this problem, and I have chosen to do so by implementing an all-in-one damage calculator feature that allows players to quickly test the expected damage outputs of multiple weapon profiles versus multiple defending unit profiles. This software parses user input for the various parameters of weapons and defenders (i.e. number of attacks, accuracy, damage, toughness, etc.) and uses a series of probability matrix methods that I've created to nearly instantly calculate the range of expected results. 

Notabley, the parsing methods are capable of taking not only Integers for input, but also String representations of dice values (i.e. D3, D6, 3D6), or any combination of the two (i.e. 2D6+3).

<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/a9e44ef8-cbf9-4b3b-b1d9-450f0e5df7e5" width="250" height="500" />

Special rules for weapons such as the "Twin-Linked" rule - which re-rolls all failed wound rolls - are taken into consideration when calculations are run and logged in the Results section.

<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/32d90f3d-9065-44a1-ab21-e88867309e92" width="250" height="500" /> <img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/626f4f68-5579-43a1-bc37-36bf7aefdf09" width="250" height="500" />

<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/437bedc5-6b0f-417c-8bcb-0fd03613d2e0" width="250" height="500" /> <img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/3614f236-45e6-4f90-8893-2be9521cda51" width="250" height="500" />


Manually creating weapon profiles and entering the data is not the only way to use the damage calculator feature of this app. Users can quickly import any weapon profile they are viewing into the list of profiles on display in the damage calculator tab by simply clicking the corresponding calculator icon next to weaopon profiles wherever they appear. 

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExa3N0N3N3aWFvbG91OGxvaXoyYXpmZm8xeGRtaHM2MjVpc3hwZTJlMyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/2DgGU7EkVOPhfJzdHX/giphy.gif)

This integration - alongside the app's lightweight multi-tab design - makes it incredibly easy to gauge a unit's performance and decide how to configure it's loadout while building a roster. 

### Forced Roster Validation

Rules for army construction in Warhammer 40k can be very complicated at times, resulting in a lot of frustration and tedium while trying to build a roster to use. Specifically, trying to figure out a legal loadout for a given unit can be incredibly difficult. The Games Workshop Warhammer 40k app does not make this process much easier, as it merely describes the many parameters a unit may have to be valid, and then throws validation warnings at the user if the configuration does not comply. In the development of my version of the app I ensured that Unit validation errors would never occur, as the app would pre-emptively determine what would and would not be legal configuration of a unit, block said configuration if it would cause a validation error, and provide explaination as to what validation error would be caused.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExOGwxaG10aXlmdzBrMmJydDNyYWVwMzZheWhrcWtndnJ6eXM5ZXh5NCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/GbCX49RxnkCgF58tBQ/giphy.gif)

This change - alongside other improvements to the UI surrounding wargear - represent a drastic improvement in visual clarity over the Games Workshop version.
