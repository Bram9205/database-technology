#Conjunctive Query Containment
In this project we explore and verify the query containment 
algorithm presented in C. Chekuri, A. Rajaraman "Conjunctive Query Containment Revisted"

#How to use
In the QueryContainment/dist folder you can find the QueryContainment.jar file. If you execute this, you will see a Graphical User Interface in which a couple of parameters can be set. 

Firstly, one can set the degree of cyclicity or query width, k. Currently we only support query width 1, 2 and 3. Next, you can set the size of the cycle, n. This determines the size of the query. Lastly you can set the amount of added noise in number of added noise edges. This noise is added to the standard structure, after which we see if it is still contained in the standard query. Finally, there are two buttons which can be used:
	1. "Generate SQL" for creating tables, filling the tables and testing the containment (e.g. on http://www.sqlfiddle.com ).
	2. "Execute*" which executes the SQL to check whether the query containment is detected properly (this requires JDBC for Java and PostgreSQL installed).

Alternatively, you could compile the code in the QueryContainment/src folder yourself and then run it.

#Code
The code can be found in the QueryContainment/src folder.

#Sample data
All data is generated automatically by the application.