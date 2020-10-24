
# Dkatalis Assignment

-------------------
### Prerequisites:
*  Maven 3.6 or higher version
*  Java 1.8 or higher

-------------------
### Tool Installations:

* Download and Install Maven from this [link](https://maven.apache.org/download.cgi) and set M2_HOME as Environment variables with path of maven bin directory.
* Verify the installtion of the prerquisite by executing below commands:
	* `mvn --version`
	* `java -version`	

### Configuration

* Unzip the given project.
* The suite execution starts by reading the **config.properties** and identifies the execution mode and loads the default properties(such as browserName, baseURL etc).
* Sample config values below in config.properties. You can set it according to your requirements.

|  Property      |	Information             |Possible values|
|----------------|--------------------------|-----------
|browserName|Name of the browser to u run tests       | `chrome/firefox/safari/ie/internet explorer/edge`            |
|url          |Application URL       |`https://odyssey-front.herokuapp.com`            |
|executionMode          |Tests can be exeucted on local, on grid or on cloud platforms such as Saucelab. When value is set to `remote` platforms are used and more properties for remote needs to be set. When value is set to grid,more properties in grid section of the properties needs to be set for grid execution.|`local\grid\remote`|
|pageLoadTimeout          |Maximum timeout value in seconds for which selenium scripts can wait for page to load.|`30`|
|implicitWaitTimeout          |Maximum timeout value in seconds for which selenium scripts can wait for html elements to appear in DOM.|`15`|
|deviceType|Tests can be executed in desktop/mobile/tablet view. Please set this value to desktop/mobile/tablet only when the browserName is set to chrome       | `desktop/mobile/tablet`    
|deviceName|Tests can be executed in desktop/mobile/tablet view. Please set this value to valid device name supported by chrome browser such as `Pixel 2`. If the browserName is other than chrome, then device will not be taken into consideration and tests will be run in desktop view for other browsers.       | `Pixel 2/Moto G4/Pixel 2 XL/iPhone 8`  or chrome supported device 

### Auto Download of BrowserDriver binaries
* For Selenium driver management this framework uses Bonigarcia WebDriverManager which is based on `Apache 2.0` License.

* Based on the operating System and the Browser version, It auto-downloads the compatible driver binary file and places them in the `.m2` directory. Hence we don't need to explicitly download the driver binary.
#### Organzing Test Data
* Test Data files are stored in **testdata** directory in the project folder.
* If there is any particular test specific data is needed, that can be stored in the properties file with the name same as the test classname. 


### Running the tests
* This framework has thread safe WebDriver and reporting mechanism which allows user to run the tests in sequential.
* As this framework uses TestNG, Test classes that are needed to run are mentioned in the **testng.xml** file.

> **Note:** You can create your own testng xml file and group the test cases as per your interest.
> 
##### Sequential execution
* Run the `mvn test` command in the root directory of the project in the terminal.

##### Parallel execution 
* Set the parallel  and thread-count attributes in the testng.xml file as per your requirement. Sample code is below:
 `<test name  =  "Parallel Tests"  parallel  =  "classes"  thread-count  =  "2">`

### Execution Reports
* This framework uses ExtentReports for reporting. New execution report gets generated with the timestamp in the `reports\ExtentReports\TimeStamp` directory for each run.

* Screen shot are auto-captured for the failed tests and will be available in the respective autogenerated timestamp folder and the same screen shots will be linked to the extent report.
* If no test case is failed, screen shot directory will not be created inside the report folder.
* In the html report, the dashboard tab contains Environment details as well.

### Jenkins Integration(CI/CD)
* `mvn test` command can be used to execute through jenkins.
*  You can use `EnvInject` plug-in in jenkins to set the values in the **config.properties** through the jenkins job. For more details you can checkout this [link](http://www.360logica.com/blog/how-to-change-the-data-into-properties-file-using-jenkins/).

### Troubleshooting
* When executed on the new machine for the first time, depending on the internet speed available on the machine, it may take some time to download the browser driver executable which is needed to launch the driver. In this case, Please allow the script some time to download the respective browser driver executable and re-run the command.
* From the second time it is expected start executing the tests without throwing the error.