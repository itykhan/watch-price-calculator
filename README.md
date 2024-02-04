# watch-price-calculator
Test task: a simplified e-commerce API with Spring boot.
The API is implemented with Spring boot, Spring Data JPA, Spring MVC, data is stored in H2 in-memory database.
The application is integrated with Swagger/OpenApi 3. A set of modul and integration JUnit tests is added.

**To run application** checkout repository and run <code>./mvnw spring-boot:run</code>.
Alternatively you can build a docker image and run the application in docker container using following commands:
<pre>
<code>
# Create an executable JAR
./mvnw install
# Build an image
docker build -t itykhan/watch-price-calculator .
# Run the application in a container
docker run -p 8080:8080 itykhan/watch-price-calculator
</code>
</pre>

**Swagger endpoint**: http://localhost:8080/swagger-ui/index.html<br/>
**OpenAPI 3 endpoint**: http://localhost:8080/v3/api-docs

**API description** <br/>
The <code>/checkout</code> endpoint takes a list of IDs of watches and returns the total cost.
**Watch catalogue**
<table>
  <tr><th>Watch ID</th><th>Watch Name</th><th>Unit price</th><th>Discount</th></tr>
  <tr><td>001</td><td>Rolex</td><td>100</td><td>3 for 200</td></tr>
  <tr><td>002</td><td>Michael Kors</td><td>80</td><td>2 for 120</td></tr>
  <tr><td>003</td><td>Swatch</td><td>50</td><td></td></tr>
  <tr><td>004</td><td>Casio</td><td>30</td><td></td></tr>
</table>
<li>The first two products have a possible discount. As an example, if the user attempts to check out three or six Rolex watches then they will receive the discount price once or twice, respectively.</li>
<li>There is no limit to the number of items or combinations of watches a user can checkout.</li>
<li>There is no limit to the number of times a discount can be used.</li>
<li>Similarly, a user can checkout a single item if they wish.</li>
<br/>

**Endpoint reference**

**_Request_**
<pre>
<code>
POST http://localhost:8080/checkout 
# Headers 
Accept: application/json 
Content-Type: application/json 
# Body 
[ 
"001", 
"002", 
"001", 
"004", 
"003" 
] 
</code>
</pre>

**_Response_** 
<pre>
<code>
# Headers 
Content-Type: application/json 
# Body 
{ "price": 360 } 
</code>
</pre>

**Current implementation**

The watch catalogue is loaded into H2 database on the application startup. Calculation logic is implemented in <code>CheckoutService</code> in <code>calculateTotalPrice</code> method.
It takes the list of IDs of watches, using JPA Repository finds Watch objects by IDs and calculates total price сonsidering the described requirements. If Watch object for any ID is not 
found <code>WatchNotFoundException</code> is thrown. The API HTTP endpoint is implemented in <code>CheckoutController</code> by <code>calculatePrice</code> method.

**What to improve**
<li>Split the application into two modules: "watch" and "checkout." The first module will encompass the "watch" entity, repository, data loading into the database, while the second module will be focused on price calculation logic and checkout endpoint.</li>
<li>Simplify data loading into the database, for example, from a CSV file.</li>
<li>Check and improve code test coverage.</li>
<li>In case it's feasible, identify areas of change and modify the design to make the process of making changes simpler.</li>
<li>Improve documentation quality.</li>

