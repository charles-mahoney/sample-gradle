# Deploying an existing Application to Openshift

### Step 1
The first thing will be to build the project.  For this example we are utilizing a Gradle based springboot application

Build Application

```
gradle clean build
```

### Step 2
Now that we have compiled the code the next step is to ensure we have a build config object in OpenShift that we will be able to utilize for our resulting JAR.

For this example we will create a new build config based on the existing "java" that comes out of the back. While naming the new BuildConfig "mysampleapplication"

```
oc new-build java --name=mysampleapplication --binary=true


```

#### Optional Step
Environmental variables can be set on the BuildConfig which will then be associated with the subsequent builds. These can be updated on the BuildConfig between each build.

```
oc set env bc/mysampleapplication GIT_COMMIT=123asdf456qwe789zxv


```

### Step 3
Now we will pass the compiled JAR file to our new BuildConfig to create the container image which we will utilize to deploy our application.

```
oc start-build bc/mysampleapplication --from-file=./build/libs/example-0.0.1.jar --follow


```

### Step 4
Create a secret from file to store the DB connection information outside of the code.  (This can be done from a different project owned by someone other than the developer)

```
oc create secret generic database-connection-secret  --from-file=deploy/db.properties


```


### Step 5
Create and deploy the DeploymentConfig for our new application.  

```
oc process -f deploy/template-deploymentconfig.yml --param-file=deploy/template-deploymentconfig.properties | oc apply -f -


```

### Step 6
Now that the DeploymentConfig is created it will help manage the pods.  Now we need a way to access the pods.  First we will need a service object.  

```
oc process -f deploy/template-service.yml -p APPLICATION_NAME=mysampleapplication | oc apply -f -


```

### Step 7
Now that we have a Service object which will provide an internal loadbalancer we would like to access that application from outside of Openshift.  Therefore we will create a Route to access my new application.

```
oc process -f deploy/template-route.yml -p APPLICATION_NAME=mysampleapplication | oc apply -f -


```

### Step 8
Now that everything is deployed we should be able to render our new application from our browser.

## Optional Steps

### Image Tags
So far the deployed application is running with the "latest" imagestream. As we look to manage additional environments we will want to tag the ImageStream so that we can manage multiple versions.  Many times these follow a standard multi-level dot structure.  i.e. 1.3.52.2483

After the "new-build" command we can tag the "latest" image with the version number.

```
oc tag mysampleapplication:latest mysampleapplication:1.3.52.2483


```

### Attaching Image tag to DeploymentConfig
This tag approach can also be utilized to have the existing DeployementConfig auto deploy the new images based on tag actions.

#### Tag image with environment designation
```
oc tag mysampleapplication:latest mysampleapplication:qa


```

#### Update the DeploymentConfig to reference the new tag
```
oc process -f deploy/template-deploymentconfig-qa.yml --param-file=deploy/template-deploymentconfig.properties | oc apply -f -


```
