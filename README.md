# It's my second project - study-project-hibernate

I use Hibernate for connect to PostgreSQL, but it is a CRUD with 3 entites again. 
My first project used MySql so I wanted to try something new, also I use Gradle not Maven here.

# Description
There are only 3 entities: User, Post and Label.

Each user can have multiple Posts, and each Post have multiple Labels (#Tags). <br>
The metadata about all entities are stored in postgreSQL. I used Listeners for fill audit fields and graphs for optimize Hibernate requests.
In folder database.graphs i implemented my class for convenient graph management.
