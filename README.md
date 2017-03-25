# Rest-Concurrency
A sample SpringBoot project demonstrating Rest-Concurrency on single instance server

Installation:

	Prerequisites: Java(1.7 above), gradle (3.x.x). java path setup
 
	1) Checkout the code. 
	2) Go to source folder (where build.gradle file is)
	3) run "gradle bootRun"

Usage: 

	1) http://localhost:8080/hotels?cityId=Bangkok&apiKey=agoda-api-key-2

Query Params: 

	1) cityId - required
	2) apiKey - required (agoda-api-key-1, agoda-api-key-2  .... agoda-api-key-10 )
	These are listed in src/main/resources/agoda-api-key.properties
	3) sortType - optional. Supports only 2 values ASC (default), DESC

API keys:

	These are a limited pre-defined set in this project. hey are defined in src/main/resources/agoda-api-key.properties along with configured Rate-Limit for each key optionally.

Hotels:

	Hotels are also hard coded. Those are listed in src/main/resources/hoteldb.csv and loaded during deployment.


Synchronization part:

	Following class is an interceptor which monitors all the requests that comes to the service. This is the best place to validate things like API Keys. Every API key has its corresponding APIData object and a PriorityQueue which stores last N requests received using it. So, every request with a specific API key takes lock on its own PQ and verifies/updates/blocks the API key. Request of one API key will not be blocking request of another API key. 

	If rate count is not required to be 100% accurate, then we could updat counts asynchronously and block it when we see more requests than rate-count.

	src/main/java/com/agoda/exercise/interceptor/APIKeyValidationInterceptor


Exception handling:

	This is done by an ExceptionHandler (src/main/java/com/agoda/exercise/exception/ExceptionControllerAdvice.java) which converts any exception into proper HTTP response. It takes actual error responses from a configuration at  src/main/resources/errorConfig.json
