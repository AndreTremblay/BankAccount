Important Notes:
You will have to change the file paths throughout the project to where ever you store the log.html file in order for it to be updated.

You will also need to do this for the newlog.html file created to replace the old log.

Be sure the places you choose as the file paths are not the same the same folder you use to run the program. 

If you are in the same folder as the newlog.html file when you try to deposit or withdraw the rename will fail. You need to be in a different directory when doing so (this does not happen when running the program via eclipse).  

To run the file:

If you have a Java SDK/IDE installed, then simply navigate to the project SRC folder and type "java Account ". The program will prompt you for input and you can follow the steps provided to navigate.

My Approach: 

My approach was test driven. As I built out each functionality I extensively tested it on many inputs and attempted to break it. After I had fully fleshed out a function and tested it extensively I would move on to the next. Then I tried to jump through the different functionalities together, to be sure they did not somehow adversely affect each other.

I chose to use regular expressions to search out the proper location on the log file to add new information. This ensured I would not break the html formatting requirements. 

In hindsight I may have used a BigInteger rather than a double for this, as parsing for correct dollar amounts would have been more straightfoward. 
