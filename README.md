

# Javahammer

Javahammer is a companion app for the popular tabletop game Warhammer 40K. It assists players in learning and referencing game rules, constructing army rosters, and making strategic decisions with built-in damage projection features. This app is inspired by the official Warhammer 40K companion app developed by Games Workshop. However, it has been entirely developed from scratch, with redesigned features aimed at streamlining and enhancing the gameplay experience.

All media shown in this document are screenshots and recordings from my version of the companion app.

## Original Features

The features included in this section are ones are present in the Games Workshop's version of the Warhammer 40k app, who's function has also been replicated in my app via my own implementation. 


### Core Rules / Faction Reference

The app includes a reference list of all 24 playable factions in Warhammer 40K, along with their relevant rules, detachments, enhancements, and stratagems. 

<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/9a043bf7-7a0c-4c95-ac20-0ecbcf45fb19" width="250" height="500" />
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/54fd527c-6a08-46fb-9bc1-61176395e705" width="250" height="500" />
<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/a361127a-3884-4c43-91e3-c9da185c94ed" width="250" height="500" />

Users can also access unit-specific data in a compact, lightweight format.

<img src="https://github.com/ThomasJohnson35/javahammer/assets/24684871/5126a04c-57b5-456d-b4ac-f47cb029ddd5" width="250" height="500" />

### Roster Building Tool

Players can create and store an unlimited number of rosters, specifying faction, battle size, detachment, and unit selections. The UI dynamically updates with relevant faction images and unit choices based on the user's selections. The app also calculates and displays the total roster points in real-time.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExNmxjaDFzNzhobzJwY2cyeWY4YXE4bXVjcHl0MTM0NjJldmdscmgwaiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/ykoqfwfNowZT0v6PqS/giphy.gif)


### Roster Exporting Tool

Users can export their roster in a readable plaintext format with a simple click. Additionally, the app includes a copy-to-clipboard function, allowing easy sharing outside of the application.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExZTM3Y2piNTViZmVteTBjanQ0ZWt5dmtuZDVqbHZpYXBmYW1wOWtuNiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/BbKa6IF7qVU5yymwqG/giphy.gif)


### Persistent Data Storage

Rosters are automatically serialized into a custom JSON format whenever the app is closed. Upon reopening, the data is parsed and restored, eliminating the need for manual saving and preventing accidental data loss.

<img src="https://github.com/ThomasJohnson35/javahammer/blob/main/Real%20Data%20Persistence.gif" width="250" height="500" />

### Light-weight design

The app features a streamlined UI with a bottom navigation bar, a fragment manager, and a tagging system. This allows users to seamlessly switch between the Rules Reference, Roster Editor, and Damage Calculator without losing progress.

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

### Advanced Roster Validation

Army construction rules can be complex, often leading to frustrating validation errors in the official Warhammer 40K app. Javahammer prevents invalid configurations by proactively enforcing unit restrictions and providing clear explanations when limitations apply. This ensures a smoother and more user-friendly roster-building experience.

![](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExOGwxaG10aXlmdzBrMmJydDNyYWVwMzZheWhrcWtndnJ6eXM5ZXh5NCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/GbCX49RxnkCgF58tBQ/giphy.gif)

## Conclusion

Javahammer offers a more intuitive and efficient alternative to the official Warhammer 40K companion app. With enhanced roster management, a powerful damage calculator, and robust validation features, it streamlines gameplay preparation and decision-making, making the Warhammer 40K experience more enjoyable and accessible for players.

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

