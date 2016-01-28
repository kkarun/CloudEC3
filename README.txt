Cloud Computing EC3 Assignment
==============================
Assignment Description:
======================
You will work closely with Group-5.

Create an AWS Beanstalk application say BucketConsumer. In an index.html file you will
have a portion of the page that constantly polls the Bucket every 10 seconds for new objects and displays them.

Assignment Explained:
=====================
* Implemented with JSP, AJAX refreshing and Servlets
* Bucket name used is eciiiassignment from "US East (N. Virginia)" region
* MVC kind of pattern is used where 
  -> View is index.jsp - which constently polls the data from the servlet
  -> Controller is web.xml + servlet - which redirects the requests and process them
  -> Model - we don't a model for this assignment as the data interpretation with this assignment doesn't require any
* Demo available in http://cc-ec3-team6-test1.elasticbeanstalk.com/ [will be removed after evaluation]
* Deployment in AWS Beanstalk with Tomcat container, Code compiled in JRE7

Team Members:
=============
* Aishwarya [2013HA92056]
* Anbarasi [2013HA92080]
* Arun Kumar K [2013HA92058]
* Sairam [2013HA92042]
