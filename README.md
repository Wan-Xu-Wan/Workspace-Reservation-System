# Workspace-Reservation-System
This project is to help users to reserve a workspace for daily work.

## LoginPage 

There are three users in the user database currently.
 - Username: user1
 - Password: abc123
 - Username: user2
 - Password: abc456
 - Username: user3
 - Password: abc789

If wrong username or password is entered, an error message would pop up. Hit Reset to clear the entries. The user will receive a login success message if correct username and password are entered.

![image](https://user-images.githubusercontent.com/91846138/173274635-dd12f005-9b1a-4e01-a9f6-f7a5a4f0a8c2.png)

![image](https://user-images.githubusercontent.com/91846138/173274645-36a21722-64ef-4e1f-bbb4-db506c2dc00f.png)

![image](https://user-images.githubusercontent.com/91846138/173274650-fd58df64-ebc6-43a0-a192-5e588fb0b4a6.png)

## ReservePage

ReservePage.java is the main part where the reservation functionality is implemented. After the user enter the correct username and password, the reservation window will pop up automatically. Or you can just run the application in ReservePage.java.

Reserve tab: 

After the user logs in successfully, the reservation page window will pop up. It shows user’s first name on the top. 

-	To choose a date, click on the calendar icon. If a past day is selected, the system will show an error message.  
-	After the date and time are selected, hit search button to show the available seats. The system will search the reservation database for available seats.
-	A seat in red means it is reserved during the selected timeframe and unable to select. Click on the green ones to select a seat. The selected seat will turn into yellow after click and if the user selects another seat later, the old selected seat will turn back to green.
-	Hit Reserve button to make a reservation. A detail information window will show up. Click on the confirm button to make a reservation. Here I use synchronize method to prevent users from reserving the same seat for the same period. If this situation happens, the user who were slightly slower would get an error message. He/she would have to reselect a seat. If there is no conflict, then the user will see a reserved success message. The reservation record will be inserted into the reservation database.

![image](https://user-images.githubusercontent.com/91846138/173274776-98d8e734-edbb-4aca-b8c8-842153b1c1af.png)


Click << and >> button to select the date.

![image](https://user-images.githubusercontent.com/91846138/173274803-fdeac6c3-1693-461e-aaee-ead35ba8a8bd.png)

If a past date is chosen, an error message will pop up.

![image](https://user-images.githubusercontent.com/91846138/173274868-4ead4ff6-9d55-4b29-b5a2-62ee0ece01f7.png)

Available seats are in green. The seat id starts from 0.

![image](https://user-images.githubusercontent.com/91846138/173274893-bfcc85ef-42f0-408e-b0ff-db8011ce3258.png)

Choose a green seat. If the user click on the reserve button before a seat is selected, he/she will receive an error message too.

![image](https://user-images.githubusercontent.com/91846138/173274934-2af26f20-fd92-4fdf-a07e-414c57683742.png)

Select a seat and hit the reserve button

![image](https://user-images.githubusercontent.com/91846138/173274965-0f314f1e-a84f-4392-950c-a33713afa061.png)

Click on the confirm button

![image](https://user-images.githubusercontent.com/91846138/173274992-60fe1f03-4419-413e-b804-1c629bff4979.png)

Use synchronized instance method to prevent the shared database being modified by different users at the same time.

![image](https://user-images.githubusercontent.com/91846138/173275022-39f067fb-133e-470a-ac3a-861341684b35.png)

![image](https://user-images.githubusercontent.com/91846138/173275036-50395731-5662-4c8b-9ecf-72e85a86a9fa.png)

## ReservePage

My Reservation tab:

-	This tab shows the next 5 upcoming schedules in a sorted order. Any past reservation will not be here.  
-	To cancel a reservation, click on the cancel button and click yes. The reservation will be deleted in the reservation database
-	The delete method is also synchronized to avoid the database being corrupted if it is accessed simultaneously by multiple users.
-	To refresh, go to Reserve tab and then come back, My Reservation tab will be updated automatically. 

![image](https://user-images.githubusercontent.com/91846138/173275111-04a9b113-8b42-434e-9ccb-334573decc11.png)

![image](https://user-images.githubusercontent.com/91846138/173275123-5c5cf9cc-51dd-4fbd-b3e0-7d29be4e52ba.png)

![image](https://user-images.githubusercontent.com/91846138/173275147-bae8215a-3bbc-400c-9ac2-1a69e1c589ef.png)


