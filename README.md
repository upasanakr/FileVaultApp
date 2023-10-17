# FileVaultApp
* CMPE 281 - Project 1 
* University Name: San Jose State University
* Course: Cloud Technologies
* Professor: Sajnay Garje
* Student: Upasana Kumar

Application Introduction
FileVault application resides on the AWS cloud infrastructure, offering authorized users a secure portal for managing their files remotely. Users can effortlessly upload, download, update, and delete their files from anywhere with confidence. This undertaking encompasses a 3-tier web application, making extensive use of diverse AWS resources.This application orchestrates a suite of components to deliver a robust, scalable, and cost-efficient solution for securely backing up data to Amazon S3 and CloudFront. It ensures a seamless user experience even during peak loads, thanks to the adaptive AWS auto-scaling capabilities. Additionally, it effectively manages the health of EC2 instances by harnessing the power of CloudWatch, AWS Lambda, and SNS, all seamlessly integrated with the auto-scale group.

Application link - http://filevaultonline.com/

Application Demo Video :https://vimeo.com/874970646

AWS Configurations Demo Video :  https://vimeo.com/875020802



AWS Resource Architechture

<img width="1115" alt="image" src="https://github.com/upasanakr/FileVaultApp/assets/144417727/5b036494-cddb-47b4-991b-5e9741634837">


* Instructions to run project locally

• Downlaod Software -Java SDK 17 , Intellij IDEA , MySQL Workbench , Internet Browser Local configuration 

• Clone the code from git

• Run npm install on  React project to install all the dependencies.

• Run the react app using npm start command.

• Configure AWS access key , secret key ,RDS Username,RDS Password and Bucket Name in the env variable.


*  AWS Resources Configurations:
  
  •EC2 Instance: First, I set up an EC2 instance and installed Node.js and Nginx as the web server. Then, I cloned the project from Git repositories and configured Nginx to route /api requests to the Node server 
   while directing all other requests to the React app.
   
  •AutoScaling Group: To ensure high availability and scalability, I configured an auto-scaling policy. The system can scale to a maximum of 2 instances, with a desired instance count set to 1.
  
  •Application Load Balancer: I implemented a load balancer to distribute incoming traffic evenly across instances, reducing the load on the server.
  
  •S3: I used Amazon S3 to store and manage user-uploaded files, employing the Standard S3 storage class.
  
  •S3-Standard Infrequent Access: For cost efficiency, I set up lifecycle rules for the S3 bucket, moving objects to the Standard Infrequent Access storage class after 75 days.
  
  •Transfer Acceleration for S3 Bucket: I enabled Transfer Acceleration for the S3 bucket, ensuring secure and faster data transfer.
  
  •AWS Glacier for S3 Bucket: For long-term storage, I configured files to transition to the Glacier storage class after 365 days.
  
  •RDS: A Relational Database Service (RDS) instance was created to maintain records of user-uploaded files and their associated parameters, such as descriptions, creation, and update timestamps.
  
  •CloudFront: I established a CloudFront distribution optimized for file downloads.
  
  •Route 53: I utilized Amazon Route 53 as the Domain Name Server to resolve the IP address of the application's domain.
  
  •CloudWatch: To monitor the health of the auto-scaling, EC2 instances, and Lambda functions, I set up CloudWatch.
  
  •Lambda: In response to delete events in the original S3 bucket, I created a Lambda function in Node.js. This function triggers deleting the object from  destination bucket as well.
  
  •SNS: The Simple Notification Service (SNS) was configured to notify AWS resources via email whenever an upload occurred in the S3 bucket, as specified in the topic.

* Sample screenshots

Home Page
<img width="1672" alt="image" src="https://github.com/upasanakr/FileVaultApp/assets/144417727/6b2e2b90-c307-4301-a2c3-98a101337f1e">

Registration
<img width="1672" alt="image" src="https://github.com/upasanakr/FileVaultApp/assets/144417727/21e4a5cd-cfe6-4c57-9425-350b4a9e929d">

Login
<img width="1672" alt="image" src="https://github.com/upasanakr/FileVaultApp/assets/144417727/b4d5a9e8-c9bc-4b5c-a3c2-bd1bb030ee03">


Landing Page
<img width="1672" alt="image" src="https://github.com/upasanakr/FileVaultApp/assets/144417727/2b22bb87-a7ab-4443-8809-e7271a7c5a42">


View File
<img width="1672" alt="image" src="https://github.com/upasanakr/FileVaultApp/assets/144417727/0dcae953-c6bf-4f39-b118-b51f99e472c4">


Upload File
<img width="1672" alt="image" src="https://github.com/upasanakr/FileVaultApp/assets/144417727/75e3138f-138c-4768-a058-3e45e3d83cde">


Admin View
<img width="1672" alt="image" src="https://github.com/upasanakr/FileVaultApp/assets/144417727/4393ae6c-82ef-4488-9a47-2f1999b46c06">















  





 




