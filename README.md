# RuntimePermissionWrapper
Android Runtime Permission Wrapper

## Note
Android Runtime permission only depend on current activity, 
Because even in Non-Activity class is needed Activity instance for onRequestPermissionsResult callback to handle permission result.

## Example Screenshots
![test image size](/1.jpg?raw=true "Home Page"){:height="50%" width="50%"}
![test image size](/2.jpg?raw=true "First runtime request permission"){:height="50%" width="50%"}
![test image size](/3.jpg?raw=true "First runtime request denied"){:height="50%" width="50%"}
ShouldShowRequestPermissionRationale is trigger:
![test image size](/4.jpg?raw=true "User permission explanation"){:height="50%" width="50%"}
![test image size](/5.jpg?raw=true "Runtime request permission with NeverAskAgain option"){:height="50%" width="50%"}
![test image size](/6.jpg?raw=true "Runtime permission gradated"){:height="50%" width="50%"}
![test image size](/7.jpg?raw=true "NeverAskAgain user permission enabled"){:height="50%" width="50%"}
![test image size](/8.jpg?raw=true "App permission required dialog to navigate to setting"){:height="50%" width="50%"}
