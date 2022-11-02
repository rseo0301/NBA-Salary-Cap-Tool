# CPSC 210 Personal Project: NBA Salary Cap Tool

  The NBA, similar to most professional team sports, sets a salary cap on each team. This is a maximum limit that teams cannot cross under most circumstances, regarding the amount of money spent on their player contracts. However, the NBA allows various exceptions that allows teams to *exceed* far past this salary cap. Due to these exceptions, manipulation of the salary cap is a very intricate process in the NBA that comes at the confusion to most fans.
  The purpose of this application is to be **used as a tool to explain these intricacies and simulate the NBA salary cap, allowing the user to manipulate contracts in response to their salary cap situation**. Users will be able to edit contracts in order to comply to the salary cap, while learning about the various exceptions that can apply to their players. Thus, this application can be used for users in opposite ends of the spectrum: professional, experienced users, and even NBA General Managers should be able to use this as a tool to address financial issues with their team in a convenient way. On the other hand, casual NBA viewers would be able to use this tool to clarify the different rules and how it applies to their own team. 
  I was intrigued by this idea as my eventual goal is to work as a sports data analyst, and I've become a big basketball fan over the years. As a result, I've become interested in analytical, financial side of basketball. Thus, I decided to create a program that makes this analytical process for accessible to everyone.

As a user:
- I want to add players and enter their salary into my team's roster 
- I want the program to notify me if I'm over either the salary cap or the tax line
- I want the program to calculate for me the minimum salary owed to the player based on their experience
- I want to change the salary cap and tax line based on my liking
- I want to save the data from my current roster as a file
- I want to be able to load data from a previous roster on the file, only when I want to

## Phase 3: Instructions for Grader
- You can generate the first required event by clicking "Add player..." under the edit tab on the main menu.
- You can generate the second event by adding a player with a salary above the salary cap/tax line, in which the label on the main menu will be underlined. (ie. when first launching the program, the 'Salary Floor' value will be underlined because the roster's combined salary is below that amount)
- You can generate the third required event by clicking "Add player...", then choosing a year in the "Number of Years in League" choiceBox and "Minimum Exception" in the "Exception" choiceBox. The "Salary" textField will automatically display the salary. 
- You can generate the fourth required event by going under the edit tab on on the main menu, and selecting "Settings". Then, it will allow you to change any of the values, provided that they meet the requirements (Otherwise a pop-up message will be displayed).
- You can save the state of the application by going under the file tab and clicking "save".
- You can reload the state of the application by clicking "Load Save State", or you can start a new roster if you wish.

## Phase 4: Task 2
- I incorporated the Map interface in my code through a Hashtable. This is in the Roster class, and the hashtable is initialized through the initializeDictionary method. The keys in the Hashtable refers to the player's number of years in the league, and the corresponding value is that player's minimum salary.
  - When the user wishes to the add a new player to the roster, the user enters the number of years the player has served in the league. This is the deciding factor for a player's minimum salary, so the program uses this key and returns the corresponding salary. This is more efficient compared to the previous implementation, which used a switch with 10+ cases.

## Phase 4: Task 3
Problem 1: Before, the minimumSalary HashTable had unneccessary implements in both the Player and AddPlayerController classes. In reality, a player's minimum salary should be the sole responsibility of the roster, who pays the player, and not the Player class (who should only have to know their own salary) or the UI (which should only be displaying the salary). By deleting the implementations in the Player and AddPlayerController classes, it fixes a few problems. 
1. By the Single Responsibility Principle, it improves cohesion by ensuring that the UI only needs to care about displaying the data,  the roster knows everything about how to 'treat' the players in the roster, and the individual players only knows information about themselves. 
2. Reduces coupling, as if there is problem with displaying the data, we can troubleshoot in the AddPlayerController class- and if theres problems with the actual values, we can troubleshoot the Roster class. 
3. It generally cleans up the code. 

Problem 2: In the startMenu method, there are two methods: One which opens a new window with a new roster, and another which opens a new window with a saved roster. Previously, both these methods had duplicated code in opening the mainMenu, with the only difference in which roster was passed through. Therefore, I added a new method, called loadWindow, which takes in a Roster parameter. This allows the previous methods to just load/create a roster, then pass this roster through to the loadWindow method. This reduced coupling, and erased the duplicated code as well.

Problem 3: In the handleAddPlayer method in MainMenuController, the UI checked if the roster size was maxed through a simple inequality. Although it is a small change, it should still be the Roster's responsibility to check if the size is maxed, so a new method with this inequality was added in the Roster class called isRosterMaxed. 

Problem 4: Similarly to Problem 3, four new boolean methods were added to the Roster class to to check if the total salary is below the salary floor, or above the salary cap, tax line, or tax apron. Although this isn't an issue at the program's current state, I figured that these methods would be useful if more features were added to this program, and in that case this would decrease duplication and increase cohesion (without the added features, it still increases cohesion). 

